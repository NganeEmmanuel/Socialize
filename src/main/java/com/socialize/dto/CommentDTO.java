package com.socialize.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private Integer parentCommentId;
    private Integer totalReaction;
    private UserDTO user;
    private PostDTO post;
    // Additional fields as needed
}