package ua.pp.fairwind.communications.devices;

import com.sun.management.OperatingSystemMXBean;
import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftDoubleProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftIntegerProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftStringProperty;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;


/**
 * Created by Сергей on 27.11.2015.
 */
public class SystemInfoDevice extends AbstractDevice {
    private final OperatingSystemMXBean osBean;
    private final RuntimeMXBean runtimeMXBean;

    private final SoftDoubleProperty processCpuLoad;
    private final SoftDoubleProperty systemLoadAverage;
    private final SoftDoubleProperty systemCpuLoad;

    private final SoftLongProperty totalPhisicalMemory;
    private final SoftLongProperty totalSwapSize;
    private final SoftLongProperty freePhisicalMemory;
    private final SoftLongProperty freeSwapSize;
    private final SoftLongProperty commitedVirtualMemorySize;
    private final SoftLongProperty processCPUTime;

    private final SoftIntegerProperty availableProcessors;

    private final SoftStringProperty arhitecture;
    private final SoftStringProperty os;
    private final SoftStringProperty osversion;


    private final SoftLongProperty memoryUsed;
    private final SoftLongProperty memoryFree;
    private final SoftLongProperty memoryTotal;
    private final SoftLongProperty memoryMax;

    private final SoftLongProperty upTime;


    private final int mb = 1024*1024;

    public SystemInfoDevice() {
        super("sysinfo", null);
        osBean =ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        processCpuLoad=new SoftDoubleProperty("process_cpu_load", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getProcessCpuLoad());
        systemLoadAverage=new SoftDoubleProperty("system_load_average", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getSystemLoadAverage());
        systemCpuLoad=new SoftDoubleProperty("system_cpu_load", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getSystemCpuLoad());
        totalPhisicalMemory=new SoftLongProperty("total_phisical_memory",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getTotalPhysicalMemorySize());
        totalSwapSize=new SoftLongProperty("total_swap_size",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getTotalSwapSpaceSize());
        freePhisicalMemory=new SoftLongProperty("free_phisical_memory",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getFreePhysicalMemorySize());
        freeSwapSize=new SoftLongProperty("free_swap_size",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getFreeSwapSpaceSize());
        availableProcessors=new SoftIntegerProperty("available_processors",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getAvailableProcessors());
        commitedVirtualMemorySize=new SoftLongProperty("commited_virtual_memory_size",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getCommittedVirtualMemorySize());
        processCPUTime=new SoftLongProperty("process_cpu_time",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getProcessCpuTime());
        arhitecture=new SoftStringProperty("arhitecture",null,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getArch());
        os=new SoftStringProperty("os_name",null,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getName());
        osversion=new SoftStringProperty("os_version",null,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,osBean.getVersion());
        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();
        memoryUsed=new SoftLongProperty("used_memory", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,(runtime.totalMemory() - runtime.freeMemory()) / mb);
        memoryFree=new SoftLongProperty("free_memory", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,(runtime.freeMemory()) / mb);
        memoryTotal=new SoftLongProperty("total_memory", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,(runtime.totalMemory()) / mb);
        memoryMax=new SoftLongProperty("max_memory", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,(runtime.maxMemory()) / mb);

        upTime=new SoftLongProperty("up_time", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,runtimeMXBean.getUptime());

        addPropertys(processCpuLoad);
        addPropertys(systemLoadAverage);
        addPropertys(systemCpuLoad);
        addPropertys(totalPhisicalMemory);
        addPropertys(totalSwapSize);
        addPropertys(freePhisicalMemory);
        addPropertys(freeSwapSize);
        addPropertys(availableProcessors);
        addPropertys(commitedVirtualMemorySize);
        addPropertys(processCPUTime);
        addPropertys(arhitecture);
        addPropertys(os);
        addPropertys(osversion);

        addPropertys(memoryUsed);
        addPropertys(memoryFree);
        addPropertys(memoryTotal);
        addPropertys(memoryMax);

        addPropertys(upTime);
    }

    @Override
    protected void readProperty(AbstractProperty property, Event sourceEvent){
        setInternalValue(deviceLastTryCommunicateTime, System.currentTimeMillis());
        boolean res=false;
        if(property==processCpuLoad){
            res=true;
            setInternalValue(processCpuLoad, osBean.getProcessCpuLoad());
        } else if(property==systemLoadAverage){
            res=true;
            setInternalValue(systemLoadAverage, osBean.getSystemLoadAverage());
        } else if(property==systemCpuLoad){
            res=true;
            setInternalValue(systemCpuLoad, osBean.getSystemCpuLoad());
        } else if(property==freePhisicalMemory){
            res=true;
            setInternalValue(freePhisicalMemory, osBean.getFreePhysicalMemorySize());
        } else if(property==freeSwapSize){
            res=true;
            setInternalValue(freeSwapSize, osBean.getFreeSwapSpaceSize());
        } else if(property==commitedVirtualMemorySize){
            res=true;
            setInternalValue(commitedVirtualMemorySize, osBean.getCommittedVirtualMemorySize());
        } else if(property==processCPUTime){
            res=true;
            setInternalValue(processCPUTime, osBean.getProcessCpuTime());
        } else if(property==upTime){
            res=true;
            setInternalValue(upTime,runtimeMXBean.getUptime());
        } else if(property==memoryUsed){
            res=true;
            Runtime runtime = Runtime.getRuntime();
            setInternalValue(memoryUsed, (runtime.totalMemory() - runtime.freeMemory()) / mb);
        } else if(property==memoryFree){
            res=true;
            Runtime runtime = Runtime.getRuntime();
            setInternalValue(memoryFree, (runtime.freeMemory()) / mb);
        } else if(property==memoryTotal){
            res=true;
            Runtime runtime = Runtime.getRuntime();
            setInternalValue(memoryTotal, (runtime.totalMemory()) / mb);
        } else if(property==memoryMax){
            res=true;
            Runtime runtime = Runtime.getRuntime();
            setInternalValue(memoryMax, (runtime.maxMemory()) / mb);
        }
        if(res) {
            setInternalValue(lastCommunicationStatus, true);
            setInternalValue(deviceLastSuccessCommunicateTime, System.currentTimeMillis());
        } else {
            setInternalValue(lastCommunicationStatus, false);
            setInternalValue(errorCommunicationStatus, true);
        }
    }

    public void refreshAllValues(){
        setInternalValue(lastCommunicationStatus, true);
        setInternalValue(deviceLastTryCommunicateTime, System.currentTimeMillis());
        setInternalValue(deviceLastSuccessCommunicateTime, System.currentTimeMillis());
        processCpuLoad.setValue(osBean.getProcessCpuLoad());
        systemLoadAverage.setValue(osBean.getSystemLoadAverage());
        systemCpuLoad.setValue(osBean.getSystemCpuLoad());
        freePhisicalMemory.setValue(osBean.getFreePhysicalMemorySize());
        freeSwapSize.setValue(osBean.getFreeSwapSpaceSize());
        commitedVirtualMemorySize.setValue(osBean.getCommittedVirtualMemorySize());
        processCPUTime.setValue(osBean.getProcessCpuTime());
        Runtime runtime = Runtime.getRuntime();
        memoryUsed.setValue((runtime.totalMemory() - runtime.freeMemory()) / mb);
        memoryFree.setValue((runtime.freeMemory()) / mb);
        memoryTotal.setValue((runtime.totalMemory()) / mb);
        memoryMax.setValue((runtime.maxMemory()) / mb);
        upTime.setValue(runtimeMXBean.getUptime());
    }

    @Override
    protected void writeProperty(AbstractProperty property, Event sourceEvent){

    }

    @Override
    public String getDeviceType() {
        return null;
    }
}
