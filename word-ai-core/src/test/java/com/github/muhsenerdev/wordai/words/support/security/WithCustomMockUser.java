package com.github.muhsenerdev.wordai.words.support.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomSecurityContextFactory.class)
public @interface WithCustomMockUser {

	String userId() default "00000000-0000-0000-0000-000000000000"; // Default Mock User Id

	String username() default "mock-user";

	String[] roles() default { "USER" };

	String motherLanguage() default "TURKISH";

	String learningLanguage() default "ENGLISH";
}
