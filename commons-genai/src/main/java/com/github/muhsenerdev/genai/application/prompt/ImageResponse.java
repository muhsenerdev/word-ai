package com.github.muhsenerdev.genai.application.prompt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageResponse extends AiResponse {

    private final byte[] bytes;
    private final String mimeType;
    private final String displayName;

    @Builder(toBuilder = true)
    public ImageResponse(byte[] bytes, String mimeType, String displayName) {
        super(AiResponseType.IMAGE);
        this.bytes = bytes;
        this.mimeType = mimeType;
        this.displayName = displayName;
    }
}
