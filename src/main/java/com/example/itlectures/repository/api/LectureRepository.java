package com.example.itlectures.repository.api;

import com.example.itlectures.model.Lecture;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
  Optional<Lecture> findById(Long id);
  List<Lecture> findAllByUsersLogin(String login);
}
