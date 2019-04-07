package misc;

import java.awt.*;
import java.awt.image.*;

public class UnknownImage extends BufferedImage {
	//just default....
	public UnknownImage() {
		super(2,2,BufferedImage.TYPE_INT_ARGB);
		int magenta = Color.magenta.getRGB();
		int black = Color.black.getRGB();
		this.setRGB(0, 0, black);
		this.setRGB(0, 1, magenta);
		this.setRGB(1, 0, magenta);
		this.setRGB(1, 1, black);
	}
}
