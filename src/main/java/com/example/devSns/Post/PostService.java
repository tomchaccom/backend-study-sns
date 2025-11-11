package com.example.devSns.Post;

import com.example.devSns.Comment.CommentRepository;
import com.example.devSns.Post.Dto.AddPostRequestDto;
import com.example.devSns.Post.Dto.GetPostResponseDto;
import com.example.devSns.Post.Dto.UpdatePostRequestDto;
import com.example.devSns.global.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public PostService(PostRepository postRepository,CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
    public void createPost(AddPostRequestDto Dto) {
        Post post = new Post(
                Dto.content(),
                Dto.username()
        );
        postRepository.save(post);
    }

    public GetPostResponseDto findById(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));


        return new GetPostResponseDto(
                post.getContent(),
                post.getLikeCount(),
                post.getUserName(),
                post.getCreatedAt(),
                commentRepository.findByPostIdAndParentIsNull(post.getId())
        );
    }

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


    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("삭제하려는 게시글이 존재하지 않습니다"));

        postRepository.delete(post);
    }

    public void updatePost(Long id , UpdatePostRequestDto Dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->new EntityNotFoundException("게시글이 존재하지 않습니다"));

        post.Update(Dto);
        postRepository.save(post); // JDBC에서는 Update 쿼리가 나오는 메소드로 사용했지만 변경감지로 처리
    }

}
