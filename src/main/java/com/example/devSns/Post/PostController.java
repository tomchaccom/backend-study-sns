package com.example.devSns.Post;

import com.example.devSns.Post.Dto.AddPostRequestDto;
import com.example.devSns.Post.Dto.GetPostResponseDto;
import com.example.devSns.Post.Dto.UpdatePostRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("members/{member_id}/posts")
    public ResponseEntity<Void> createPost(
            @Valid
            @RequestBody AddPostRequestDto Dto,
           @PathVariable("member_id") Long member_id) {

        postService.createPost(Dto,member_id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/posts")
    public List<GetPostResponseDto> getAllPosts() {
        return postService.findAll();
    }
    @GetMapping("/posts/{post_id}")
    public GetPostResponseDto getPostById(@PathVariable(name ="post_id") long id) {
        return postService.findById(id);
    }

    @PatchMapping("/posts/{post_id}")
    public void updatePost(
            @Valid
            @RequestBody UpdatePostRequestDto Dto,
            @PathVariable(name ="post_id") Long id) {
        postService.updatePost(id, Dto);
    }

    @DeleteMapping("/posts/{post_id}")
    public ResponseEntity<Void> deletePostById(@PathVariable(name ="post_id") long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
