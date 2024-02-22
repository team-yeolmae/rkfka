package org.rkfka.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rkfka.board.domain.dto.CreatePostRequest;
import org.rkfka.board.domain.dto.CreatePostResponse;
import org.rkfka.board.domain.dto.UpdatePostRequest;
import org.rkfka.board.domain.entity.Post;
import org.rkfka.board.repository.PostRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(Extension.class)
public class PostServiceTest {

    @Mock//가짜 객체, 테스트 실행시 실체가 아닌 Mock 객체 반환
    private PostRepository postRepository;

    @InjectMocks //Mock 객체가 주입될 클래스 지정
    private PostService postService;

    private Post savedPost;
    private Post post;
    //입력값 임의로 지정
    private CreatePostRequest createPostRequest;
    private UpdatePostRequest updatePostRequest;

    @BeforeEach
    void setup() {
        //초기화 설정
        savedPost = new Post(2L, "저장되어 있는 제목", "저장되어 있는 내용");
        post = new Post(1L, "테스트 제목", "테스트 내용");
        createPostRequest = new CreatePostRequest("테스트 제목", "테스트 내용");
        updatePostRequest = new UpdatePostRequest("변경된 테스트 제목", "변경된 테스트 내용");
    }

    @Test
    @DisplayName("게시글 등록 기능 테스트")
    void create_post_service() {

        //given
        given(postRepository.save(any())).willReturn(post);

        //when
        CreatePostResponse createPostResponse = postService.createPost(createPostRequest);

        //then
        assertThat(createPostResponse.getPostId()).isEqualTo(1L);
        assertThat(createPostResponse.getTitle()).isEqualTo("테스트 제목");
        assertThat(createPostResponse.getContent()).isEqualTo("테스트 내용");

    }




}
