package com.example.devSns.Member;

import com.example.devSns.Member.Dto.GetMemberPostAndCommentResponseDto;
import com.example.devSns.Member.Dto.GetMemberResponseDto;
import com.example.devSns.Member.Dto.SignMemberRequestDto;
import com.example.devSns.Post.Dto.GetPostResponseDto;
import com.example.devSns.Post.Post;

import com.example.devSns.Post.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostService postService;


    // 멤버의 게시글 & 댓글 조회 (멤버 객체에 저장된 게시글 정보를 통해서 불러오기)
    // (선택) 팔로우 기능 구현 (닉네임으로 친구 추가 보내기)
    // 좋아요 기능 - 어떤 Member가 눌렀는지(흠 얘는 멤버 -  좋아요 - 게시글 형태의 DB의 도메인 으로 변경해야 겠는데?
    // 좋아요 릴레이션 (like_id, member_id, post_id) 형식으로

    // 멤버 객체 생성 (회원가입_느낌으로다가)
    @Transactional
    public void createMember(SignMemberRequestDto dto) {
        memberRepository.save(dto.toEntity());
    }

    // 특정 멤버 검색하기
    @Transactional(readOnly = true)
    public GetMemberResponseDto getMemberById(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 멤버 입니다."));
        return new GetMemberResponseDto(member);
    }

    @Transactional
    public GetMemberPostAndCommentResponseDto getMemberPostAndComment(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 멤버 입니다."));

        List<Post> postList = member.getPosts();
        List<GetPostResponseDto> postResponseDtoList = new ArrayList<>();

        for(Post post : postList){
            postResponseDtoList.add(postService.findById(post.getId()));
        }
        return new GetMemberPostAndCommentResponseDto(
                member.getNickname(),
                postResponseDtoList
        );
    }


}
