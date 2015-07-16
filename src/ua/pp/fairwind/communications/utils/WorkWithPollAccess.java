package ua.pp.fairwind.communications.utils;

import java.util.concurrent.ExecutorService;

/**
 * Created by Сергей on 04.09.2014.
 */
@FunctionalInterface
public interface WorkWithPollAccess {
    public void process(ExecutorService serviceForExecute);
}
