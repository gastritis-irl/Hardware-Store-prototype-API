package edu.bbte.idde.bfim2114.springbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DatabaseBackupService {

    private static final String BACKUP_COMMAND = "mysqldump -u %s -p%s %s > %s";
    private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    private static final String BACKUP_DIR = "/path/to/your/backup/directory";

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.database}")
    private String database;

    public void backupDatabase() throws IOException,InterruptedException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        String backupFile = String.format("%s/backup_%s.sql", BACKUP_DIR, timestamp);
        String escapedPassword = password.replace("$", "\\$"); // Escape special characters, such as $
        String command = String.format(BACKUP_COMMAND, username, escapedPassword, database, backupFile);

        ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // It's a good practice to read the output of the process
        try (InputStream inputStream = process.getInputStream()) {
            String output = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println(output); // or log it
        }

        // Wait for the process to finish
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Backup process exited with error code: " + exitCode);
        }
    }
}
