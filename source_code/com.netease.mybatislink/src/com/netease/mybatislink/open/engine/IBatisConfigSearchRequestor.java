package com.netease.mybatislink.open.engine;

import org.eclipse.core.resources.IProject;

import com.netease.mybatislink.open.dialog.IBatisConfigContentProvider;
import com.netease.mybatislink.open.dialog.IBatisConfigNameMatch;

/**
 * 
 * @author CuiKun cuikunbj@cn.ibm.com
 * 
 */
public class IBatisConfigSearchRequestor {

	private IBatisConfigItemsFilter pluginSearchFilter;
	private IBatisConfigContentProvider provider;
	private IProject project;

	public IBatisConfigSearchRequestor(IProject project, IBatisConfigItemsFilter pluginSearchFilter,
			IBatisConfigContentProvider provider) {
		super();
		this.project = project;
		this.pluginSearchFilter = pluginSearchFilter;
		this.provider = provider;
	}

	public IBatisConfigItemsFilter getPluginSearchFilter() {
		return pluginSearchFilter;
	}

	public void setPluginSearchFilter(IBatisConfigItemsFilter pluginSearchFilter) {
		this.pluginSearchFilter = pluginSearchFilter;
	}

	public void add(IBatisConfigNameMatch match) {
		provider.add(match);
	}

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

}
