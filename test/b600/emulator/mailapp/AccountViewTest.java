package b600.emulator.mailapp;

import static org.junit.Assert.*;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

public class AccountViewTest {

	@Test
	public void accountViewTest() {

		final Display d = new Display();
		Realm.runWithDefault(SWTObservables.getRealm(d), new Runnable() {

			@Override
			public void run() {
				Shell shell = new Shell(d);
				shell.setLayout(new FillLayout());
				AccountView view = new AccountView(shell,
						new MailSessionFactoryImpl());
				view.setUsername("john");
				view.setPassword("doe");
				view.setHost("smtp.google.mail");
				view.init();

				shell.open();

				while (!shell.isDisposed()) {
					if (!d.readAndDispatch()) {
						d.sleep();
					}
				}

			}
		});
		
		d.dispose();

		assert(true);
	}

}
