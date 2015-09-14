package ua.pp.fairwind.javafx.panels.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import jssc.SerialPort;
import ua.pp.fairwind.communications.devices.abstracts.SerialDeviceInterface;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.javafx.guiElements.editors.IntegerInputText;

import java.util.Optional;

/**
 * Created by Сергей on 10.09.2015.
 */
public class LineParametersDialog {

    public static void getSerialLineParameterDialog(SerialDeviceInterface serialDevice){
        if(serialDevice==null)return;
        Dialog<LineParameters> dialog = new Dialog<>();
        dialog.setTitle("Line Parameters Dialog");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        LineParameters curent=serialDevice.getLineParameters();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        int speed=SerialPort.BAUDRATE_9600;
        int databit=SerialPort.DATABITS_8;
        int stopbit=SerialPort.STOPBITS_2;
        int parity=SerialPort.PARITY_NONE;
        int floatcontrol=SerialPort.FLOWCONTROL_NONE;
        boolean rts=false;
        boolean dts=false;
        boolean clearRX=false;
        boolean clearTX=false;
        if(curent!=null && curent.getLineParameter(LineParameters.RS_SPEED)!=null){
            speed=(int)curent.getLineParameter(LineParameters.RS_SPEED);
        }
        if(curent!=null && curent.getLineParameter(LineParameters.RS_DATABIT)!=null){
            databit=(int)curent.getLineParameter(LineParameters.RS_DATABIT);
        }
        if(curent!=null && curent.getLineParameter(LineParameters.RS_STOBIT)!=null){
            stopbit=(int)curent.getLineParameter(LineParameters.RS_STOBIT);
        }
        if(curent!=null && curent.getLineParameter(LineParameters.RS_PARITY)!=null){
            parity=(int)curent.getLineParameter(LineParameters.RS_PARITY);
        }
        if(curent!=null && curent.getLineParameter(LineParameters.RS_FLOWCONTROL)!=null){
            floatcontrol=(int)curent.getLineParameter(LineParameters.RS_FLOWCONTROL);
        }

        if(curent!=null && curent.getLineParameter(LineParameters.RS_DTR)!=null){
            dts=(boolean)curent.getLineParameter(LineParameters.RS_DTR);
        }
        if(curent!=null && curent.getLineParameter(LineParameters.RS_RTS)!=null){
            rts=(boolean)curent.getLineParameter(LineParameters.RS_RTS);
        }
        if(curent!=null && curent.getLineParameter(LineParameters.RS_PURGE_RX)!=null){
            clearRX=(boolean)curent.getLineParameter(LineParameters.RS_PURGE_RX);
        }
        if(curent!=null && curent.getLineParameter(LineParameters.RS_PURGE_TX)!=null){
            clearTX=(boolean)curent.getLineParameter(LineParameters.RS_PURGE_TX);
        }

        ComboBox<Integer>rsspeed=createSpeedSelect(speed);
        ComboBox<Integer>rsdata=createDataBitSelect(databit);
        ComboBox<Integer>rsstop=createStopBitSelect(stopbit);
        ComboBox<Integer>rsparity=createParitySelect(parity);
        ComboBox<Integer>rsflow=createFlowControlSelect(floatcontrol);
        CheckBox rsdtr=new CheckBox();
        rsdtr.setSelected(dts);
        CheckBox rsrts=new CheckBox();
        rsrts.setSelected(rts);
        CheckBox rsclearrx=new CheckBox();
        rsclearrx.setSelected(clearRX);
        CheckBox rscleartx=new CheckBox();
        rscleartx.setSelected(clearTX);

        IntegerInputText intEditor=new IntegerInputText((Integer)curent.getLineParameter(LineParameters.SUB_LINE_NUMBER),10);
        grid.add(new Label("BAUD RATE:"), 0, 0);
        grid.add(rsspeed, 1, 0);
        grid.add(new Label("DATA BIT:"), 0, 1);
        grid.add(rsdata, 1, 1);
        grid.add(new Label("STOP BIT:"), 0, 2);
        grid.add(rsstop, 1, 2);
        grid.add(new Label("PARITY:"), 0, 3);
        grid.add(rsparity, 1, 3);
        grid.add(new Label("FLOW CONTROL:"), 0, 4);
        grid.add(rsflow, 1, 4);
        grid.add(new Label("SET DTR:"), 0, 5);
        grid.add(rsdtr, 1, 5);
        grid.add(new Label("SET RTS:"), 0, 6);
        grid.add(rsrts, 1, 6);
        grid.add(new Label("SET CLEAR RX:"), 2, 5);
        grid.add(rsclearrx, 3, 5);
        grid.add(new Label("SET CLEAR TX:"), 2, 6);
        grid.add(rscleartx, 3, 6);
        grid.add(new Label("SET LINE NUMBER:"), 0,7);
        grid.add(intEditor, 1, 7);

        dialog.getDialogPane().setContent(grid);



        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                int sspeed=rsspeed.getSelectionModel().getSelectedItem()==null?SerialPort.BAUDRATE_9600:(int)rsspeed.getSelectionModel().getSelectedItem();
                int sdatabit=rsdata.getSelectionModel().getSelectedItem()==null?SerialPort.DATABITS_8:(int)rsdata.getSelectionModel().getSelectedItem();
                int sstopbit=rsstop.getSelectionModel().getSelectedItem()==null?SerialPort.STOPBITS_2:(int)rsstop.getSelectionModel().getSelectedItem();
                int sparity=rsparity.getSelectionModel().getSelectedItem()==null?SerialPort.PARITY_NONE:(int)rsparity.getSelectionModel().getSelectedItem();
                int sfloatcontrol=rsflow.getSelectionModel().getSelectedItem()==null?SerialPort.FLOWCONTROL_NONE:rsflow.getSelectionModel().getSelectedItem();
                return new CommunicationLineParameters(sspeed,sdatabit,sparity,sstopbit,sfloatcontrol,rsdtr.isSelected(),rsrts.isSelected(),rsclearrx.isSelected(),rscleartx.isSelected());
            }
            return null;
        });

        Optional<LineParameters> result=dialog.showAndWait();
        result.ifPresent(lineparams->serialDevice.setLineParameters(lineparams));
    }

    public static ComboBox<Integer> createSpeedSelect(int sinitialValue){
        ComboBox<Integer> box=new ComboBox<>();
        box.getItems().addAll(new Integer(SerialPort.BAUDRATE_110),
                new Integer(SerialPort.BAUDRATE_300),
                new Integer(SerialPort.BAUDRATE_600),
                new Integer(SerialPort.BAUDRATE_1200),
                new Integer(SerialPort.BAUDRATE_4800),
                new Integer(SerialPort.BAUDRATE_9600),
                new Integer(SerialPort.BAUDRATE_14400),
                new Integer(SerialPort.BAUDRATE_19200),
                new Integer(SerialPort.BAUDRATE_38400),
                new Integer(SerialPort.BAUDRATE_57600),
                new Integer(SerialPort.BAUDRATE_115200),
                new Integer(SerialPort.BAUDRATE_128000),
                new Integer(SerialPort.BAUDRATE_256000)
                );
        box.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                switch (value) {
                    case SerialPort.BAUDRATE_110:
                        return "BAUD RATE 110";
                    case SerialPort.BAUDRATE_300:
                        return "BAUD RATE 300";
                    case SerialPort.BAUDRATE_600:
                        return "BAUD RATE 600";
                    case SerialPort.BAUDRATE_1200:
                        return "BAUD RATE 1200";
                    case SerialPort.BAUDRATE_4800:
                        return "BAUD RATE 4800";
                    case SerialPort.BAUDRATE_9600:
                        return "BAUD RATE 9600";
                    case SerialPort.BAUDRATE_14400:
                        return "BAUD RATE 14400";
                    case SerialPort.BAUDRATE_19200:
                        return "BAUD RATE 19200";
                    case SerialPort.BAUDRATE_38400:
                        return "BAUD RATE 38400";
                    case SerialPort.BAUDRATE_57600:
                        return "BAUD RATE 57600";
                    case SerialPort.BAUDRATE_115200:
                        return "BAUD RATE 115200";
                    case SerialPort.BAUDRATE_128000:
                        return "BAUD RATE 128000";
                    case SerialPort.BAUDRATE_256000:
                        return "BAUD RATE 256000";
                    default:
                        return "BAUD RATE 9600";
                }
            }

            @Override
            public Integer fromString(String value) {
                switch (value) {
                    case "BAUD RATE 110":
                        return SerialPort.BAUDRATE_110;
                    case "BAUD RATE 300":
                        return SerialPort.BAUDRATE_300;
                    case "BAUD RATE 600":
                        return SerialPort.BAUDRATE_600;
                    case "BAUD RATE 1200":
                        return SerialPort.BAUDRATE_1200;
                    case "BAUD RATE 4800":
                        return SerialPort.BAUDRATE_4800;
                    case "BAUD RATE 9600":
                        return SerialPort.BAUDRATE_9600;
                    case "BAUD RATE 14400":
                        return SerialPort.BAUDRATE_14400;
                    case "BAUD RATE 19200":
                        return SerialPort.BAUDRATE_19200;
                    case "BAUD RATE 38400":
                        return SerialPort.BAUDRATE_38400;
                    case "BAUD RATE 57600":
                        return SerialPort.BAUDRATE_57600;
                    case "BAUD RATE 128000":
                        return SerialPort.BAUDRATE_128000;
                    case "BAUD RATE 115200":
                        return SerialPort.BAUDRATE_115200;
                    case "BAUD RATE 250000":
                        return SerialPort.BAUDRATE_256000;
                    default:
                        return SerialPort.BAUDRATE_9600;
                }
            }
        });
        box.getSelectionModel().select((Integer)sinitialValue);
        return box;
    }

    public static ComboBox<Integer> createDataBitSelect(int sinitialValue){
        ComboBox<Integer> box=new ComboBox<>();
        box.getItems().addAll(new Integer(SerialPort.DATABITS_5),
                new Integer(SerialPort.DATABITS_6),
                new Integer(SerialPort.DATABITS_7),
                new Integer(SerialPort.DATABITS_8)
        );
        box.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                switch (value) {
                    case SerialPort.DATABITS_5:
                        return "5 DATA BIT";
                    case SerialPort.DATABITS_6:
                        return "6 DATA BIT";
                    case SerialPort.DATABITS_7:
                        return "7 DATA BIT";
                    case SerialPort.DATABITS_8:
                        return "8 DATA BIT";
                    default:
                        return "8 DATA BIT";
                }
            }

            @Override
            public Integer fromString(String value) {
                switch (value) {
                    case "5 DATA BIT":
                        return SerialPort.DATABITS_5;
                    case "6 DATA BIT":
                        return SerialPort.DATABITS_6;
                    case "7 DATA BIT":
                        return SerialPort.DATABITS_7;
                    case "8 DATA BIT":
                        return SerialPort.DATABITS_8;
                    default:
                        return SerialPort.DATABITS_8;
                }
            }
        });
        box.getSelectionModel().select((Integer)sinitialValue);
        return box;
    }

    public static ComboBox<Integer> createStopBitSelect(int sinitialValue){
        ComboBox<Integer> box=new ComboBox<>();
        box.getItems().addAll(new Integer(SerialPort.STOPBITS_1),
                new Integer(SerialPort.STOPBITS_1_5),
                new Integer(SerialPort.STOPBITS_2)
        );
        box.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                switch (value) {
                    case SerialPort.STOPBITS_1:
                        return "1 STOP BIT";
                    case SerialPort.STOPBITS_1_5:
                        return "1,5 STOP BIT";
                    case SerialPort.STOPBITS_2:
                        return "2 STOP BIT";
                    default:
                        return "2 STOP BIT";
                }
            }

            @Override
            public Integer fromString(String value) {
                switch (value) {
                    case "1 STOP BIT":
                        return SerialPort.STOPBITS_1;
                    case "1,5 STOP BIT":
                        return SerialPort.STOPBITS_1_5;
                    case "2 STOP BIT":
                        return SerialPort.STOPBITS_2;
                    default:
                        return SerialPort.STOPBITS_2;
                }
            }
        });
        box.getSelectionModel().select((Integer)sinitialValue);
        return box;
    }

    public static ComboBox<Integer> createParitySelect(int sinitialValue){
        ComboBox<Integer> box=new ComboBox<>();
        box.getItems().addAll(new Integer(SerialPort.PARITY_NONE),
                new Integer(SerialPort.PARITY_EVEN),
                new Integer(SerialPort.PARITY_ODD),
                new Integer(SerialPort.PARITY_SPACE),
                new Integer(SerialPort.PARITY_MARK)
        );
        box.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                switch (value) {
                    case SerialPort.PARITY_NONE:
                        return "NONE";
                    case SerialPort.PARITY_EVEN:
                        return "EVEN";
                    case SerialPort.PARITY_ODD:
                        return "ODD";
                    case SerialPort.PARITY_SPACE:
                        return "SPACE";
                    case SerialPort.PARITY_MARK:
                        return "MARK";
                    default:
                        return "NONE";
                }
            }

            @Override
            public Integer fromString(String value) {
                switch (value) {
                    case "NONE":
                        return SerialPort.PARITY_NONE;
                    case "EVEN":
                        return SerialPort.PARITY_EVEN;
                    case "ODD":
                        return SerialPort.PARITY_ODD;
                    case "SPACE":
                        return SerialPort.PARITY_SPACE;
                    case "MARK":
                        return SerialPort.PARITY_MARK;
                    default:
                        return SerialPort.PARITY_NONE;
                }
            }
        });
        box.getSelectionModel().select((Integer)sinitialValue);
        return box;
    }

    public static ComboBox<Integer> createFlowControlSelect(int sinitialValue){
        ComboBox<Integer> box=new ComboBox<>();
        box.getItems().addAll(new Integer(SerialPort.FLOWCONTROL_NONE),
                new Integer(SerialPort.FLOWCONTROL_RTSCTS_IN),
                new Integer(SerialPort.FLOWCONTROL_RTSCTS_OUT),
                new Integer(SerialPort.FLOWCONTROL_XONXOFF_IN),
                new Integer(SerialPort.FLOWCONTROL_XONXOFF_OUT)
        );
        box.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                switch (value) {
                    case SerialPort.FLOWCONTROL_NONE:
                        return "NONE";
                    case SerialPort.FLOWCONTROL_RTSCTS_IN:
                        return "RTSCTS_IN";
                    case SerialPort.FLOWCONTROL_RTSCTS_OUT:
                        return "RTSCTS_OUT";
                    case SerialPort.FLOWCONTROL_XONXOFF_IN:
                        return "XONXOFF_IN";
                    case SerialPort.FLOWCONTROL_XONXOFF_OUT:
                        return "XONXOFF_OUT";
                    default:
                        return "NONE";
                }
            }

            @Override
            public Integer fromString(String value) {
                switch (value) {
                    case "NONE":
                        return SerialPort.FLOWCONTROL_NONE;
                    case "RTSCTS_IN":
                        return SerialPort.FLOWCONTROL_RTSCTS_IN;
                    case "RTSCTS_OUT":
                        return SerialPort.FLOWCONTROL_RTSCTS_OUT;
                    case "XONXOFF_IN":
                        return SerialPort.FLOWCONTROL_XONXOFF_IN;
                    case "XONXOFF_OUT":
                        return SerialPort.FLOWCONTROL_XONXOFF_OUT;
                    default:
                        return SerialPort.FLOWCONTROL_NONE;
                }
            }
        });
        box.getSelectionModel().select((Integer)sinitialValue);
        return box;
    }
}
