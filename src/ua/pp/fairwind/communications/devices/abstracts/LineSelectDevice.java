package ua.pp.fairwind.communications.devices.abstracts;

import ua.pp.fairwind.communications.abstractions.LineSelector;

/**
 * Created by Сергей on 27.08.2015.
 */
public interface LineSelectDevice {
    LineSelector selectLine(Object needLine);
}
