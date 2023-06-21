package com.example.itlectures.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lecture extends AbstractEntity {

  @Column(nullable = false, unique = true, updatable = false)
  private String title;
  @Column(nullable = false)
  private LocalDateTime startTime;
  @Column(nullable = false)
  private LocalDateTime endTime;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      name = "user_lecture",
      joinColumns = { @JoinColumn(name = "lecture_id") },
      inverseJoinColumns = { @JoinColumn(name = "user_id") })
  @Builder.Default
  private Set<User> users = new HashSet<>();
}
