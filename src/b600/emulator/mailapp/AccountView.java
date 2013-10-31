package b600.emulator.mailapp;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.databinding.viewers.TreeStructureAdvisor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class AccountView {

	private IMailSessionFactory mailSessionFactory;
	private TreeViewer viewer;
	private String username;
	private String password;
	private String host;
	private IMailSession mailSession;

	public AccountView(Composite parent, IMailSessionFactory mailSessionFactory) {

		this.mailSessionFactory = mailSessionFactory;
		viewer = new TreeViewer(parent, SWT.FULL_SELECTION);
		viewer.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				if(element instanceof IAccount){
					return ((IAccount)element).getName();
				}else if(element instanceof IFolder){
					return ((IFolder)element).getName();
				}
				
				return super.getText(element);
			}
		});
		
		
		
		IObservableFactory factory = new IObservableFactory() {
			private IListProperty prop = BeanProperties.list("folders");
			@Override
			public IObservable createObservable(Object target) {
				if(target instanceof IObservableList){
					return (IObservable)target;
				}else if(target instanceof IFolderContainer){
					return prop.observe(target);
				}
				return null;
			}
		};
		
		TreeStructureAdvisor advisor = new TreeStructureAdvisor() {
		};
		
		viewer.setContentProvider(new ObservableListTreeContentProvider(factory, advisor));
	}

	public void setUsername(String username){
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setHost(String host){
		this.host = host;
	}
	
	public void init(){
		if(username != null && password != null && host != null){
			mailSession = mailSessionFactory.openSession(host,username,password);
			viewer.setInput(mailSession.getAccounts());
		}
		
	}
}
