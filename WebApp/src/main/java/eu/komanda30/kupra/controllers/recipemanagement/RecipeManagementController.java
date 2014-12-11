package eu.komanda30.kupra.controllers.recipemanagement;

import com.google.common.collect.ImmutableList;
import eu.komanda30.kupra.UploadUtils;
import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.entity.RecipeProduct;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Recipes;
import eu.komanda30.kupra.uploads.TmpUploadedFileManager;
import eu.komanda30.kupra.uploads.UploadedImageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@SessionAttributes("unitList")
@RequestMapping("/recipes")
public class RecipeManagementController {
    private final static Logger LOG = LoggerFactory.getLogger(RecipeManagementController.class);

    @Resource
    private RecipeManagementFormValidator recipeManagementFormValidator;

    @Resource
    private Recipes recipes;

    @Resource
    private Products products;

    @Value("${recipe.img.dir}")
    private File recipeImgDir;

    @Value("${recipe.img.context}")
    private String recipeImgContext;

    @Resource
    private TmpUploadedFileManager tmpUploadedFileManager;

    @Resource
    private KupraUsers kupraUsers;

    @InitBinder("recipeManagementForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(recipeManagementFormValidator);
    }

    @ModelAttribute("productsList")
    public Iterable<Product> getProductList() { return products.findAll();}

    @RequestMapping(value="/create", method = RequestMethod.GET)
    public String showNewRecipeForm(final RecipeManagementForm form, Model model) {
        form.setTmpId(UUID.randomUUID().toString());

        model.addAttribute("unitList", new ArrayList<RecipeProductListUnit>());
        return "recipe_form";
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    @Transactional
    public String createRecipe(
                @Valid final RecipeManagementForm recipeForm,
                final BindingResult bindingResult,
                @ModelAttribute("unitList") List<RecipeProductListUnit> productListUnits,
                @ModelAttribute RecipeImageList imagesList,
                SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()){
            imagesList.addAll(getListOfTmpImages(recipeForm.getTmpId()));
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
        recipe.setAuthor(kupraUsers.findByUsername(auth.getName()));

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        dateFormat.format(date);

        recipe.setRecipeDate(date);

        final String fileGroupId = recipeForm.getTmpId();
        for (String fileId : ImmutableList.copyOf(tmpUploadedFileManager.getFileIds(fileGroupId))) {
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

        List<RecipeProduct> productList = new ArrayList<RecipeProduct>();

        for (RecipeProductListUnit productUnit : productListUnits){
            RecipeProduct recipeProduct = new RecipeProduct();
            recipeProduct.setProduct(products.findOne(productUnit.getProductId()));
            recipeProduct.setQuantity(productUnit.getQuantity());
            recipeProduct.setRecipe(recipe);
            productList.add(recipeProduct);
        }

        recipe.setRecipeProductList(productList);
        recipes.save(recipe);
        sessionStatus.setComplete();
        return "redirect:/recipes";
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

    @RequestMapping(value="addProduct", method = RequestMethod.POST)
    public String addProduct(@RequestParam("quantity") Double quantity,
                             @RequestParam("product_id") Integer productId,
                             @ModelAttribute("unitList") List<RecipeProductListUnit> productListUnits){
        final Product product = products.findOne(productId);

        final Optional<RecipeProductListUnit> item = productListUnits.stream()
                .filter(input -> input.getProductId() == productId)
                .findFirst();

        if (item.isPresent()){
            item.get().increaseQuantity(quantity);
        }else {
            final RecipeProductListUnit unit = new RecipeProductListUnit();
            unit.setProductId(product.getId());
            unit.setProductName(product.getName());
            unit.setQuantity(quantity);
            productListUnits.add(unit);
        }



        return "recipe_form :: recipeProduct";
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

    @RequestMapping(value="deleteProduct", method = RequestMethod.POST)
    public String deleteProduct(@RequestParam("product_id") Integer productId,
                                @ModelAttribute("unitList") List<RecipeProductListUnit> productListUnits) {

        final Optional<RecipeProductListUnit> item = productListUnits.stream()
                .filter(input -> input.getProductId() == productId)
                .findFirst();

        productListUnits.remove(item.get());

        return "recipe_form :: recipeProduct";
    }
}
