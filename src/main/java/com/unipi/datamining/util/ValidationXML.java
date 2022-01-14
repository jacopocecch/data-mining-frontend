package com.unipi.datamining.util;

import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

public class ValidationXML {
    public static boolean validate(String fileXML, String fileXSD){
       try {
           DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
           SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
           Document d = db.parse(new File(fileXML));
           Schema s = sf.newSchema(new StreamSource(new File(fileXSD)));
           s.newValidator().validate(new DOMSource(d));
           return true;
       } catch (IOException | ParserConfigurationException | org.xml.sax.SAXException ex) {
           System.out.println(ex.getMessage());
       }
       return false;
   }
}

