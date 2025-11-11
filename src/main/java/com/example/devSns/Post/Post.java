package com.example.devSns.Post;

import com.example.devSns.Comment.Comment;
import com.example.devSns.Member.Member;
import com.example.devSns.Post.Dto.UpdatePostRequestDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키 생성 전략

    @Lob
    @Column( nullable = false)
    private String content;

    @Column(nullable = false)
    private String userName;

    private LocalDateTime createdAt; // 생성 시점

    private LocalDateTime updatedAt; // 수정되는 시점에 변경

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long likeCount;

    public void Update(UpdatePostRequestDto Dto){
        this.content = Dto.content();
        this.userName = Dto.username();
        this.updatedAt = LocalDateTime.now();
    }
    public Post(String content, String userName, Long likeCount) {
        this.content = content;
        this.userName = userName;
        this.likeCount = likeCount;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public void writePost(Member member){
        this.member = member;
    }
    public void updateLikeCount(Long count) {
        this.likeCount = count;
    }

}
