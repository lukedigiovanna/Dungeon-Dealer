package console;

import java.util.ArrayList;

public class MessageHandler {
	//note: older messages are later in the list
	private ArrayList<Message> msgs = new ArrayList<Message>(0);
	
	public MessageHandler() {
		
	}
	
	public void addMessage(Message msg) {
		ArrayList<Message> newMsgs = new ArrayList<Message>();
		for (int i = msgs.size()-1; i >= 0; i--) 
			newMsgs.add(0,msgs.get(i));
		newMsgs.add(0,msg);
		this.msgs = newMsgs;
	}
	
	public ArrayList<Message> getLivingMessages() {
		ArrayList<Message> newMsgs = new ArrayList<Message>();
		for (Message msg : msgs)
			if (msg.isAlive())
				newMsgs.add(msg);
		return newMsgs;
	}

	public ArrayList<Message> getMessages() {
		return msgs;
	}
}
