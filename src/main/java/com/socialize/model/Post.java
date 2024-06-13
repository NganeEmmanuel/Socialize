package com.socialize.model;

import com.socialize.enums.PostStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "posts")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(length = 5000)
    private String content;

    private String mediaFileName;

    private String mediaMimeType;

    @Lob
    @Column(name = "media_file_content", columnDefinition = "LONGBLOB")
    private byte[] mediaFileContent;

    private Integer totalReactions;

    private Integer totalComments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments;

    private PostStatus postStatus;

    @OneToMany(fetch =  FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Reaction> reactions;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEdited;

    public Post(User user, String content, byte[] mediaFileContent, String mediaFileName,
                String mediaMimeType){
        this.user = user;
        this.content = content;
        this.mediaFileContent = mediaFileContent;
        this.mediaFileName = mediaFileName;
        this.mediaMimeType = mediaMimeType;
        this.postStatus = PostStatus.ACTIVE;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment){
        this.comments.remove(comment);
        comment.setPost(null);
    }

    @PrePersist
    protected void onCreate() {
        this.addedDate = new Date();
        this.lastEdited = new Date();
    }

}
