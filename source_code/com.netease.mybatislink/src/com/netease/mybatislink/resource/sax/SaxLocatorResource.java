package com.netease.mybatislink.resource.sax;

public class SaxLocatorResource {
	private String tagName;
	private String attributeKey;
	private String attributeValue;
	private boolean root;
	private int line;
	private int column;

	public SaxLocatorResource(String tagName, String attributeKey, String attributeValue, boolean root, int line,
			int column) {
		super();
		this.tagName = tagName;
		this.attributeKey = attributeKey;
		this.attributeValue = attributeValue;
		this.root = root;
		this.line = line;
		this.column = column;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeKey() {
		return attributeKey;
	}

	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
