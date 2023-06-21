package com.example.itlectures.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "user_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends AbstractEntity {

  @Column(nullable = false, unique = true, updatable = false)
  private String login;

  @Column(nullable = false)
  private String email;
}
