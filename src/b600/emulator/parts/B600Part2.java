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
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.UIEvents.Perspective;
import org.eclipse.e4.ui.workbench.modeling.IPartListener;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.osgi.service.log.LogService;

public class B600Part2 {

	private Image image;

	private Button button;

	@Inject
	private LogService LOGGER;
	
	@Inject
	private MWindow window;
	
	@Inject
	private UISynchronize ui;
	
	Label label;
	

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		createImage(parent);
		// parent.setBackgroundImage(image);
		// parent.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		label = new Label(parent, SWT.NONE);
		label.setText(window.getLabel());
		
		// button1 = new Button(parent, SWT.PUSH);
		LOGGER.log(LogService.LOG_DEBUG, "Hello");
		
		button = new Button(parent, SWT.PUSH);
		button.setText("Do not push");
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				button.setEnabled(false);
				new Job("Button Pusher") {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						ui.asyncExec(new Runnable() {							
							@Override
							public void run() {
								button.setEnabled(true);
							}
						});
					
						return Status.OK_STATUS;
					}
				}.schedule(1000);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	private void createImage(Composite parent) {
		Bundle bundle = FrameworkUtil.getBundle(B600Part2.class);
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
	
	@Inject
	@Optional
	public void setSelection(
		@Named(IServiceConstants.ACTIVE_SELECTION)
		Object selection){
		if(selection != null){
			label.setText(selection.toString());
		}
	
	}
}
