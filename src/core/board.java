package core;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Map.Entry;

/**
 * This class represents the entire space in which the snake is moving.
 * As such, it also holds and creates the entire environment (like the snake itself)
 * @author manu311
 */
public class board extends Observable {
	/** Representation of all pixels of the board, ordered by their Positionvalues */
	protected HashMap<Point, pixel> pixels = new HashMap<Point, pixel>();
	/** width / height of the board */
	private int width, height;
	/** the percentage of chips (below 10% == 0.1) */
	private double chips;
	/** representation of a scoreboard, holding score and remaining lifes */
	protected scoreboard score;
	/** representation of the worm, can make him move, etc */
	protected worm snake;
	
	@Override
	protected void setChanged()
	{
		super.setChanged();
	}
	
	/**
	 * Initializes the board
	 * @param height the height of the gamefield
	 * @param width the width of the gamefield
	 * @param chips percentage of chips (mustn't be above 0.1)
	 * @return if successful
	 */
	public boolean initBoard(int height, int width, double chips, scoreboard score)
	{
		if (chips > 0.1) return false;
		this.width = width;
		this.height = height;
		this.score = score;
		this.chips = chips;
		this.restart();
		return true;
	}
	
	/** (re)creates the whole board, including a new snake and chips */
	protected void restart()
	{
		this.score.reset();
		if (this.snake != null)
			while (!this.snake.isEmpty())
				this.snake.shorter();
		this.snake = new worm(this);
		this.setChanged();
		this.notifyObservers(this.snake);
		this.createRandomChips(chips);
	}
	
	/**
	 * Getter for a Pixel on the board
	 * @param left y position, starting with 0 on the left side, ending with width - 1 on the right
	 * @param top x position, starting with 0 on the top, ending with height - 1 on the bottom
	 * @return the pixel at that position (pointer-alert!)
	 */
	public pixel getPixelAt(int left, int top)
	{
		return this.getPixelAt(new Point(left, top));
	}
	
	/**
	 * Getter for a Pixel on the board
	 * @param pos Position of the searched pixel
	 * @return the searched pixel
	 */
	public pixel getPixelAt(Point pos)
	{
		if (!pixels.containsKey(pos))
			if (	pos.x >= this.width || pos.y >= this.height ||
					pos.x < 0 || pos.y < 0)
				return pixel.OUTOFAREA;
			else
				return pixel.FREE;
		return pixels.get(pos);
	}	
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	/**
	 * Creates multiple random Chips on this board
	 * @param percentage musn't be higher then 0.1
	 */
	private void createRandomChips(double percentage)
	{
		for (Entry<Point, pixel> entry : this.pixels.entrySet()) {
			if (entry.getValue() == pixel.CHIP) {
				entry.setValue(pixel.FREE);
				this.setChanged();
				this.notifyObservers(entry.getKey());
			}
		}
		
		int toGo = ((int) ((double) this.width * this.height * percentage));
		
		if (toGo < 1 || percentage > 0.1)
			toGo = 1;
		
		while (toGo > 0) {
			this.createRandomChip();
			toGo--;
		}
	}
	
	/**
	 * Creates on Random Chip on the board (only at empty pixels)
	 */
	protected void createRandomChip()
	{
		Point pos = null;
		boolean found = false;
		
		while (!found) {
			pos = new Point((int) (Math.random() * this.width), (int) (Math.random() * this.height));
			if (this.getPixelAt(pos) == pixel.FREE)
				found = true;
		}
		this.pixels.put(pos, pixel.CHIP);
		this.setChanged();
		this.notifyObservers(pos);
	}
}
