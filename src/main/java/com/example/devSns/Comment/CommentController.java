package com.example.devSns.Comment;

import com.example.devSns.Comment.Dto.CreateCommentDto;
import com.example.devSns.Comment.Dto.UpdateCommentDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/{post_id}/comments")
    public void postComment(
            @Valid
            @RequestBody CreateCommentDto dto,
            @PathVariable Long post_id) {
        commentService.createComment(post_id, dto);
    }

    @PostMapping("posts/{post_id}/comments/{comment_id}")
    public void postReplyComment(
            @Valid
            @RequestBody CreateCommentDto dto,
            @PathVariable Long post_id,
            @PathVariable Long comment_id
    ) {
        commentService.createReplyComment(post_id, comment_id, dto);
    }


    @GetMapping("posts/{post_id}/comments")
    public List<Comment> getComments(@PathVariable("post_id") Long post_id) {
        return commentService.getAllComments(post_id);
    }
    @GetMapping("posts/{post_id}/comments/{comment_id}")
    public Comment getCommentsByPostId(
            @PathVariable("post_id") Long post_id,
            @PathVariable("comment_id") Long comment_id) {
        return commentService.getCommentById(post_id, comment_id);
    }
    @PatchMapping("/comments/{comment_id}")
    public Comment updateComment(
            @PathVariable("comment_id") Long comment_id,
            @Valid
            @RequestBody UpdateCommentDto dto) {
            return commentService.updateComment(comment_id, dto);

    }
    @DeleteMapping("comments/{comment_id}")
    public void deleteComment(@PathVariable("comment_id") Long comment_id) {
        commentService.deleteCommentById(comment_id);
    }


}
