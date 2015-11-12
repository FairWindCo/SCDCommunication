package ua.pp.fairwind.javafx.panels.propertypanels;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.VBox;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupPropertyInterface;

/**
 * Created by Сергей on 08.11.2015.
 */
public class PropertyEditorPanel extends VBox {
    final TreeTableView<AbstractProperty> treeTableView=new TreeTableView<>();


    private TreeItem<AbstractProperty> getItem(AbstractProperty property){
        TreeItem<AbstractProperty> root=new TreeItem<>(property);
        if(property instanceof GroupProperty){
            int count=((GroupProperty) property).propertyCount();
            for(int i=0;i<count;i++) {
                TreeItem<AbstractProperty> subroot = getItem(((GroupProperty) property).getPopertyByIndex(i));
                root.getChildren().add(subroot);
            }
        } else if(property instanceof GroupPropertyInterface){
            int count=((GroupPropertyInterface) property).propertyCount();
            for(int i=0;i<count;i++) {
                TreeItem<AbstractProperty> subroot = getItem(((GroupPropertyInterface) property).getPopertyByIndex(i));
                root.getChildren().add(subroot);
            }
        }
        return root;
    }

    private void checkIn(TreeItem<AbstractProperty> item,AbstractProperty propertyes){
        if(propertyes!=null && item!=null) {
            if(propertyes instanceof GroupProperty){
                int count=((GroupProperty) propertyes).propertyCount();
                if(count>0) {
                    for(int i=0;i<count;i++) {
                        AbstractProperty subproperty = ((GroupProperty) propertyes).getPopertyByIndex(i);
                        if(checkItem(item,subproperty)){
                            return;
                        }
                    }
                } else {
                    checkItem(item,propertyes);
                }
            } else {
                checkItem(item,propertyes);
            }
        }
    }

    private boolean checkItem(TreeItem<AbstractProperty> item,AbstractProperty propertyes){
        if(propertyes!=null && item!=null) {
            if(propertyes==item.getValue())return false;
            if(item.getChildren().size()>0){
                boolean result=false;
                for(TreeItem<AbstractProperty> subitem:item.getChildren()){
                    if(propertyes==subitem.getValue()){
                        result=true;
                        break;
                    }
                }
                if(!result){
                    item.getChildren().add(getItem(propertyes));
                    return true;
                }
            } else {
                item.getChildren().add(getItem(propertyes));
                return true;
            }
        }
        return false;
    }

    private TreeItem<AbstractProperty> getItem(AbstractProperty... propertyes){
        TreeItem<AbstractProperty> root=new TreeItem<>();
        if(propertyes!=null && propertyes.length>0) {
            for(AbstractProperty property:propertyes){
                TreeItem<AbstractProperty> subroot = getItem(property);
                root.getChildren().add(subroot);
            }
        }
        return root;
    }

    public PropertyEditorPanel(AbstractProperty property) {
        TreeItem<AbstractProperty> root=getItem(property);
        treeTableView.setShowRoot(true);
        treeTableView.setRoot(root);
        initControl();
    }

    public PropertyEditorPanel(AbstractProperty... propertyes) {
        TreeItem<AbstractProperty> root=getItem(propertyes);
        treeTableView.setShowRoot(false);
        treeTableView.setRoot(root);
        initControl();
    }

    private void initControl(){
        this.getChildren().add(treeTableView);

        TreeTableColumn<AbstractProperty,String> column = new TreeTableColumn<>("Column");

        column.setPrefWidth(250);
        //Defining cell content
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<AbstractProperty, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue()!=null?p.getValue().getValue().getCodename():"NO NAME"));

        column.setCellFactory(param -> new LabelTreeTableCellForProperty<>());

        TreeTableColumn<AbstractProperty,Property> editorcolumn = new TreeTableColumn<>("Editor");
        TreeTableColumn<AbstractProperty,Property> actionColumn = new TreeTableColumn<>("Action");
        editorcolumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AbstractProperty, Property> p) -> {

                    AbstractProperty ap = p.getValue().getValue();

                    return ConvertorSoftPropertyValue.getSoftPropertyAdapter(ap);
                }
        );
        actionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AbstractProperty, Property> p) -> {

                    AbstractProperty ap = p.getValue().getValue();

                    return ConvertorSoftPropertyValue.getSoftPropertyAdapter(ap);
                }
        );
        editorcolumn.setCellFactory(param -> new EditorTreeTableCellForProperty<>());
        actionColumn.setCellFactory(param -> new ActionTreeTableCellForProperty<>());


        editorcolumn.setEditable(true);
        setAlignment(Pos.CENTER);
        treeTableView.getColumns().add(column);
        treeTableView.getColumns().add(editorcolumn);
        treeTableView.getColumns().add(actionColumn);
        treeTableView.setPrefWidth(550);
    }

    public TreeTableView<AbstractProperty> getTreeTableView() {
        return treeTableView;
    }

    public AbstractProperty getSelectedValue(){
        if(treeTableView.getSelectionModel().getSelectedItem()!=null){
            return treeTableView.getSelectionModel().getSelectedItem().getValue();
        } else {
            return null;
        }
    }

    public void checkSelectedValue(){
        TreeItem<AbstractProperty> item=treeTableView.getSelectionModel().getSelectedItem();
        if(item!=null){
            AbstractProperty property=treeTableView.getSelectionModel().getSelectedItem().getValue();
            if (property != null){
                checkIn(item,property);
            }
        }
    }
}
