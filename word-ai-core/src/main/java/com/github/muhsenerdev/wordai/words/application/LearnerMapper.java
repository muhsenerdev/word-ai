package com.github.muhsenerdev.wordai.words.application;

import java.util.UUID;

import org.mapstruct.Mapper;

import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.wordai.words.domain.Learner;

@Mapper(componentModel = "spring")
public interface LearnerMapper {

    LearnerCreationResponse toResponse(Learner learner);

    default UUID map(UserId value) {
        return value.getValue();
    }

}
