package io.pivotal.pal.tracker.dao;

import io.pivotal.pal.tracker.bean.TimeEntry;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);

    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                            "VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS
            );

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        TimeEntry timeEntry;
        try {
            timeEntry = jdbcTemplate.queryForObject("select * from time_entries where id=" + timeEntryId, mapper);
        }catch (IncorrectResultSizeDataAccessException ex){
            timeEntry = null;
        }
        return timeEntry;
    }

    @Override
    public List<TimeEntry> list() {

        List<TimeEntry> entries = jdbcTemplate.query("select * from time_entries ", mapper);
        return  entries;
    }

    @Override
    public TimeEntry update(long eq, TimeEntry any) {
        jdbcTemplate.update("UPDATE time_entries set project_id=?, user_id=?,date=?,hours=? where id=?",
                any.getProjectId(), any.getUserId(), any.getDate(), any.getHours(),eq);

        return find(eq);
    }

    @Override
    public void delete(long timeEntryId) {
        jdbcTemplate.update("DELETE FROM time_entries where id=?",timeEntryId);
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );
}
