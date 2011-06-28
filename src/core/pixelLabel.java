package core;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
/**
 * graphical representation of a single pixel
 * @author manu311
 */
public class pixelLabel extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	/** position of this pixel */
	private Point position;
	/** board on which this pixel is located */
	private board game;
	
	/**
	 * @param game board on which this pixel is located
	 * @param pos position of this pixel on the board
	 */
	public pixelLabel(board game, Point pos) {
		this.position = pos;
		this.game = game;
		this.game.addObserver(this);
		this.drawMe();
	}
	
	/**
	 * (re)draw's this pixel based on its content
	 */
	private void drawMe()
	{
		pixel pix = this.game.getPixelAt(this.position);
		switch (pix) {
			case FREE: this.setBackground(null); break;
			case CHIP: this.setBackground(Color.RED); break;
			case WORM: this.setBackground(Color.GREEN); break;
		}
		this.revalidate();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Point && arg.equals(this.position))
			this.drawMe();
	}
}
