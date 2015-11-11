package ua.pp.fairwind.communications;

import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.lines.LoopBackLine;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.timeaction.PropertyTimer;
import ua.pp.fairwind.communications.utils.EllementsCreator;

import java.util.List;

/**
 * Created by ������ on 30.06.2015.
 */
public class SCADASystem extends SystemElementDirectory implements AutoCreateDeviceFunction {
    private final EllementsCreator elementsCreator;


    public SCADASystem(String codename, EllementsCreator elementsCreator) {
        super(codename, null);
        this.elementsCreator = elementsCreator;
    }

    static public SCADASystem createScadaSystem(String name, int maxTrunsactionTime) {
        MessageSubSystem topLevel = new MessageSubSystemMultiDipatch();
        SCADASystem scada = new SCADASystem(name, new EllementsCreator());
        List<LineInterface> serialLines = SerialLine.getSerialLines(maxTrunsactionTime);
        scada.addElemnt(new LoopBackLine());
        scada.addLines(serialLines);
        return scada;
    }

    private void destroyAllLine() {
        getAllLines().forEach(LineInterface::destroy);
    }

    @Override
    public void destroy() {
        PropertyTimer.stopWork();
        MessageSubSystemMultiDipatch.destroyAllService();
        destroyAllLine();
        destroyAll();
        super.destroy();
    }

    @Override
    public AbstractDevice createDevice(String name, String typeOfDevice, Object... params) {
        AbstractDevice device =elementsCreator.createDevice(name,typeOfDevice,params);
        if (device != null) {
            addElemnt(device);
        }
        return device;
    }

    public EllementsCreator getElementsCreator() {
        return elementsCreator;
    }
}
