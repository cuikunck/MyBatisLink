package com.netease.mybatislink.resource;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IRegion;

public class StatementResource {

	private String id;
	private IFile file;
	private IRegion region;

	public StatementResource(String id, IFile file, IRegion region) {
		super();
		this.id = id;
		this.file = file;
		this.region = region;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IFile getFile() {
		return file;
	}

	public void setFile(IFile file) {
		this.file = file;
	}

	public IRegion getRegion() {
		return region;
	}

	public void setRegion(IRegion region) {
		this.region = region;
	}

}
