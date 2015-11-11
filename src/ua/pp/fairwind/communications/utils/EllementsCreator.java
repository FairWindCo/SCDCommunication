package ua.pp.fairwind.communications.utils;

import org.reflections.Reflections;
import ua.pp.fairwind.communications.abstractions.annottations.Device;
import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.elementsdirecotry.DeviceCreatorInterface;
import ua.pp.fairwind.communications.internatianalisation.I18N;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Сергей on 11.11.2015.
 */
public class EllementsCreator implements DeviceCreatorInterface{
    final Map<String,Class<? extends AbstractDevice>> deviceSet;
    final Map<String,String> deviceDecriptionSet;

    private final Map<String,AbstractDevice> createdDeviceSet=new HashMap<>();

    public EllementsCreator() {
        this("ua.pp.fairwind.communications");
    }

    public EllementsCreator(String basePackageName) {
        Reflections reflections=new Reflections(basePackageName);
        Set<Class<?>> anotated_class=reflections.getTypesAnnotatedWith(Device.class);
        Map<String,Class<? extends AbstractDevice>> deviceSet=new HashMap<>();
        Map<String,String> deviceDecriptionSet=new HashMap<>();
        for(Class<?> cls:anotated_class){
            if(!Modifier.isAbstract(cls.getModifiers()))
                if(AbstractDevice.class.isAssignableFrom(cls)){
                    Device devAnnotation=cls.getAnnotation(Device.class);
                    if(devAnnotation!=null) {
                        String codeName = devAnnotation.value();
                        String description = devAnnotation.getVisualName();
                        if (codeName == null || codeName.isEmpty()) {
                            codeName = cls.getCanonicalName();
                        }
                        if (description != null && !description.isEmpty()) {
                            description = I18N.getLocalizedString(description);
                        } else {
                            description = I18N.getLocalizedString(codeName);
                        }
                        deviceSet.put(codeName, (Class<? extends AbstractDevice>) cls);
                        deviceDecriptionSet.put(codeName, description);
                    } else {
                        System.err.println(cls.getCanonicalName());
                    }
            }
        }
        this.deviceSet= Collections.unmodifiableMap(deviceSet);
        this.deviceDecriptionSet=Collections.unmodifiableMap(deviceDecriptionSet);
    }

    public AbstractDevice getDevice(String codeName,Object... param){
        return getDevice(codeName,codeName,param);
    }


    synchronized public AbstractDevice getDevice(String codeName,String codeNameType,Object... param){
        if(codeName==null||codeName.isEmpty())return null;
        AbstractDevice dev=createdDeviceSet.get(codeName);
        if(dev!=null)return dev;
        Class<? extends AbstractDevice> cls=deviceSet.get(codeName);
        if(cls == null && (codeNameType!=null&&!codeNameType.isEmpty())) {
            cls = deviceSet.get(codeNameType);
        }
        if(cls==null)return null;
        try {
            if(RSLineDevice.class.isAssignableFrom(cls)){

                Constructor<? extends AbstractDevice> constructor=cls.getConstructor(long.class,String.class,String.class);
                Object param1=(param!=null&&param.length>0&&param[0] instanceof Number)?((Number)param[0]).longValue():1L;
                Object param2=(param!=null&&param.length>1&&param[1] instanceof String)?param[1]:null;
                AbstractDevice newdev=constructor.newInstance(param1,codeName,param2);
                createdDeviceSet.put(codeName,newdev);
                return newdev;
            } else {
                Constructor<? extends AbstractDevice> constructor=cls.getConstructor(String.class,String.class);
                Object param1=(param!=null&&param.length>0&&param[0] instanceof String)?param[1]:null;
                AbstractDevice newdev=constructor.newInstance(codeName,param1);
                createdDeviceSet.put(codeName,newdev);
                return newdev;
            }
        } catch (NoSuchMethodException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (InvocationTargetException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return null;
    }


    @Override
    public AbstractDevice createDevice(String name, String typeOfDevice, Object... params) {
        return getDevice(name,typeOfDevice,params);
    }
}
