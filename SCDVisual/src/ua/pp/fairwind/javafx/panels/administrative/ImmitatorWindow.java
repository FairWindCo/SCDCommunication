package ua.pp.fairwind.javafx.panels.administrative;

import images.MyResourceLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.devices.abstracts.ImitatorDevice;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.propertyes.AbsractCommandProperty;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.tableButton.ButtonCell;
import ua.pp.fairwind.javafx.guiElements.tableButton.CheckBoxCell;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleMenuView;
import ua.pp.fairwind.javafx.panels.dialogs.AddImitatorDialog;
import ua.pp.fairwind.javafx.panels.dialogs.LineParametersImitatorDialog;

import java.util.Collection;

public class ImmitatorWindow extends SimpleMenuView{
	final private TableView<ImitatorDevice> deviceTable=new TableView<>();
	final private ObservableList<ImitatorDevice> devices=FXCollections.observableArrayList();
	private volatile LineInterface curentLine;
	private volatile LineParameters curentParams;
	private final ImitatorDialogCreator dialogCreator=new ImitatorDialogCreator();

	private final ButtonCell<ImitatorDevice> removeDevice=new ButtonCell<>("REMOVE",(device,val)-> {
		if(device!=null && curentLine!=null) {
			devices.remove(device);
			curentLine.removeDeviceToService(device);
		}
	});

	private final ButtonCell<ImitatorDevice> showDevice=new ButtonCell<>("SHOW",(device,val)-> {
		if(device!=null && curentLine!=null) {
			dialogCreator.getDialog(device).show();
		}
	});

	private final ButtonCell<ImitatorDevice> randomize=new ButtonCell<>("RANDOM",(device,val)-> {
		if(device!=null && curentLine!=null) {
			AbsractCommandProperty random=device.getCommandByCodeName(AbstractDevice.COMMAND_RANDOM);
			if(random!=null)random.activate();
		}
	});

	private final CheckBoxCell<ImitatorDevice> activator=new CheckBoxCell<>("ACTIVE",(devices,val)->devices.setActivete((Boolean)val),(device)->device.isActive());
	private final SCADASystem scadaSystem;
	private final ComboBox<LineInterface.SERVICE_MODE> modeSet=changeMode();

	public ImmitatorWindow(String menuItem, String menuHint, SCADASystem scadaSystem) {
		super(menuItem, menuHint);
		this.scadaSystem = scadaSystem;
	}

	public ImmitatorWindow(String menuItem, SCADASystem scadaSystem) {
		super(menuItem);
		this.scadaSystem = scadaSystem;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Node createView() {
		BorderPane base=new BorderPane();
		base.setId("basePanel");
		HBox title=new HBox(10d);
		title.setAlignment(Pos.CENTER);
		base.topProperty().set(title);
		Label titleLabel=new Label(I18N_FX.getLocalizedString("LINE"));
		title.getChildren().add(titleLabel);
		title.getChildren().add(createLineComboBox());
		Label titleLabel2=new Label(I18N_FX.getLocalizedString("MODE"));
		title.getChildren().add(titleLabel2);
		title.getChildren().add(modeSet);
		Label titleLabel3=new Label(I18N_FX.getLocalizedString("PARAM"));
		title.getChildren().add(titleLabel3);
		title.getChildren().add(createConfigureButton());

		titleLabel.getStyleClass().add("formLabel");
		BorderPane.setMargin(deviceTable, new Insets(1d));
		base.setCenter(deviceTable);
		TableColumn<ImitatorDevice, String> device=new TableColumn<>(I18N_FX.getLocalizedString("DEVICE"));
		device.setMinWidth(350);
		device.setCellValueFactory(arg0 -> {
			if (arg0 != null && arg0.getValue() != null) {
				return new SimpleStringProperty(arg0.getValue().getName());
			} else {
				return new SimpleStringProperty("---");
			}
		});
		deviceTable.getColumns().addAll(device, activator.createButtonColumn("ACTIVE"), removeDevice.createButtonColumn("ACTION"), randomize.createButtonColumn("RANDOM"), showDevice.createButtonColumn("FORM"));/**/
		//deviceTable.getColumns().addAll(device, randomize.createButtonColumn("RANDOM"));/**/
		deviceTable.autosize();
		deviceTable.setItems(devices);
		HBox buttom=new HBox(80d);
		base.setBottom(buttom);
		buttom.getChildren().add(createAddButton());
		return base;

    }

	@Override
	public void onShow(MenuExecutor executor) {
		super.onShow(executor);
	}

	private ComboBox<LineInterface> createLineComboBox(){
		ComboBox<LineInterface> combo=new ComboBox<>();
		combo.setPrefWidth(150);
		Collection lines=scadaSystem.getAllLines();
		if(lines!=null) combo.getItems().addAll(lines);
		combo.setOnAction(a -> {
			LineInterface selected = combo.getValue();
			if(selected!=null) {
				curentLine=selected;
				modeSet.setValue(selected.getServiceMode());
				curentParams=selected.getServerLineParameter();
				devices.addAll(selected.getDeivicesForService());
			}
		});
		combo.setValue(null);
		return combo;
	}

	public ComboBox<LineInterface.SERVICE_MODE> changeMode(){
		ComboBox<LineInterface.SERVICE_MODE> box=new ComboBox<>();
		box.getItems().addAll(LineInterface.SERVICE_MODE.values());
		box.setConverter(new StringConverter<LineInterface.SERVICE_MODE>() {
			@Override
			public String toString(LineInterface.SERVICE_MODE value) {
				switch (value) {
					case CLIENT_ONLY:
						return "CLIENT_ONLY";
					case SERVER_ONLY:
						return "SERVER_ONLY";
					case CLIENT_SERVER:
						return "CLIENT_SERVER";
					default:
						return "CLIENT_ONLY";
				}
			}

			@Override
			public LineInterface.SERVICE_MODE fromString(String value) {
				switch (value) {
					case "CLIENT_ONLY":
						return LineInterface.SERVICE_MODE.CLIENT_ONLY;
					case "SERVER_ONLY":
						return LineInterface.SERVICE_MODE.SERVER_ONLY;
					case "CLIENT_SERVER":
						return LineInterface.SERVICE_MODE.CLIENT_SERVER;
					default:
						return LineInterface.SERVICE_MODE.CLIENT_ONLY;
				}
			}
		});
		box.setOnAction((event) -> curentLine.setServiceMode(box.getValue(), curentParams));
		//Tooltip.install(box, new Tooltip(addressProperty.getDescription()));
		return box;
	}

	public LineInterface getCurentLine() {
		return curentLine;
	}

	public LineParameters getCurentParams() {
		return curentParams;
	}

	public void setCurentParams(LineParameters curentParams) {
		this.curentParams = curentParams;
	}

	public Button createConfigureButton(){
		Button button=new Button(I18N_FX.getLocalizedString("CONFIG_DEVICE_DIALOG"));
		Image imageDecline = new Image(MyResourceLoader.class.getResourceAsStream("setting_ico.png"));
		button.setGraphic(new ImageView(imageDecline));
		button.getStyleClass().add("rich-blue");
		button.setOnAction(action -> LineParametersImitatorDialog.getSerialLineParameterDialog(this));
		Tooltip.install(button, new Tooltip(I18N_FX.getLocalizedString("CONFIG_DEVICE_DIALOG.description")));
		return button;
	}

	public Button createAddButton(){
		Button button=new Button(I18N_FX.getLocalizedString("ADD_IMITATOR_DEVICE_DIALOG"));
		Image imageDecline = new Image(MyResourceLoader.class.getResourceAsStream("setting_ico.png"));
		button.setGraphic(new ImageView(imageDecline));
		button.getStyleClass().add("rich-blue");
		button.setOnAction(action -> AddImitatorDialog.getSerialLineParameterDialog(this));
		Tooltip.install(button, new Tooltip(I18N_FX.getLocalizedString("ADD_IMITATOR_DEVICE_DIALOG.description")));
		return button;
	}

	public void addImitator(ImitatorDevice imitator){
		if(curentLine!=null) {
			devices.add(imitator);
			curentLine.addDeviceToService(imitator);
		}
	}
}
