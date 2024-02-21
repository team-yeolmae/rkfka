package org.rkfka.board.controller;

import lombok.RequiredArgsConstructor;
import org.rkfka.board.domain.dto.*;
import org.rkfka.board.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //@Controller + @Responsebody => 서버에서 클라이언트로 응답 데이터를 전송하기 위해 @ResponseBody 어노테이션을 사용하여 자바 객체를 HTTP 응답 본문의 객체로 변환하여 클라이언트로 전송
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor //final 혹은 @NonNull 어노테이션이 붙은 필드에 대한 생성자를 자동으로 생성
public class PostController {

    private final PostService postService;

    //게시긆 작성
    @PostMapping
    public ResponseEntity<CreatePostResponse> postCreate (@RequestBody CreatePostRequest request) {

        CreatePostResponse response = postService.createPost(request);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //게시글 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<ReadPostResponse> postRead (@PathVariable Long postId) {

        ReadPostResponse response = postService.readPost(postId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //게시글 업데이트
    @PutMapping("/{postId}")
    public ResponseEntity<UpdatePostResponse> postUpdate (@PathVariable Long postId, @RequestBody UpdatePostRequest request) {

        UpdatePostResponse response = postService.updatePost(postId, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<DeletePostResponse> postDelete(@PathVariable Long postId) {

        DeletePostResponse response = postService.deletePost(postId);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //게시글 전체 조회(리스트-> 페이징)
    @GetMapping
    public ResponseEntity<Page<ReadPostResponse>> readAll(
            @PageableDefault(size = 5, sort = "postID", direction = Sort.Direction.DESC)Pageable pageable
            ) {

        Page<ReadPostResponse> responses = postService.readAllPost(pageable);

        return new ResponseEntity<>(responses, HttpStatus.OK);

    }

}
