package org.rkfka.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.rkfka.board.domain.dto.*;
import org.rkfka.board.domain.entity.Post;
import org.rkfka.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    /* 테스트 시 사용하는 구조 : given-when-then */
    @Test
    @DisplayName("게시글 작성 기능 테스트")
    void create_post_test() throws Exception{

        //given : 등록할 때 필요한 데이터와 보여주는 response를 임의로 둠.

        CreatePostRequest request = new CreatePostRequest("테스트 제목", "테스트 내용");
        CreatePostResponse response = new CreatePostResponse(1L, "테스트 제목", "테스트 내용");

        given(postService.createPost(any())).willReturn(response);

        //when&then

        mockMvc.perform(post("/api/v1/posts")/* 테스트 하려는 환경을 어디서 할 것인지에 대한 경로 */
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(1L))
                .andExpect(jsonPath("$.title").value("테스트 제목"))
                .andExpect(jsonPath("$.content").value("테스트 내용"))
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 단일 조회 기능 테스트")
    void read_post_test() throws Exception{

        //given
        Long postId = 1L;
        ReadPostResponse response = new ReadPostResponse(1L, "테스트 제목", "테스트 내용");

        given(postService.readPost(any())).willReturn(response);

        //when&then
        mockMvc.perform(get("/api/v1/posts/{postId}",postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(1L))
                .andExpect(jsonPath("$.title").value("테스트 제목"))
                .andExpect(jsonPath("$.content").value("테스트 내용"))
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 업데이트 기능 테스트")
    void update_post_test() throws Exception{

        //given
        Long postId = 1L;

        UpdatePostRequest request = new UpdatePostRequest("변경 제목","변경 내용");
        UpdatePostResponse response = new UpdatePostResponse(1L,"변경 제목","변경 내용");


        //PostService의 updatePost(Long postId, UpdateRequest request)
        given(postService.updatePost(any(),any())).willReturn(response);

        //when
        mockMvc.perform(put("/api/v1/posts/{postId}",postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(1L))
                .andExpect(jsonPath("$.title").value("변경 제목"))
                .andExpect(jsonPath("$.content").value("변경 내용"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제 기능 테스트")
    void delete_post_test() throws Exception{

        //given
        Long postId = 1L;

        DeletePostResponse response = new DeletePostResponse(1L);

        given(postService.deletePost(any())).willReturn(response);

        //when&then
        mockMvc.perform(delete("/api/v1/posts/{postId}",postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(1L))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글을 전체 조회하는 테스트")
    void read_all_post_test() throws Exception{

        //given
        //하나의 페이지의 페이징 처리
        //페이지를 어떻게 정보를 몇개씩 페이징 할 것이냐
        Pageable pageable = PageRequest.of(0,5);
        //정보
        ReadPostResponse readPostResponse = new ReadPostResponse(1L, "리스트 제목", "리스트 내용");

        //정보 묶기//response들
        List<ReadPostResponse> responses = new ArrayList<>();
        responses.add(readPostResponse);

        Page<ReadPostResponse> responsePage = new PageImpl<>(responses, pageable, responses.size());

        given(postService.readAllPost(any())).willReturn(responsePage);

        //when
        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(jsonPath("$.content[0].postId").value(1L))
                .andExpect(jsonPath("$.content[0].title").value("리스트 제목"))
                .andExpect(jsonPath("$.content[0].content").value("리스트 내용"))
                .andDo(print());

    }


}
