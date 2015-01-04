package eu.komanda30.kupra.controllers.viewprofile;

import eu.komanda30.kupra.controllers.editprofile.ProfilePhotoList;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.entity.UserProfile;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Recipes;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Controller
@RequestMapping("/user")
public class ViewProfileController {
    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private Friendships friendships;

    @Resource
    private Recipes recipes;

    @Transactional
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public String showRecipes(@PathVariable String userId, final ProfileInfo profileInfo, final ProfilePhotoList profilePhotoList) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser user = kupraUsers.findOne(auth.getName());
        final KupraUser targetUser = kupraUsers.findOne(userId);
        if (targetUser == null) {
            throw new ResourceNotFoundException();
        }
        final UserProfile profile = targetUser.getProfile();
        boolean showEverything = friendships.isFriends(user, targetUser) || userId.contentEquals(auth.getName());
        Iterable<Recipe> allRecipes;

        profileInfo.setFriend(friendships.isFriends(user, targetUser));
        profileInfo.setRequestSent(friendships.isRequestSent(user, targetUser));
        profileInfo.setRequestReceived(friendships.isRequestSent(targetUser, user));
        profileInfo.setPersonal(user.equals(targetUser));

        if (showEverything) {
            allRecipes = recipes.findByUser(targetUser, new PageRequest(0, 100)); //TODO: pataisyti
            profileInfo.setDisplayName(profile.getFullName() + " (" + targetUser.getUserId() + ")");
            profileInfo.setEmail(profile.getEmail());
            profileInfo.setDescription(profile.getDescription());
            profileInfo.setPhoto(profile.getMainPhoto().orElse(null));
        } else {
            allRecipes = recipes.findByUserPublic(targetUser, new PageRequest(0, 100)); //TODO: pataisyti
            profileInfo.setDisplayName(targetUser.getUserId());
        }

        for (Recipe r : allRecipes) {
            RecipePreview recipePreview = new RecipePreview();
            recipePreview.setName(r.getName());
            recipePreview.setDescription(r.getDescription());
            recipePreview.setPublicAccess(r.isPublicAccess());
            recipePreview.setAccessible(user == r.getAuthor());
            recipePreview.setRecipeId(r.getRecipeId());

            if (!r.getRecipeImages().isEmpty()) {
                recipePreview.setRecipeImage(r.getRecipeImages().get(0));
            }

            profileInfo.addRecipe(recipePreview);
        }

        return "view-profile";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {}
}
