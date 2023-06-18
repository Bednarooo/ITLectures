package com.example.itlectures.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
  private String description;
  @Column(nullable = false)
  private LocalDateTime startTime;
  @Column(nullable = false)
  private LocalDateTime endTime;

  @ManyToMany(mappedBy = "lectures")
  @Builder.Default
  private List<User> users = new ArrayList<>();
}
