package b600.emulator;

import java.net.URL;

import javax.inject.Inject;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.WriterAppender;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import b600.emulator.parts.console.ConsoleTextStream;

public class Activator implements BundleActivator {

	private static BundleContext context;
	public static WriterAppender agentWriterAppender = null;
	public static WriterAppender firmWriterAppender = null;
	public static ConsoleTextStream agentTextStream = null;
	public static ConsoleTextStream firmTextStream = null;
	static{
		agentTextStream = new ConsoleTextStream();
		agentWriterAppender = new WriterAppender(new PatternLayout("%m%n"), agentTextStream);
		agentWriterAppender.setName("agentwriterappender");
		//Logger.getRootLogger().addAppender(agentWriterAppender);
		//agentWriterAppender.setImmediateFlush(true);
		firmTextStream = new ConsoleTextStream();
		firmWriterAppender = new WriterAppender(new PatternLayout(), firmTextStream);
		firmWriterAppender.setName("firmwriterappender");		
		
	}
	
	
	

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		init();	//  log4j �ʱ�ȭ 
		
		
		System.out.println("START B600.emulator Activator");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
	private void init() throws Exception {
		// init log
		URL log4jConfig = Activator.class.getResource("/META-INF/log4j.property");
		if(log4jConfig != null)
			PropertyConfigurator.configure(log4jConfig);
		else
			BasicConfigurator.configure();
		
	}
	
}
