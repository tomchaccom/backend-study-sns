package com.example.devSns.Member.Dto;

import com.example.devSns.Member.Gender;
import com.example.devSns.Member.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignMemberRequestDto(

        @NotBlank(message = "공백 또는 null 값은 허용하지 않습니다")
        String nickname,

        @NotBlank(message = "공백 또는 null 값은 허용하지 않습니다")
        @Email(message ="올바른 이메일 양식이 아닙니다")
        String email,

        @NotBlank(message = "공백 또는 null 값은 허용하지 않습니다")
        @Size(min = 8, max =16, message = "비밀번호는 8자 이상 16자 이하로 설정해주세요")
        String password,

        @NotNull(message = "공백 또는 null 값은 허용하지 않습니다")
        Gender gender,

        @NotNull(message = "공백 또는 null 값은 허용하지 않습니다")
        Integer age

) {
    public Member toEntity(){
        return new Member(
                this.email,
                this.nickname,
                this.password,
                this.gender,
                this.age
        );
    }
}
