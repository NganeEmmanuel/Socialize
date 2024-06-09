package com.socialize.dto;

import com.socialize.enums.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
 public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private UserAuthority authority;
    private byte[] profilePicture;
    private String mediaMimeType;

    public UserDTO(String userNotFound) {
        this.name = userNotFound;
    }
    // Additional fields as needed
}