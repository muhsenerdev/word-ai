package com.github.muhsenerdev.wordai.words.application;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.exception.ResourceNotFoundException;
import com.github.muhsenerdev.commons.jpa.UserId;

public class LearnerNotFoundException extends ResourceNotFoundException

{

	public LearnerNotFoundException(UserId userId) {
		this(userId, "learner.not-found");
	}

	public LearnerNotFoundException(UserId userId, String code) {
		super("learner", "userId", Assert.notNull(userId, "userId"), code);
	}

}
