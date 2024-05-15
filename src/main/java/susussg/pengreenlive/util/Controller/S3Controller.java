package susussg.pengreenlive.util.Controller;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import susussg.pengreenlive.util.Service.S3Service;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

//    본 컨트롤러는 평소에 사용되지 않습니다.
//    이미지 업로드 소요시간 측정을 위해 사용합니다.
//    S3Service 서비스 클래스를 호출해서 메소드를 사용해주세요.
//
//    사용법은 다음과 같습니다.
//    String uploadedImgUrl = S3Service.uploadFile(실제 byte[] 파일 변수, "저장될 폴더명");
//    ex)  S3Service.uploadFile(productImage, "product");
//    이미지 업로드 전에 반드시 ImageService 내 compressAndResizeImage()를 사용해서 압축 후 사용해주세요.

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, String folderName) throws IOException {
        String fileUrl = s3Service.uploadFile(file, folderName);
        return ResponseEntity.ok(fileUrl);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) throws IOException {
        S3Object s3Object = s3Service.downloadFile(fileName);
        byte[] content = s3Object.getObjectContent().readAllBytes();

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
            .body(content);
    }
}
