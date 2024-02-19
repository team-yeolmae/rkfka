package org.rkfka.board.controller;

import lombok.RequiredArgsConstructor;
import org.rkfka.board.domain.dto.CreatePostRequest;
import org.rkfka.board.domain.dto.CreatePostResponse;
import org.rkfka.board.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //@Controller + @Responsebody => 서버에서 클라이언트로 응답 데이터를 전송하기 위해 @ResponseBody 어노테이션을 사용하여 자바 객체를 HTTP 응답 본문의 객체로 변환하여 클라이언트로 전송
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor //final 혹은 @NonNull 어노테이션이 붙은 필드에 대한 생성자를 자동으로 생성
public class PostController {

    private PostService postService;

    @PostMapping
    private ResponseEntity<CreatePostResponse> postCreate (@RequestBody CreatePostRequest request) {

        CreatePostResponse response = postService.createPost(request);

        return new ResponseEntity<>(response);

    }
}
