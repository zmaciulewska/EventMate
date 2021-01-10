package com.eventmate.scheduler;

import com.eventmate.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventArchiveTask {
    private static final Logger logger = LoggerFactory.getLogger(EventArchiveTask.class);

    @Autowired
    EventService eventService;

    @Scheduled(cron = "0 14 21 * * *")
    // @Scheduled(cron = "0 0 0 * * *")
    public void executeTask() {
        logger.info("EventArchiveTask executed at {}", LocalDateTime.now());
        eventService.getExpiredEvents().stream().forEach(e -> {
            eventService.softDeleteEvent(e);
            logger.info(e.toString() + " deleted succesfully");
        });
        logger.info("EventArchiveTask finished at {}", LocalDateTime.now());
    }
}
