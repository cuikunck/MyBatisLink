package com.netease.mybatislink.resource;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import com.netease.mybatislink.util.IBatisLog;
import com.netease.mybatislink.util.IBatisUtil;

public class IBatisResourceContext {

	private static final String XML_FILE_EXTENSION = "xml";

	private static IBatisResourceContext instance = new IBatisResourceContext();

	private Map<IProject, ProjectResource> map;

	private IBatisResourceContext() {
		map = new HashMap<IProject, ProjectResource>();
	}

	public static IBatisResourceContext getInstance() {
		return instance;
	}

	public ProjectResource getProjectResource(IProject project) {
		ProjectResource resource = map.get(project);
		if (resource == null) {
			resource = createNewProjectResource(project);
			map.put(project, resource);
		}
		return resource;
	}

	public ProjectResource getProjectResource() {
		return getProjectResource(IBatisUtil.getCurrentProject());
	}

	private ProjectResource createNewProjectResource(final IProject project) {
		if (project != null) {
			final ProjectResource projectResource = new ProjectResource();
			try {
				IJavaProject javaProject = JavaCore.create(project);
				final IPath location = javaProject.getOutputLocation();
				project.accept(new IResourceVisitor() {
					@Override
					public boolean visit(IResource resource) throws CoreException {
						if (resource instanceof IFolder && resource.getFullPath().equals(location)) {
							return false;
						}
						fireFileChanged(projectResource, resource);
						return true;
					}
				});
				ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {

					@Override
					public void resourceChanged(IResourceChangeEvent event) {
						IResourceDelta resource = event.getDelta();
						try {
							resource.accept(new IResourceDeltaVisitor() {
								@Override
								public boolean visit(IResourceDelta delta) throws CoreException {
									if (delta.getResource() instanceof IFolder
											&& delta.getResource().getFullPath().equals(location)) {
										return false;
									}
									fireFileChanged(projectResource, delta.getResource());
									return true;
								}
							});
						} catch (CoreException e) {
							IBatisLog.log(e);
						}
					}

				}, IResourceChangeEvent.POST_CHANGE | IResourceChangeEvent.PRE_DELETE);
			} catch (CoreException e) {
				IBatisLog.log(e);
			}
			return projectResource;
		}
		return null;
	}

	private void fireFileChanged(ProjectResource projectResource, IResource resource) {
		if (XML_FILE_EXTENSION.equals(resource.getFileExtension())) {
			if (resource instanceof IFile) {
				projectResource.changeFile((IFile) resource);
			}
		}
	}

}
