package com.unipi.datamining.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.security.AnyTypePermission;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@XStreamAlias("ConfigurationParameters")
public class ConfigurationParameters {
    public String serverIP;
    public int serverPort;

    public ConfigurationParameters(String fileXML){
        try {
            System.out.println("Reading the configuration parameters..");
            String fileXSD = "src/main/resources/configurationParameters.xsd";
            boolean result = ValidationXML.validate(fileXML, fileXSD);
            if(result)
                System.out.println("The validation of the XML file ended correctly!");
            XStream xstream = new XStream();
            xstream.alias("ConfigurationParameters", ConfigurationParameters.class);
            xstream.addPermission(AnyTypePermission.ANY);
            xstream.processAnnotations(ConfigurationParameters.class);
            ConfigurationParameters p = (ConfigurationParameters)(xstream.fromXML(new String(Files.readAllBytes(Paths.get(fileXML)))));
            serverIP = p.serverIP;
            serverPort = p.serverPort;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
