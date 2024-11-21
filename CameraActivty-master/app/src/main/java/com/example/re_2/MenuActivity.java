package com.example.re_2;

import static com.example.re_2.functions.BitmapConverter.BitmapToString;

import android.annotation.SuppressLint;
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

import com.example.re_2.functions.retrofit.JsonPlaceHolderApi;
import com.example.re_2.functions.retrofit.Post;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MenuActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


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
            
            //파일 비트맵 스트링으로 전환하고나서 파일 삭제 내가 추가함


            boolean deleted = file.delete();
            //

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }





        //Retrofit Post설정

        final String BASEURL = "http://jsonplaceholder.typicode.com/";
        TextView textViewResult;

        JsonPlaceHolderApi jsonPlaceHolderApi;



            textViewResult = findViewById(R.id.text_view_result);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);





            Post post = new Post( bitmap_string);

            Call<Post> call = jsonPlaceHolderApi.createPost(post);

            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if (!response.isSuccessful()) {
                        textViewResult.setText("서버와 통신이 안됩니다\ncode: " + response.code());
                        return;
                    }

                    Post postResponse = response.body();

                    String content = "";

                    content += "bitmap: " + postResponse.getBitmap() + "\n";


                    textViewResult.setText(content);
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    textViewResult.setText(t.getMessage());
                }
            });


        //








        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}