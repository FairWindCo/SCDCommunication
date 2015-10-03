package ua.pp.fairwind.communications.messagesystems;

import ua.pp.fairwind.communications.internatianalisation.I18N;

import java.util.UUID;

/**
 * Created by Сергей on 24.09.2015.
 */
public class MessageSystemManager {
    private static final MessageSubSystem messagesystem = createMessageSubsystem();

    private static MessageSubSystem createMessageSubsystem() {
        String name = (String) I18N.getObjectErr("message.system.name");
        if (name == null) {
            return new MessageSubSystemMultiDipatch();
        } else {
            MessageSubSystem sys = null;
            try {
                Class messageSystemclass = Class.forName(name);
                sys = (MessageSubSystem) messageSystemclass.newInstance();
            } catch (ClassNotFoundException e) {
                System.err.println(e.getLocalizedMessage());
            } catch (InstantiationException e) {
                System.err.println(e.getLocalizedMessage());
            } catch (IllegalAccessException e) {
                System.err.println(e.getLocalizedMessage());
            }
            if (sys == null) {
                sys = new MessageSubSystemMultiDipatch();
            }
            return sys;
        }
    }

    public static MessageSubSystem getMessageSystem() {
        return messagesystem;
    }

    public static MessageSubSystem getElementMessageSystem(UUID requestedElement) {
        return messagesystem.getNewChild(requestedElement);
    }

    static public void destroy() {
        messagesystem.destroyService();
    }


}
