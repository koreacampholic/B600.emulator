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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.UIEvents.Perspective;
import org.eclipse.e4.ui.workbench.modeling.IPartListener;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
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
import org.osgi.service.prefs.BackingStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class B600Part {
	
	private static Logger LOGGER = LoggerFactory.getLogger(B600Part.class);

	private Image image;

	private Button button1;

	//@Inject
	//private LogService LOGGER;
	
	@Inject
	private MWindow window;
	
	@Inject
	@Named("math.random")
	private Object random;
	
	@Inject
	@Preference(nodePath="b00.emulator.parts", value="greeting")
	private String greeting;
	
	@Inject
	@Preference(nodePath="b600.emulator.parts")
	private IEclipsePreferences prefs;
	
	
	Label label;
	

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		createImage(parent);
		// parent.setBackgroundImage(image);
		// parent.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		label = new Label(parent, SWT.NONE);
		label.setText(greeting + " "+window.getLabel()+" "+random);			
		
		// button1 = new Button(parent, SWT.PUSH);
		LOGGER.debug("random :: {}", random);
		
		parent.addPaintListener(new PaintListener() {			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawLine(10, 10, 100, 100);
				
			}
		});

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
	
	@Inject
	@Optional
	public void setSelection(
		@Named(IServiceConstants.ACTIVE_SELECTION)
		Object selection){

		if(selection != null){
			if(selection instanceof ISelection) {
				label.setText(selection.toString());
			}
		}
	}
	
	@Inject
	@Optional
	public void receiveEvent(@UIEventTopic("rainbow/color") String data) throws BackingStoreException{
		// label.setText(data);
		prefs.put("greeting", "I like " + data);
		prefs.sync();
	}
	
	@Inject
	@Optional
	public void setText(@Preference(nodePath="b600.emulator.parts",value="greeting") String text){
		if(text!=null && label!=null && !label.isDisposed()){
			label.setText(text);
		}
	}
}
