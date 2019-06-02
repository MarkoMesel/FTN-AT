package agentstuff;

public class AID {
	
	private String name;
	private String hostName;
	private AgentType type;
	
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
}
