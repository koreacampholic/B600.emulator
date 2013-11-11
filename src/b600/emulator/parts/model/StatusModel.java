package b600.emulator.parts.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StatusModel {

	private String statusItem;
	private String statusValue;
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public StatusModel() {

	}

	public StatusModel(String statusItem, String statusValue) {
		super();
		this.statusItem = statusItem;
		this.statusValue = statusValue;
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public String getStatusItem() {
		return statusItem;
	}

	public void setStatusItem(String statusItem) {
		propertyChangeSupport.firePropertyChange("statusItem", this.statusItem,
				this.statusItem = statusItem);
	}

	public String getStatusValue() {

		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		propertyChangeSupport.firePropertyChange("statusValue",
				this.statusValue, this.statusValue = statusValue);

	}

	@Override
	public String toString() {
		return statusItem + " " + statusValue;
	}

	@Override
	public boolean equals(Object obj) {
		return this.statusItem.equals(((StatusModel) obj).getStatusItem()) ? true
				: false;
	}

}
