package core;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Representation of the scoreboard, which holds points and lifes of the current snake
 * it can also be used as JPanel, which is a graphical display.
 * @author manu311
 */
public class scoreboard extends JPanel
{
	private static final long serialVersionUID = 1L;
	private int points, lifes;
	private JLabel pointLabel, lifeLabel;
	
	/**
	 * Initializes this JPanel with content
	 */
	public scoreboard() {
		super(new GridLayout(2, 2));
		this.points = 0;
		this.lifes = 5;
		this.pointLabel = new JLabel(new Integer(this.points).toString());
		this.lifeLabel = new JLabel(new Integer(this.lifes).toString());
		this.add(new JLabel("Points: "));
		this.add(this.pointLabel);
		this.add(new JLabel("Lifes: "));
		this.add(this.lifeLabel);
	}
	
	/**
	 * Increases the pointcount (also refreshes the JLabel)
	 */
	protected void addAChip()
	{
		this.points += 1;
		this.pointLabel.setText(new Integer(this.points).toString());
	}
	
	/**
	 * Removes one life (also refreshes the JLabel)
	 * @return if there are lifes left = true
	 */
	protected boolean die()
	{
		this.lifes --;
		this.lifeLabel.setText(new Integer(this.lifes).toString());
		if (this.lifes <= 0)
			return false;
		else
			return true;
	}
	
	public int getPoints()
	{
		return this.points;
	}
	
	public void reset()
	{
		this.points = 0;
	}
}
