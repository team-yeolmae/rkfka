package org.rkfka.board.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {

    @Id
    @GeneratedValue //고유 식별자 //JPA에서 엔티티 클래스의 주요 키(Primary Key) 값을 자동으로 생성하기 위해 사용
    private Long postId;

    private String title;
    private String content;

}
