package com.netease.mybatislink.open.dialog;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import com.netease.mybatislink.util.IBatisUtil;
import com.netease.mybatislink.util.ImageUtil;

/**
 * 
 * @author CuiKun cuikunbj@cn.ibm.com
 * 
 */
public class IBatisConfigItemLabelProvider extends LabelProvider implements ILabelDecorator {

	private LocalResourceManager imageManager;
	private OpenIBatisConfigDialog dialog;

	public IBatisConfigItemLabelProvider(OpenIBatisConfigDialog dialog) {
		imageManager = new LocalResourceManager(JFaceResources.getResources());
		this.dialog = dialog;
	}

	public void dispose() {
		super.dispose();
		imageManager.dispose();
	}

	public Image getImage(Object element) {
		if (!(element instanceof IBatisConfigNameMatch)) {
			return super.getImage(element);
		}

		return ImageUtil.IBATIS_ICON.createImage();
	}

	public String getText(Object element) {
		if (element instanceof IBatisConfigItemsListSeparator) {
			return getSeparatorLabel(((IBatisConfigItemsListSeparator) element).getName());
		}

		if (!(element instanceof IBatisConfigNameMatch)) {
			return super.getText(element);
		}
		return IBatisUtil.getFullyQualifiedText((IBatisConfigNameMatch) element);
	}

	private String getSeparatorLabel(String separatorLabel) {
		Rectangle rect = dialog.getTableViewer().getTable().getBounds();

		int borderWidth = dialog.getTableViewer().getTable().computeTrim(0, 0, 0, 0).width;

		int imageWidth = 20;

		int width = rect.width - borderWidth - imageWidth;

		GC gc = new GC(dialog.getTableViewer().getTable());
		gc.setFont(dialog.getTableViewer().getTable().getFont());

		int fSeparatorWidth = gc.getAdvanceWidth('-');
		int fMessageLength = gc.textExtent(separatorLabel).x;

		gc.dispose();

		StringBuffer dashes = new StringBuffer();
		int chars = (((width - fMessageLength) / fSeparatorWidth) / 2) - 2;
		for (int i = 0; i < chars; i++) {
			dashes.append('-');
		}

		StringBuffer result = new StringBuffer();
		result.append(dashes);
		result.append(" " + separatorLabel + " ");
		result.append(dashes);
		return result.toString().trim();
	}

	public Image decorateImage(Image image, Object element) {
		return image;
	}

	public String decorateText(String text, Object element) {
		if (!(element instanceof IBatisConfigNameMatch)) {
			return null;
		}
		return IBatisUtil.getFullyQualifiedText((IBatisConfigNameMatch) element);
	}

}