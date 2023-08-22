package com.jydev.mindtravel.external.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmMessageFactory {
    private final ObjectMapper objectMapper;

    public Message getCommentMessage(FcmPayload fcmPayload, Object fcmData, String fcmToken) {
        try {
            fcmPayload.setIsContainData(fcmData != null);
            String payload = objectMapper.writeValueAsString(fcmPayload);
            String data = objectMapper.writeValueAsString(fcmData);
            return Message.builder()
                    .putData("payload", payload)
                    .putData("data", data)
                    .setToken(fcmToken)
                    .build();
        } catch (JsonProcessingException e) {
            throw new FcmException("메세지 생성 오류");
        }
    }
}
