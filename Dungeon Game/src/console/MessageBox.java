package console;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MessageBox {
	public final static int PERSIST_OLD_MESSAGES = 0, SHOW_LIVING_MESSAGES = 1;
	public final static int DEFAULT_DISPLAY_MODE = SHOW_LIVING_MESSAGES;
	private Color backgroundColor = new Color(255,255,255,1);
	private MessageHandler msgs = new MessageHandler();
	private int displayMode = DEFAULT_DISPLAY_MODE;
	
	public MessageBox() {
		
	}
	
	public MessageHandler getHandler() {
		return msgs;
	}
	
	public String typeMessage = "";
	private int offset = 0;
	public BufferedImage getImage() {
		int width = 400, height = 300;
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		backgroundColor = new Color(255,255,255,0);
		if (displayMode == PERSIST_OLD_MESSAGES)
			backgroundColor = new Color(255,255,255,25);
		g.setColor(backgroundColor);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		ArrayList<Message> msgsArr = null;
		switch (displayMode) {
		case SHOW_LIVING_MESSAGES:
			msgsArr = msgs.getLivingMessages();
			break;
		case PERSIST_OLD_MESSAGES:
			msgsArr = msgs.getMessages();
			break;
		default:
			msgsArr = msgs.getLivingMessages();
		}
		
		int lineOffset = 0; 
		for (int i = 0; i < msgsArr.size(); i++) {
			String msg = msgsArr.get(i).getText();
			Color c = Color.WHITE;
			int s = Font.PLAIN;
			boolean random = false;
			int x = 0;
			int index = 0;
			while (msg.length() > 0) {
				char character = msg.charAt(0);
				msg = (msg.length() == 1) ? "" : msg.substring(1);
				index++;
				if (msg.length() >= 5 && character == '<' && msg.charAt(1) == '=' && msg.charAt(3) == '>') {
					char comChar = msg.charAt(0);
					char valChar = msg.charAt(2);
					switch (comChar) {
					case 'c':
						switch (valChar) {
						case 'r':
							c = Color.RED;
							break;
						case 'b':
							c = Color.BLUE;
							break;
						case 'o':
							c = Color.ORANGE;
							break;
						case 'y':
							c = Color.YELLOW;
							break;
						case 'g':
							c = Color.GREEN;
							break;
						case 'm':
							c = Color.MAGENTA;
							break;
						case 'c':
							c = Color.CYAN;
							break;
						case 'd':
							c = Color.GREEN.darker().darker();
							break;
						case 'a':
							c = Color.GRAY;
							break;
						case 'w':
							c = Color.WHITE;
							break;
						}
						break;
					case 's':
						switch (valChar) {
						case 'p':
							s = Font.PLAIN;
							break;
						case 'b':
							s = Font.BOLD;
							break;
						case 'i':
							s = Font.ITALIC;
							break;
						case 't':
							s = Font.BOLD|Font.ITALIC;
							break;
						}
						break;
					case 'r':
						switch (valChar) {
						case 't':
							random = true;
							break;
						case 'f':
							random = false;
						}
						break;
					}
					msg = msg.substring(4);
					index+=4;
					continue;
				}
				if (random)
					character = randomChar();
				g.setFont(new Font("Monospace",s,12));
				g.setColor(c);
				int extY = (displayMode == PERSIST_OLD_MESSAGES) ? g.getFontMetrics().getHeight() : 0;
				g.drawString(character+"", 10+x, (int) lineOffset+offset+(height-i*(g.getFontMetrics().getHeight())-g.getFontMetrics().getHeight())-extY);
				x+=g.getFontMetrics().stringWidth(character+"");
				if (displayMode == PERSIST_OLD_MESSAGES) {
					String bar = (main.Runtime.timer(400)) ? "" : "|";
					g.setColor(Color.WHITE);
					g.setFont(new Font("Monospace",Font.PLAIN,12));
					g.drawString(typeMessage+bar, 10, offset+height-g.getFontMetrics().getHeight());
				} else {
					typeMessage = "";
				}
			}
		}
		return img;
	}
	
	public MouseWheelListener scroll = new MouseWheelListener() {
		public void mouseWheelMoved(MouseWheelEvent e) {
			if (displayMode == PERSIST_OLD_MESSAGES) {
				int addition = (int)(e.getPreciseWheelRotation()*20);
				if (addition == 0 && e.getPreciseWheelRotation() > 0)
					addition = 1;
				else if (addition == 0 && e.getPreciseWheelRotation() < 0)
					addition = -1;
				addition*=-1;
				offset+=addition;
			}
			if (offset < 0)
				offset = 0;
		}
	};
	
	public KeyListener activation = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_T: 
				if (getDisplayMode() == MessageBox.SHOW_LIVING_MESSAGES)
					setDisplayMode(MessageBox.PERSIST_OLD_MESSAGES);
				break;
			case KeyEvent.VK_SLASH:
				if (getDisplayMode() == MessageBox.SHOW_LIVING_MESSAGES)
					setDisplayMode(MessageBox.PERSIST_OLD_MESSAGES);
				if (typeMessage.equals(""))
				typeMessage = "/";
				break;
			case KeyEvent.VK_ESCAPE:
				if (getDisplayMode() == MessageBox.PERSIST_OLD_MESSAGES)
					setDisplayMode(MessageBox.SHOW_LIVING_MESSAGES);
				break;
			}
		}
	};
	
	public KeyListener keyboard = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if (displayMode == PERSIST_OLD_MESSAGES) {
				if (e.getKeyCode()==KeyEvent.VK_BACK_SPACE && typeMessage.length() > 0) {
					typeMessage = typeMessage.substring(0,typeMessage.length()-1);
				} else if (e.getKeyCode()==KeyEvent.VK_ENTER && typeMessage.length() > 0) {
					if (typeMessage.charAt(0) == '/') {
						Console.log(typeMessage); //indicative of a command so dont include the prefix
						Console.setDisplayMode(MessageBox.SHOW_LIVING_MESSAGES);
					} else
						Console.log("<s=t><c=y><USER><s=p><c=w> "+typeMessage);
					typeMessage = "";
				} else if (isLetter(e.getKeyChar())) {
					offset = 0;
					typeMessage+=e.getKeyChar();
				}
			}
		}
	};
	
	private boolean isLetter(char c) {
		c = Character.toLowerCase(c);
		String alphabet = "abcdefghijklmnopqrstuvwxyz!@#$%^&*()_+~`1234567890-=[]{};':\"<>?,./ |\\";
		char[] chars = alphabet.toCharArray();
		for (char a : chars)
			if (c == a)
				return true;
		return false;
	}
	
	private char randomChar() {
		String alphabet = "!@#$%^&*?";
		char[] arr = alphabet.toCharArray();
		return arr[(int)(Math.random()*arr.length)];
	}
	
	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}
	
	public int getDisplayMode() {
		return this.displayMode;
	}
}
