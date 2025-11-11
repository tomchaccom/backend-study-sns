package com.example.devSns.Member.Dto;

import com.example.devSns.Member.Gender;
import com.example.devSns.Member.Member;

public record GetMemberResponseDto(

        Long id,
        String nickname,
        String email,
        Gender gender,
        Integer age
) {
    public GetMemberResponseDto(Member member) {
        this(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getGender(),
                member.getAge()
        );
    }
}
