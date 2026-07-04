package org.libpng;

public class PngImage {
    private final long p;

    public PngImage() {
        p = mallocPngImage();
        if (p == 0) {
            throw new OutOfMemoryError();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        freePngImage(p);

        super.finalize();
    }

    private static native long mallocPngImage();

    private static native void freePngImage(long p);
}
