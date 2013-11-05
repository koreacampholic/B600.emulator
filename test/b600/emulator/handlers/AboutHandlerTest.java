package b600.emulator.handlers;

import static org.junit.Assert.*;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.AfterClass;
import org.junit.Test;

public class AboutHandlerTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.open();
		
		new AboutHandler().execute(shell);
		
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
	}

}
