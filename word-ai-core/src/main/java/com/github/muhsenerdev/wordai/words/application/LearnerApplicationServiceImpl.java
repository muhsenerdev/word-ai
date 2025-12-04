package com.github.muhsenerdev.wordai.words.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.muhsenerdev.commons.core.DomainException;
import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.SystemException;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.wordai.words.domain.Language;
import com.github.muhsenerdev.wordai.words.domain.Learner;
import com.github.muhsenerdev.wordai.words.domain.LearnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LearnerApplicationServiceImpl implements LearnerApplicationService {

    private final LearnerRepository learnerRepository;
    private final LearnerMapper learnerMapper;

    @SuppressWarnings("null")
    @Override
    @Transactional
    public LearnerCreationResponse create(CreateLearnerCommand command) {
        try {

            // 1. Create VOs
            UserId userId = UserId.of(command.userId());
            Language motherLanguage = Language.fromString(command.motherLanguage());
            Language targetLanguage = Language.fromString(command.targetLanguage());

            // 2. Create Learner
            Learner learner = Learner.of(userId, motherLanguage, targetLanguage);

            // 3. Save Learner
            learnerRepository.save(learner);

            // 4. Map Learner to Response
            return learnerMapper.toResponse(learner);

        } catch (DomainException e) {
            throw new BusinessValidationException(e.getMessage(), e.getErrorCode(), e);
        } catch (Exception e) {
            throw new SystemException("Failed to create learner, due to: " + e.getMessage(), e);
        }
    }
}
