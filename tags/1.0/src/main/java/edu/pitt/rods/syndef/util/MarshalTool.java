package edu.pitt.rods.syndef.util;


import edu.pitt.rods.syndef.model.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * User: jue
 * Date: Sep 12, 2007
 * Time: 11:05:56 AM
 */
public class MarshalTool {

    private static final String genClassLocation = "edu.pitt.rods.syndef.model";

    public static String marshall(Object obj) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(genClassLocation);
        javax.xml.bind.Marshaller marshaller = jaxbContext.createMarshaller();

        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return writer.toString();

    }

    public static final Object unmarshall(String xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(genClassLocation);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return unmarshaller.unmarshal(new StringReader(xml));
    }

    public static ObjectFactory getObjectFactory() {
        return new ObjectFactory();

    }

}
