package susussg.pengreenlive.statistics.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.statistics.service.PythonService;

import java.io.*;
import java.util.Map;

@Service
@Log4j2
public class PythonServiceImpl implements PythonService {

    @Value("${python.env.url}")
    private String PATH;

    @Override
    public Map<String, String> generateImage(String review) throws IOException, InterruptedException {
        String pythonScriptPath = "opt/susussg-backend/word_network.py";

        ObjectMapper objectMapper = new ObjectMapper();
        String reviewJson = objectMapper.writeValueAsString(review);

        String pythonPath = PATH;

        ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, pythonScriptPath, reviewJson);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            log.error("Python script execution failed with exit code: " + exitCode);
            log.error("Script output: " + output.toString());
            log.info("Script output: " + output.toString());

            throw new RuntimeException("Python script execution failed with exit code: " + exitCode);
        }

        String result = output.toString();
        log.info("Python script output: " + result);

        Map<String, String> resultMap = objectMapper.readValue(result, Map.class);

        if (resultMap.containsKey("error")) {
            log.error("Error from Python script: " + resultMap.get("error"));
            log.info("Error from Python script: " + resultMap.get("error"));

            throw new RuntimeException("Error from Python script: " + resultMap.get("error"));
        }

        return resultMap;
    }
}
