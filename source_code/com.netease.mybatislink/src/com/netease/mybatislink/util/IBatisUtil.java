package com.netease.mybatislink.util;

import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import com.netease.mybatislink.Activator;
import com.netease.mybatislink.IBatisConstants;
import com.netease.mybatislink.open.dialog.IBatisConfigNameMatch;
import com.netease.mybatislink.resource.StatementResource;

public class IBatisUtil implements IBatisConstants {

	public static IProject getCurrentProject() {
		IFile file = getCurrentOpenFile();
		if (file != null) {
			return file.getProject();
		}
		return null;
	}

	public static IFile getCurrentOpenFile() {
		IEditorInput input = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor().getEditorInput();
		if (input instanceof FileEditorInput) {
			IFile file = ((FileEditorInput) input).getFile();
			return file;
		}
		return null;
	}

	public static IJavaProject getCurrentJavaProject() {
		IJavaProject javaProject = JavaCore.create(getCurrentProject());
		return javaProject;
	}

	public static boolean isStatementElement(String localName) {
		for (String tag : STATEMENT_TAG) {
			if (tag.equals(localName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isRootElement(String localName) {
		for (String tag : ROOT_TAG) {
			if (tag.equals(localName)) {
				return true;
			}
		}
		return false;
	}

	public static String getFullyQualifiedText(IBatisConfigNameMatch type) {
		StringBuffer result = new StringBuffer();
		String authorName = type.getId();
		if (authorName != null) {
			result.append(authorName);
			result.append(CONCAT_STRING);
		}
		result.append(type.getSimpleTypeName());
		String containerName = type.getPackageName();
		if (containerName.length() > 0) {
			result.append(CONCAT_STRING);
			result.append(containerName);
		}
		result.append(CONCAT_STRING);
		result.append(type.getContainerName());
		return result.toString();
	}

	public static void openEditor(StatementResource resource) throws PartInitException {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = IDE.openEditor(page, resource.getFile(), true);
		if (editor instanceof ITextEditor) {
			ITextEditor textEditor = (ITextEditor) editor;
			textEditor.selectAndReveal(resource.getRegion().getOffset(), resource.getRegion().getLength());
			textEditor.setHighlightRange(resource.getRegion().getOffset(), resource.getRegion().getLength(), true);
		} else {
			showWithMarker(editor, resource);
		}
	}

	private static void showWithMarker(IEditorPart editor, StatementResource resource) throws PartInitException {
		IMarker marker = null;
		try {
			marker = resource.getFile().createMarker("");
			HashMap<String, Integer> attributes = new HashMap<String, Integer>(4);
			attributes.put(IMarker.CHAR_START, new Integer(resource.getRegion().getOffset()));
			attributes.put(IMarker.CHAR_END, new Integer(resource.getRegion().getOffset()
					+ resource.getRegion().getLength()));
			marker.setAttributes(attributes);
			IDE.gotoMarker(editor, marker);
		} catch (CoreException e) {
			IBatisLog.log(e);
		} finally {
			if (marker != null)
				try {
					marker.delete();
				} catch (CoreException e) {
					// ignore
				}
		}
	}

	public static boolean isEmptyString(String content) {
		if (content == null || content.length() == 0) {
			return true;
		}
		return false;
	}

	public static String getCurrentTextSelection() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ISelection selection = window.getSelectionService().getSelection();
		if (selection instanceof ITextSelection) {
			String text = ((ITextSelection) selection).getText();
			if (text != null) {
				return text;
			}
		}
		return "";
	}
}
