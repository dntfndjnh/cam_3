package com.example.re_2;

import static com.example.re_2.functions.BitmapConverter.BitmapToString;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.re_2.functions.retrofit.DataObject;
import com.example.re_2.functions.retrofit.RetrofitClient;
import com.example.re_2.functions.retrofit.RetrofitService;

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


            //파일 비트맵 스트링으로 전환하고나서 파일 삭제
            boolean deleted = file.delete();
            //


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }





        //Retrofit Post설정

        textView = findViewById(R.id.text_view_result);
        RetrofitService service = RetrofitClient.getService();

        // 전송할 데이터 생성
        DataObject dataObject = new DataObject();
        dataObject.setBitmap("bitmap="+bitmap_string);


        // 서버에 요청
        service.sendData(dataObject).enqueue(new Callback<DataObject>() {
            @Override
            public void onResponse(Call<DataObject> call, Response<DataObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 응답 성공 시 TextView에 표시
                    textView.setText(response.body().toString());
                } else {
                    textView.setText("응답 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DataObject> call, Throwable t) {
                // 통신 실패 처리
                textView.setText("통신 실패: " + t.getMessage());
                Toast.makeText(MenuActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
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