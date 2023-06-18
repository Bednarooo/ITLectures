package com.example.itlectures.config;

import com.example.itlectures.model.Lecture;
import com.example.itlectures.repository.api.LectureRepository;
import com.example.itlectures.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(LectureRepository lectureRepository) {

    return args -> {
      log.info("Preloading " + lectureRepository.save(Lecture.builder()
          .title("Digital Workplace Experience: Virtual Conference Series")
          .startTime(DateUtils.getLocalDateTimeFor1stJune1000())
          .endTime(DateUtils.getLocalDateTimeFor1stJune1145())
          .build()));

      log.info("Preloading " + lectureRepository.save(Lecture.builder()
          .title("Adobe Summit")
          .startTime(DateUtils.getLocalDateTimeFor1stJune1200())
          .endTime(DateUtils.getLocalDateTimeFor1stJune1345())
          .build()));

      log.info("Preloading " + lectureRepository.save(Lecture.builder()
          .title("IBM Think")
          .startTime(DateUtils.getLocalDateTimeFor1stJune1400())
          .endTime(DateUtils.getLocalDateTimeFor1stJune1545())
          .build()));

      log.info("Preloading " + lectureRepository.save(Lecture.builder()
          .title("MarketplaceLIVE")
          .startTime(DateUtils.getLocalDateTimeFor1stJune1000())
          .endTime(DateUtils.getLocalDateTimeFor1stJune1145())
          .build()));

      log.info("Preloading " + lectureRepository.save(Lecture.builder()
          .title("Digital Enterprise Show")
          .startTime(DateUtils.getLocalDateTimeFor1stJune1200())
          .endTime(DateUtils.getLocalDateTimeFor1stJune1345())
          .build()));

      log.info("Preloading " + lectureRepository.save(Lecture.builder()
          .title("Dublin Tech Summit")
          .startTime(DateUtils.getLocalDateTimeFor1stJune1400())
          .endTime(DateUtils.getLocalDateTimeFor1stJune1545())
          .build()));

      log.info("Preloading " + lectureRepository.save(Lecture.builder()
          .title("TNW (The Next Web)")
          .startTime(DateUtils.getLocalDateTimeFor1stJune1000())
          .endTime(DateUtils.getLocalDateTimeFor1stJune1145())
          .build()));

      log.info("Preloading " + lectureRepository.save(Lecture.builder()
          .title("Gartner Digital Workplace Summit")
          .startTime(DateUtils.getLocalDateTimeFor1stJune1200())
          .endTime(DateUtils.getLocalDateTimeFor1stJune1345())
          .build()));

      log.info("Preloading " + lectureRepository.save(Lecture.builder()
          .title("Gartner Data & Analytics Summit")
          .startTime(DateUtils.getLocalDateTimeFor1stJune1400())
          .endTime(DateUtils.getLocalDateTimeFor1stJune1545())
          .build()));
    };
  }
}
