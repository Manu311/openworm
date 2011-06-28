package core;

import java.awt.Point;
import java.util.ArrayDeque;

/**
 * Representation of the snake/worm
 * @author manu311
 */
public class worm extends ArrayDeque<Point> {
	private static final long serialVersionUID = 1L;
	/** Board on which this worm is running */
	private board game;
	
	/**
	 * Teleports the worm to the given Position
	 * @param here Position which should be visited
	 * @return false if the worm dies
	 */
	private boolean visit(Point pos) {
		switch (game.getPixelAt(pos)) {
		case FREE: break;
		case CHIP: this.game.score.addAChip(); this.game.createRandomChip(); break;
		case WORM: case OUTOFAREA:
			if (game.score.die())
				game.restart();
			else
				System.exit(0); //Dead
			return false; //TODO: Evtl ergänzen
	}
	this.game.pixels.put(pos, pixel.WORM);
	this.add(pos);
	this.game.setChanged();
	this.game.notifyObservers(pos);
	return true;
	}
	
	/**
	 * Moves the worm into the given direction
	 * @param to the direction to which the worm is going
	 * @return false if the worm dies
	 */
	protected boolean visit(direction direct) {
		Point curPos = this.peekLast();
		switch (direct) {
			case NORTH: return this.visit(new Point(curPos.x, curPos.y - 1));
			case EAST: return this.visit(new Point(curPos.x + 1, curPos.y));
			case SOUTH: return this.visit(new Point(curPos.x, curPos.y + 1));
			case WEST: return this.visit(new Point(curPos.x - 1, curPos.y));
		}
		return true; //Will never be reached
	}
	
	/**
	 * Draws the worm comin' in from the center of the left wall going over one third of the board (to the right)
	 */
	protected worm(board game)
	{
		this.game = game;
		int heightHalf = game.getHeight() / 2;
		int widthThird = game.getWidth() / 3;
		for (int i = 0; i < widthThird; i++) {
			this.visit(new Point(i, heightHalf));
		}
	}
	
	/**
	 * Shortens the worm for one pixel
	 */
	protected void shorter()
	{
		if (this.isEmpty())
			return;
		Point pos = this.pop();
		this.game.pixels.put(pos, pixel.FREE);
		this.game.setChanged();
		this.game.notifyObservers(pos);
	}
	
}
