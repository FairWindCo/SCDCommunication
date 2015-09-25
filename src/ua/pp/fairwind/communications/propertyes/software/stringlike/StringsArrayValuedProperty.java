package ua.pp.fairwind.communications.propertyes.software.stringlike;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.groups.StaticGroupProperty;

import java.io.*;
import java.util.stream.Stream;

/**
 * Created by Wind on 28.07.2014.
 */
public class StringsArrayValuedProperty extends StaticGroupProperty{
    private final String delimeter;


    public StringsArrayValuedProperty(String name, String uuid, String description, MessageSubSystem centralSystem, String delimeter, StringValuedPropertry... propertyList) {
        super(name, uuid, propertyList);
        this.delimeter = delimeter;
    }


    public void readValueFromString(String str, int radix) throws NumberFormatException {
        synchronized (this) {
            String[] strs=str.split(delimeter);
            if(strs.length == 0) throw new NumberFormatException("EMPTY STRING");
            for(String onestr:strs){
                String[] parts=onestr.split("=");
                if(parts.length == 2){
                    StringValuedPropertry propeertyForRead=(StringValuedPropertry)get(parts[0]);
                    if(propeertyForRead!=null){
                        propeertyForRead.restoreValueFromString(onestr, radix);
                    } else {
                        throw new NumberFormatException("Incorrect Property Name");
                    }
                } else {
                    throw new NumberFormatException("Incorrect Property Format");
                }
            }
            fireEvent(EventType.ELEMENT_CHANGE,null);
        }
    }

    public void writeConfigToFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();


        if (file.canWrite()) {

            try (PrintWriter writer = new PrintWriter((file))) {
                Stream<AbstractProperty> stream = getStream();
                if (stream != null) {
                    //stream.collect(Collectors.joining("\n");
                    stream.forEach(p -> writer.println(((StringValuedPropertry) p).formStringValue("%s")));
                }
                writer.flush();

            }
        }
    }

    public void readFile(String fileName) throws IOException {
        File file=new File(fileName);
        if(file.exists() && file.canRead()){
            BufferedReader br=null;
            try (FileReader fr = new FileReader(file)) {
                br = new BufferedReader(fr);
                String line = br.readLine();
                int index = 0;
                while (line != null) {
                    String[] vals = line.split("=");
                    if (vals.length == 2) {
                        StringValuedPropertry ch = (StringValuedPropertry) getPopertyByIndex(index++);
                        if (ch != null) {
                            ch.restoreValueFromString(line, 10);
                            ch.checkInternalValue();
                        }
                    }
                    line = br.readLine();
                }

            } finally {
                if (br != null) br.close();

            }
        }
    }
}
