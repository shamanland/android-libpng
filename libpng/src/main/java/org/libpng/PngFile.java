package org.libpng;

import android.system.OsConstants;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Keep
public class PngFile implements Closeable {
    private static final String LOG_TAG = PngFile.class.getSimpleName();

    @NonNull
    private final String path; // accessed from native code

    @SuppressWarnings("unused")
    private long fp; // accessed from native code

    public PngFile(@NonNull File file) {
        this.path = file.getAbsolutePath();
    }

    public void open(@NonNull String mode) throws IOException {
        int errno = fopen(mode);
        if (errno == OsConstants.ENOENT) {
            throw new FileNotFoundException(path);
        } else if (errno != 0) {
            String errnoName = OsConstants.errnoName(errno);
            throw new IOException(errnoName != null ? errnoName : String.valueOf(errno));
        }
    }

    public void close() throws IOException {
        int errno = fclose();
        if (errno != 0) {
            String errnoName = OsConstants.errnoName(errno);
            throw new IOException(errnoName != null ? errnoName : String.valueOf(errno));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();

        super.finalize();
    }

    private native int fopen(@NonNull String mode);

    private native int fclose();

    static {
        System.loadLibrary("png");
    }
}
