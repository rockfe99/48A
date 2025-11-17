package com.example.movieapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movie_member")
public class MovieMember {

    @Id
    @Column(length = 30)
    private String id;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int enabled = 1;

    @Column(length = 30)
    private String rolename = "ROLE_USER";

    public MovieMember() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getEnabled() { return enabled; }
    public void setEnabled(int enabled) { this.enabled = enabled; }

    public String getRolename() { return rolename; }
    public void setRolename(String rolename) { this.rolename = rolename; }
}
