package agentstuff;

import agentcenter.AgentCenterBean;

public class AID {
	
	private String name;
	private AgentCenterBean host;
	private AgentType type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AgentCenterBean getHost() {
		return host;
	}
	public void setHost(AgentCenterBean host) {
		this.host = host;
	}
	public AgentType getType() {
		return type;
	}
	public void setType(AgentType type) {
		this.type = type;
	}
}
