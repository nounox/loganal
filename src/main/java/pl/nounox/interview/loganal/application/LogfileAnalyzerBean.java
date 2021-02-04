package pl.nounox.interview.loganal.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.nounox.interview.loganal.domain.LogEntry;
import pl.nounox.interview.loganal.domain.LogEvent;
import pl.nounox.interview.loganal.infrastructure.database.LogEntryRepository;

@Component
public class LogfileAnalyzerBean implements LogfileAnalyzer {
	private static final Logger logger = LoggerFactory.getLogger(LogfileAnalyzerBean.class);

	// Flag any long events that take longer than 4ms
	public static final int DURATION_ALERT_LEVEL = 4;

	@Value("${threads.number:1}")
	private int threadsNumber = 1;

	@Autowired
	private LogEntryRepository logEntryRepository;

	@Override
	public long analyze(String fileName) {
		ConcurrentMap<String, LogEvent> events = new ConcurrentHashMap<>();
		ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);
		long eventsAnalyzed = 0;
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try (FileInputStream fis = new FileInputStream(fileName); Scanner scanner = new Scanner(fis, "UTF-8")) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				LogEvent logEvent = mapper.readValue(line, LogEvent.class);
				eventsAnalyzed++;
				
				Runnable task = () -> {
					logger.debug("Analyzing event: {}", logEvent);
					LogEvent previousLogEvent = events.putIfAbsent(logEvent.getId(), logEvent);
					if (previousLogEvent != null) {
						logger.debug("Got match for: {}", logEvent.getId());
						long duration = Math.abs(logEvent.getTimestamp() - previousLogEvent.getTimestamp());
						LogEntry logEntry = new LogEntry()
								.setId(logEvent.getId())
								.setDuration(duration)
								.setAlert(duration > DURATION_ALERT_LEVEL ? true : false)
								.setHost(logEvent.getHost())
								.setType(logEvent.getType());
						
						logEntryRepository.save(logEntry);
						logger.debug("LogEntry saved: {}", logEntry);
						events.remove(logEvent.getId());
					}
				};
				
				executorService.execute(task);
			}
		} catch (IOException ex) {
			logger.error("Error while processing logfile.", ex);
		} finally {
			executorService.shutdown();
		}
		
		return eventsAnalyzed;
	}
}
