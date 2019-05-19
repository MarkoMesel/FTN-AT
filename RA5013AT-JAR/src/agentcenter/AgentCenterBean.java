package agentcenter;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(AgentCenter.class)
@LocalBean

public class AgentCenterBean implements AgentCenter {
	
	private String alias;
	private String address;
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
