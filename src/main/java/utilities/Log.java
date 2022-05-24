package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	static {
		SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy_HH-mm-ss");
		System.setProperty("current.date.time", df.format(new Date()));
	}
	
	//Initialize Log4j instance
	private static Logger log = LogManager.getLogger(Log.class.getName());
	
	//starting tests
	public static void startLog(String testClassName) {
		log.info("Starting Testing ..." + testClassName);
	}
	
	//ending testing
	public static void endLog(String testClassName) {
		log.info("Ending Testing ..." + testClassName);
	}
	
	// info level logs
	public static void info(String message) {
		log.info(message);
	}
	
	// warn level logs
	public static void warm(String message) {
		log.warn(message);
	}
	
	// fatal level logs
	public static void fatal(String message) {
		log.fatal(message);
	}
	
	// debug level logs
	public static void debug(String message) {
		log.debug(message);
	}
}