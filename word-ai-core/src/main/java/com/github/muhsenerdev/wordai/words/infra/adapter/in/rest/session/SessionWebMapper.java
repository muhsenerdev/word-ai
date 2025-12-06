package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.session;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.wordai.words.application.session.SessionStartResponse;
import com.github.muhsenerdev.wordai.words.application.session.StartSessionCommand;
import com.github.muhsenerdev.wordai.words.application.shared.WordVoMapper;
import com.github.muhsenerdev.wordai.words.infra.config.SecurityPrincipal;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SessionWebMapper {

	private final WordVoMapper voMapper;

	public StartSessionCommand toStartSessionCommand(SecurityPrincipal principal) {
		if (principal == null) {
			return null;
		}
		return StartSessionCommand.builder().userId(principal.getUserId()).motherLanguage(principal.getMotherLanguage())
				.learningLanguage(principal.getLearningLanguage()).build();
	}

	public StartSessionOutput toStartSessionOutput(SessionStartResponse response) {
		if (response == null) {
			return null;
		}
		return StartSessionOutput.builder().sessionId(voMapper.fromId(response.sessionId())).build();
	}
}
