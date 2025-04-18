package com.sample.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Message {
    @Schema(type = "string", example = "메시지입니다.", description = "메시지 내용")
    private String message;

    public Message() {}

    @Builder
    public Message(String message) {
        this.message = message;
    }
}
