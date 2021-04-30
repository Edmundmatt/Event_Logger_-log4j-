package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONObject;


public class JSONLayout extends Layout {

    @Override
    public String format(LoggingEvent loggingEvent) {
        //Create JSON object
        JSONObject jsonObj = new JSONObject();

        try {
            //Get the JSONObject fields
            String logger = loggingEvent.getLoggerName();
            Level level = loggingEvent.getLevel();
            long time = loggingEvent.getTimeStamp();
            String thread = loggingEvent.getThreadName();
            String message = loggingEvent.getRenderedMessage();

            //Input the JSONObject fields
            jsonObj.put("logger", logger);
            jsonObj.put("level", level);
            jsonObj.put("starttime", time);
            jsonObj.put("thread", thread);
            jsonObj.put("message", message);
        }catch(Exception e){
            e.printStackTrace();
        }

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