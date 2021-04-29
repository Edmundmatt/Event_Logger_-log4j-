package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONObject;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class JSONLayout extends Layout
{
    public static void main( String[] args ) {
        Logger logger = Logger.getLogger("org.apache.log4j.Test");
        LoggingEvent event0 = new LoggingEvent("org.apache.logging.log4j", logger, System.currentTimeMillis(),
                Level.ERROR, "test message 0", null);
        LoggingEvent event1 = new LoggingEvent("org.apache.logging.log4j", logger, System.currentTimeMillis(),
                Level.ERROR, "test message 1", null);
        LoggingEvent event2 = new LoggingEvent("org.apache.logging.log4j", logger, System.currentTimeMillis(),
                Level.ERROR, "test message 2", null);

        MemAppender appender = new MemAppender();
        appender.append(event0);
        appender.append(event1);
        appender.append(event2);
        appender.exportToJSON("jsonFileTest.json");

        //JMX Agent
        try{
            ObjectName objectName = new ObjectName("nz.ac.wgtn.swen301.assignment2:type=basic,name=memAppender");
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            server.registerMBean(new MemAppender(), objectName);
        }catch(MalformedObjectNameException | InstanceAlreadyExistsException |
                MBeanRegistrationException | NotCompliantMBeanException e){
            e.printStackTrace();
        }
        while(true){}
    }

    @Override
    public String format(LoggingEvent loggingEvent) {
        //Create JSON object
        JSONObject jsonObj = new JSONObject();

        try {
            String logger = loggingEvent.getLoggerName();
            Level level = loggingEvent.getLevel();
            long time = loggingEvent.getTimeStamp();
            String thread = loggingEvent.getThreadName();
            String message = loggingEvent.getRenderedMessage();


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