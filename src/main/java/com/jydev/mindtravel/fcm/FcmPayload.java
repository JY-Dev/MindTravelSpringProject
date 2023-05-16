package com.jydev.mindtravel.fcm;

import lombok.Getter;

@Getter
public class FcmPayload {
    private FcmService fcmService;
    private String title;
    private String content;
    private Boolean isContainData;

    public FcmPayload(FcmService fcmService, String title, String content){
        this.fcmService = fcmService;
        this.title = title;
        this.content = content;
    }

    public void setIsContainData(Boolean isContainData) {
        this.isContainData = isContainData;
    }
}
