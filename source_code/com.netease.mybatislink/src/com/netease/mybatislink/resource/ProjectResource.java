package com.netease.mybatislink.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.netease.mybatislink.util.IBatisLog;

public class ProjectResource {

	private static final int LINE_LIMIT = 8;

	private static final String[] SQL_MAP_DTD = { "mybatis-3-mapper.dtd", "sql-map-2.dtd", "sql-map-config-2.dtd" , "mybatis-3-config.dtd"};
	private static final String[] SQL_MAP_TAG = { "<sqlMap", "<mapper", "<configuration" };

	private Map<String, StatementResource> statementMap;

	private Map<IFile, List<StatementResource>> fileMap;

	private Map<IFile, Object> changedList;

	public ProjectResource() {
		statementMap = new HashMap<String, StatementResource>();
		fileMap = new HashMap<IFile, List<StatementResource>>();
		changedList = new LinkedHashMap<IFile, Object>();
	}

	public void changeFile(IFile file) {
		try {
			if (isIBatisConfigFile(file)) {
				changedList.put(file, new Object());
			}
		} catch (CoreException e) {
			IBatisLog.log(e);
		} catch (IOException e) {
			IBatisLog.log(e);
		}
	}

	private boolean isIBatisConfigFile(IFile file) throws CoreException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(file.getContents()));
		String line = null;
		int i = 0;
		while ((line = reader.readLine()) != null) {
			for (String dtd : SQL_MAP_DTD) {
				if (line.contains(dtd)) {
					return true;
				}
			}
			for (String tag : SQL_MAP_TAG) {
				if (line.contains(tag)) {
					return true;
				}
			}
			if (i > LINE_LIMIT) {
				return false;
			}
			if (line.length() > 0) {
				i++;
			}
		}
		return false;
	}

	public StatementResource getStatement(String key) {
		fireChangeFiles();
		return statementMap.get(key);
	}

	public void fireChangeFiles() {
		if (changedList.size() > 0) {
			parseChangedFiles();
			changedList.clear();
		}
	}

	private void parseChangedFiles() {
		IBatisStatementParser parser = new IBatisStatementParser();
		Iterator<IFile> iterator = changedList.keySet().iterator();
		while (iterator.hasNext()) {
			IFile file = iterator.next();
			removeFile(file);
			if (file.exists()) {
				List<StatementResource> list = parser.parse(file);
				for (StatementResource statment : list) {
					statementMap.put(statment.getId(), statment);
				}
				fileMap.put(file, list);
			}
		}
	}

	private void removeFile(IFile file) {
		List<StatementResource> list = fileMap.remove(file);
		if (list == null) {
			return;
		}
		for (StatementResource statement : list) {
			statementMap.remove(statement.getId());
		}
	}

	public Map<String, StatementResource> getStatementMap() {
		return statementMap;
	}

}
