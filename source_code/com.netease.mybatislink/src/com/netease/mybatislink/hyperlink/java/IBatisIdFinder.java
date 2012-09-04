package com.netease.mybatislink.hyperlink.java;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import com.netease.mybatislink.IBatisConstants;
import com.netease.mybatislink.util.IBatisLog;

public class IBatisIdFinder implements IBatisConstants {

	private static IBatisIdFinder instance = new IBatisIdFinder();

	private IBatisIdFinder() {

	}

	public static IBatisIdFinder getInstance() {
		return instance;
	}

	public IRegion findId(IDocument document, int offset) {
		IRegion wordRegion = findWord(document, offset, "\"", "\"");
		if (wordRegion.getLength() > 0) {
			int methodOffset = getMethodOffset(document, wordRegion, '(');
			if (methodOffset > -1) {
				IRegion methodRegion = findWord(document, methodOffset, null, null);
				if (isIBatisMethod(document, methodRegion)) {
					return wordRegion;
				}
			}
		}
		return null;
	}

	public IRegion findMapId(IDocument document, int offset) {
		IRegion wordRegion = findWord(document, offset, "\"", "\"");
		if (wordRegion.getLength() > 0) {
			int attributeOffset = getMethodOffset(document, wordRegion, '=');
			if (attributeOffset > -1) {
				IRegion attributeRegion = findWord(document, attributeOffset, null, null);
				if (isResultMap(document, attributeRegion)) {
					return wordRegion;
				}
			}
		}
		return null;
	}

	private boolean isResultMap(IDocument document, IRegion attributeRegion) {
		try {
			String attributeName = document.get(attributeRegion.getOffset(), attributeRegion.getLength());
			for (String name : mapAttributeNames) {
				if (name.equals(attributeName)) {
					return true;
				}
			}
		} catch (BadLocationException e) {
			IBatisLog.log(e);
		}
		return false;
	}

	private boolean isIBatisMethod(IDocument document, IRegion methodRegion) {
		try {
			String methodName = document.get(methodRegion.getOffset(), methodRegion.getLength());
			for (String method : ibatisMethods) {
				if (method.equals(methodName)) {
					return true;
				}
			}
		} catch (BadLocationException e) {
		}
		return false;
	}

	private int getMethodOffset(IDocument document, IRegion wordRegion, char edge) {
		int pos = wordRegion.getOffset() - 2;
		boolean passBrackets = false;
		try {
			while (pos >= 0) {
				char c = document.getChar(pos);
				if (Character.isWhitespace(c)) {
					--pos;
				} else {
					if (passBrackets) {
						return pos;
					}
					if (c == edge) {
						passBrackets = true;
						--pos;
					} else {
						return -1;
					}
				}
			}
		} catch (BadLocationException e) {

		}
		return -1;
	}

	public IRegion findWord(IDocument document, int offset, String startChar, String endChar) {

		int start = -2;
		int end = -1;

		try {
			int pos = offset;
			char c;

			while (pos >= 0) {
				c = document.getChar(pos);
				if (isIdentifier(startChar, c)) {
					break;
				}
				--pos;
			}
			start = pos;

			pos = offset;
			int length = document.getLength();

			while (pos < length) {
				c = document.getChar(pos);
				if (isIdentifier(endChar, c)) {
					break;
				}
				++pos;
			}
			end = pos;

		} catch (BadLocationException x) {
		}

		if (start >= -1 && end > -1) {
			if (start == offset && end == offset)
				return new Region(offset, 0);
			else if (start == offset)
				return new Region(start, end - start);
			else
				return new Region(start + 1, end - start - 1);
		}

		return null;
	}

	private boolean isIdentifier(String content, char c) {
		if (content == null || content.length() == 0) {
			return !Character.isJavaIdentifierPart(c);
		} else {
			return content.charAt(0) == c;
		}
	}
}
