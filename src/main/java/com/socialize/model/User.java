package com.socialize.model;

import com.socialize.helpers.modelHelpers.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Lob
    private byte[] profilePicture;

    private String mediaMimeType;

    private UserStatus userStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    @ManyToMany
    @JoinTable(name = "user_followers", joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<User> following = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    public User(String name, String userName, String email, String password){
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userStatus = UserStatus.PENDING;
    }

    @PrePersist
    protected void onCreate() {
        joinDate = new Date();
        lastUpdated = new Date();
    }

}



