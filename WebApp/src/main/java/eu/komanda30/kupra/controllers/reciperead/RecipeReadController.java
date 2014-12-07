package eu.komanda30.kupra.controllers.reciperead;

import eu.komanda30.kupra.entity.Comment;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Recipes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/recipes")
public class RecipeReadController {

    @Resource
    private Recipes recipes;

    @Resource
    private KupraUsers kupraUsers;

    private static final Logger LOG = LoggerFactory.getLogger(RecipeReadController.class);

    @RequestMapping(value = "/read/{recipeId}", method = RequestMethod.GET)
    public String readRecipe(final RecipeReadForm form, final AddCommentForm addCommentForm,
                           @PathVariable Integer recipeId){
        Recipe recipe = recipes.findOne(recipeId);
        form.setName(recipe.getName());
        form.setServings(recipe.getServings());
        form.setCookingTime(recipe.getCookingTime());
        form.setDescription(recipe.getDescription());
        form.setPublicAccess(recipe.isPublicAccess());
        form.setProcessDescription(recipe.getProcessDescription());

        return "recipeRead";
    }


    @Transactional
    @RequestMapping(value = "/create_comment/{recipeId}", method = RequestMethod.POST)
    public String submit(@Valid final AddCommentForm addCommentForm, final RecipeReadForm form,
                         @PathVariable Integer recipeId) {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        recipes.findOne(recipeId).addRecipeComments(new Comment(addCommentForm.getComment(), kupraUser));




        return "recipeRead";
    }
}
