package com.netease.mybatislink.resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.LocatorImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import com.netease.mybatislink.IBatisConstants;
import com.netease.mybatislink.resource.sax.IgnoreDTDEntityResolver;
import com.netease.mybatislink.resource.sax.SaxLocatorHandler;
import com.netease.mybatislink.resource.sax.SaxLocatorResource;
import com.netease.mybatislink.util.IBatisUtil;

public class IBatisStatementParser implements IBatisConstants {

	public List<StatementResource> parse(IFile file) {
		List<StatementResource> list = new ArrayList<StatementResource>();
		try {
			IDocument document = getTextDocument(file);
			SaxLocatorHandler handler = new SaxLocatorHandler();
			XMLReader reader = XMLReaderFactory.createXMLReader();
			handler.setDocumentLocator(new LocatorImpl());
			reader.setContentHandler(handler);
			reader.setEntityResolver(new IgnoreDTDEntityResolver());
			reader.parse(new InputSource(file.getContents()));
			List<SaxLocatorResource> locatorResources = handler.getLocatorResources();
			SaxLocatorResource rootLocator = locatorResources.get(0);
			for (int i = 1; i < locatorResources.size(); i++) {
				if (!IBatisUtil.isStatementElement(locatorResources.get(i).getTagName())
						|| IBatisUtil.isEmptyString(locatorResources.get(i).getAttributeKey())
						|| IBatisUtil.isEmptyString(locatorResources.get(i).getAttributeValue())) {
					continue;
				}
				IRegion region = getStatementRegion(document, locatorResources.get(i - 1), locatorResources.get(i));
				list.add(new StatementResource(locatorResources.get(i).getAttributeValue(), file, region));
				if (rootLocator.getAttributeValue() != null) {
					list.add(new StatementResource(rootLocator.getAttributeValue() + "."
							+ locatorResources.get(i).getAttributeValue(), file, region));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return list;
	}

	private IDocument getTextDocument(IFile file) throws IOException, CoreException {
		InputStreamReader input = new InputStreamReader(file.getContents(), file.getCharset());
		char[] array = new char[1024];
		int amount = -1;
		StringBuffer buffer = new StringBuffer();
		while ((amount = input.read(array)) != -1) {
			buffer.append(array, 0, amount);
		}
		IDocument document = new Document();
		document.set(buffer.toString());
		return document;
	}

	private IRegion getStatementRegion(IDocument document, SaxLocatorResource previousLocatorResource,
			SaxLocatorResource currentLocatorResource) throws BadLocationException {
		int offset = document.getLineOffset(previousLocatorResource.getLine() - 1)
				+ previousLocatorResource.getColumn();
		FindReplaceDocumentAdapter findAdapter = new FindReplaceDocumentAdapter(document);
		IRegion region = findAdapter.find(offset, TAG_START + currentLocatorResource.getTagName(), true, false, false,
				false);
		if (region == null) {
			return null;
		}
		int start = region.getOffset() + region.getLength();
		region = findAdapter.find(start, TAG_END, true, false, false, false);
		int end = region.getOffset();
		boolean isKey = true, inQuotes = false, isIdAttribute = false;
		char quotes = 0;
		StringBuffer buffer = null;
		while (start <= end) {
			char ch = document.getChar(start);
			if (Character.isWhitespace(ch) && !inQuotes) {
				if (isKey && buffer != null) {
					if (buffer.toString().equals(currentLocatorResource.getAttributeKey())) {
						isIdAttribute = true;
					}
					isKey = true;
				} else if (!isKey && isIdAttribute && buffer != null) {
					return findAdapter.find(start, buffer.toString(), false, true, false, false);
				}
				buffer = null;
			} else if (ch == '=') {
				isKey = false;
				if (buffer.toString().equals(currentLocatorResource.getAttributeKey())) {
					isIdAttribute = true;
				}
				buffer = null;
			} else if (ch == '\"' || ch == '\'') {
				if (inQuotes && ch == quotes) {
					if (!isKey && isIdAttribute && buffer != null) {
						return findAdapter.find(start, buffer.toString(), false, true, false, false);
					} else {
						inQuotes = false;
						quotes = 0;
					}
				} else if (!inQuotes) {
					inQuotes = true;
					quotes = ch;
				} else {
					buffer.append(ch);
				}
			} else {
				if (buffer == null) {
					buffer = new StringBuffer();
				}
				buffer.append(ch);
			}
			start++;
		}
		return null;
	}
}
