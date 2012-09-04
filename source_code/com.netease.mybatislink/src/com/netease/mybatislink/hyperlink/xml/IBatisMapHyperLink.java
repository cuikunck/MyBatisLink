package com.netease.mybatislink.hyperlink.xml;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

import com.netease.mybatislink.hyperlink.AbstractIBatisHyperlink;
import com.netease.mybatislink.resource.IBatisResourceContext;
import com.netease.mybatislink.resource.StatementResource;
import com.netease.mybatislink.util.IBatisLog;
import com.netease.mybatislink.util.IBatisUtil;

public class IBatisMapHyperLink extends AbstractIBatisHyperlink {

	public IBatisMapHyperLink(IDocument document, IRegion region) {
		super(document, region);
	}

	@Override
	public String getHyperlinkText() {
		try {
			return "Link to the location of " + document.get(fRegion.getOffset(), fRegion.getLength());
		} catch (BadLocationException e) {
			IBatisLog.log(e);
		}
		return "Link to the location";
	}

	@Override
	public void open() {
		try {
			String mapName = document.get(fRegion.getOffset(), fRegion.getLength());
			StatementResource resource = IBatisResourceContext.getInstance().getProjectResource().getStatement(mapName);
			if (resource != null) {
				IBatisUtil.openEditor(resource);
			}
		} catch (Exception e) {
			IBatisLog.log(e);
		}

	}

}
