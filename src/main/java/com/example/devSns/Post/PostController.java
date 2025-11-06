package com.example.devSns.Post;

import com.example.devSns.Post.Dto.AddPostRequestDto;
import com.example.devSns.Post.Dto.GetPostResponseDto;
import com.example.devSns.Post.Dto.UpdatePostRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<Void> createPost(
            @Valid
            @RequestBody AddPostRequestDto Dto) {
        postService.createPost(Dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public List<GetPostResponseDto> getAllPosts() {
        return postService.findAll();
    }
    @GetMapping("/{post_id}")
    public GetPostResponseDto getPostById(@PathVariable(name ="post_id") long id) {
        return postService.findById(id);
    }

    @PatchMapping("/{post_id}")
    public void updatePost(
            @Valid
            @RequestBody UpdatePostRequestDto Dto,
            @PathVariable(name ="post_id") Long id) {
        postService.updatePost(id, Dto);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<Void> deletePostById(@PathVariable(name ="post_id") long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
