package agentcenter;

import agentstuff.AID;
import agentstuff.Agent;
import agentstuff.AgentType;

public interface AgentCenter {

	public void reciNesto();
	
	public String getAlias();

	public void startServerAgent(AID aid);
	
	public void createNewAgent(String typeName, AID aid);
	
	public Agent findAgent(AID aid);
	
	public Boolean theyAreEqual(AID a1, AID a2);
	
	public String[] getAgentTypes();

}
