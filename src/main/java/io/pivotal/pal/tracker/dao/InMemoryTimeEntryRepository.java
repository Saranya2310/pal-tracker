package io.pivotal.pal.tracker.dao;

import io.pivotal.pal.tracker.bean.TimeEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    Map<Long, TimeEntry> timeEntries = new HashMap<>();
    long i=0L;

    public TimeEntry create(TimeEntry timeEntry) {

        i++;
        timeEntry.setId(i);
        timeEntries.put(i, timeEntry );
        return timeEntry;
    }

    public TimeEntry find(long id) {

        return timeEntries.get(id);
    }

    public List<TimeEntry> list() {

        List<TimeEntry> timeEntryList = new ArrayList<>();

        for(Long id: timeEntries.keySet()) {
            timeEntryList.add(timeEntries.get(id));
        }

        return timeEntryList;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {

        if(timeEntries.get(id)==null){
            return null;
        }

        timeEntry.setId(id);
        timeEntries.put(id, timeEntry);
        return timeEntry;
    }

    public void delete(long id){

        timeEntries.remove(id);

    }
}