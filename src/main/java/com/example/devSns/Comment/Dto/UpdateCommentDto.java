package com.example.devSns.Comment.Dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentDto(
        @NotBlank
        String content,

        @NotBlank
        String author) {

}
