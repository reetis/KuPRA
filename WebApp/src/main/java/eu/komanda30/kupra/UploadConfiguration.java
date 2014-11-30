package eu.komanda30.kupra;

import java.io.File;

public final class UploadConfiguration {
    public static final int MAX_UPLOAD_FILE_SIZE_MB = 10;
    public static final int MAX_REQUEST_SIZE_MB = 15;
    public static final int MAX_FILE_THRESHOLD_MB = 2;

    public static final File UPLOAD_DIRECTORY = new File("/uploads/tmp");

    private UploadConfiguration() {
    }
}
