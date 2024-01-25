package edu.bbte.idde.bfim2114.springbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public void backupDatabase() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        String backupFile = String.format("%s/backup_%s.sql", BACKUP_DIR, timestamp);
        String command = String.format(BACKUP_COMMAND, username, password, database, backupFile);

        ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", command);
        processBuilder.start();
    }
}
