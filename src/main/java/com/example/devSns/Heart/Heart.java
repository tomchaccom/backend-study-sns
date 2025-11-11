package com.example.devSns.Heart;

import com.example.devSns.Member.Member;
import com.example.devSns.Post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 하나의 포스트 id를 가진 인스턴스의 수가 좋아요가 됨. 이를 영속화 단계에서 계산하는 게 좋을 거 같은데.
    // 리포지터리에 메소드로 계산하면 되겠지?

    @Column(name ="like_status",  nullable = false)
    @Enumerated(EnumType.STRING)
    private LikeStatus like;

    public Heart(Post post, Member member, LikeStatus heart) {
        this.post = post;
        this.member = member;
        this.like = heart;
    }

    public void toggleLike() {

        if (like == LikeStatus.LIKE) {
            like = LikeStatus.NONE;
        }else if(like == LikeStatus.NONE){
            like = LikeStatus.LIKE;
        }
    }
}
