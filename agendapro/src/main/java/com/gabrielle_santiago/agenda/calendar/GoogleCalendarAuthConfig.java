package com.gabrielle_santiago.agenda.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Configuration
public class GoogleCalendarAuthConfig {

    @Value("${google.calendar.application.name}")
    private String applicationName;

    @Value("${google.calendar.tokens.directory}")
    private String tokensDirectoryPath;

    @Value("${google.calendar.credentials.path}")
    private String credentialsFilePath;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);
    private final HttpTransport httpTransport;
    private FileDataStoreFactory dataStoreFactory;

    public GoogleCalendarAuthConfig() throws GeneralSecurityException, IOException {
        this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    }

    @PostConstruct
    public void init() throws IOException {
        this.dataStoreFactory = new FileDataStoreFactory(new File(tokensDirectoryPath));
    }

    @Bean
    public GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow() throws IOException {
        InputStream in = getClass().getResourceAsStream(credentialsFilePath);
        if (in == null) {
            throw new IOException("Credentials file not found: " + credentialsFilePath);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(dataStoreFactory)
                .setAccessType("offline")
                .build();
    }

    public Credential getCredentialForUser(String userId) throws IOException {
        GoogleAuthorizationCodeFlow flow = googleAuthorizationCodeFlow();
        Credential credential = flow.loadCredential(userId);

        if (credential == null || (credential.getRefreshToken() == null)) {
            LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                                                                    .setPort(8081)
                                                                    .setCallbackPath("/oauth2callback")
                                                                    .build();
            credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize(userId);
            System.out.println("Credentials saved for the user " + userId + " in: " + new File(tokensDirectoryPath, userId).getAbsolutePath());
        }
        return credential;
    }


    public Calendar getCalendarService(String userId) throws IOException {
        Credential credential = getCredentialForUser(userId);
        return new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(applicationName)
                .build();
    }
}