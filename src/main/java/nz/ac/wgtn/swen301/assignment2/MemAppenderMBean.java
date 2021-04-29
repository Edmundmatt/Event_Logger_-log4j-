package nz.ac.wgtn.swen301.assignment2;

public interface MemAppenderMBean {

    public String[] getLogs();
    public long getLogCount();
    public long getDiscardedLogCount();
    public void exportToJSON(String fileName);

}
