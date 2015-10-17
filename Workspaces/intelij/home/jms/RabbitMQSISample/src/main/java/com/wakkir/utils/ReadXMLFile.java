/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakkir.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import org.w3c.dom.NamedNodeMap;

public class ReadXMLFile
{

    public static void main(String argv[])
    {

        try
        {

            File fXmlFile = new File("E:\\Wakkir\\workspaces\\Github\\WakHome\\Workspaces\\intelij\\home\\jms\\RabbitMQSISample\\src\\main\\resources\\test.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            //doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            
            NamedNodeMap atts=doc.getDocumentElement().getAttributes();
            Node node=atts.getNamedItemNS("http://www.example.com/FOO", "att2");      
            System.out.println(node.getLocalName());
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
