package org.libpng.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.libpng.PngFile;
import org.libpng.PngImage;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        try (PngFile pngFile = new PngFile(new File(getFilesDir(), "1.txt"))) {
            pngFile.open("r");
        } catch (IOException ex) {
            Log.w(LOG_TAG, ex);
        }

        PngImage pngImage = new PngImage();

        TextView textView = new TextView(this);
        textView.setText("Hello PNG: " + pngImage);

        FrameLayout root = new FrameLayout(this);
        root.addView(textView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
        ));

        setContentView(root);
    }
}
