package com.socialize.model;

import com.socialize.enums.ReactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ReactionType reaction;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date likeDate;

    public Reaction(ReactionType reaction, User user){
        this.reaction = reaction;
        this.user = user;
    }

    @PrePersist
    protected void onCreate(){
        this.likeDate = new Date();
    }
}
