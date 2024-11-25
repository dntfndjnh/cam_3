package com.example.re_2;

import static com.example.re_2.functions.BitmapConverter.BitmapToByteArray;
import static com.example.re_2.functions.BitmapConverter.BitmapToString;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.re_2.functions.retrofit.ApiService;
import com.example.re_2.functions.retrofit.OutputData;
import com.example.re_2.functions.retrofit.RetrofitClient;
import com.google.android.material.card.MaterialCardView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        TextView textView;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        String bitmap_string ="";
        byte[] byteArray = null;

        File storageDir = new File(getFilesDir() + "/capture");
        String filename = "pic" + ".jpg";

        File file = new File(storageDir, filename);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            bitmap_string= BitmapToString(bitmap);

            // Bitmap을 ByteArray로 변환
            byteArray = BitmapToByteArray(bitmap);
            //


           // Log.v("bitmapp_string=",bitmap_string);
            Log.v("bitmapp_string length:",bitmap_string.length()+"");


            //파일 비트맵 스트링으로 전환하고나서 파일 삭제
            boolean deleted = file.delete();
            //


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }





        //Retrofit Post설정
        RequestBody requestBody = RequestBody.create(byteArray, MediaType.parse("image/png"));
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", "sample_image.png", requestBody);

        // Retrofit 호출
        ApiService apiService = RetrofitClient.getApiService();
        apiService.uploadBitmap(imagePart).enqueue(new Callback<List<OutputData>>() {
            @Override
            public void onResponse(Call<List<OutputData>> call, Response<List<OutputData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OutputData> outputDataList = response.body();
                    displayCards(outputDataList);
                }
            }

            @Override
            public void onFailure(Call<List<OutputData>> call, Throwable t) {
                t.printStackTrace();
            }
        });


        //








        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void displayCards(List<OutputData> outputDataList) {
        LinearLayout layout = findViewById(R.id.card_container);

        for (OutputData data : outputDataList) {
            MaterialCardView cardView = new MaterialCardView(this);
            cardView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            cardView.setCardElevation(8);
            cardView.setContentPadding(16, 16, 16, 16);

            TextView textView = new TextView(this);
            textView.setText(String.format("Menu: %s\n주문횟수: %d", data.getMenu(), data.getCount()));

            cardView.addView(textView);
            layout.addView(cardView);
        }
    }
}

