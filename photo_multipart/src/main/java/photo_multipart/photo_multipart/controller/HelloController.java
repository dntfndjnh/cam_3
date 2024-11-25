package photo_multipart.photo_multipart.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import photo_multipart.photo_multipart.data.OutputData;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @PostMapping("/upload")
    public ResponseEntity<List<OutputData>> uploadImage(@RequestParam("image") MultipartFile file) {
        // 파일 처리 로직 (생략)
        System.out.println("파일 이름: " + file.getOriginalFilename());

        // 결과 반환
        List<OutputData> results = new ArrayList<>();
        results.add(new OutputData("Pizza", 5));
        results.add(new OutputData("Burger", 3));
        results.add(new OutputData("Pasta", 7));

        return ResponseEntity.ok(results);
    }



}



    //




    //






    //





