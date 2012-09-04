package com.netease.mybatislink.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;

import com.netease.mybatislink.Activator;

public class ImageUtil {
	private static final URL baseURL = Activator.getDefault().getBundle().getEntry("/icons/");

	public static ImageDescriptor create(String name) {
		try {
			return ImageDescriptor.createFromURL(makeIconFileURL(name));
		} catch (MalformedURLException _ex) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	private static URL makeIconFileURL(String name) throws MalformedURLException {
		if (baseURL == null) {
			throw new MalformedURLException();
		} else {
			return new URL(baseURL, name);
		}
	}

	public static ImageDescriptor IBATIS_ICON = create("ibatis_icon.png");
}
