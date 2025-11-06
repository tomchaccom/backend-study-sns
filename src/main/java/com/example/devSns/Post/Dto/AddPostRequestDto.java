package com.example.devSns.Post.Dto;


import jakarta.validation.constraints.NotBlank;

public record AddPostRequestDto(
        @NotBlank
        String content,

        @NotBlank
        String username
)
{}