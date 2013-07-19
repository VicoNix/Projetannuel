package com.project.xmlgen;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class DomParser {
public boolean Parse(String path)
{
	DocumentBuilderFactory builderFactory =
	DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = null;
	try {
	    builder = builderFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    e.printStackTrace();  
	}
	try {
	    builder.parse(new FileInputStream(path));
	} catch (SAXException e) {
	    e.printStackTrace();
	    return false;
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
}
}
 