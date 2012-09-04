package com.netease.mybatislink.hyperlink;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

public abstract class AbstractIBatisHyperlink implements IHyperlink {
	protected IRegion fRegion;
	protected IDocument document;

	public AbstractIBatisHyperlink(IDocument document, IRegion region) {
		this.document = document;
		this.fRegion = region;
	}

	@Override
	public IRegion getHyperlinkRegion() {
		return fRegion;
	}

	@Override
	public String getTypeLabel() {
		return null;
	}
}
