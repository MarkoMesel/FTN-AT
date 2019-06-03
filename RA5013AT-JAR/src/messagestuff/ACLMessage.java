package messagestuff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import agentstuff.AID;

public class ACLMessage implements Serializable {

	
	public ACLMessage(AID sender, AID receiver, Performative performative, String content) {
		this.sender = sender;
		receivers = new ArrayList<>();
		receivers.add(receiver);
		this.performative = performative;
		this.content = content;
	}
	
	public AID sender;
	public List<AID> receivers;
	public Performative performative;
	public String content;
	public AID replyTo;
	public Object contentObj;
	public HashMap<String, Object> userArgs;
	public String language;
	public String encoding;
	public String ontology;
	public String protocol;
	public String conversationId;
	public String replyWith;
	public String inReplyTo;
	public Long replyBy;
	
}
