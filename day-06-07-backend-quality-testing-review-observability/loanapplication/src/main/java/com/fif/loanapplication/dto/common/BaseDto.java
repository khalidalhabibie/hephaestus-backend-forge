package com.fif.loanapplication.dto.common;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseDto {
    @JsonProperty("uid")
    UUID uid;
    @JsonProperty("created_at")
    ZonedDateTime createdAt;
    @JsonProperty("updated_at")
    ZonedDateTime updatedAt;
}
