package com.example.devSns.Comment.Dto;

import com.example.devSns.Comment.Comment;
import jakarta.validation.constraints.NotBlank;

public record CreateCommentDto(
        @NotBlank
        String content,

        @NotBlank
        String author
) {
    public Comment toEntity() {
        return new Comment(content, author);

    }

}
