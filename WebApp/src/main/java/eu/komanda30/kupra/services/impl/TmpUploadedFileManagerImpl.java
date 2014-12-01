package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.services.TmpUploadedFileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = WebApplicationContext.SCOPE_SESSION)
public class TmpUploadedFileManagerImpl implements TmpUploadedFileManager {
    private final static Logger LOG = LoggerFactory.getLogger(TmpUploadedFileManagerImpl.class);

    @Resource
    private transient ServletContext servletContext;

    @Value("${tmp.file.dir}")
    private transient File tmpFileDir;

    @Value("${tmp.file.context}")
    private transient String tmpFileContext;

    @Resource
    private transient HttpSession httpSession;

    private final Map<String, Map<String, File>> fileMap = new HashMap<>();

    @Override
    public boolean addTempFile(String groupId, String fileId, Part part) {
        Map<String, File> groupFileMap = fileMap.get(groupId);
        if (groupFileMap == null) {
            groupFileMap = new HashMap<>();
            fileMap.put(groupId, groupFileMap);
        }

        final File file = groupFileMap.get(fileId);
        if (file != null && file.exists() && !file.delete()) {
            LOG.error("Failed to delete file from session");
            return false;
        }

        final File copyFile = new File(tmpFileDir, System.currentTimeMillis() + part.getSubmittedFileName());
        copyFile.deleteOnExit();

        try (OutputStream outputStream = new FileOutputStream(copyFile)) {
            ByteStreams.copy(part.getInputStream(), outputStream);
        } catch (IOException e) {
            LOG.error("Failed to copy uploaded file to temporary directory!");
            return false;
        }

        groupFileMap.put(fileId, copyFile);
        return true;
    }

    @Override
    public Iterable<String> getFileIds(String groupId) {
        final Map<String, File> groupMap = fileMap.get(groupId);
        return groupMap != null ? groupMap.keySet() : ImmutableList.of();
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

        return tmpFileContext + file.getAbsolutePath().substring(tmpDir.getAbsolutePath().length());
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
}
