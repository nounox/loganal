package pl.nounox.interview.loganal.infrastructure.database;

import org.springframework.data.repository.CrudRepository;

import pl.nounox.interview.loganal.domain.LogEntry;

public interface LogEntryRepository extends CrudRepository<LogEntry, String> {

}
