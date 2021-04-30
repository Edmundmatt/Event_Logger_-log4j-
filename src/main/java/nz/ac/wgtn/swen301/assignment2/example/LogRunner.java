package nz.ac.wgtn.swen301.assignment2.example;

import nz.ac.wgtn.swen301.assignment2.MemAppender;
import org.apache.log4j.spi.LoggingEvent;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class LogRunner {
    /**
     * Run for 2 minutes, generate one new log event per second
     *
     */


    public static void main(String[] args) throws InterruptedException {
        MemAppender appender = new MemAppender();

        //MBeans initialisation
        try{
            ObjectName objectName = new ObjectName("nz.ac.wgtn.swen301.assignment2:type=MemAppender");
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            server.registerMBean(appender, objectName);
        }catch(MalformedObjectNameException | InstanceAlreadyExistsException |
                MBeanRegistrationException | NotCompliantMBeanException e){
            e.printStackTrace();
        }

        //Run for 2 minutes
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 120000;
        while(System.currentTimeMillis() < endTime){
            LoggingEvent event = appender.randomEvent();
            appender.append(event);
            Thread.sleep(1000);
            System.out.println(appender.getLogCount());
            System.out.println(appender.defaultFormatting(event));

        }
    }
}
