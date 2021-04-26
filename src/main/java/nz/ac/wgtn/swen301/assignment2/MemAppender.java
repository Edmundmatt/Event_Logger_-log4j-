package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import java.util.LinkedList;
import java.util.Queue;

public class MemAppender implements Appender {

    private static String name = "default";
    private static final long maxSize = 1000;
    private Queue<LoggingEvent> logEvents = new LinkedList<>();
    private int discardedLogCount = 0;

    public void exportToJSON(String fileName){
        
    }

    @Override
    public void addFilter(Filter filter) {

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void clearFilters() {

    }

    @Override
    public void close() {

    }

    @Override
    public void doAppend(LoggingEvent loggingEvent) {
        //Add new logging event to the end of the queue
        logEvents.add(loggingEvent);
        //Check if queue is over capacity
        if(logEvents.size() > maxSize) removeOldest();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {

    }

    @Override
    public ErrorHandler getErrorHandler() {
        return null;
    }

    @Override
    public void setLayout(Layout layout) {

    }

    @Override
    public Layout getLayout() {
        return null;
    }

    @Override
    public void setName(String s) {
        this.name = s;
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public Queue<LoggingEvent> getCurrentLogs(){
        final Queue<LoggingEvent> currentLogs = logEvents;
        return currentLogs;
    }

    private void removeOldest(){
        logEvents.poll();
        this.discardedLogCount++;
    }

    public long getDiscardedLogCount(){
        return this.discardedLogCount;
    }
}
