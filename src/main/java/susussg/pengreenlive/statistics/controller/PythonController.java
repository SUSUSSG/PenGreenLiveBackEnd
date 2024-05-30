package susussg.pengreenlive.statistics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.Map;
import susussg.pengreenlive.naver.service.ReviewSummaryService;
import susussg.pengreenlive.statistics.service.PythonService;

@RestController
@Log4j2
@RequestMapping("/api")
public class PythonController {

    @Autowired
    PythonService pythonService;

    @Autowired
    ReviewSummaryService reviewSummaryService;

    @Operation(summary = "리뷰 의미 연결망을 분석합니다.", description = "리뷰 데이터를 받아, 파이썬 스크립트를 실행해 의미 연결망을 분석합니다.")
    @PostMapping("/review-semantic")
    public ResponseEntity<Map<String, String>> generateImage(@RequestParam("productSeq") Long productSeq) throws IOException, InterruptedException {
        String reviews = reviewSummaryService.ReviewsByProductSeq(productSeq);
        Map<String, String> result = pythonService.generateImage(reviews);
        return ResponseEntity.ok(result);
    }
}
