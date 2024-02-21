package org.rkfka.board.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.rkfka.board.domain.dto.*;
import org.rkfka.board.domain.entity.Post;
import org.rkfka.board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {

        //데이터 입력
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        //입력한 데이터 저장
        Post savedPost = postRepository.save(post);

        //입력한 데이터 반환
        return new CreatePostResponse(savedPost.getPostId(), savedPost.getTitle(), savedPost.getTitle());
    }

    public ReadPostResponse readPost(Long postId) {

        //postId로 조회
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 postId로 조회된 게시글이 없습니다."));

        return new ReadPostResponse(foundPost.getPostId(), foundPost.getTitle(), foundPost.getContent());
    }

    @Transactional
    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest request) {

        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 postId로 조회된 게시글이 없습니다."));

        //Post entity클래스에 update 메소드를 정의해 둠.
        foundPost.update(request.getTitle(), request.getContent());

        return new UpdatePostResponse(foundPost.getPostId(), foundPost.getTitle(), foundPost.getContent());

    }

    @Transactional
    public DeletePostResponse deletePost(Long postId) {

        Post founfPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 postId로 조회된 게시글이 없습니다."));

        postRepository.delete(founfPost);

        return new DeletePostResponse(founfPost.getPostId());

    }

    public Page<ReadPostResponse> readAllPost(Pageable pageable) {

        Page<Post> postsPage = postRepository.findAll(pageable);

        return postsPage.map(post -> new ReadPostResponse(post.getPostId(), post.getTitle(), post.getContent()));

    }
}
