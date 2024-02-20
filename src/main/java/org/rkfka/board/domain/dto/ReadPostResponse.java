package org.rkfka.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReadPostResponse {

    private Long postId;
    private String title;
    private String content;

}
