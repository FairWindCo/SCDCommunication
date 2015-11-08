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
        } else {
        }
        return root;
    }

    public PropertyEditorPanel(AbstractProperty property) {
        TreeItem<AbstractProperty> root=getItem(property);

        treeTableView.setRoot(root);
        initControl();
    }

    private void initControl(){
        this.getChildren().add(treeTableView);

        TreeTableColumn<AbstractProperty,String> column = new TreeTableColumn<>("Column");
        TreeTableColumn<AbstractProperty,Property> editorcolumn = new TreeTableColumn<>("Editor");
        column.setPrefWidth(150);
        //Defining cell content
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<AbstractProperty, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getCodename()));
        editorcolumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<AbstractProperty, Property> p) -> {

                    AbstractProperty ap = p.getValue().getValue();

                    return ConvertorSoftPropertyValue.getSoftPropertyAdapter(ap);
                }
        );
        //editorcolumn.setCellFactory();
        //editorcolumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        editorcolumn.setCellFactory(new Callback<TreeTableColumn<AbstractProperty, Property>, TreeTableCell<AbstractProperty, Property>>() {
            @Override
            public TreeTableCell<AbstractProperty, Property> call(TreeTableColumn<AbstractProperty, Property> param) {
                return new EditorTreeTableCell<Property>();
            }
        });
        editorcolumn.setEditable(true);
        treeTableView.getColumns().add(column);
        treeTableView.getColumns().add(editorcolumn);
        treeTableView.setPrefWidth(470);
        treeTableView.setShowRoot(true);
    }


}
