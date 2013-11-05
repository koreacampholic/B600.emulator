package b600.emulator;

import java.net.URL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		init();	//  log4j √ ±‚»≠ 
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
