package com.github.muhsenerdev.wordai.words.application.session;

import com.github.muhsenerdev.wordai.words.domain.SessionId;

import lombok.Builder;

@Builder(toBuilder = true)
public record SessionStartResponse(SessionId sessionId) {

}
