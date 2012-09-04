package com.netease.mybatislink.open.engine;

import java.util.Iterator;

import com.netease.mybatislink.open.dialog.IBatisConfigNameMatch;
import com.netease.mybatislink.resource.IBatisResourceContext;
import com.netease.mybatislink.resource.ProjectResource;
import com.netease.mybatislink.resource.StatementResource;

/**
 * 
 * @author CuiKun cuikunbj@cn.ibm.com
 * 
 */
public class IBatisConfigSearchEngine {

	private IBatisConfigItemsFilter filter;
	private IBatisConfigSearchRequestor requestor;

	public IBatisConfigSearchEngine(IBatisConfigSearchRequestor requestor) {
		this.requestor = requestor;
		filter = this.requestor.getPluginSearchFilter();
	}

	public void search() {
		ProjectResource projectResource = IBatisResourceContext.getInstance()
				.getProjectResource(requestor.getProject());
		projectResource.fireChangeFiles();
		Iterator<StatementResource> iterator = projectResource.getStatementMap().values().iterator();
		while (iterator.hasNext()) {
			StatementResource resource = iterator.next();
			if (filter.matches(resource.getId())) {
				requestor.add(new IBatisConfigNameMatch(resource, resource.getId()));
			}
		}
	}
}
