package eu.komanda30.kupra.uploads;

import java.io.File;
import java.io.InputStream;

public interface TmpUploadedFileManager {
    File addTempFile(String groupId, String fileId, InputStream stream, String fileName);
    Iterable<String> getFileIds(String groupId);
    File getFile(String groupId, String fileId);
    String getVirtualPath(String groupId, String fileId);
    String getVirtualThumbPath(String groupId, String fileId);
    boolean addImageWithThumb(String groupId, String fileId, InputStream stream, String fileName);
    void deleteFile(String formTmpId, String fileId);
    void deleteImageWithThumb(String formTmpId, String imgId);
    File getThumbFile(String tmpId, String fileId);
}
