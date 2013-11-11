package b600.emulator.parts.model;

import java.util.ArrayList;
import java.util.List;

public enum StatusModelProvider {
	
	INSTANCE;
	
	private List<StatusModel> status;
	
	private StatusModelProvider(){
		status = new ArrayList<StatusModel>();
		status.add(new StatusModel("Agent기동", "NO"));
		status.add(new StatusModel("Firmware기동", "NO"));
		status.add(new StatusModel("Cloud연결", "NO"));
		
	}
	
	public List<StatusModel> getStatus(){
		return status;
	}

}
