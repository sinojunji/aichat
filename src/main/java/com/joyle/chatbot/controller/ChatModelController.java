package com.joyle.chatbot.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;

@RestController
@RequestMapping("/ai/chat")
public class ChatModelController {

    private final ChatModel chatModel;

    public ChatModelController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chat")
    public String chat(String input) {

        Prompt prompt = new Prompt(input, DashScopeChatOptions.builder()
                .withModel("qwen-turbo")
                .withTemperature(0.4F)
                .build());
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    @GetMapping("/stream")
    public String stream(String input) {

        StringBuilder res = new StringBuilder();
        Flux<ChatResponse> stream = chatModel.stream(new Prompt(input));
        stream.toStream().toList().forEach(resp -> {
            res.append(resp.getResult().getOutput().getContent());
        });

        return res.toString();
    }

}