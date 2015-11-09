package ua.pp.fairwind.javafx.panels.propertypanels;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupPropertyInterface;

/**
 * Created by Сергей on 08.11.2015.
 */
public class PropertyEditorPanel extends HBox {
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

        column.setPrefWidth(150);
        //Defining cell content
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<AbstractProperty, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue()!=null?p.getValue().getValue().getCodename():"NO NAME"));
/*
        TreeTableColumn<AbstractProperty,String> editorcolumn = new TreeTableColumn<>("Editor");
        editorcolumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AbstractProperty, String> p) -> {

                    AbstractProperty ap = p.getValue().getValue();

                    return ConvertorSoftPropertyValue.getSoftPropertyAdapter(ap);
                }
        );
        editorcolumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        /**/
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
        //editorcolumn.setCellFactory();
        //editorcolumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        editorcolumn.setCellFactory(new Callback<TreeTableColumn<AbstractProperty, Property>, TreeTableCell<AbstractProperty, Property>>() {
            @Override
            public TreeTableCell<AbstractProperty, Property> call(TreeTableColumn<AbstractProperty, Property> param) {
                //return new EditorTreeTableCell<>();
                return new EditorTreeTableCellForProperty<>();
            }
        });
        actionColumn.setCellFactory(new Callback<TreeTableColumn<AbstractProperty, Property>, TreeTableCell<AbstractProperty, Property>>() {
            @Override
            public TreeTableCell<AbstractProperty, Property> call(TreeTableColumn<AbstractProperty, Property> param) {
                //return new EditorTreeTableCell<>();
                return new ActionTreeTableCellForProperty<>();
            }
        });
        /**/

        editorcolumn.setEditable(true);
        treeTableView.getColumns().add(column);
        treeTableView.getColumns().add(editorcolumn);
        treeTableView.getColumns().add(actionColumn);
        treeTableView.setPrefWidth(470);
    }


}
