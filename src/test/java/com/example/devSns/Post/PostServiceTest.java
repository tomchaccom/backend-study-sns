package com.example.devSns.Post;

import com.example.devSns.Comment.CommentRepository;
import com.example.devSns.Post.Dto.AddPostRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("게시글 생성 로직이 Repository.save()를 호출한다")
    void createPost_callsRepositorySave() {
        // given
        AddPostRequestDto dto = new AddPostRequestDto("내용입니다", "홍길동");

        // when
        postService.createPost(dto);

        // then
        //
        verify(postRepository, times(1)).save(any(Post.class));
    }
}
