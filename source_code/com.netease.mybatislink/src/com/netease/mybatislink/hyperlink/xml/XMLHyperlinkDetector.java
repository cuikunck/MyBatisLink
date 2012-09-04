package com.netease.mybatislink.hyperlink.xml;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import com.netease.mybatislink.hyperlink.java.IBatisIdFinder;
import com.netease.mybatislink.util.IBatisLog;
import com.netease.mybatislink.util.IBatisUtil;

public class XMLHyperlinkDetector extends AbstractHyperlinkDetector {

	public XMLHyperlinkDetector() {
	}

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		List<IHyperlink> hyperlinks = new ArrayList<IHyperlink>();
		if ((region != null) && (textViewer != null)) {
			IDocument document = textViewer.getDocument();
			IRegion wordRegion = IBatisIdFinder.getInstance().findMapId(document, region.getOffset());
			if (wordRegion != null) {
				try {
					String name = document.get(wordRegion.getOffset(), wordRegion.getLength());
					IType type = null;
					try {
						type = IBatisUtil.getCurrentJavaProject().findType(name);
					} catch (JavaModelException e) {
						IBatisLog.log(e);
					}
					if (type == null) {
						hyperlinks.add(new IBatisMapHyperLink(document, wordRegion));
						return hyperlinks.toArray(new IHyperlink[hyperlinks.size()]);
					}
				} catch (BadLocationException e) {
					IBatisLog.log(e);
				}
			}
		}
		return null;
	}

}
