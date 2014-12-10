package eu.komanda30.kupra.controllers.editprofile;

import eu.komanda30.kupra.UploadUtils;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UserProfile;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.uploads.TmpUploadedFileManager;
import eu.komanda30.kupra.uploads.UploadedImageInfo;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

/**
 * Created by Lukas on 2014.10.23.
 */
@RequestMapping("/profile")
@Controller
public class EditProfileController {
    public static final String MAIN_PHOTO_REPO_ID = "mainPhoto";
    private final static Logger LOG = LoggerFactory.getLogger(EditProfileController.class);
    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private EditProfileValidator editProfileValidator;

    @Value("${profile.img.dir}")
    private File profileImgDir;

    @Value("${profile.img.context}")
    private String profileImgContext;

    @Resource
    private TmpUploadedFileManager tmpUploadedFileManager;

    @Resource
    private PasswordEncoder passwordEncoder;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(editProfileValidator);
    }

    @Transactional
    @RequestMapping(value="edit", method = RequestMethod.GET)
    public String showForm(final EditProfileForm form,
                           final EditPasswordForm passForm,
                           final ProfilePhotoList profilePhotoList) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser user = kupraUsers.findOne(auth.getName());
        final UserProfile profile = user.getProfile();

        form.setTmpId(UUID.randomUUID().toString());
        form.setName(profile.getName());
        form.setSurname(profile.getSurname());
        form.setEmail(profile.getEmail());
        form.setDescription(profile.getDescription());

        profilePhotoList.setMainPhoto(
                profile.getMainPhoto().map(p ->
                        new UploadedImageInfo(
                                MAIN_PHOTO_REPO_ID,
                                p.getImageUrl(),
                                p.getThumbUrl()))
                        .orElse(null));

        return "editProfile";
    }

    @Transactional
    @RequestMapping(value="edit", method = RequestMethod.POST)
    public String submit(@Valid final EditProfileForm form,
                         final BindingResult bindingResult,
                         @Valid final EditPasswordForm passForm,
                         final BindingResult passErrors) {
        if (bindingResult.hasErrors()) {
            return "editProfile";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!passForm.getPassword().isEmpty() && passErrors.hasErrors()) {
            return "editProfile";
        }


        final KupraUser user = kupraUsers.findByUsername(auth.getName());
        if (!passForm.getPassword().isEmpty()) {
            user.setPassword(passForm.getPassword(), passwordEncoder);
        }

        final UserProfile profile = user.getProfile();
        profile.setName(form.getName());
        profile.setSurname(form.getSurname());
        profile.setEmail(form.getEmail());
        profile.setDescription(form.getDescription());

        final File uploadedFile = tmpUploadedFileManager.getFile(form.getTmpId(),
                MAIN_PHOTO_REPO_ID);
        final File uploadedThumb = tmpUploadedFileManager.getThumbFile(form.getTmpId(),
                MAIN_PHOTO_REPO_ID);
        if (uploadedFile != null && uploadedThumb != null) {
            final File imgCopy = copyToProfileDir(uploadedFile);
            final File thumbCopy = copyToProfileDir(uploadedThumb);

            final String imgUrl = UploadUtils.resolveVirtualPath(
                    imgCopy, profileImgDir, profileImgContext);
            final String thumbUrl = UploadUtils.resolveVirtualPath(
                    thumbCopy, profileImgDir, profileImgContext);

            profile.setMainPhoto(imgUrl, thumbUrl);
        }

        return "redirect:/";
    }

    private File copyToProfileDir(File imgFile) {
        if (!profileImgDir.exists() && !profileImgDir.mkdirs()) {
            LOG.error("Failed to create recipe image upload directory: {}",
                    profileImgDir.getAbsolutePath());
        }
        final File outFile = new File(profileImgDir, imgFile.getName());
        try {
            FileCopyUtils.copy(imgFile, outFile);
            return outFile;
        } catch (IOException e) {
            LOG.error("Failed to copy image to recipe image directory!", e);
            return null;
        }
    }

    @RequestMapping(value="uploadPhoto", method = RequestMethod.POST)
    public String uploadPhoto(
                        @RequestParam("tmpId") String formTmpId,
                        @RequestParam("profilePhoto") MultipartFile file,
                        final ProfilePhotoList profilePhotoList) {
        if (file != null) {
            tmpUploadedFileManager.deleteImageWithThumb(formTmpId, MAIN_PHOTO_REPO_ID);

            try {
                tmpUploadedFileManager.addImageWithThumb(
                        formTmpId, MAIN_PHOTO_REPO_ID,
                        file.getInputStream(), file.getOriginalFilename());
            } catch (IOException e) {
                LOG.error("Failed to read uploaded file!", e);
            }
        }

        profilePhotoList.setMainPhoto(new UploadedImageInfo(
                MAIN_PHOTO_REPO_ID,
                tmpUploadedFileManager.getVirtualPath(formTmpId, MAIN_PHOTO_REPO_ID),
                tmpUploadedFileManager.getVirtualThumbPath(formTmpId, MAIN_PHOTO_REPO_ID)));

        return "editProfile :: profilePhoto";
    }

}
