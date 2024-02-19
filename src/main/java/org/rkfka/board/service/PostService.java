package org.rkfka.board.service;

import lombok.RequiredArgsConstructor;
import org.rkfka.board.domain.dto.CreatePostRequest;
import org.rkfka.board.domain.dto.CreatePostResponse;
import org.rkfka.board.domain.entity.Post;
import org.rkfka.board.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        //입력한 데이터 저장
        Post savedPost = postRepository.save(post);

        //입력한 데이터 반환
        return new CreatePostResponse(savedPost.getPostId(), savedPost.getTitle(), savedPost.getTitle());
    }
}
