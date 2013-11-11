package b600.emulator.parts.model;

import java.util.ArrayList;
import java.util.List;

public enum StatusModelProvider {
	
	INSTANCE;
	
	private List<StatusModel> status;
	
	private StatusModelProvider(){
		status = new ArrayList<StatusModel>();
		status.add(new StatusModel("Agent�⵿", "NO"));
		status.add(new StatusModel("Firmware�⵿", "NO"));
		status.add(new StatusModel("Cloud����", "NO"));
		
	}
	
	public List<StatusModel> getStatus(){
		return status;
	}

}
