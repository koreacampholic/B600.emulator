/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package b600.emulator.parts;

import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.UIEvents.Perspective;
import org.eclipse.e4.ui.workbench.modeling.IPartListener;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class B600Part {

	private  Image image;
	
	private Button button1;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		createImage(parent);
		parent.setBackgroundImage(image);
		parent.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		button1 = new Button(parent, SWT.PUSH);
		

		/*parent.getShell().addListener(SWT.RESIZE, new Listener() {

			@Override
			public void handleEvent(Event event) {
				System.out.println("RESIZE Event");				
			}
		});*/

	}

	private void createImage(Composite parent) {
		Bundle bundle = FrameworkUtil.getBundle(B600Part.class);
		URL url = FileLocator.find(bundle, new Path("icons/b600_bg.png"), null);
		ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		this.image = imageDcr.createImage();

		this.image = resizeImage(image, parent);

	}

	private Image resizeImage(Image orgimage, Composite parent) {
		Image scaled = new Image(Display.getDefault(), parent.getShell()
				.getClientArea().width,
				parent.getShell().getClientArea().height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(orgimage, 0, 0, orgimage.getBounds().width, orgimage
				.getBounds().height, 0, 0,
				parent.getShell().getClientArea().width, parent.getShell()
						.getClientArea().height);
		gc.dispose();
		orgimage.dispose();
		return scaled;

	}

	@Focus
	public void setFocus() {

	}
}
