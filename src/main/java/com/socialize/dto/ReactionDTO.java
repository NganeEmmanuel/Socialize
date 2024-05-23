package com.socialize.dto;

import com.socialize.enums.ReactionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDTO {
    private Long id;
    private ReactionType reaction;
    private UserDTO user;
    private PostDTO post;
    private CommentDTO comment;
    // Additional fields as needed
}