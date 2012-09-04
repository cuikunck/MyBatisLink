package com.netease.mybatislink.resource.sax;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.LocatorImpl;
import org.xml.sax.helpers.XMLReaderFactory;

public class SaxTest {

	/**
	 * @param args
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, SAXException {
		SaxLocatorHandler handler = new SaxLocatorHandler();
		XMLReader reader = XMLReaderFactory.createXMLReader();
		handler.setDocumentLocator(new LocatorImpl());
		reader.setContentHandler(handler);
		reader.setEntityResolver(new IgnoreDTDEntityResolver());
		reader.parse(new InputSource(SaxTest.class.getResourceAsStream("SetUrsConfigForUnitTest.xml")));
	}

}
