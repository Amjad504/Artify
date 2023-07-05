package com.example.artify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

//    Button btn;
    ImageView img1;

    LinearLayout vintage,adjust,cartoon,sketch,b_w,sepia,grayscale,adjust_option,bright,saturation,sharp,contrast;

    Boolean bright_bool = false;
    Boolean saturation_bool = false;
    Boolean contrast_bool = false;
    Boolean sharp_bool = false;
    BitmapDrawable drawable;
    Bitmap bitmap,bitmap2;
    String imageString,imageString2;
    SeekBar seekBar;

    Boolean adjust_bool = true;

    ImageView tick,save,reset;

    ImageView reset_image;

    Python py;
    PyObject module;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();




//        btn = findViewById(R.id.btn);
        img1 = findViewById(R.id.image1);
        vintage = findViewById(R.id.vintage);
        b_w = findViewById(R.id.b_w);
        cartoon = findViewById(R.id.cartoonify);
        sketch = findViewById(R.id.sketch);
        adjust = findViewById(R.id.adjust);
        sepia = findViewById(R.id.sepia);
        grayscale = findViewById(R.id.grayscale);
        adjust_option = findViewById(R.id.adjust_option);
        bright = findViewById(R.id.brightness);
        saturation = findViewById(R.id.saturation);
        sharp = findViewById(R.id.sharpness);
        contrast = findViewById(R.id.contrast);
        tick = findViewById(R.id.tick);
        reset = findViewById(R.id.reset);
        save = findViewById(R.id.save);


//        img2 = findViewById(R.id.image2);

        // Retrieve the byte array from the intent extra
        byte[] bitmapBytes = getIntent().getByteArrayExtra("bitmapBytes");

        if (bitmapBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            img1.setImageBitmap(bitmap);
        }

        drawable = (BitmapDrawable) img1.getDrawable();
        bitmap = drawable.getBitmap();
        bitmap2 = drawable.getBitmap();

        imageString = getImageString(bitmap);
        imageString2 = getImageString(bitmap);



        // "context" must be an Activity, Service or Application object from your app.
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        py = Python.getInstance();

        module = py.getModule("script");


        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawable = (BitmapDrawable) img1.getDrawable();
                bitmap = drawable.getBitmap();

                imageString = getImageString(bitmap);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setImageBitmap(bitmap2);
                drawable = (BitmapDrawable) img1.getDrawable();
                bitmap = drawable.getBitmap();

                imageString = getImageString(bitmap);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });


        adjust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adjust_bool)
                {
                    adjust_bool = false;
                    adjust_option.setVisibility(View.VISIBLE);
                }
                else
                {
                    adjust_bool = true;
                    adjust_option.setVisibility(View.GONE);
                    seekBar.setVisibility(View.GONE);
                }

            }
        });


        saturation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setVisibility(View.VISIBLE);
                seekBar.setProgress(0);
                saturation_bool = true;
                contrast_bool = false;
                sharp_bool = false;
                bright_bool = false;
            }
        });

        bright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setVisibility(View.VISIBLE);
                seekBar.setProgress(0);
                saturation_bool = false;
                contrast_bool = false;
                sharp_bool = false;
                bright_bool = true;
            }
        });


        sharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setVisibility(View.VISIBLE);
                seekBar.setProgress(0);
                saturation_bool = false;
                contrast_bool = false;
                sharp_bool = true;
                bright_bool = false;
            }
        });

        contrast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setVisibility(View.VISIBLE);
                seekBar.setProgress(0);
                saturation_bool = false;
                contrast_bool = true;
                sharp_bool = false;
                bright_bool = false;
            }
        });

        sketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PyObject module1 = py.getModule("script");
                PyObject obj = module1.callAttr("sketch_image",imageString);

                String str = obj.toString();

                //convert it to byte array
                byte data[] = Base64.decode(str, Base64.DEFAULT);
//n             //now conver it to bitmap
                Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);

                img1.setImageBitmap(bmp);


            }
        });

        grayscale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PyObject module1 = py.getModule("script");
                PyObject obj = module1.callAttr("gray_scale",imageString);

                String str = obj.toString();

                //convert it to byte array
                byte data[] = Base64.decode(str, Base64.DEFAULT);
//n             //now conver it to bitmap
                Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);

                img1.setImageBitmap(bmp);


            }
        });


        cartoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PyObject module1 = py.getModule("script");
                PyObject obj = module1.callAttr("cartoonify_image",imageString);

                String str = obj.toString();

                //convert it to byte array
                byte data[] = Base64.decode(str, Base64.DEFAULT);
//n             //now conver it to bitmap
                Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);

                img1.setImageBitmap(bmp);


            }
        });

        vintage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PyObject module1 = py.getModule("script");
                PyObject obj = module1.callAttr("apply_vintage",imageString);

                String str = obj.toString();

                //convert it to byte array
                byte data[] = Base64.decode(str, Base64.DEFAULT);
//n             //now conver it to bitmap
                Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);

                img1.setImageBitmap(bmp);



            }
        });

        sepia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PyObject module1 = py.getModule("script");
                PyObject obj = module1.callAttr("apply_sepia",imageString);

                String str = obj.toString();

                //convert it to byte array
                byte data[] = Base64.decode(str, Base64.DEFAULT);
//n             //now conver it to bitmap
                Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);

                img1.setImageBitmap(bmp);



            }
        });


        b_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PyObject module1 = py.getModule("script");
                PyObject obj = module1.callAttr("apply_black_and_white",imageString);

                String str = obj.toString();

                //convert it to byte array
                byte data[] = Base64.decode(str, Base64.DEFAULT);
//n             //now conver it to bitmap
                Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);

                img1.setImageBitmap(bmp);



            }
        });

        seekBar = findViewById(R.id.seekBar_luminosite);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the TextView with the current progress value
                this.progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Called when the user starts interacting with the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Called when the user stops interacting with the SeekBar
                performAction(progress);

            }
        });

    }

    private String getImageString(Bitmap bitmap) {

        // Convert Bitmap to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

// Convert byte array to Base64 encoded string
        String base64EncodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return  base64EncodedString;
    }

    // Function to perform an action based on the progress value
    private void performAction(int progress) {

        if (sharp_bool)
        {
            PyObject obj = module.callAttr("adjust_sharpening",imageString,progress);
            String str = obj.toString();
            byte data[] = Base64.decode(str, Base64.DEFAULT);
            //now conver it to bitmap
            Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);
            img1.setImageBitmap(bmp);

        } else if (contrast_bool) {

            PyObject obj = module.callAttr("adjust_contrast",imageString,progress);
            String str = obj.toString();
            byte data[] = Base64.decode(str, Base64.DEFAULT);
            //now conver it to bitmap
            Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);
            img1.setImageBitmap(bmp);
        } else if (bright_bool) {

            PyObject obj = module.callAttr("adjust_brightness",imageString,progress);
            String str = obj.toString();
            byte data[] = Base64.decode(str, Base64.DEFAULT);
            //now conver it to bitmap
            Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);
            img1.setImageBitmap(bmp);

        } else if (saturation_bool) {
            PyObject obj = module.callAttr("adjust_saturation",imageString,progress);
            String str = obj.toString();
            byte data[] = Base64.decode(str, Base64.DEFAULT);
            //now conver it to bitmap
            Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);
            img1.setImageBitmap(bmp);

        }

//


    }

    private void saveImage() {

        drawable = (BitmapDrawable) img1.getDrawable();

        Bitmap imageBitmap = drawable.getBitmap();

        ImageSaver imageSaver = new ImageSaver(this, imageBitmap);
        imageSaver.saveImage();
    }


}