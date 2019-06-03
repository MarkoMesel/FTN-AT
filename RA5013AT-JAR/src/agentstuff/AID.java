package agentstuff;

import java.io.Serializable;

public class AID implements Serializable {
	
	private String name;
	private String hostName;
	private AgentType type;
	
	public AID() {
		hostName = "acb";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHost() {
		return hostName;
	}
	public void setHost(String hostName) {
		this.hostName = hostName;
	}
	public AgentType getType() {
		return type;
	}
	public void setType(AgentType type) {
		this.type = type;
	}
	public String getStr() {
		return name + " - " 
					+ type.getName() + " - "
					+ type.getModule();
	}
	public void formFromString(String string) {
		String[] aidSplit = string.split(" - ");
		setName(aidSplit[0]);
		AgentType at = new AgentType();
		at.setName(aidSplit[1]);
		at.setModule(aidSplit[2]);
		setType(at);
	}
}
