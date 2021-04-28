package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MemAppender extends AppenderSkeleton {

    private static String name = "default";
    private static final long maxSize = 1000;
    private List<LoggingEvent> logEvents = new ArrayList<>();
    private int discardedLogCount = 0;


    @Override
    protected void append(LoggingEvent loggingEvent) {
        //Add new logging event to the end of the queue
        logEvents.add(loggingEvent);
        //Check if queue is over capacity
        if(logEvents.size() > maxSize) removeOldest();
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public List<LoggingEvent> getCurrentLogs(){
        final List<LoggingEvent> currentLogs = logEvents;
        return currentLogs;
    }

    private void removeOldest(){
        logEvents.remove(logEvents.size()-1);
        this.discardedLogCount++;
    }

    public long getDiscardedLogCount(){
        return this.discardedLogCount;
    }

    public void exportToJSON(String fileName){
        try(FileWriter file = new FileWriter(fileName)){
            file.write(eventsToString(logEvents));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String eventsToString(List<LoggingEvent> logEvents){
        String output = "[\n";
        for(LoggingEvent event: logEvents){
            output += eventToString(event);
        }
        output += "]";
        return output;
    }

    private String eventToString(LoggingEvent logEvent){
        return "\t{\n" +
                "\t\t\"logger\":\"" + logEvent.getLoggerName() + "\",\n" +
                "\t\t\"level\":\"" + logEvent.getLevel() + "\",\n" +
                "\t\t\"starttime\":\"" + logEvent.getTimeStamp() + "\",\n" +
                "\t\t\"thread\":\"" + logEvent.getThreadName() + "\",\n" +
                "\t\t\"message\":\"" + logEvent.getRenderedMessage() + "\"\n" +
                "\t}\n";
    }
}