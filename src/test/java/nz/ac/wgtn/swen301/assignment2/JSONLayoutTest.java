package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class JSONLayoutTest {

    private static final Logger LOGGER = Logger.getLogger("loggerName");

    @Test
    public void JSONLayoutTest1() throws Exception{
        //Create new LoggingEvent and test the JSONLayout format method
        LoggingEvent event = new LoggingEvent("org.apache.logging.log4j", LOGGER, 0,
                Level.ERROR, "test1Message", null);

        JSONLayout layout = new JSONLayout();
        String formatted = layout.format(event);
        System.out.println(formatted);

        JSONObject object = new JSONObject(formatted);
        assertEquals(object.get("level"), "ERROR");
        assertEquals(object.get("logger"), "loggerName");
        assertEquals(object.get("starttime"), 0);
        assertEquals(object.get("thread"), "main");
        assertEquals(object.get("message"), "test1Message");
    }

}