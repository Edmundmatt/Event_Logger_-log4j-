package nz.ac.wgtn.swen301.assignment2.example;

import nz.ac.wgtn.swen301.assignment2.JSONLayout;
import nz.ac.wgtn.swen301.assignment2.MemAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Random;

public class LogRunner {
    /**
     * Run for 2 minutes, generate one new log event per second
     *
     */

    private static Logger LOGGER = Logger.getLogger("org.apache.log4j.Test");
    public static void main(String args[]) throws InterruptedException {
        MemAppender appender = new MemAppender();
        JSONLayout layout = new JSONLayout();

        //MBeans initialisation
        try{
            ObjectName objectName = new ObjectName("nz.ac.wgtn.swen301.assignment2:type=basic,name=MemAppender");
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            server.registerMBean(new MemAppender(), objectName);
        }catch(MalformedObjectNameException | InstanceAlreadyExistsException |
                MBeanRegistrationException | NotCompliantMBeanException e){
            e.printStackTrace();
        }

        //Run for 2 minutes
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 120000;
        while(System.currentTimeMillis() < endTime){
            appender.append(randomEvent());
            Thread.sleep(1000);
            System.out.println(appender.getLogCount());
        }
    }

    private static LoggingEvent randomEvent(){
        Random r = new Random();
        Level[] levels = {Level.ERROR, Level.WARN};
        Level level = levels[r.nextInt(levels.length)];
        LoggingEvent event = new LoggingEvent("org.apache.logging.log4j", LOGGER, System.currentTimeMillis(),
                level, "Message: Level - " + level, null);

        return event;
    }
}
