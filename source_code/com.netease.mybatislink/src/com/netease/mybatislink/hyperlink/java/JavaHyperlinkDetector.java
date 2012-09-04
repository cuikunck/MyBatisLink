package com.netease.mybatislink.hyperlink.java;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;

public class JavaHyperlinkDetector extends AbstractHyperlinkDetector {

	public JavaHyperlinkDetector() {

	}

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		List<IHyperlink> hyperlinks = new ArrayList<IHyperlink>();
		if ((region != null) && (textViewer != null)) {
			IDocument document = textViewer.getDocument();
			IRegion wordRegion = IBatisIdFinder.getInstance().findId(document, region.getOffset());
			if (wordRegion != null) {
				hyperlinks.add(new IBatisStatementIdHyperlink(document, wordRegion));
				return hyperlinks.toArray(new IHyperlink[hyperlinks.size()]);
			}

		}
		return null;
	}
}
