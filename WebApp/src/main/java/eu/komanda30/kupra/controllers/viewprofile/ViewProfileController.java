package eu.komanda30.kupra.controllers.viewprofile;

import eu.komanda30.kupra.controllers.editprofile.ProfilePhotoList;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.entity.UserProfile;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Recipes;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by Rytis on 2014-12-02.
 */
@Controller
@RequestMapping("/user")
public class ViewProfileController {
    public static final String MAIN_PHOTO_REPO_ID = "mainPhoto";

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private Friendships friendships;

    @Resource
    private Recipes recipes;

    @Transactional
    @RequestMapping(value = " /{userId}", method = RequestMethod.GET)
    public String showRecipes(@PathVariable String userId, final ProfileInfo profileInfo, final ProfilePhotoList profilePhotoList) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser user = kupraUsers.findOne(auth.getName());
        final KupraUser targetUser = kupraUsers.findOne(userId);
        final UserProfile targetUserProfile = targetUser.getUserProfile();
        boolean showEverything = friendships.isFriends(user, targetUser) || userId.contentEquals(auth.getName());
        Iterable<Recipe> allRecipes;

        profileInfo.setName(targetUserProfile.getName());
        profileInfo.setSurname(targetUserProfile.getSurname());
        profileInfo.setEmail(targetUserProfile.getEmail());
        profileInfo.setDescription(targetUserProfile.getDescription());
        profileInfo.setFriend(friendships.isFriends(user, targetUser));
        profileInfo.setPhoto(targetUserProfile.getMainPhoto());

        if (showEverything) {
            allRecipes = recipes.findByUser(targetUser, new PageRequest(0, 100)); //TODO: pataisyti
        } else {
            allRecipes = recipes.findByUserPublic(targetUser, new PageRequest(0, 100)); //TODO: pataisyti
        }

        for (Recipe r : allRecipes) {
            RecipePreview recipePreview = new RecipePreview();
            recipePreview.setName(r.getName());
            recipePreview.setDescription(r.getDescription());
            recipePreview.setPublicAccess(r.isPublicAccess());
            profileInfo.addRecipe(recipePreview);
        }

        return "view-profile";
    }

}
