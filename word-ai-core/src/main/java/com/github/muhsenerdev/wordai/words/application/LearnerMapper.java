package com.github.muhsenerdev.wordai.words.application;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.wordai.words.domain.Learner;

@Component
public class LearnerMapper {

    public LearnerCreationResponse toResponse(Learner learner) {
        if (learner == null) {
            return null;
        }
        return LearnerCreationResponse.builder()
                .userId(learner.getUserId() != null ? learner.getUserId().getValue() : null).build();
    }

    public UUID map(UserId value) {
        return value != null ? value.getValue() : null;
    }

}
