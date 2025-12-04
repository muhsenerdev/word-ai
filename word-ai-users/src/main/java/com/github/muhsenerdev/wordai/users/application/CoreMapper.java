package com.github.muhsenerdev.wordai.users.application;

import org.mapstruct.Mapper;

import com.github.muhsenerdev.commons.jpa.BaseEmbeddableId;
import com.github.muhsenerdev.commons.jpa.Username;

@Mapper(componentModel = "spring")
public interface CoreMapper {

    default <T> T fromBaseId(BaseEmbeddableId<T> baseEmbeddableId) {
        return baseEmbeddableId == null ? null : baseEmbeddableId.getValue();
    }

    default String fromUsernameToString(Username username) {
        return username == null ? null : username.getValue();
    }
}
