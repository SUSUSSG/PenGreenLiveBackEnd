package susussg.pengreenlive.broadcast.service;

import org.springframework.stereotype.Service;

@Service
public interface TranslationService {
    String translateKoreanToEnglish(String koreanText);
}

