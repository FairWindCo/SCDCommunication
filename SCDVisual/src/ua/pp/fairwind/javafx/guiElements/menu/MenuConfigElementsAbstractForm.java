package ua.pp.fairwind.javafx.guiElements.menu;

import javafx.stage.Modality;
import ua.pp.fairwind.javafx.guiElements.windows.ApplicationView;

public abstract class MenuConfigElementsAbstractForm extends MenuConfigElements implements ApplicationView{
	final private Modality modality;


	public MenuConfigElementsAbstractForm(String name, String hint, Modality modality) {
		super(name, hint);
		this.modality = modality;
	}

	public MenuConfigElementsAbstractForm(String name, Modality modality) {
		super(name);
		this.modality = modality;
	}

	public MenuConfigElementsAbstractForm(String name, String hint) {
		super(name, hint);
		this.modality = null;
	}

	public MenuConfigElementsAbstractForm(String name) {
		super(name);
		this.modality = null;
	}

	public Modality getModality() {
		return modality;
	}
}
