package agentstuff;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;

import agentcenter.AgentCenter;
import agentcenter.AgentCenterBean;
import jmstest.MDBConsumer;
import messagestuff.ACLMessage;

import java.util.concurrent.TimeUnit;

@Stateful
@StatefulTimeout(unit = TimeUnit.MINUTES, value = 20)
public abstract class AbstractAgent implements Agent {
	
	protected AID aid;
	
	protected AgentCenterBean agc;
	
	@Override
	public void handleMessage(ACLMessage msg) {
		
	}

	@Override
	public AID getAid() {
		return aid;
	}
	
	@Override
	public void setAgc(AgentCenterBean agc) {
		this.agc = agc;
	}
}
