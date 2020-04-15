package io.pivotal.pal.tracker;

import io.pivotal.pal.tracker.dao.InMemoryTimeEntryRepository;
import io.pivotal.pal.tracker.dao.JdbcTimeEntryRepository;
import io.pivotal.pal.tracker.dao.TimeEntryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class PalTrackerApplication {
    public static void main(String[] args) {
      SpringApplication.run(PalTrackerApplication.class,args);
    }

    @Bean
    public TimeEntryRepository getTimeRepo(DataSource ds) {
        return new JdbcTimeEntryRepository(ds);
    }
}

