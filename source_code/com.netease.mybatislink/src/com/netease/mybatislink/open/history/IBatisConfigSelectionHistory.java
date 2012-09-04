package com.netease.mybatislink.open.history;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;

/**
 * 
 * @author CuiKun cuikunbj@cn.ibm.com
 * 
 */
public class IBatisConfigSelectionHistory {

	private static IBatisConfigSelectionHistory instance = new IBatisConfigSelectionHistory();

	private List<Object> historyItems;

	private IBatisConfigSelectionHistory() {
		super();
		historyItems = new ArrayList<Object>();
	}

	public static IBatisConfigSelectionHistory getInstance() {
		return instance;
	}

	public synchronized Object[] getHistoryItems() {
		return historyItems.toArray();
	}

	public synchronized void remove(Object item) {
		historyItems.remove(item);
	}

	public synchronized boolean contains(Object item) {
		return historyItems.contains(item);
	}

	public void load(IMemento memento) {
		// TODO Auto-generated method stub

	}

	public void save(IMemento memento) {
		// TODO Auto-generated method stub

	}

	public synchronized void accessed(Object item) {
		historyItems.add(item);
	}

}
