package eu.komanda30.kupra.controllers.reciperead;

import eu.komanda30.kupra.entity.*;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Recipes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeReadController {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeReadController.class);
    @Resource
    private Recipes recipes;
    @Resource
    private KupraUsers kupraUsers;
    @Resource
    private Friendships friendships;

    @Transactional
    @RequestMapping(value = "/read/{recipeId}", method = RequestMethod.GET)
    public String readRecipe(final RecipeReadForm form, final AddCommentForm addCommentForm,
                             @PathVariable Integer recipeId) {
        Recipe recipe = recipes.findOne(recipeId);
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        if (friendships.isFriends(kupraUser, recipe.getAuthor()) || recipe.getAuthor()
                .equals(kupraUser)) {

            form.setRecipeAuthor(recipe.getAuthor().getProfile().getName() + " "
                    + recipe.getAuthor().getProfile().getSurname());

        } else {
            form.setRecipeAuthor(recipe.getAuthor().getUserId());
        }

        form.setRecipeAuthorId(recipe.getAuthor().getUserId());

        form.setName(recipe.getName());
        form.setServings(recipe.getServings());
        form.setCookingTime(recipe.getCookingTime());
        form.setDescription(recipe.getDescription());
        form.setPublicAccess(recipe.isPublicAccess());
        form.setProcessDescription(recipe.getProcessDescription());
        form.setDate(recipe.getRecipeDate());

        final List<Comment> commentList = recipe.getRecipeComments();
        List<RecipeProduct> productList = recipe.getRecipeProductList();
        List<RecipeImage> imageList = recipe.getRecipeImages();

        for (RecipeImage recipeImage: imageList) {
            form.addImage(recipeImage.getImageUrl());
        }

        for (RecipeProduct recipeProduct: productList){
            Product product = recipeProduct.getProduct();
            RecipeProductUnit recipeProductUnit = new RecipeProductUnit();
            recipeProductUnit.setName(product.getName());
            recipeProductUnit.setQuantity(recipeProduct.getQuantity());
            recipeProductUnit.setUnit(product.getUnit().getAbbreviation());
            form.addRecipeProducts(recipeProductUnit);
        }

        for (Comment comment : commentList) {
            final CommentUnit commentUnit = new CommentUnit();
            commentUnit.setComment(comment.getComment());
            commentUnit.setDate(comment.getDate());

            if (friendships.isFriends(kupraUser, comment.getAuthor())
                    || comment.getAuthor().equals(kupraUser)) {
                commentUnit.setAuthor(comment.getAuthor().getProfile().getFullName() + ": ");
                commentUnit.setImage(comment.getAuthor().getProfile().getMainPhoto().orElse(null));
            } else {
                commentUnit.setAuthor(comment.getAuthor().getUserId());
            }

            commentUnit.setCommentAuthorId(comment.getAuthor().getUserId());
            form.addComment(commentUnit);
        }

        return "recipeRead";
    }


    @Transactional
    @RequestMapping(value = "/create_comment/{recipeId}", method = RequestMethod.POST)
    public String submit(@Valid final AddCommentForm addCommentForm,
                         final BindingResult bindingResult,
                         final RecipeReadForm form,
                         @PathVariable Integer recipeId) {

        if (bindingResult.hasErrors()) {
            return "recipeRead";
        }

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        dateFormat.format(date);

        recipes.findOne(recipeId)
                .addRecipeComments(new Comment(addCommentForm.getComment(), kupraUser, date));


        return "redirect:/recipes/read/{recipeId}";
    }

    @Transactional
    @RequestMapping(value = "/calculateProducts/{recipeId}", method = RequestMethod.GET)
    public String calculateLackOfProducts(@PathVariable("recipeId") Integer recipeId, LackOfProductsForm form) {
        Recipe recipe = recipes.findOne(recipeId);

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        ArrayList<RecipeProduct> productsNeeded = kupraUser.getLackingProducts(recipe);
        form.setRecipeName(recipe.getName());

        for(RecipeProduct productNeeded : productsNeeded){

            LackOfProductsItem item = new LackOfProductsItem();

            item.setName(productNeeded.getProduct().getName());
            item.setAmount(productNeeded.getQuantity());
            item.setUnit(productNeeded.getProduct().getUnit().getAbbreviation());


            form.addLackOfProductsItem(item);
        }

        return "popups/recipeLackOfProducts :: lackOfProducts";
    }
}
