package testleak.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class JaxbUtil {

    private static Map<Class<?>, JAXBContext> contextStore = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static String leakingMarshalObject(Object object) {
        String messageXml = null;
        String className = null;
        try {
            if (object != null) {
                Class objClass = object.getClass();
                ByteArrayOutputStream baOutput = new ByteArrayOutputStream();
                className = objClass.getSimpleName();

                className = Character.toLowerCase(className.charAt(0))
                        + (className.length() > 1 ? className.substring(1) : "");

                JAXBElement xml = new JAXBElement(new QName("http://test", className), objClass, object);
                JAXBContext jc = JAXBContext.newInstance(objClass);
                jc.createMarshaller().marshal(xml, baOutput);
                messageXml = baOutput.toString();
            }
        } catch (Exception exception) {
            throw new RuntimeException(
                    "Failed Marshalling of " + className + " to message.",
                    exception);
        }
        return messageXml;
    }

    @SuppressWarnings("unchecked")
    public static String notLeakingMarshalObject(Object object) {
        String messageXml = null;
        String className = null;
        try {
            if (object != null) {
                Class objClass = object.getClass();
                ByteArrayOutputStream baOutput = new ByteArrayOutputStream();
                className = objClass.getSimpleName();

                className = Character.toLowerCase(className.charAt(0))
                        + (className.length() > 1 ? className.substring(1) : "");

                JAXBElement xml = new JAXBElement(new QName("http://test", className), objClass, object);

				JAXBContext jc = getContextInstance(objClass);
                jc.createMarshaller().marshal(xml, baOutput);
                messageXml = baOutput.toString();
            }
        } catch (Exception exception) {
            throw new RuntimeException(
                    "Failed Marshalling of " + className + " to message.",
                    exception);
        }
        return messageXml;
    }


    protected static JAXBContext getContextInstance(Class<?> objectClass) throws JAXBException {
        JAXBContext context = contextStore.get(objectClass);
        if (context == null) {
            context = JAXBContext.newInstance(objectClass);
            contextStore.put(objectClass, context);
        }
        return context;
    }
}
