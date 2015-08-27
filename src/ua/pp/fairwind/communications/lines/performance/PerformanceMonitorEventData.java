package ua.pp.fairwind.communications.lines.performance;

/**
 * Created by Сергей on 27.08.2015.
 */
public class PerformanceMonitorEventData {
    public static enum EXECUTE_TYPE{
        BEFORE_WRITE_PAUSE,
        BEFORE_READ_PAUSE,
        WRITE_OPERATION,
        READ_OPERATION,
        ANALISE_OPERATINO
    }
    private EXECUTE_TYPE executionType;
    private long executionTime;

    public PerformanceMonitorEventData(EXECUTE_TYPE executionType, long executionTime) {
        this.executionType = executionType;
        this.executionTime = executionTime;
    }

    public EXECUTE_TYPE getExecutionType() {
        return executionType;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public String toString() {
        return "Performance{" +
                executionType +
                " Time=" + executionTime +
                '}';
    }
}
