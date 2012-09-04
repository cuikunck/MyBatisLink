package com.netease.mybatislink.util;

import org.eclipse.core.runtime.Status;

import com.netease.mybatislink.Activator;

public class IBatisLog {

	public static void log(Exception e) {
		Activator.getDefault().getLog().log(new Status(0, Activator.PLUGIN_ID, "", e));
	}
}
