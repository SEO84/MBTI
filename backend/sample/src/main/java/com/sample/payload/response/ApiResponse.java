package com.sample.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ApiResponse {
    @Schema(type = "boolean", example = "true", description = "올바르게 처리되었으면 True, 아니면 False")
    private boolean check;

    @Schema(type = "object", example = "information", description = "처리 결과 정보")
    private Object information;

    public ApiResponse() {}

    @Builder
    public ApiResponse(boolean check, Object information) {
        this.check = check;
        this.information = information;
    }
}
