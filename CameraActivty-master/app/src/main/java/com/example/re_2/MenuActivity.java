package com.example.re_2;

import static com.example.re_2.functions.BitmapConverter.BitmapToString;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.re_2.functions.retrofit.data_model;
import com.example.re_2.functions.retrofit.retrofit_client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Call<data_model> call;
        TextView textView;

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
           // Log.v("bitmapp_string=",bitmap_string);
            Log.v("bitmapp_string length:",bitmap_string.length()+"");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        textView =findViewById(R.id.txt_view);

        call = retrofit_client.getApiService().test_api_get("bitmap example");
        call.enqueue(new Callback<data_model>(){
            //콜백 받는 부분
            @Override
            public void onResponse(Call<data_model> call, Response<data_model> response) {
                data_model result = response.body();
                String str;
                str= result.getBitmap() +"\n";

                textView.setText(str);
            }

            @Override
            public void onFailure(Call<data_model> call, Throwable t) {

            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}