package com.netease.mybatislink.open;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.netease.mybatislink.IBatisConstants;
import com.netease.mybatislink.open.dialog.OpenIBatisConfigDialog;
import com.netease.mybatislink.resource.StatementResource;
import com.netease.mybatislink.util.IBatisLog;
import com.netease.mybatislink.util.IBatisUtil;

public class OpenAction extends Action implements IWorkbenchWindowActionDelegate, IActionDelegate {

	@Override
	public void run(IAction action) {
		IProject project = IBatisUtil.getCurrentProject();
		String initContent = IBatisUtil.getCurrentTextSelection();
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		Shell parent = window.getShell();
		SelectionDialog dialog = new OpenIBatisConfigDialog(parent, project, initContent);
		dialog.setTitle(IBatisConstants.DIALOG_TITLE);
		dialog.setMessage("");

		int result = dialog.open();
		if (result != IDialogConstants.OK_ID)
			return;

		Object[] files = dialog.getResult();
		if (files != null && files.length > 0) {
			StatementResource resource = null;
			for (int i = 0; i < files.length; i++) {
				resource = (StatementResource) files[i];
				try {
					IBatisUtil.openEditor(resource);
				} catch (CoreException x) {
					IBatisLog.log(x);
				}
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}
}
