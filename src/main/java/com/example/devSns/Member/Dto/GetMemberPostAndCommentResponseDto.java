package com.example.devSns.Member.Dto;

import com.example.devSns.Post.Dto.GetPostResponseDto;
import java.util.List;

public record GetMemberPostAndCommentResponseDto (

        String nickname,
        List<GetPostResponseDto> post)

{
}
