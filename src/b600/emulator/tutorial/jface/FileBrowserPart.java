package b600.emulator.tutorial.jface;

import java.io.File;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class FileBrowserPart {
	
	private Image image;
	private TreeViewer viewer;
	
	@PostConstruct
	public void createControls(Composite parent){
		createImage();
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(File.listRoots());
	}

	private void createImage() {
		Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/folder.png"), null);
		ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		this.image = imageDcr.createImage();
	}
	
	class ViewLabelProvider extends LabelProvider{

		@Override
		public String getText(Object element) {
			File file = (File)element;
			String name = file.getName();
			return name.isEmpty()?file.getPath():name;
		}
		
		public Image getImage(Object element){
			File file = (File)element;
			if(file.isDirectory()){
				return image;
			}
			return null;
		}

	}


}
