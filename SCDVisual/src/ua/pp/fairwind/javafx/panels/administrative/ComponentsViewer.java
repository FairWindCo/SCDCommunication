package ua.pp.fairwind.javafx.panels.administrative;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupPropertyInterface;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleMenuView;
import ua.pp.fairwind.javafx.panels.propertypanels.ActionTreeTableCellForElement;
import ua.pp.fairwind.javafx.panels.propertypanels.ConvertorSoftPropertyValue;
import ua.pp.fairwind.javafx.panels.propertypanels.EditorTreeTableCellForElement;

/**
 * Created by Сергей on 27.11.2015.
 */
public class ComponentsViewer extends SimpleMenuView {
    private final SCADASystem scadaSystem;
    private final TreeTableView<ElementInterface> treeTableView = new TreeTableView<>();
    private final TreeItem<ElementInterface> root;

    public ComponentsViewer(String menuItem,SCADASystem scadaSystem) {
        super(menuItem);
        this.scadaSystem=scadaSystem;
        root = new TreeItem<>(scadaSystem);
    }

    @Override
    protected Node createView() {


        TreeTableColumn<ElementInterface,String> column = new TreeTableColumn<>("System Element Name");
        TreeTableColumn<ElementInterface,Property> column3 = new TreeTableColumn<>("Value");
        TreeTableColumn<ElementInterface,Property> column4 = new TreeTableColumn<>("Action");
        TreeTableColumn<ElementInterface,String> column2 = new TreeTableColumn<>("System Element UUID");
        column.setPrefWidth(250);

        //Defining cell content
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ElementInterface, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getName()));
        column2.setPrefWidth(170);

        column2.setCellValueFactory((TreeTableColumn.CellDataFeatures<ElementInterface, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getUUIDString()));

        column3.setPrefWidth(200);
        column3.setEditable(true);
        column3.setCellValueFactory((TreeTableColumn.CellDataFeatures<ElementInterface, Property> p) -> {
            if (p.getValue() != null && p.getValue().getValue() instanceof AbstractProperty) {
                AbstractProperty ap = (AbstractProperty) p.getValue().getValue();
                return ConvertorSoftPropertyValue.getSoftPropertyAdapter(ap);
            } else {
                return (ObservableValue) new SimpleStringProperty("");
            }

        });
        column3.setCellFactory(param -> new EditorTreeTableCellForElement<>());

        column4.setPrefWidth(200);
        column4.setEditable(true);
        column4.setCellValueFactory((TreeTableColumn.CellDataFeatures<ElementInterface, Property> p) ->
            (ObservableValue) new SimpleStringProperty("")
        );
        column4.setCellFactory(param->new ActionTreeTableCellForElement<>());
        //Creating a tree table view
        treeTableView.setEditable(true);
        treeTableView.getColumns().add(column);
        treeTableView.getColumns().add(column3);
        treeTableView.getColumns().add(column4);
        treeTableView.getColumns().add(column2);
        treeTableView.setPrefWidth(152);
        treeTableView.setShowRoot(true);
        treeTableView.setRoot(root);
        return treeTableView;
    }

    private TreeItem getPropertyItem(AbstractProperty property){
        TreeItem propertyItem=new TreeItem<>(property);
        if(property instanceof GroupPropertyInterface){
            ((GroupPropertyInterface)property).getStream().forEach(subproperty ->
                propertyItem.getChildren().add(getPropertyItem(subproperty))
            );

        }
        return propertyItem;
    }

    @Override
    public void onShow(MenuExecutor executor) {
        super.onShow(executor);
        root.getChildren().clear();
        for(DeviceInterface dev:scadaSystem.getAllDevices()){
            TreeItem deviceItem=new TreeItem<>(dev);
            root.getChildren().add(deviceItem);
            for(AbstractProperty property:dev.getPropertys()){
                deviceItem.getChildren().add(getPropertyItem(property));
            }
        }
    }
}
