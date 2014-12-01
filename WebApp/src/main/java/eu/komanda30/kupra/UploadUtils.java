package eu.komanda30.kupra;

import java.io.File;

public final class UploadUtils {
    private UploadUtils() {
    }

    public static String resolveVirtualPath(File localFile, File contextDir, String contextPath) {
        final String path = localFile.getAbsolutePath();
        final String relDirPath = contextDir.getAbsolutePath();
        if (!path.startsWith(relDirPath)) {
            return null;
        }

        return contextPath+path.substring(relDirPath.length()+1);
    }
}
