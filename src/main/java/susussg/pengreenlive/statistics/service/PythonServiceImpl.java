package susussg.pengreenlive.statistics.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.statistics.service.PythonService;

import java.io.*;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class PythonServiceImpl implements PythonService {

    @Override
    public Map<String, String> generateImage(List<String> reviews) throws IOException, InterruptedException {
        String pythonScriptPath = "src/main/java/susussg/pengreenlive/statistics/py/word_network.py";

        ObjectMapper objectMapper = new ObjectMapper();
        String reviewsJson = objectMapper.writeValueAsString(reviews);

        String pythonPath = "/usr/local/bin/python3"; // TODO : 배포 시 서버 내 주소로 변경

        ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, pythonScriptPath, reviewsJson);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Python script execution failed with exit code: " + exitCode);
        }

        return objectMapper.readValue(output.toString(), Map.class);
    }
}