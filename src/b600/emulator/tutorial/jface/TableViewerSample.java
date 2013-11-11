package b600.emulator.tutorial.jface;

import javax.annotation.PostConstruct;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import b600.emulator.parts.model.StatusModel;

public class TableViewerSample {

	private TableViewer viewer;

	@PostConstruct
	public void createComposite(final Composite parent) {
		
		parent.setLayoutDeferred(true); //
		
		// create tableviewer
		createViewer(parent);
	}

	private void createViewer(final Composite parent) {
		// define the TableViewer
		viewer = new TableViewer(parent, SWT.MULTI | SWT.V_SCROLL
				| SWT.FULL_SELECTION | SWT.BORDER);
		
		viewer.getControl().setBounds(100, 100, 200, 300);
				
		// create the columns
		createColumns(viewer);
		
		//make lines and header visible
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);		
		
		
		viewer.setContentProvider(new ArrayContentProvider());
		
	}
	
	
	private void createColumns(TableViewer viewer2) {
		String[] titles = {"항목", "상태정보"};
		int[] bounds = {100, 100};
		
		TableViewerColumn col = createTableViwerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				return ((StatusModel)element).getStatusItem();
			}
		});
		
		col = createTableViwerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				return ((StatusModel)element).getStatusValue();
			}
		});
		
		
	}
	
	private TableViewerColumn createTableViwerColumn(String title, int bound, int j) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
}
