package com.netease.mybatislink.open.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

import com.netease.mybatislink.open.engine.IBatisConfigItemsFilter;
import com.netease.mybatislink.open.history.IBatisConfigSelectionHistory;

/**
 * 
 * @author CuiKun cuikunbj@cn.ibm.com
 * 
 */
public class IBatisConfigContentProvider implements IStructuredContentProvider {

	private IBatisConfigSelectionHistory javaTypeSelectionHistory;

	private OpenIBatisConfigDialog dialog;

	private List<Object> items;

	private List<Object> resultItems;

	private List<Object> sortedItems;

	public IBatisConfigContentProvider(IBatisConfigSelectionHistory pluginSelectionHistory,
			OpenIBatisConfigDialog dialog) {
		this.items = new ArrayList<Object>();
		this.resultItems = new ArrayList<Object>();
		this.sortedItems = new ArrayList<Object>();
		this.dialog = dialog;
		this.javaTypeSelectionHistory = pluginSelectionHistory;
	}

	public void add(Object item) {
		boolean contained = false;
		Iterator<Object> iterator = items.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(item)) {
				contained = true;
				break;
			}
		}
		if (!contained) {
			this.items.add(item);
		}
	}

	public void addHistoryItems(IBatisConfigItemsFilter itemsFilter) {
		if (this.javaTypeSelectionHistory != null) {
			Object[] items = this.javaTypeSelectionHistory.getHistoryItems();
			for (int i = 0; i < items.length; i++) {
				Object item = items[i];
				if (itemsFilter != null) {
					if (itemsFilter.matchItem(item)) {
						if (itemsFilter.isConsistentItem(item)) {
							if (!this.items.contains(item)) {
								this.items.add(item);
							}
						} else {
							this.javaTypeSelectionHistory.remove(item);
						}
					}
				}
			}
		}
	}

	public void refresh() {
		setResultItems();
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				TableViewer viewer = dialog.getTableViewer();
				if (viewer != null && !viewer.getTable().isDisposed()) {
					viewer.setItemCount(resultItems.size());
					viewer.refresh();
					if (resultItems.size() > 0) {
						viewer.getTable().setSelection(0);
						viewer.getTable().notifyListeners(SWT.Selection, new Event());
					}
				}
			}
		});
	}

	public void reset() {
		this.items.clear();
		this.sortedItems.clear();
	}

	private Comparator<Object> getHistoryComparator() {
		return new IBatisConfigHistoryComparator();
	}

	public void addHistoryElement(Object item) {
		if (this.javaTypeSelectionHistory != null)
			this.javaTypeSelectionHistory.accessed(item);
		if (dialog.getFilter() == null || !dialog.getFilter().matchItem(item)) {
			this.items.remove(item);
			this.sortedItems.remove(item);
		}
		synchronized (sortedItems) {
			Collections.sort(sortedItems, getHistoryComparator());
		}
		this.refresh();
	}

	public boolean isHistoryElement(Object item) {
		if (!(item instanceof IBatisConfigNameMatch)) {
			return false;
		}
		if (this.javaTypeSelectionHistory != null) {
			return this.javaTypeSelectionHistory.contains(item);
		}
		return false;
	}

	public void rememberResult(IBatisConfigItemsFilter itemsFilter) {
		if (sortedItems.size() != items.size()) {
			synchronized (sortedItems) {
				sortedItems.clear();
				sortedItems.addAll(items);
				Collections.sort(sortedItems, getHistoryComparator());
			}
		}
		List<Object> itemsList = new ArrayList<Object>();
		itemsList.addAll(sortedItems);
		if (itemsFilter == dialog.getFilter()) {
			dialog.setLastCompletedFilter(itemsFilter);
			dialog.setLastCompletedResult(itemsList);
		}

	}

	private void setResultItems() {
		if (sortedItems.size() != items.size()) {
			synchronized (sortedItems) {
				sortedItems.clear();
				sortedItems.addAll(items);
				Collections.sort(sortedItems, getHistoryComparator());
			}
		}
		resultItems.clear();
		boolean hasHistory = false;
		if (sortedItems.size() > 0) {
			hasHistory = isHistoryElement(sortedItems.get(0));
		}
		for (Object o : sortedItems) {
			if (hasHistory && !isHistoryElement(o)) {
				resultItems.add(new IBatisConfigItemsListSeparator("Workspace Matches"));
				hasHistory = false;
			}
			resultItems.add(o);
		}
	}

	public Object[] getElements(Object inputElement) {
		return resultItems.toArray();
	}

	public void dispose() {

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	public void updateElement(int index) {

		dialog.getTableViewer().replace((resultItems.size() > index) ? resultItems.get(index) : null, index);

	}

	private class IBatisConfigHistoryComparator implements Comparator<Object> {

		public int compare(Object o1, Object o2) {
			if (!(o1 instanceof IBatisConfigNameMatch) || !(o2 instanceof IBatisConfigNameMatch)) {
				return 0;
			}
			IBatisConfigNameMatch type1 = (IBatisConfigNameMatch) o1, type2 = (IBatisConfigNameMatch) o2;
			boolean history1 = isHistoryElement(type1), history2 = isHistoryElement(type2);
			if (history1 ^ history2) {
				if (history1) {
					return -1;
				} else {
					return 1;
				}
			} else {
				String name1 = null, name2 = null;
				name1 = type1.getId();
				name2 = type2.getId();
				if (name1 == null || name2 == null) {
					return 0;
				}
				int nameCompare = name1.compareTo(name2);
				if (nameCompare == 0) {
					return type1.getFullyQualifiedName().compareTo(type2.getFullyQualifiedName());
				} else {
					return nameCompare;
				}
			}
		}
	}

}