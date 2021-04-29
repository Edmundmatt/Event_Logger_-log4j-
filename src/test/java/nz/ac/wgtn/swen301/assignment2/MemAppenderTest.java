package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONTokener;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

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
    public void MemAppenderTest2() throws FileNotFoundException {
        //No events to File
        MemAppender app = new MemAppender();
        app.exportToJSON("jsonFileTest2.json");
        JSONTokener tokener = new JSONTokener(new FileReader("jsonFileTest2.json"));

    }

    @Test
    public void MemAppenderTestFile3(){
        //One LoggingEvent to file
        MemAppender app = new MemAppender();
        LoggingEvent event = new LoggingEvent("org.apache.logging.log4j", LOGGER, System.currentTimeMillis(),
                Level.ERROR, "test message 0", null);
        app.append(event);

    }
}
