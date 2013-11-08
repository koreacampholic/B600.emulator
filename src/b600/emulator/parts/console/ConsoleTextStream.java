package b600.emulator.parts.console;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class ConsoleTextStream extends OutputStream{
	
	Text consoleText;
	
	public ConsoleTextStream(Text text){
		super();
		this.consoleText = text;
	}

	public ConsoleTextStream() {
		super();
	}

	@Override
	public void write(final int b) throws IOException {
		try{
		consoleText.getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() {
				consoleText.append(Character.toString((char) b));					
			}
		});
		}catch(NullPointerException e){
			e.printStackTrace();
		}
			
	}

	public void setConsoleText(Text consoleText) {
		this.consoleText = consoleText;
	}
	
	
	
	

}
