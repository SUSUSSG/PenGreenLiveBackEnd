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
import susussg.pengreenlive.statistics.service.PythonService;

@RestController
@Log4j2
@RequestMapping("/api")
public class PythonController {

    @Autowired
    PythonService pythonService;

    @PostMapping("/generate-image")
    public ResponseEntity<Map<String, String>> generateImage(@RequestBody List<String> reviews) throws IOException, InterruptedException {
        Map<String, String> result = pythonService.generateImage(reviews);
        return ResponseEntity.ok(result);
    }
}
