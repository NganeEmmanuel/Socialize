package com.socialize.dto;

import com.socialize.enums.UserAuthority;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private UserAuthority authority;
    private byte[] profilePicture;
    private String mediaMimeType;
    // Additional fields as needed
}
