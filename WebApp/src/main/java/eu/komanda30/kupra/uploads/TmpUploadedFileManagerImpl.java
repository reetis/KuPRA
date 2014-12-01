package eu.komanda30.kupra.uploads;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PreDestroy;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;

@Component
@Scope(proxyMode = ScopedProxyMode.INTERFACES, value = WebApplicationContext.SCOPE_SESSION)
public class TmpUploadedFileManagerImpl implements TmpUploadedFileManager {
    private final static Logger LOG = LoggerFactory.getLogger(TmpUploadedFileManagerImpl.class);
    public static final int THUMB_MAX_WIDTH = 200;
    public static final int THUMB_MAX_HEIGHT = 200;

    @Value("${tmp.file.dir}")
    private transient File tmpFileDir;

    @Value("${tmp.file.context}")
    private transient String tmpFileContext;

    private final Map<String, Map<String, File>> fileMap = new HashMap<>();

    private File prepareFileForNewUpload(String groupId, String fileId, String fileName) {
        Map<String, File> groupFileMap = fileMap.get(groupId);
        if (groupFileMap == null) {
            groupFileMap = new HashMap<>();
            fileMap.put(groupId, groupFileMap);
        }

        final File file = groupFileMap.get(fileId);
        if (file != null && file.exists() && !file.delete()) {
            LOG.error("Failed to delete file from session");
            return null;
        }

        if (!tmpFileDir.exists() && !tmpFileDir.mkdirs()) {
            LOG.error("Failed to create temp upload file directory!");
            return null;
        }

        final String uniquePrefix = UUID.randomUUID().toString();
        final File copyFile = new File(tmpFileDir, uniquePrefix+ "_" + fileName);
        //copyFile.deleteOnExit();
        groupFileMap.put(fileId, copyFile);
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
        final Map<String, File> groupMap = fileMap.get(groupId);
        return groupMap != null ? ImmutableList.copyOf(groupMap.keySet()) : ImmutableList.of();
    }

    @Override
    public File getFile(String groupId, String fileId) {
        final Map<String, File> groupMap = fileMap.get(groupId);
        if (groupMap == null) {
            return null;
        }

        final File file = groupMap.get(fileId);
        if (file == null) {
            return null;
        }

        return file;
    }

    @Override
    public String getVirtualPath(String groupId, String fileId) {
        final Map<String, File> groupMap = fileMap.get(groupId);
        if (groupMap == null) {
            return null;
        }

        final File file = groupMap.get(fileId);
        if (file == null) {
            return null;
        }

        final File tmpDir = tmpFileDir;
        if (!file.getAbsolutePath().startsWith(tmpDir.getAbsolutePath())) {
            LOG.error("Invalid file saved. Dir does not start with {}", tmpDir);
            return null;
        }

        return tmpFileContext + file.getAbsolutePath().substring(tmpDir.getAbsolutePath().length()+1);
    }

    @Override
    public String getVirtualThumbPath(String groupId, String fileId) {
        return getVirtualPath(groupId+":thumb", fileId);
    }

    @PreDestroy
    public void preDestroy() {
        //Delete all temp files
        for (Map<String, File> files : fileMap.values()) {
            for (File f : files.values()) {
                if (f.exists() && !f.delete()) {
                    LOG.error("Failed to delete temporary uploaded file: {}", f);
                }
            }
        }
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
        Map<String, File> groupFileMap = fileMap.get(groupId);
        if (groupFileMap != null) {
            final File file = groupFileMap.get(fileId);
            if (file != null && file.exists() && !file.delete()) {
                LOG.error("Failed to delete file from session: " + file);
            }
            groupFileMap.remove(fileId);
            if (groupFileMap.isEmpty()) {
                fileMap.remove(groupId);
            }
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
