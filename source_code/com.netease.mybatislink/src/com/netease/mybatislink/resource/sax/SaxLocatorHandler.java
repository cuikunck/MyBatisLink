package com.netease.mybatislink.resource.sax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import com.netease.mybatislink.IBatisConstants;
import com.netease.mybatislink.util.IBatisUtil;

public class SaxLocatorHandler extends DefaultHandler2 implements IBatisConstants {

	private Locator locator;

	private List<SaxLocatorResource> locatorResources;

	public SaxLocatorHandler() {
		locatorResources = new ArrayList<SaxLocatorResource>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		int line = locator.getLineNumber();
		int column = locator.getColumnNumber();
		if (IBatisUtil.isRootElement(qName)) {
			String namespace = attributes.getValue(ATTRIBUTE_NAMESPACE);
			SaxLocatorResource resource = new SaxLocatorResource(qName, ATTRIBUTE_NAMESPACE, namespace, true, line,
					column);
			locatorResources.add(resource);
		} else if (IBatisUtil.isStatementElement(qName)) {
			if (TYPE_ALIAS_TAG.equals(qName)) {
				String alias = attributes.getValue(ATTRIBUTE_TYPE_ALIAS);
				SaxLocatorResource resource = new SaxLocatorResource(qName, ATTRIBUTE_TYPE_ALIAS, alias, true, line,
						column);
				locatorResources.add(resource);
			} else {
				String id = attributes.getValue(ATTRIBUTE_ID);
				SaxLocatorResource resource = new SaxLocatorResource(qName, ATTRIBUTE_ID, id, true, line, column);
				locatorResources.add(resource);
			}

		}
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		int line = locator.getLineNumber();
		int column = locator.getColumnNumber();
		if (IBatisUtil.isStatementElement(qName)) {
			SaxLocatorResource resource = new SaxLocatorResource(qName, null, null, true, line, column);
			locatorResources.add(resource);
		}
		super.endElement(uri, localName, qName);
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
		super.setDocumentLocator(locator);
	}

	public List<SaxLocatorResource> getLocatorResources() {
		return locatorResources;
	}
}
