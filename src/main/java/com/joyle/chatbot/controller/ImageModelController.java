package com.joyle.chatbot.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/image")
public class ImageModelController {

    private final ImageModel imageModel;

    ImageModelController(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @GetMapping("/image/{input}")
    public void image(@PathVariable("input") String input, HttpServletResponse response) {

        ImageOptions options = ImageOptionsBuilder.builder()
                .withModel("wanx-v1")
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(input, options);
        ImageResponse imageResponse = imageModel.call(imagePrompt);
        String imageUrl = imageResponse.getResult().getOutput().getUrl();

        try {
            URL url = new URL(imageUrl);
            InputStream in = url.openStream();

            response.setHeader("Content-Type", MediaType.IMAGE_PNG_VALUE);
            response.getOutputStream().write(in.readAllBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}