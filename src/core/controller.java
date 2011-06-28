package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is an Interface class, every movement of
 * the snake is controlled by this class, including keyboard events.
 * @author manu311
 */
public class controller implements KeyListener, Observer {
	/** Direction in which the snake is currently moving */
	private direction forward;
	/** The direction in which the snake is ordered to move after the next move */
	private direction nextForward = null;
	/** the snake which should be controlled */
	private worm snake;
	/** Timer which makes the snake moving */
	protected Timer goOn = new Timer(true);
	
	/**
	 * @param game pointer to the gameboard (must have snake initialized)
	 * @param delay delay before the snake moves to the next pixel
	 */
	public controller(board game, int delay) {
		this.snake = game.snake;
		game.addObserver(this);
		this.forward = direction.EAST;
		this.goOn.schedule(new moveOn(), delay + 2000, delay);
	}
	
	/**
	 * converts a Keycode to an direction
	 * @param keyCode keycode
	 * @return direction
	 */
	private direction toDirection(int keyCode) {
		switch (keyCode) { //TODO: Still possible to run backward with a fake direction in between
			case KeyEvent.VK_UP: if (this.forward != direction.SOUTH) return direction.NORTH; break;
			case KeyEvent.VK_RIGHT: if (this.forward != direction.WEST) return direction.EAST; break;
			case KeyEvent.VK_DOWN: if (this.forward != direction.NORTH) return direction.SOUTH; break;
			case KeyEvent.VK_LEFT: if (this.forward != direction.EAST) return direction.WEST; break;
		}
		return this.forward;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		this.nextForward = this.toDirection(arg0.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {}
	
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	/** make the snake move one pixel forward and removes the last pixel in it */
	private class moveOn extends TimerTask {
		@Override
		public void run() {
			snake.visit(forward);
			if (nextForward != null) {
				forward = nextForward;
				nextForward = null;
			}
			snake.shorter();
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof worm) {
			this.forward = direction.EAST;
			this.snake = (worm) arg1;
		}
	}
}
