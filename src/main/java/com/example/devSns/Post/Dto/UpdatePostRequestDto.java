package com.example.devSns.Post.Dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePostRequestDto(
        @NotBlank
        String content,

        @NotBlank
        String username
) {
}
