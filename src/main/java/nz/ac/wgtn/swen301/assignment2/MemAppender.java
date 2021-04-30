package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MemAppender extends AppenderSkeleton implements MemAppenderMBean {

    private static String name = "";
    private static final long maxSize = 1000;
    private List<LoggingEvent> logEvents = new ArrayList<>();

    private String[] logs = getLogs();

    private int discardedLogCount = 0;

    @Override
    public void append(LoggingEvent loggingEvent) {
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

    /**
     * Get the current logs in a string JSONLayout format - Needed for MBeans
     * @return array of strings
     */
    @Override
    public String[] getLogs() {

        JSONLayout layout = new JSONLayout();
        String[] logArray = new String[logEvents.size()];

        for(int i = 0; i < logArray.length; i++){
            logArray[i] = layout.format(logEvents.get(i));
        }
        return logArray;
    }

    /**
     * Get the number of logs recorded - Needed for MBeans
     * @return log count - long
     */
    @Override
    public long getLogCount() {
        return logEvents.size();
    }

    /**
     * Get the number of logs that have overflowed from the logs recorded
     * @return discarded log count - long
     */
    public long getDiscardedLogCount(){
        return this.discardedLogCount;
    }

    /**
     * Get the current logs recorded
     * @return list LoggingEvent(s)
     */
    public List<LoggingEvent> getCurrentLogs(){
        final List<LoggingEvent> currentLogs = logEvents;
        return currentLogs;
    }

    /**
     * Remove the oldest log from the logEvents list and increment the discardedlog count
     */
    private void removeOldest(){
        logEvents.remove(0);
        this.discardedLogCount++;
    }

    /**
     * Export the logs recorded to a file named by the input String
     * @param fileName
     */
    public void exportToJSON(String fileName){
        try(FileWriter file = new FileWriter(fileName)){
            file.write(eventsToString(logEvents));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Format logEvents string in JSON format
     * @param logEvents
     * @return String to JSON file
     */
    private String eventsToString(List<LoggingEvent> logEvents){
        String output = "[";
        for(int i = 0; i < logEvents.size(); i++){
            if(logEvents.get(i) != null) output += eventToString(logEvents.get(i));
            if(i != logEvents.size()-1) output += ",";
        }
        output += "\n]";
        return output;
    }

    /**
     * Format each specific LoggingEvent in JSON format
     * @param logEvent
     * @return logEvent JSON String
     */
    public String eventToString(LoggingEvent logEvent){
        return "\n\t{\n" +
                "\t\t\"logger\":\"" + logEvent.getLoggerName() + "\",\n" +
                "\t\t\"level\":\"" + logEvent.getLevel() + "\",\n" +
                "\t\t\"starttime\":\"" + logEvent.getTimeStamp() + "\",\n" +
                "\t\t\"thread\":\"" + logEvent.getThreadName() + "\",\n" +
                "\t\t\"message\":\"" + logEvent.getRenderedMessage() + "\"\n" +
                "\t}";
    }

    /**
     * Get the default formatting pattern
     * @param event
     * @return default pattern
     */
    public String defaultFormatting(LoggingEvent event){
        PatternLayout layout = new PatternLayout();
        return layout.format(event);
    }

    /**
     * Read for a file and output a String of the contents
     * @param fileName
     * @return String of file contents
     */
    public String readFromFile(String fileName){
        String output = "";
        try {
            //Read file data to String output by line
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine() + '\n';
                output += data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Create a LoggingEvent with a random Level and corresponding Message
     * @return random LoggingEvent
     */
    public LoggingEvent randomEvent(){
        Logger logger =  Logger.getLogger("org.apache.log4j.Test");
        //Randomise the Logger Level
        Random r = new Random();
        Level[] levels = {Level.TRACE, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR, Level.FATAL};
        Level level = levels[r.nextInt(levels.length)];
        //Return the created LoggingEvent
        return new LoggingEvent("org.apache.logging.log4j", logger, System.currentTimeMillis(),
                level, "Message: Level - " + level, null);
    }
}