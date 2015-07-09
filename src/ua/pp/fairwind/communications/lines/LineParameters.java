package ua.pp.fairwind.communications.lines;

/**
 * Created by Сергей on 09.07.2015.
 */
public interface LineParameters {
    Object getLineParameter(String name);
    String[] getParametresName();
}
