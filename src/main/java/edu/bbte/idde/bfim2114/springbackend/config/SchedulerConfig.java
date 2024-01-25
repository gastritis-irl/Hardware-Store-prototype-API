package edu.bbte.idde.bfim2114.springbackend.config;

import edu.bbte.idde.bfim2114.springbackend.service.DatabaseBackupService;
import edu.bbte.idde.bfim2114.springbackend.service.HardwareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
@Slf4j
@EnableAsync
@RequiredArgsConstructor
public class SchedulerConfig {

    private final HardwareService hardwarePartService;
    private final DatabaseBackupService databaseBackupService;

    @Scheduled(fixedRate = 30000)
    public void backupDatabase() {
        try {
            databaseBackupService.backupDatabase();
        } catch (IOException|InterruptedException e) {
            log.error("Error while backing up database", e);
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    public void clearUnassociatedParts() {
        log.info("Clearing unassociated parts");
        hardwarePartService.clearUnassociatedParts();
    }
    @Scheduled(fixedRate = 10000)
    public void scheduleTaskWithFixedRate() {
        log.info("Fixed Rate Task :: Execution Time - {}", System.currentTimeMillis());
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 1000)
    public void scheduleTaskWithFixedDelay() {
        log.info("Fixed Delay Task :: Execution Time - {}", System.currentTimeMillis());
    }
}
