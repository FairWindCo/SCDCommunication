package ua.fairwind.javafx;

import javafx.scene.layout.StackPane;
import org.jrebirth.af.api.ui.Model;
import org.jrebirth.af.core.application.DefaultApplication;

/**
 * Created by Сергей on 17.08.2015.
 */
public class Start extends DefaultApplication<StackPane> {
    public static void main(String[] args) {
        preloadAndLaunch(args);
    }
    @Override
    public Class<? extends Model> getFirstModelClass() {
        return SampleModel.class;
    }
}
