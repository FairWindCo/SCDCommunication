package ua.pp.fairwind.javafx.controls.slidecheckbox;

/**
 * Created by Сергей on 28.08.2015.
 */
import javafx.scene.control.CheckBox;


/**
 * User: hansolo
 * Date: 07.10.13
 * Time: 07:44
 * Original version: http://dribbble.com/shots/765858-Switch-gif
 */
public class SlideCheckBox extends CheckBox {

    // ******************** Constructors **************************************
    public SlideCheckBox() {
        this("");
    }
    public SlideCheckBox(final String TEXT) {
        super(TEXT);
        getStylesheets().add(getClass().getResource("slidecheckbox.css").toExternalForm());
        setSkin(new SlideCheckBoxSkin(this));
    }
}
