package eu.komanda30.kupra.services;

import java.io.File;

import javax.servlet.http.Part;

public interface TmpUploadedFileManager {
    boolean addTempFile(String groupId, String fileId, Part part);
    Iterable<String> getFileIds(String groupId);
    File getFile(String groupId, String fileId);
    String getVirtualPath(String groupId, String fileId);
}
