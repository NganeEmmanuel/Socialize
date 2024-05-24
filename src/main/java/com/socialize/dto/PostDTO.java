package com.socialize.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private UserDTO user;
    private String content;
    private byte[] mediaContent;
    private String mediaType;
    private String mediaName;
    private Integer totalReactions;
    private Integer totalComments;

    public void setUserId(Long id) {
    }
    // Additional fields as needed
}