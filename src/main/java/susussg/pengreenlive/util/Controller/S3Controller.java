package susussg.pengreenlive.util.Controller;

import com.amazonaws.services.s3.model.S3Object;
import io.swagger.v3.oas.annotations.Operation;
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

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Operation(summary = "이미지 파일을 업로드합니다.", description = "이미지 파일을 S3에 업로드 후, url을 리턴합니다.")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, String folderName) throws IOException {
        String fileUrl = s3Service.uploadFile(file, folderName);
        return ResponseEntity.ok(fileUrl);
    }
    @Operation(summary = "이미지를 다운로드합니다.", description = "이미지 파일을 S3에서 다운로드 후, byte[]로 리턴합니다.")
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
