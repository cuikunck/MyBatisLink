package com.netease.mybatislink.hyperlink.java;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

import com.netease.mybatislink.hyperlink.AbstractIBatisHyperlink;
import com.netease.mybatislink.resource.IBatisResourceContext;
import com.netease.mybatislink.resource.StatementResource;
import com.netease.mybatislink.util.IBatisLog;
import com.netease.mybatislink.util.IBatisUtil;

public class IBatisStatementIdHyperlink extends AbstractIBatisHyperlink {

	public IBatisStatementIdHyperlink(IDocument document, IRegion region) {
		super(document, region);
	}

	@Override
	public String getHyperlinkText() {
		return "Open iBatis Config File";
	}

	@Override
	public void open() {
		try {
			String id = document.get(fRegion.getOffset(), fRegion.getLength());
			StatementResource resource = IBatisResourceContext.getInstance().getProjectResource().getStatement(id);
			if (resource != null) {
				IBatisUtil.openEditor(resource);
			}
		} catch (Exception e) {
			IBatisLog.log(e);
		}
	}

}
