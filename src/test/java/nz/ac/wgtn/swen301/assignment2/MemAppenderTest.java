package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONTokener;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MemAppenderTest {

    private static Logger LOGGER = Logger.getLogger("org.apache.log4j.Test");

    @Test
    public void MemAppenderTestName1(){
//        MemAppender app = new MemAppender();
//        assert(app.getName().equals(null));
    }

    @Test
    public void MemAppenderTestName2(){
        MemAppender app = new MemAppender();
        app.setName("changedName");
        assert(app.getName().equals("changedName"));
    }

    @Test
    public void MemAppenderTestLogs1(){
        MemAppender app = new MemAppender();
        assert(app.getCurrentLogs().size() == 0);
    }

    @Test
    public void MemAppenderTestLogs2(){
        MemAppender app = new MemAppender();
        LoggingEvent event0 = new LoggingEvent("org.apache.logging.log4j", LOGGER, System.currentTimeMillis(),
                Level.ERROR, "test message 0", null);
        app.append(event0);
        assert(app.getCurrentLogs().size() == 1);
        assert(app.getCurrentLogs().get(0).equals(event0));
    }

    @Test
    public void MemAppenderTestLogs3(){
        //Check 1000 max size log limit
        MemAppender app = new MemAppender();
        for(int i = 0; i < 1020; i++){
            app.append(null);
        }
        assert(app.getCurrentLogs().size() == 1000);
    }

    @Test
    public void MemAppenderTestLogs4(){
        //Check discarded log count
        MemAppender app = new MemAppender();
        for(int i = 0; i < 1020; i++){
            app.append(null);
        }
        assert(app.getDiscardedLogCount() == 20);
    }

    @Test
    public void MemAppenderTestLogs5(){
        //Check oldest logs are discarded
        JSONLayout layout = new JSONLayout();
        MemAppender app = new MemAppender();
        for(int i = 0; i < 1020; i++){
            app.append(new LoggingEvent("org.apache.logging.log4j", LOGGER, System.currentTimeMillis(),
                    Level.ERROR, i, null));
        }
        for(LoggingEvent event : app.getCurrentLogs()){
            System.out.println(layout.format(event));
        }
        assert(app.getCurrentLogs().get(0).getRenderedMessage().equals("20"));
    }

    @Test
    public void MemAppenderTestFile1(){
        //Null LoggingEvent to File
        MemAppender app = new MemAppender();
        app.append(null);

    }

    @Test
    public void MemAppenderTestFile2() throws FileNotFoundException {
        //No events to File
        MemAppender app = new MemAppender();
        String fileName = "jsonFileTest2.json";
        app.exportToJSON(fileName);
        String output = app.readFromFile(fileName);
        String expected = "[\n]\n";

//        Charset charset = Charset.forName("ASCII");
//        byte[] outputArray = output.getBytes(charset);
//        byte[] expectedArray = expected.getBytes(charset);
//        for(int i = 0; i < outputArray.length; i++){
//            System.out.print(outputArray[i] + " ");
//        }
//        System.out.println();
//        for(int i = 0; i < expectedArray.length; i++){
//            System.out.print(expectedArray[i] + " ");
//        }
        assert(output.equals(expected));

    }

    @Test
    public void MemAppenderTestFile3(){
        //One LoggingEvent to file
        MemAppender app = new MemAppender();
        JSONLayout layout = new JSONLayout();
        LoggingEvent event = new LoggingEvent("org.apache.logging.log4j", LOGGER, System.currentTimeMillis(),
                Level.ERROR, "test message 0", null);
        app.append(event);
        String fileName = "jsonFileTest2.json";
        app.exportToJSON(fileName);
        String output = app.readFromFile(fileName);
        String eventString = app.eventToString(event);
        String expected = "[" +
                eventString +
                "\n]\n";
        assert(output.equals(expected));
    }

    @Test
    public void MemAppenderTestFile4(){
        //6 LoggingEvent's to file
        MemAppender app = new MemAppender();
        JSONLayout layout = new JSONLayout();
        List<LoggingEvent> events = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            LoggingEvent randEvent = app.randomEvent();
            events.add(randEvent);
            app.append(randEvent);
        }
        String fileName = "jsonFileTest2.json";
        app.exportToJSON(fileName);
        String output = app.readFromFile(fileName);
        String expected = "[";
        for(int i = 0; i < events.size()-1; i++){
            expected += app.eventToString(events.get(i));
            expected += ",";
        }
        expected += app.eventToString((events.get(5)));
        expected += "\n]\n";
        System.out.println(expected);
        assert(output.equals(expected));


    }

    @Test
    public void MemAppenderLogsTest(){
        MemAppender appender = new MemAppender();
        assert(appender.getLogCount() == 0);
        appender.append(new LoggingEvent("org.apache.logging.log4j", LOGGER, System.currentTimeMillis(),
                Level.ERROR, "test message 0", null));
        assert(appender.getLogCount() == 1);
    }

    @Test
    public void MemAppenderCleanUpTest(){
        MemAppender appender = new MemAppender();
        appender.close();
        assert(appender.requiresLayout() == false);
    }

}
