package edu.pitt.rods.syndef.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.StringWriter;
import java.math.BigInteger;

/**
 * User: jue
 * Date: Oct 29, 2007
 * Time: 11:43:38 AM
 */
public class syndefTest extends TestCase {
    JAXBContext jaxbContext;
    Marshaller marshaller;
    Unmarshaller unmarshaller;
    ObjectFactory factory;


    public syndefTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();

        jaxbContext = JAXBContext.newInstance("edu.pitt.rods.syndef.model");
        marshaller = jaxbContext.createMarshaller();
        unmarshaller = jaxbContext.createUnmarshaller();
        factory = new ObjectFactory();

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() {
        return new TestSuite(syndefTest.class);
    }

    public void testMarshalPlacemark() throws Exception {

        // Test Marshalling
        System.out.println("\nTesting marshalling of syndrome class\n");

        // Create the object hierarchy
        SyndromeType syndrome = new SyndromeType();
        syndrome.setAuthor("Jeremy Espino");
        syndrome.setDescription("Test Syndrome");
        syndrome.setId(new BigInteger("0"));
        syndrome.setSyndromeName("testSyndrome");
        XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        cal.setMonth(10);
        cal.setDay(29);
        cal.setYear(2007);
        syndrome.setLastUpdateDate(cal);
        
        ConditionType condition1 = new ConditionType();
        condition1.setConditionName("condition1");
        condition1.setDescription("condition 1 description");
        ConceptType concept1 = new ConceptType();
        concept1.setConceptName("concept 1");
        concept1.setCui("CUI1");
        concept1.setDescription("CUI 1 description");
        condition1.setPrimaryUMLSConcept(concept1);

        PatternMatchType searchString1 = new PatternMatchType();
        searchString1.setValue("Search1");
        searchString1.setSearchReason(SearchReason.CONCEPT_NAME);
        searchString1.setTypeOfSearch(TypeOfSearch.TEXT);
        condition1.getPatternMatch().add(searchString1);
        syndrome.getCondition().add(condition1);

        PatternMatchType searchString2 = new PatternMatchType();
        searchString2.setValue("Sarrch2");
        searchString2.setSearchReason(SearchReason.MISPELING);
        searchString2.setTypeOfSearch(TypeOfSearch.ORACLE_TEXT);
        condition1.getPatternMatch().add(searchString2);

        JAXBElement doc = factory.createSyndrome(syndrome);

        // marshall it
        StringWriter writer = new StringWriter();
        marshaller.setProperty("jaxb.formatted.output", true);

        marshaller.marshal(doc, writer);
        String result = writer.toString();
        System.out.println(result);

        //tests
        //assertTrue((result.indexOf("Attached") > 0));
        //assertTrue(result.indexOf("-122.0822035425683") > 0);


    }

    /* public void testUnmarshalPlacemark() throws Exception {

        System.out.println("\nTesting unmarshalling of placemark kml");

        String kmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.1\">\n" +
                "<Placemark>\n" +
                "<name>Simple placemark</name>\n" +
                "<description>Attached to the ground. Intelligently places itself\n" +
                "at the height of the underlying terrain.</description>\n" +
                "<Point>\n" +
                "<coordinates>-122.0822035425683,37.42228990140251,0</coordinates>\n" +
                "</Point>\n" +
                "</Placemark>\n" +
                "</kml>";
        Object kmlObj = unmarshaller.unmarshal(new InputSource(new StringReader(kmlString)));
        //assertTrue(((JAXBElement) kmlObj).getDeclaredType() == org.openrods.kml.KmlType.class);

    }*/
}
