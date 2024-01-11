package id.kevinchristian.snap.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {
    // @Bean
    // FirebaseApp FirebaseApp(GoogleCredentials credentials) throws IOException {
    //     FileInputStream refreshToken = new FileInputStream(
    //             "main/resources/firebase/snap-346c3-firebase-adminsdk-5f4va-668a0972d5.json");

    //     FirebaseOptions options = FirebaseOptions.builder()
    //             .setCredentials(GoogleCredentials.fromStream(refreshToken))
    //             .build();

    //     return FirebaseApp.initializeApp(options);
    // }
}
