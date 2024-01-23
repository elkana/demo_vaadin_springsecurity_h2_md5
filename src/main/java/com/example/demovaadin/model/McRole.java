package com.example.demovaadin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// @Entity
public class McRole {
    @Id
    private String roleId;

    @Column(nullable = false, unique = true)
    private String name;
}
