package com.example.demovaadin.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import com.example.demovaadin.common.JsonDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class McUser {
    @Id
    private String userId;

    private String shortName;
    private String fullName;
    @JsonIgnore
    private String encryptedPwd;
    private String email;
    private String role;
    @Column(length = 20)
    private String createdBy;

    @CreatedDate
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date createdDate;

    // @ManyToMany
    // @JoinTable(name = "users_roles",
    //         joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    //         inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    // private Collection<McRole> roles;
}
