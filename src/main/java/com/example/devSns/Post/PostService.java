package com.example.devSns.Post;

import com.example.devSns.Comment.CommentRepository;
import com.example.devSns.Heart.HeartRepository;
import com.example.devSns.Heart.LikeStatus;
import com.example.devSns.Member.Member;
import com.example.devSns.Member.MemberRepository;
import com.example.devSns.Post.Dto.AddPostRequestDto;
import com.example.devSns.Post.Dto.GetPostResponseDto;
import com.example.devSns.Post.Dto.UpdatePostRequestDto;
import com.example.devSns.global.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final HeartRepository heartRepository;

    @Transactional
    public void createPost(AddPostRequestDto Dto, Long memberId) {
        Post post = new Post(
                Dto.content(),
                Dto.username(),
                0L
        );
        Member member =  memberRepository.findById(memberId)
                .orElseThrow(()->new EntityNotFoundException("Member not found"));

        post.writePost(member);
        postRepository.save(post);
    }
    @Transactional(readOnly = true)
    public GetPostResponseDto findById(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));


        return new GetPostResponseDto(
                post.getContent(),
                post.getLikeCount(), // 이거 나중에 외래키로 계산해서 보내기, Dto 변환로직 만들어 두자
                post.getUserName(),
                post.getCreatedAt(),
                commentRepository.findByPostIdAndParentIsNull(post.getId())
        );
    }
    @Transactional(readOnly = true)
    public List<GetPostResponseDto> findAll() {
        return postRepository.findAll()
                .stream()
                .map(post -> new GetPostResponseDto(
                        post.getContent(),
                        post.getLikeCount(),
                        post.getUserName(),
                        post.getCreatedAt(),
                        commentRepository.findByPostIdAndParentIsNull(post.getId())
                ))
                .collect(Collectors.toList());
    }
    @Transactional
    @Scheduled(cron = "0 * * * * *") // 매 분마다
    public void countLikes() {
        List<Post> posts = postRepository.findAll();

        for (Post post : posts) {
            long likeCount = heartRepository.countByPostIdAndLike(post.getId(), LikeStatus.LIKE);
            post.updateLikeCount((Long) likeCount);
        }
    }
    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("삭제하려는 게시글이 존재하지 않습니다"));

        postRepository.delete(post);
    }

    @Transactional
    public void updatePost(Long id , UpdatePostRequestDto Dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->new EntityNotFoundException("게시글이 존재하지 않습니다"));

        post.Update(Dto);
        postRepository.save(post); // JDBC에서는 Update 쿼리가 나오는 메소드로 사용했지만 변경감지로 처리
    }

}
