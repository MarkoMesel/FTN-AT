package messagestuff;

public enum Performative {
	ACCEPT_PROPOSAL,
	AGREE, 
	CANCEL, 
	CALL_FOR_PROPOSAL, 
	CONFIRM, 
	DISCONFIRM, 
	FAILURE, 
	INFORM, 
	INFORM_IF, 
	INFORM_REF, 
	NOT_UNDERSTOOD, 
	PROPAGATE, 
	PROPOSE, 
	PROXY, 
	QUERY_IF, 
	QUERY_REF,
	REFUSE, 
	REJECT_PROPOSAL, 
	REQUEST, 
	REQUEST_WHEN, 
	REQUEST_WHENEVER, 
	SUBSCRIBE,
	RESUME;
	
	public static String[] getPerformativesStringArray() {
	    Performative[] performatives = values();
	    String[] pStrings = new String[performatives.length];

	    for (int i = 0; i < performatives.length; i++) {
	        pStrings[i] = performatives[i].name();
	    }

	    return pStrings;
	}
}
