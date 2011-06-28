package core;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Mainclass for Graphical display - also holds the static main method
 * @author manu311
 */
public class WinMain extends JFrame {
	private static final long serialVersionUID = 1L;
	/** board which should be displayed */
	private board game;
	/** scoreboard holding the scores for this game */
	private scoreboard score;
	
	/**
	 * Initilize everything, board, scoreboard, snake, etc
	 */
	public WinMain() {
		this(100, 50, 0.01);
	}
	
	public WinMain(int width, int height, double percentage) {
		this.game = new board();
		this.score = new scoreboard();
		this.game.initBoard(width, height, percentage, this.score);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getRootPane().setLayout(new BorderLayout());
		this.getRootPane().add(this.score, BorderLayout.BEFORE_FIRST_LINE);
		this.getRootPane().add(this.drawBoard(), BorderLayout.CENTER);
		this.addKeyListener(new controller(this.game, 200));
		this.setVisible(true);
	}
	
	/**
	 * Draws the board with all it's pixels
	 * @return the JPanel containing the board
	 */
	private JPanel drawBoard()
	{
		JPanel retval = new JPanel(new GridLayout(this.game.getHeight(), this.game.getWidth()));
		
		for (int y = 0; y < game.getHeight(); y++) {
			for (int x = 0; x < game.getWidth(); x++) {
				pixelLabel addMe = new pixelLabel(this.game, new Point(x, y));
				retval.add(addMe);
			}
		}
		
		return retval;
	}
	
	/**
	 * Just create an instance of this class
	 * @param args width, height, number of chips / total pixels / 1000
	 */
	public static void main(String[] args) {
		for (String blah : args)
			if (blah.equals("--help")) {
				System.out.println("OpenWorm \nSyntax:\nopenbox width height chipspercentage\nwidth and height = dimension of the board\nchipspercentage = between 1 and 100");
				return;
			}
		
		try {
			int width, height;
			double percentage = 0.01;
			width = new Integer(args[0]).intValue();
			height = new Integer(args[1]).intValue();
			if (args.length > 2) {
				percentage = ((double) new Integer(args[2]).intValue()) / 1000;
				if (percentage > 0.1)
					percentage = 0.01;
			}
			new WinMain(width, height, percentage);
		}catch (RuntimeException e) {
			new WinMain();
		}
	}
}
