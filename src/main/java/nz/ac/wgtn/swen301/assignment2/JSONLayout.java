package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class JSONLayout extends Layout
{
    public static void main( String[] args ) {

    }

    @Override
    public String format(LoggingEvent loggingEvent) {
        //Create JSON attributes
        String logger = loggingEvent.getLoggerName();
        Level level = loggingEvent.getLevel();
        long time = loggingEvent.getStartTime();
        String thread = loggingEvent.getThreadName();
        String message = loggingEvent.getRenderedMessage();

        //Create JSON object
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("logger", logger);
        jsonObj.put("level", level);
        jsonObj.put("starttime", time);
        jsonObj.put("thread", thread);
        jsonObj.put("message", message);

        return jsonObj.toString();
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public void activateOptions() {

    }
}
