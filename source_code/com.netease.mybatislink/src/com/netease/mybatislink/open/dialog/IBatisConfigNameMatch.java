package com.netease.mybatislink.open.dialog;

/**
 * 
 * @author CuiKun cuikunbj@cn.ibm.com
 *
 */
import com.netease.mybatislink.resource.StatementResource;

public class IBatisConfigNameMatch {

	private StatementResource resource;
	private String id;

	public String getFullyQualifiedName() {
		return resource.getFile().toString();
	}

	public String getSimpleTypeName() {
		return resource.getFile().getName();
	}

	public IBatisConfigNameMatch(StatementResource resource, String id) {
		super();
		this.resource = resource;
		this.id = id;
	}

	public String getPackageName() {
		String path = resource.getFile().getFullPath().toString();
		String[] segs = path.split("/");
		StringBuffer packageName = new StringBuffer();
		for (int i = 3; i < segs.length - 2; i++) {
			packageName.append(segs[i] + ".");
		}
		if (segs.length - 2 >= 3) {
			packageName.append(segs[segs.length - 2]);
		}
		return packageName.toString();
	}

	public String getContainerName() {
		String path = resource.getFile().getFullPath().toString();
		String[] segs = path.split("/");
		StringBuffer containerName = new StringBuffer();
		if (segs.length > 1) {
			containerName.append(segs[1]);
		}
		if (segs.length > 2) {
			containerName.append("/" + segs[2]);
		}
		return containerName.toString();
	}

	public StatementResource getResource() {
		return resource;
	}

	public void setResource(StatementResource resource) {
		this.resource = resource;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IBatisConfigNameMatch) {
			IBatisConfigNameMatch type = (IBatisConfigNameMatch) obj;
			if (type.getId().equals(this.getId())) {
				return true;
			}
		}
		return super.equals(obj);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
