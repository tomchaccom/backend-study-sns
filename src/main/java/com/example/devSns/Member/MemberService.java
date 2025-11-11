package com.example.devSns.Member;

import com.example.devSns.Heart.Heart;
import com.example.devSns.Heart.HeartRepository;
import com.example.devSns.Heart.LikeStatus;
import com.example.devSns.Member.Dto.GetMemberPostAndCommentResponseDto;
import com.example.devSns.Member.Dto.GetMemberResponseDto;
import com.example.devSns.Member.Dto.SignMemberRequestDto;
import com.example.devSns.Post.Dto.GetPostResponseDto;
import com.example.devSns.Post.Post;

import com.example.devSns.Post.PostRepository;
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
    private final HeartRepository heartRepository;
    private final PostRepository postRepository;


    // (선택) 팔로우 기능 구현 (닉네임으로 친구 추가 보내기)

    // 멤버 객체 생성 (회원가입_느낌으로다가)
    @Transactional
    public GetMemberResponseDto createMember(SignMemberRequestDto dto) {
        Member saved = memberRepository.save(dto.toEntity());
        return new GetMemberResponseDto(saved);
    }

    // 특정 멤버 검색하기
    @Transactional(readOnly = true)
    public GetMemberResponseDto getMemberById(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 멤버 입니다."));
        return new GetMemberResponseDto(member);
    }

    // Member가 작성한 모든 게시글, 그 밑에 달려있는 댓글까지 모두 조회
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
    @Transactional
    // 좋아요 상태를 토글
    public void convertLikeStatus(Long postId, Long memberId){ // memberId는 나중에 토큰에서 읽어오기


        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        if(!heartRepository.existsByPostIdAndMemberId(postId, memberId)){
            Heart heart = new Heart(
                    post,
                    member,
                    LikeStatus.NONE

            );
            heart.toggleLike();
            heartRepository.save(heart);
        }
        else{
            Heart heart = heartRepository.findByPostIdAndMemberId(postId, memberId)
                    .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));

            heart.toggleLike();
            heartRepository.save(heart);
        }

    }



}
