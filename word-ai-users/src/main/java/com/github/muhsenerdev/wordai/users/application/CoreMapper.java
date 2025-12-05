package com.github.muhsenerdev.wordai.users.application;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.commons.jpa.BaseEmbeddableId;
import com.github.muhsenerdev.commons.jpa.Username;

@Component
public class CoreMapper {

    public <T> T fromBaseId(BaseEmbeddableId<T> baseEmbeddableId) {
        return baseEmbeddableId == null ? null : baseEmbeddableId.getValue();
    }

    public String fromUsernameToString(Username username) {
        return username == null ? null : username.getValue();
    }
}
