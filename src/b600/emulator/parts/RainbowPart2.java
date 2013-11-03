package b600.emulator.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class RainbowPart2 {
	
	private static final Object[] rainbow = {"Red","Orange","Yellow","Green"};
	
	@Inject
	private ESelectionService selectionSerivce;
	
	@PostConstruct
	public void create(Composite parent){
		ListViewer lv = new ListViewer(parent, SWT.NONE);
		lv.setContentProvider(new ArrayContentProvider());
		lv.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				selectionSerivce.setSelection(event.getSelection());
			}
		});
		
		lv.setInput(rainbow);
	} 

}
