package ua.pp.fairwind.javafx.panels;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupPropertyInterface;
import ua.pp.fairwind.communications.propertyes.software.*;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.VisualControls;

/**
 * Created by Сергей on 24.09.2015.
 */
public class TupicalPanels {
    public static void setDIChanelControl(GridPane grid,SoftBoolProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col+0, rowindex);
        grid.add(VisualControls.createLedIndicator(chanel, Color.CORAL), col + 1, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col + 2, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col + 3, rowindex);
    }

    public static void setDOChanelControl(GridPane grid,SoftBoolProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLedIndicator(chanel, Color.AQUA), col++, rowindex);
        grid.add(VisualControls.createSlideIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createBoolChangeCommandButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex);
    }

    public static int setAOChanelControl(GridPane grid,SoftFloatProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);

        //grid.add(createBoolChangeCommandButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        grid.add(VisualControls.createSliderControl(chanel, 0, 10, 1, 10, 10), 0, rowindex++,5,1);
        return rowindex;
    }



    public static void setAIChanelControl(GridPane grid,SoftFloatProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col+0, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col+1, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col + 2, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col + 3, rowindex);
    }

    public static Pane createDeviceStatusPane(RSLineDevice device){
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(30, 20, 10, 10));
        int rowindex=0;
        grid.setId("formGrid");
        grid.add(new Label(device.getDeviceType() + " : " + device.getName() + " UUID=" + device.getUUID()), 0, rowindex++, 3, 1);
        grid.add(new Label(device.getDescription()),0,rowindex++,3,1);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_ADDRES")), 0, rowindex);
        grid.add(VisualControls.createAddressSelect(device.getDeviceAddressProperty()), 1, rowindex++);

        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_STATUS")), 0, rowindex);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_LINE1_STATUS")), 1, rowindex);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_LINE2_STATUS")), 2, rowindex++);

        grid.add(VisualControls.createLedIndicator(device.getLastCommunicationStatus(), Color.GREENYELLOW), 0, rowindex);
        grid.add(VisualControls.createLedIndicator(device.getLastCommunicationStatusLine1(), Color.GREENYELLOW), 1, rowindex);
        grid.add(VisualControls.createLedIndicator(device.getLastCommunicationStatusLine2(), Color.GREENYELLOW), 2, rowindex++);

        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_ERROR")), 0, rowindex);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_LINE1_ERROR")), 1, rowindex);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_LINE2_ERROR")), 2, rowindex++);


        grid.add(VisualControls.createLedIndicator(device.getErrorCommunicationStatus(), Color.RED), 0, rowindex);
        grid.add(VisualControls.createLedIndicator(device.getErrorCommunicationStatusLine1(), Color.RED), 1, rowindex);
        grid.add(VisualControls.createLedIndicator(device.getErrorCommunicationStatusLine2(), Color.RED), 2, rowindex++);

        grid.add(VisualControls.createCommandExecuteButton(device.getValidateErrorCommand()), 0, rowindex);
        grid.add(VisualControls.createCommandExecuteButton(device.getValidateErrorCommandLine1()), 1, rowindex);
        grid.add(VisualControls.createCommandExecuteButton(device.getValidateErrorCommandLine2()), 2, rowindex++);

        grid.add(new Label(I18N_FX.getLocalizedString("LAST_COMMUNICATE_TIME")), 0, rowindex++);

        grid.add(VisualControls.createCommandExecuteButton(device.getRefreshCommand()), 0, rowindex);
        grid.add(VisualControls.createCommandExecuteButton(device.getValidateAllErrorCommand()), 2, rowindex);
        return grid;
    }

    public static int setShortChanelControl(GridPane grid,SoftShortProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    public static int setFloatChanelControl(GridPane grid,SoftFloatProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    public static int setShortChanelControlRO(GridPane grid,SoftShortProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    public static int setFloatChanelControlRO(GridPane grid,SoftFloatProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    public static int setChanelControlRO(GridPane grid,SoftShortProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    public static int setChanelControlRO(GridPane grid,SoftByteProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    public static int setChanelControlRO(GridPane grid,SoftStringProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    public static int setChanelControlRO(GridPane grid,SoftLongProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    public static int setChanelControlRO(GridPane grid,SoftIntegerProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }


    public static int setChanelControlRO(GridPane grid,SoftFloatProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    public static int setChanelControl(GridPane grid,ValueProperty chanel,int rowindex,int col,boolean showButtons){
        return setChanelControl(grid,chanel,null,rowindex,col,showButtons);
    }

    public static int setChanelControl(GridPane grid,GroupPropertyInterface chanel,int rowindex,int col,boolean showButtonsForSubProperty){
        if(chanel!=null && chanel.propertyCount()>0){
            for(int i=0;i<chanel.propertyCount();i++) {
                AbstractProperty prop=chanel.getPopertyByIndex(i);
                if(prop instanceof ValueProperty) {
                    rowindex = setChanelControl(grid,(ValueProperty)prop , rowindex, col,showButtonsForSubProperty);
                }
                if(prop instanceof GroupPropertyInterface){
                    rowindex = setChanelControl(grid,(GroupPropertyInterface)prop,rowindex,col,showButtonsForSubProperty);
                }
            }
        }
        if(chanel instanceof AbstractProperty) {
            grid.add(VisualControls.createReReadButton((AbstractProperty) chanel), 0, rowindex);
            grid.add(VisualControls.createReWriteButton((AbstractProperty) chanel), 1, rowindex);
            grid.add(VisualControls.createConfigureProppearty((AbstractProperty) chanel), 2, rowindex++);
        }
        return rowindex;
    }

    public static int setChanelControl(GridPane grid,ValueProperty chanel,String name,int rowindex,int col,boolean showButtons){
        if(name==null)name=chanel.getName();
        grid.add(new Label(I18N_FX.getLocalizedString(name)), col++, rowindex);
        grid.add(VisualControls.getPropertyControl(chanel), col++, rowindex);
        if(showButtons && chanel.isReadAccepted()) {
            grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        } else {
            col++;
        }
        if(showButtons && chanel.isWriteAccepted()) {
            grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
        } else {
            col++;
        }
        if(showButtons){
            grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        } else {
            rowindex++;
        }
        return rowindex;
    }



}
