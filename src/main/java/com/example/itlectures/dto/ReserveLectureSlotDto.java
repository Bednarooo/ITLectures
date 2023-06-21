package com.example.itlectures.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveLectureSlotDto {
  @NotNull
  @Positive
  private Long lectureId;

  @NotBlank
  private String login;

  @NotBlank
  private String email;
}
