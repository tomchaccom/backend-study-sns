package com.example.devSns.Post;

import com.example.devSns.Post.Dto.AddPostRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글이 실제 DB에 저장된다")
    void createPost_savesToDatabase() {
        // given
        AddPostRequestDto dto = new AddPostRequestDto("테스트 내용", "작성자");

        // when
        postService.createPost(dto);

        // then
        assertThat(postRepository.findAll())
                .hasSize(1)
                .first()
                .extracting(Post::getContent)
                .isEqualTo("테스트 내용");
    }
}
