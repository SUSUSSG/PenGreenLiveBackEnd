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
    public Map<String, String> generateImage(String review)
        throws IOException, InterruptedException {
        String pythonScriptPath = "src/main/java/susussg/pengreenlive/statistics/py/word_network.py";

        ObjectMapper objectMapper = new ObjectMapper();
        String reviewJson = objectMapper.writeValueAsString(review);

        String pythonPath = PATH; // TODO : 배포 시 서버 내 주소로 변경

        ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, pythonScriptPath,
            reviewJson);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException(
                "Python script execution failed with exit code: " + exitCode);
        }

        return objectMapper.readValue(output.toString(), Map.class);
    }
}
