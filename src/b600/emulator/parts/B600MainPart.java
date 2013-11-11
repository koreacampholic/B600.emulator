package b600.emulator.parts;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.prefs.BackingStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import b600.emulator.Activator;
import b600.emulator.parts.console.ConsoleTextStream;
import b600.emulator.parts.model.StatusModel;
import b600.emulator.parts.model.StatusModelProvider;

public class B600MainPart {

	private static Logger LOGGER = LoggerFactory.getLogger("firmLogger");
	private static Logger LOGGER2 = LoggerFactory.getLogger("agentLogger");

	private Image image; // backgorud image

	private Text agentConsoleText;

	private Text firmConsoleText;

	@Inject
	MPart mpart;

	private TableViewer viewer;

	@PostConstruct
	public void createComposite(final Composite parent) {

		// draw backgroud image
		createImage(parent);
		parent.setBackgroundImage(image);

		// parent.setLayout(new GridLayout(3, false));

		parent.setLayoutDeferred(true); //

		// add console
		addConsoleGroup(parent);

		// add logger by console
		addLoggerByConsole();

		// add label for table
		addLabelForTable(parent);

		// create tableviewer
		createTableViewerForStatusInfo(parent);

		// add buttons
		Button button = new Button(parent, SWT.FLAT | SWT.PUSH);
		button.setBounds(50, 325, 55, 30);
		button.setBackground(parent.getDisplay()
				.getSystemColor(SWT.COLOR_BLACK));
		button.setText("START");
		
		Display.getCurrent().asyncExec(new Runnable() {
			
			@Override
			public void run() {				
				java.util.List<StatusModel> statuses = StatusModelProvider.INSTANCE.getStatus();
				for(StatusModel status : statuses){
					if(status.getStatusItem().equals("Firmware기동")){
						status.setStatusValue("OK");
					}
				}
				viewer.refresh();
			}
		});

	}

	private void addLabelForTable(final Composite parent) {
		Label tlabel1 = new Label(parent, SWT.NONE);
		tlabel1.setText("[상태정보]");
		tlabel1.setForeground(parent.getDisplay().getSystemColor(
				SWT.COLOR_WHITE));
		tlabel1.setBounds(120, 155, 100, 15);
		
		Label tlabel2 = new Label(parent, SWT.NONE);
		tlabel2.setText("[버전정보]");
		tlabel2.setForeground(parent.getDisplay().getSystemColor(
				SWT.COLOR_WHITE));
		tlabel2.setBounds(350, 155, 100, 15);
	}

	private void createTableViewerForStatusInfo(final Composite parent) {
		// define the TableViewer
		viewer = new TableViewer(parent, SWT.MULTI | SWT.V_SCROLL
				| SWT.FULL_SELECTION | SWT.BORDER);

		viewer.getControl().setBounds(50, 170, 220, 140);

		// create the columns
		createColumns(viewer);

		// make lines and header visible
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.getHorizontalBar().setVisible(false);

		//viewer.setContentProvider(new ArrayContentProvider());
		viewer.setContentProvider(new ObservableListContentProvider());
		java.util.List<StatusModel> status = StatusModelProvider.INSTANCE.getStatus();
		IObservableList input = Properties.selfList(StatusModel.class).observe(status);
		
		viewer.setInput(input);
				
	}

	private void createColumns(TableViewer viewer2) {
		String[] titles = { "항목", "상태정보" };
		int[] bounds = { 110, 110 };

		TableViewerColumn col = createTableViwerColumn(titles[0], bounds[0], 0,
				SWT.LEFT);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((StatusModel) element).getStatusItem();
			}
		});

		col = createTableViwerColumn(titles[1], bounds[1], 1, SWT.CENTER);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((StatusModel) element).getStatusValue();
			}
		});

	}

	private TableViewerColumn createTableViwerColumn(String title, int bound,
			int j, int align) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(false);
		column.setMoveable(false);
		column.setAlignment(align);
		return viewerColumn;
	}

	private void addLoggerByConsole() {
		Activator.agentTextStream.setConsoleText(agentConsoleText);
		Activator.firmTextStream.setConsoleText(firmConsoleText);

		org.apache.log4j.Logger.getLogger("agentLogger").addAppender(
				Activator.agentWriterAppender);
		org.apache.log4j.Logger.getLogger("firmLogger").addAppender(
				Activator.firmWriterAppender);

		LOGGER.debug("writerappend test");
	}

	private void addConsoleGroup(final Composite parent) {
		Group agentConsoleGroup = new Group(parent, SWT.NONE);
		agentConsoleGroup.setText("Agent Console");
		agentConsoleGroup.setBounds(50, 50, 220, 100);

		agentConsoleText = new Text(agentConsoleGroup, SWT.MULTI | SWT.V_SCROLL);
		agentConsoleText.setBounds(2, 15, 215, 83);
		agentConsoleText.setBackground(parent.getDisplay().getSystemColor(
				SWT.COLOR_BLACK));
		agentConsoleText.setForeground(parent.getDisplay().getSystemColor(
				SWT.COLOR_WHITE));
		agentConsoleText.setEditable(false);

		Group firmConsoleGroup1 = new Group(parent, SWT.NONE);
		firmConsoleGroup1.setText("Firmware Console");
		firmConsoleGroup1.setBounds(280, 50, 220, 100);

		firmConsoleText = new Text(firmConsoleGroup1, SWT.MULTI | SWT.V_SCROLL);
		firmConsoleText.setBounds(2, 15, 215, 83);
		firmConsoleText.setBackground(parent.getDisplay().getSystemColor(
				SWT.COLOR_BLACK));
		firmConsoleText.setForeground(parent.getDisplay().getSystemColor(
				SWT.COLOR_WHITE));
		firmConsoleText.setEditable(false);

	}

	@Focus
	public void setFocus() {

	}

	private void createImage(Composite parent) {
		Bundle bundle = FrameworkUtil.getBundle(B600Part.class);
		URL url = FileLocator.find(bundle, new Path("icons/b600_bg.png"), null);
		ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		this.image = imageDcr.createImage();

	}

	@Inject
	@Optional
	public void receiveEvent(@UIEventTopic("rainbow/color") String data)
			throws BackingStoreException {

		LOGGER.debug("receive event :: {}", data);

	}
}
