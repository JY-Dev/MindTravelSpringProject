package com.jydev.mindtravel.external.fcm;

import lombok.Getter;

@Getter
public class FcmPayload {
    private final FcmService fcmService;
    private final String title;
    private final String content;
    private Boolean isContainData;

    public FcmPayload(FcmService fcmService, String title, String content) {
        this.fcmService = fcmService;
        this.title = title;
        this.content = content;
    }

    public void setIsContainData(Boolean isContainData) {
        this.isContainData = isContainData;
    }
}
