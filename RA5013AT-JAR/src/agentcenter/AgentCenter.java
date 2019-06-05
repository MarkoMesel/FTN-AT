package agentcenter;

import agentstuff.AID;
import agentstuff.Agent;
import agentstuff.AgentType;
import jmstest.SendMessage;
import messagestuff.ACLMessage;

public interface AgentCenter {

	public void reciNesto();
	
	public String getAlias();
	
	public SendMessage getSM();

	public void startServerAgent(AID aid);
	
	public void createNewAgent(String typeName, AID aid);
	
	public Agent findAgent(AID aid);
	
	public Boolean theyAreEqual(AID a1, AID a2);
	
	public String[] getAgentTypes();

	public String[] getRunningAgents();

	public void stopServerAgent(AID aid);

	public void formMessage(String data);

	public void forwardToSM(ACLMessage aclMsg);

	public int numOfAgents();

}
