package com.netease.mybatislink.open.engine;

import org.eclipse.ui.dialogs.SearchPattern;

import com.netease.mybatislink.open.dialog.IBatisConfigNameMatch;

/**
 * 
 * @author CuiKun cuikunbj@cn.ibm.com
 * 
 */
public class IBatisConfigItemsFilter {

	private SearchPattern searchPattern;

	public IBatisConfigItemsFilter(String pattern) {
		super();
		searchPattern = new SearchPattern();
		searchPattern.setPattern(pattern);
	}

	public boolean isConsistentItem(Object item) {
		return true;
	}

	public String getPattern() {
		return searchPattern.getPattern();
	}

	public boolean equalsFilter(IBatisConfigItemsFilter newFilter) {
		if (newFilter != null) {
			return newFilter.getSearchPattern().equals(this.searchPattern);
		}
		return false;
	}

	public boolean isSubFilter(IBatisConfigItemsFilter itemsFilter) {
		if (itemsFilter != null) {
			return this.searchPattern.isSubPattern(itemsFilter.getSearchPattern());
		}
		return false;
	}

	public SearchPattern getSearchPattern() {
		return searchPattern;
	}

	public boolean matchItem(Object item) {
		if (!(item instanceof IBatisConfigNameMatch)) {
			return false;
		}
		IBatisConfigNameMatch type = (IBatisConfigNameMatch) item;
		return matches(type.getId());
	}

	public boolean matches(String text) {
		if (text == null || "".equals(text)) {
			return false;
		}
		return searchPattern.matches(text);
	}

}
