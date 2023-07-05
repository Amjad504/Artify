package com.example.artify;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

public class ImageSaver {
    private static final String TAG = "ImageSaver";
    private Context context;
    private Bitmap imageBitmap;

    public ImageSaver(Context context, Bitmap imageBitmap) {
        this.context = context;
        this.imageBitmap = imageBitmap;
    }

    public void saveImage() {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "image_" + System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        OutputStream imageOutputStream = null;
        try {
            // Insert the image into MediaStore
            Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (imageUri != null) {
                imageOutputStream = contentResolver.openOutputStream(imageUri);
                if (imageOutputStream != null) {
                    // Compress the image bitmap to JPEG format
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutputStream);
                    Toast.makeText(context, "Image saved.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to save image: " + e.getMessage());
            Toast.makeText(context, "Failed to save image.", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (imageOutputStream != null) {
                    imageOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
