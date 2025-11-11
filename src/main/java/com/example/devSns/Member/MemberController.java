package com.example.devSns.Member;

import com.example.devSns.Member.Dto.GetMemberPostAndCommentResponseDto;
import com.example.devSns.Member.Dto.GetMemberResponseDto;
import com.example.devSns.Member.Dto.SignMemberRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 멤버 생성하기
    @PostMapping("/members")
    public ResponseEntity<GetMemberResponseDto> createMember(
            @Valid
            @RequestBody SignMemberRequestDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(dto));
    }

    // 특정 멤버 검색하기
    @GetMapping("/members/{member_id}")
    public ResponseEntity<GetMemberResponseDto> getMember(
            @PathVariable(name ="member_id") Long memberId
    ) {
        return ResponseEntity.status(200).body(memberService.getMemberById(memberId));
    }

    // like_status toggle
    @PostMapping("/members/{member_id}/posts/{post_id}/like")
    public ResponseEntity<?> convertLike(
            @PathVariable(name ="post_id") Long postId,
            @PathVariable(name ="member_id") Long memberId){
        try {
            memberService.convertLikeStatus(postId, memberId);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(201).build();

    }

    // 해당 멤버가 작성한 게시글의 댓글까지 조회
    @GetMapping("/members/{member_id}/posts")
    public ResponseEntity<GetMemberPostAndCommentResponseDto> getMemberPostAndComment(
            @PathVariable(name = "member_id") Long memberId
    ){
        return ResponseEntity.status(200).body(memberService.getMemberPostAndComment(memberId));
    }

}
