package susussg.pengreenlive.statistics.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PythonService {
    Map<String, String> generateImage(List<String> reviews) throws IOException, InterruptedException;
}