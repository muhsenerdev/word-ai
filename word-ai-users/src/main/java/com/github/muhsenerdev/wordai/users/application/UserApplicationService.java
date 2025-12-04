package com.github.muhsenerdev.wordai.users.application;

import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.SystemException;

public interface UserApplicationService {
    UserCreationResponse createUser(CreateUserCommand command)
            throws DuplicateUserException, BusinessValidationException, SystemException;

}
