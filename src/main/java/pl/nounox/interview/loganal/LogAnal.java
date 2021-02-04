package pl.nounox.interview.loganal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pl.nounox.interview.loganal.application.LogfileAnalyzerBean;

@SpringBootApplication
public class LogAnal implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(LogAnal.class);

	@Autowired
	private LogfileAnalyzerBean logfileAnalyzer;

	public static void main(String[] args) {
		SpringApplication.run(LogAnal.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length == 0) {
			logger.warn("Usage: mvn spring-boot:run -Dspring-boot.run.arguments=<path_to_log_file>");
			return;
		}

		long eventsAnalyzed = logfileAnalyzer.analyze(args[0]);
		logger.info("Analyzed events: {}", eventsAnalyzed);
	}
}
