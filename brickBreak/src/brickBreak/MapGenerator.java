package brickBreak;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {

	public int map[][];
	public int brickWidth;
	public int brickHieght;

	
	public MapGenerator (int row, int col)
	{
		this.map = new int [row][col];
		for (int i=0; i<this.map.length; i++)
			for (int j=0; j<map[0].length; j++)
				//the block is alive
				map[i][j] = 1;
		
		this.brickWidth = 540/col;
		this.brickHieght = 150/row;	
	}
	
	public void draw(Graphics2D g)
	{
		for (int i=0; i<this.map.length; i++)
		{
			for (int j=0; j<map[0].length; j++)
			{
				if (this.map[i][j] == 1)
				{
					g.setColor(Color.white);
					g.fillRect(j*brickWidth+80, i*brickHieght+50, brickWidth, brickHieght);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j*brickWidth+80, i*brickHieght+50, brickWidth, brickHieght);
					
				}
			}
		}
	}
	
	/**
	 * TO Update the brick's value if it got hit
	 * @param value
	 * @param row
	 * @param col
	 */
	public void setBrickValue(int value, int row, int col)
	{
		map[row][col] = value;
	}
	
}
