package com.example.re_2;

import static com.example.re_2.functions.BitmapConverter.BitmapToString;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        String bitmap_string ="";

        File storageDir = new File(getFilesDir() + "/capture");
        String filename = "pic" + ".jpg";

        File file = new File(storageDir, filename);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            bitmap_string= BitmapToString(bitmap);
            Log.v("bitmapp_string=",bitmap_string);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}