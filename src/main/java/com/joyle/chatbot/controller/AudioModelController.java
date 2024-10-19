package com.joyle.chatbot.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/audio")
public class AudioModelController {

    private static final Logger logger = LoggerFactory.getLogger(AudioModelController.class);

    @GetMapping("/speech")
    public String speech(String text) {

        logger.warn("unimplement");

        return "unimplement";
    }

    @GetMapping("/transcription")
    public String transcription(String text) {

        logger.warn("unimplement");

        return "unimplement";
    }

}