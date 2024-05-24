package com.socialize.model;

import com.socialize.enums.CommentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name ="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Post post;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private User user;

    private Integer parentCommentId;

    @Column(length = 5000)
    private String content;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "comment")
    private List<Reaction> reactions;

    private Integer totalReactions;

    private CommentStatus commentStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date commentedDate;

    public Comment(Post post, User user, Integer parentCommentId, String content){
        this.user = user;
        this.post = post;
        this.parentCommentId = parentCommentId;
        this.content = content;
        this.commentStatus = CommentStatus.ACTIVE;
    }

    @PrePersist
    protected void onCreate() {
        this.commentedDate = new Date();
    }

}
