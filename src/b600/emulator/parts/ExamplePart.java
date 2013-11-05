package b600.emulator.parts;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ExamplePart {
	
	@Inject
	public ExamplePart(Composite parent){
		Label label = new Label(parent, SWT.NONE);
		label.setText("Hello World!");
	}

}
