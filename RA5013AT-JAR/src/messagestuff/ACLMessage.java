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
	
	public ACLMessage(Performative performative) {
		receivers = new ArrayList<>();
		userArgs = new HashMap<String, Object>();
		this.performative = performative;	
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
	
	public ACLMessage makeReply(Performative performative) {
		ACLMessage reply = null;
		if(sender != null || replyTo != null) {
			reply = new ACLMessage(performative);
			receivers = new ArrayList<>();
			reply.receivers.add(replyTo != null ? replyTo : sender);
			reply.language = language;
			reply.ontology = ontology;
			reply.encoding = encoding;
			reply.protocol = protocol;
			reply.conversationId = conversationId;
			
			reply.inReplyTo = replyWith;
			return reply;
		}
		return reply;
	}
	
}
