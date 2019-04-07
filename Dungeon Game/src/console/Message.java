package console;

import java.awt.Color;

public class Message {
	public final static int DEFAULT_LIFE_SPAN = 4000;
	private String text;
	
	private int lifeSpan; //given in ms
	private boolean dead = false;
	
	public Message(String message) {
		this(message,DEFAULT_LIFE_SPAN);
	}
	
	public Message(String message, int lifeSpan) {
		this.text = message;
		this.lifeSpan = lifeSpan;
		Thread lifeThread = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(Message.this.lifeSpan);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				dead = true;
			}
		});
		lifeThread.start();
	}
	
	public static String getRandomText(int length) {
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWCYZ0123456789!@#$%^&*(,./;'[]=-\\_+{}|\":?><`~";
		String randomString = "";
		for (int i = 0; i < length; i++)
			randomString+=alphabet.charAt((int)(Math.random()*alphabet.length()));
		return randomString;
	}
	
	public boolean isAlive() {
		return !dead;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
