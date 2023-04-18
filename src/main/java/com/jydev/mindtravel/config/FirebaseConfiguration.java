package com.jydev.mindtravel.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class FirebaseConfiguration {

    @PostConstruct
    public void initFirebaseInstance() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("firebase_admin_sdk.json");
        FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
        FirebaseApp.initializeApp(options);
        log.info("Firebase Init");
    }
}
