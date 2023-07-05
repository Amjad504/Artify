package com.example.artify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Upload_Photo extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    Button upload_photo;
    Uri dpp;

    Button next;

    ImageView image;

    byte[] bitmapBytes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        getSupportActionBar().hide();


        upload_photo = findViewById(R.id.upload_photo);
        next = findViewById(R.id.next);
        image = findViewById(R.id.uploaded_image);


        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Upload_Photo.this, MainActivity.class);
                intent.putExtra("bitmapBytes", bitmapBytes); // Pass the byte array as an extra
                startActivity(intent);
                finish();
            }
        });



    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            dpp = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), dpp);
                Bitmap resizedBitmap = resizeBitmap(bitmap, 800); // Resize bitmap to a maximum width of 800 pixels
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                bitmapBytes = convertBitmapToByteArray(resizedBitmap); // Convert Bitmap to byte array
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream); // Adjust compression format and quality
        return stream.toByteArray();
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int maxWidth) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float ratio = (float) width / height;

        if (width > maxWidth) {
            width = maxWidth;
            height = (int) (width / ratio);
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
}