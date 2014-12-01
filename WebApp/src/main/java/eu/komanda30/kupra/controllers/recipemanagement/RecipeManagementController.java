package eu.komanda30.kupra.controllers.recipemanagement;

import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.repositories.Recipes;
import eu.komanda30.kupra.services.RecipeLibrary;
import eu.komanda30.kupra.services.TmpUploadedFileManager;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/recipe")
public class RecipeManagementController {
    private final static Logger LOG = LoggerFactory.getLogger(RecipeManagementController.class);

    @Resource
    private RecipeManagementFormValidator recipeManagementFormValidator;

    @Resource
    private RecipeLibrary recipeLibrary;

    @Resource
    private Recipes recipes;

    @Value("${recipe.img.dir}")
    private String recipeImgDir;

    @Resource
    private TmpUploadedFileManager tmpUploadedFileManager;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(recipeManagementFormValidator);
    }

    @RequestMapping(value="/create", method = RequestMethod.GET)
    public String showNewRecipeForm(final RecipeManagementForm form,
                                    @ModelAttribute("images") RecipeImagesForm imagesForm) {
        form.setTmpId(UUID.randomUUID().toString());
        return "recipe_form";
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public String createRecipe(@Valid final RecipeManagementForm recipeManagementForm,
                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "recipe_form";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        recipeLibrary.addRecipe(recipeManagementForm, UserId.forUsername(auth.getName()));

        return "recipe_form";
    }

    @RequestMapping(value="uploadPhotos", method = RequestMethod.POST)
    public String uploadPhotos(@RequestParam("tmpId") String tmpId,
                               @RequestParam("files") Part[] files,
                               @ModelAttribute("images") RecipeImagesForm imagesForm) {
        if (files == null || files.length <= 0) {
            return "Unable to upload. File is empty.";
        }

        for (Part file : files) {
            final String fileId = UUID.randomUUID().toString();

            tmpUploadedFileManager.addTempFile(tmpId, fileId, file);

            //TODO: Resize here somehow
            tmpUploadedFileManager.addTempFile(tmpId+":thumb",fileId, file);
        }

        for (String fileId : tmpUploadedFileManager.getFileIds(tmpId)) {
            final String virtualPath = tmpUploadedFileManager.getVirtualPath(tmpId, fileId);
            final String virtualThumbPath = tmpUploadedFileManager.getVirtualPath(tmpId+":thumb", fileId);

            imagesForm.addImage(virtualPath, virtualThumbPath);
        }

        return "recipe_form :: image_list";
    }
}
