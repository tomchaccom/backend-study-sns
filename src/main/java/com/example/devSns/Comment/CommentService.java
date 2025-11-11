package com.example.devSns.Comment;

import com.example.devSns.Comment.Dto.CreateCommentDto;
import com.example.devSns.Comment.Dto.UpdateCommentDto;
import com.example.devSns.global.EntityNotFoundException;
import com.example.devSns.Post.Post;
import com.example.devSns.Post.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    // 댓글 달기. 특정 게시글 밑에 작성 , 게시글 상세 조회 후 작성이 가능하도록
    public void createComment(Long postId, CreateCommentDto dto){
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        Comment comment = dto.toEntity();
        comment.assignToPost(post);
        commentRepository.save(comment);
    }
    @Transactional
    public void createReplyComment(Long postId, Long commentId, CreateCommentDto dto){
        postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        Comment reply = dto.toEntity();

        reply.leavedParent(comment);
        comment.leaveReply(reply);

        commentRepository.save(reply);

    }

    @Transactional
    // 댓글 목록 조회 - 게시글 밑에 나타나게끔. 근데 얘는 특정 포스트에 해당된 모든 댓글이 조회 되어야 할 터
    public List<Comment> getAllComments(Long postId){
        // repo에 인자로 전달된 postId를 가지는 comment 조회, 가져올 때 Dto 변환 시키기 JPQL
        return commentRepository.findByPostIdAndParentIsNull(postId);
    }
    @Transactional
    // 댓글 조회 - 특정 게시글 조회 시 댓글도 조회되게끔
    // 조회용 Dto 도 만들기, 내용, 작성자, 작성 일시가 나오면 될듯
    public Comment getCommentById(Long postId, Long commentId){
        return commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }
    @Transactional
    // 댓글 수정
    // id 는 path로 , dto는 body로 전달받기
    public Comment updateComment(Long id, UpdateCommentDto dto){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.updateComment(dto);
        return commentRepository.save(comment);
    }
    @Transactional
    // 댓글 삭제
    public void deleteCommentById(Long id){
        commentRepository.deleteById(id);
    }

}
