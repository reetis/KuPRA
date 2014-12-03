package eu.komanda30.kupra.controllers.recipemanagement;

import eu.komanda30.kupra.UploadUtils;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.repositories.Recipes;
import eu.komanda30.kupra.uploads.TmpUploadedFileManager;
import eu.komanda30.kupra.uploads.UploadedImageInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/recipe")
public class RecipeManagementController {
    private final static Logger LOG = LoggerFactory.getLogger(RecipeManagementController.class);

    @Resource
    private RecipeManagementFormValidator recipeManagementFormValidator;

    @Resource
    private Recipes recipes;

    @Value("${recipe.img.dir}")
    private File recipeImgDir;

    @Value("${recipe.img.context}")
    private String recipeImgContext;

    @Resource
    private TmpUploadedFileManager tmpUploadedFileManager;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(recipeManagementFormValidator);
    }

    @RequestMapping(value="/create", method = RequestMethod.GET)
    public String showNewRecipeForm(final RecipeManagementForm form) {
        form.setTmpId(UUID.randomUUID().toString());
        return "recipe_form";
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    @Transactional
    public String createRecipe(
                @Valid final RecipeManagementForm recipeForm,
                final BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "recipe_form";
        }

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Recipe recipe = new Recipe();
        recipe.setName(recipeForm.getName());
        recipe.setDescription(recipeForm.getDescription());
        recipe.setProcessDescription(recipeForm.getProcessDescription());
        recipe.setCookingTime(recipeForm.getCookingTime());
        recipe.setPublicAccess(recipeForm.isPublicAccess());
        recipe.setServings(recipeForm.getServings());
        recipe.setAuthor(auth.getName());

        final String fileGroupId = recipeForm.getTmpId();
        for (String fileId : tmpUploadedFileManager.getFileIds(fileGroupId)) {
            final File imgFile = tmpUploadedFileManager.getFile(fileGroupId, fileId);
            final File thumbFile = tmpUploadedFileManager.getThumbFile(fileGroupId, fileId);

            final File imgCopy = copyToRecipeDir(imgFile);
            final File thumbCopy = copyToRecipeDir(thumbFile);

            final String imgUrl = UploadUtils.resolveVirtualPath(
                    imgCopy, recipeImgDir, recipeImgContext);
            final String thumbUrl = UploadUtils.resolveVirtualPath(
                    thumbCopy, recipeImgDir, recipeImgContext);

            recipe.addImage(imgUrl, thumbUrl);

            tmpUploadedFileManager.deleteImageWithThumb(fileGroupId, fileId);
        }
        recipes.save(recipe);

        return "redirect:/recipes";
    }

    private File copyToRecipeDir(File imgFile) {
        if (!recipeImgDir.exists() && !recipeImgDir.mkdirs()) {
            LOG.error("Failed to create recipe image upload directory: {}", recipeImgDir.getAbsolutePath());
        }
        final File outFile = new File(recipeImgDir, imgFile.getName());
        try {
            FileCopyUtils.copy(imgFile, outFile);
            return outFile;
        } catch (IOException e) {
            LOG.error("Failed to copy image to recipe image directory!", e);
            return null;
        }
    }

    @RequestMapping(value="uploadPhotos", method = RequestMethod.POST)
    public String uploadPhotos(@RequestParam("tmpId") String formTmpId,
                               @RequestParam("images") MultipartFile[] files,
                               RecipeImageList imagesList) {
        for (MultipartFile file : files) {
            final String fileId = UUID.randomUUID().toString();
            try {
                tmpUploadedFileManager.addImageWithThumb(
                        formTmpId, fileId, file.getInputStream(),
                        file.getOriginalFilename());
            } catch (IOException e) {
                LOG.error("Failed to read uploaded file!", e);
            }
        }

        imagesList.addAll(getListOfTmpImages(formTmpId));
        return "recipe_form :: image_list";
    }

    @RequestMapping(value="deletePhoto", method = RequestMethod.POST)
    public String deletePhoto(@RequestParam("tmpId") String formTmpId,
                              @RequestParam("imgId") String imgId,
                              RecipeImageList imagesList) {
        tmpUploadedFileManager.deleteImageWithThumb(formTmpId, imgId);

        imagesList.addAll(getListOfTmpImages(formTmpId));
        return "recipe_form :: image_list";
    }

    private List<UploadedImageInfo> getListOfTmpImages(String formTmpId) {
        final List<UploadedImageInfo> list = new ArrayList<>();
        for (String fileId : tmpUploadedFileManager.getFileIds(formTmpId)) {
            list.add(new UploadedImageInfo(
                    fileId,
                    tmpUploadedFileManager.getVirtualPath(formTmpId, fileId),
                    tmpUploadedFileManager.getVirtualThumbPath(formTmpId, fileId)));
        }
        return list;
    }
}
