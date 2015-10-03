package ua.pp.fairwind.javafx.guiElements.tableButton;

/**
 * Created by Сергей on 02.10.2015.
 */
public interface ButtonCellAction<T, VALUE> {

    void action(T element, VALUE value);

}
