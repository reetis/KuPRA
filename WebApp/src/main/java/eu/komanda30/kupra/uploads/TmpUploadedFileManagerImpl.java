package eu.komanda30.kupra.uploads;

import eu.komanda30.kupra.UploadUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.io.ByteStreams;

@Component
public class TmpUploadedFileManagerImpl implements TmpUploadedFileManager {
    public static final int THUMB_MAX_WIDTH = 200;
    public static final int THUMB_MAX_HEIGHT = 200;
    private final static Logger LOG = LoggerFactory.getLogger(TmpUploadedFileManagerImpl.class);

    @Resource
    private TmpUploadedFileData fileData;

    @Value("${tmp.file.dir}")
    private File tmpFileDir;

    @Value("${tmp.file.context}")
    private String tmpFileContext;

    @PreDestroy
    public void preDestroy() {
        //Delete all temp files
        fileData.allFiles().parallelStream()
                .filter(it -> (it.exists() && !it.delete()))
                .forEach(it -> LOG.error("Failed to delete temporary uploaded file: {}", it));
    }

    private File prepareFileForNewUpload(String groupId, String fileId, String fileName) {
        final File oldFile = fileData.get(groupId, fileId);
        if (oldFile != null && oldFile.exists() && !oldFile.delete()) {
            LOG.error("Failed to delete file from session");
            return null;
        }

        if (!tmpFileDir.exists() && !tmpFileDir.mkdirs()) {
            LOG.error("Failed to create temp upload file directory!");
            return null;
        }

        final String uniquePrefix = UUID.randomUUID().toString();
        final File copyFile = new File(tmpFileDir, uniquePrefix+ "_" + fileName);
        fileData.put(groupId, fileId, copyFile);
        return copyFile;
    }

    @Override
    public File addTempFile(String groupId, String fileId, InputStream stream, String fileName) {
        final File copyFile = prepareFileForNewUpload(groupId, fileId, fileName);
        if (copyFile == null) {
            return null;
        }

        try (OutputStream outputStream = new FileOutputStream(copyFile)) {
            ByteStreams.copy(stream, outputStream);
            return copyFile;
        } catch (IOException e) {
            LOG.error("Failed to copy uploaded file to temporary directory!");
            return null;
        }
    }

    @Override
    public Iterable<String> getFileIds(String groupId) {
        return fileData.allFileIds(groupId);
    }

    @Override
    public File getFile(String groupId, String fileId) {
        return fileData.get(groupId, fileId);
    }

    @Override
    public String getVirtualPath(String groupId, String fileId) {
        return UploadUtils.resolveVirtualPath(
                fileData.get(groupId, fileId), tmpFileDir, tmpFileContext);
    }

    @Override
    public String getVirtualThumbPath(String groupId, String fileId) {
        return getVirtualPath(groupId+":thumb", fileId);
    }


    @Override
    public boolean addImageWithThumb(String groupId, String fileId, InputStream stream,
                                     String fileName) {
        final File file = this.addTempFile(groupId, fileId, stream, fileName);
        if (file == null) {
            return false;
        }

        final File thumbFile = prepareFileForNewUpload(groupId+":thumb", fileId, "thumb."+fileName);
        if (thumbFile == null) {
            return false;
        }

        try {
            Thumbnails.of(file)
                    .size(THUMB_MAX_WIDTH, THUMB_MAX_HEIGHT)
                    .toFile(thumbFile);
            return true;
        } catch (IOException e) {
            LOG.error("Failed to create thumbnail of uploaded file", e);
            return false;
        }
    }

    @Override
    public void deleteFile(String groupId, String fileId) {
        final File file = fileData.get(groupId, fileId);
        if (file != null) {
            if (file.exists() && !file.delete()) {
                LOG.error("Failed to delete file from session: " + file);
            }

            fileData.remove(groupId, fileId);
        }
    }

    @Override
    public void deleteImageWithThumb(String groupId, String imgId) {
        deleteFile(groupId, imgId);
        deleteFile(groupId+":thumb", imgId);
    }

    @Override
    public File getThumbFile(String tmpId, String fileId) {
        return getFile(tmpId+":thumb", fileId);
    }
}
