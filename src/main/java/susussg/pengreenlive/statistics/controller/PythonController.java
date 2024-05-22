package susussg.pengreenlive.statistics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @PostMapping("/review-semantic")
    public ResponseEntity<Map<String, String>> generateImage(@RequestParam("productSeq") Long productSeq) throws IOException, InterruptedException {
        String reviews = reviewSummaryService.ReviewsByProductSeq(productSeq);
        Map<String, String> result = pythonService.generateImage(reviews);
        return ResponseEntity.ok(result);
    }
}
