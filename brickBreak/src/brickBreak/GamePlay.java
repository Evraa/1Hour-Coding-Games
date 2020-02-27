package brickBreak;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener, ActionListener{

	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 8;
	private int playerX = 310;
	private int level =1;
	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballDirX = 1;
	private int ballDirY = 2;
	
	private MapGenerator map;
	
	public GamePlay() {
		this.map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocoustraverselkeyenabled(false);
		timer = new Timer(this.delay , this);
		timer.start();
	}
	/**
	 * To Draw the frame
	 */
	public void paint (Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//Draw the map
		this.map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.blue);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//the paddle
		g.setColor(Color.red);
		g.fillRect(this.playerX, 550, 100, 8);
		
		//the ball
		g.setColor(Color.magenta);
		g.fillOval(this.ballPosX, this.ballPosY, 20, 20);
		
		//Score
		g.setColor(Color.white);
		g.setFont(new Font ("serif",Font.BOLD,25));
		g.drawString("SCORE: "+score,560,30);
		
		if(this.totalBricks == 0)
		{
			play = false;
			g.setColor(Color.green);
			g.setFont(new Font ("serif",Font.BOLD,25));
			g.drawString("GOOD JOB",190,300);
			g.setFont(new Font ("serif",Font.BOLD,20));
			g.drawString("Press Enter to Restart",205,350);
			restartGame();
		}
		
		if (this.ballPosY>=580) {
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			g.setColor(Color.RED);
			g.setFont(new Font ("serif",Font.BOLD,25));
			g.drawString("Game Over, Score is: "+score,190,300);
			g.setFont(new Font ("serif",Font.BOLD,20));
			g.drawString("Press Enter to Restart",205,350);
			
		}
		g.dispose();
	}
	
	private void setFocoustraverselkeyenabled(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		timer.start();
		moveBall();
		repaint();
		
	}

	@Override
	/**
	 * TO detect which key is pressed
	 */
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && this.playerX <= 600)
			moveRight();
		if (e.getKeyCode() == KeyEvent.VK_LEFT && this.playerX >= 0)
			moveLeft();
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(!play) {
				restartGame();
				repaint();
			}
		}
		
	}
	/**
	 * move the paddle right
	 */
	public void moveRight() {
		play = true;
		if (this.playerX >= 580)
		{
			this.playerX = 600;
			return;			
		}
		playerX += 30;
		return;
	}
	/**
	 * move the paddle left
	 */
	public void moveLeft() {
		play = true;
		if (this.playerX <= 20 )
		{
			this.playerX = 0;
			return;			
		}
		playerX -= 30;
		return;
	}
	
	/**
	 * Update the ball's status
	 */
	public void moveBall()
	{
		if (play)
		{
			//create a new rectangle around the ball
			Rectangle ballRect = new Rectangle(this.ballPosX,this.ballPosY,20,20);
			if (ballRect.intersects(new Rectangle (this.playerX,550,100,8)))
				this.ballDirY = -this.ballDirY;
			
			for (int i=0; i<map.map.length; i++) {
				for (int j=0; j<map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						//detect the intersection
						int brickX = j*map.brickWidth +80;
						int brickY = i*map.brickHieght +50;
						Rectangle rect = new Rectangle (brickX, brickY, map.brickWidth,map.brickHieght);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0,i,j);
							totalBricks--;
							score += 5;
							 
							//change the ball's directions
							if (ballPosX+19 <= brickRect.x || ballPosX+1 >= brickRect.x+brickRect.width)
								ballDirX =- ballDirX;
							else 
								ballDirY = -ballDirY;
							if (totalBricks == 10 && level == 1)
								{
									if (ballDirY < 0)
										ballDirY -= 1;
									else
										ballDirY +=1;
									level+=1;
								}
							if (totalBricks == 5 && level == 2)
							{
								if (ballDirY < 0)
									ballDirY -= 1;
								else
									ballDirY +=1;
								level+=1;
							}
							if (totalBricks == 3 && level == 3)
							{
								if (ballDirY < 0)
									ballDirY -= 1;
								else
									ballDirY +=1;
								level+=1;
							}
							
							}
						}
					}
				}
			this.ballPosX += this.ballDirX;
			this.ballPosY += this.ballDirY;
			//if hits the left wall
			if (this.ballPosX <= 0)
				this.ballDirX = - this.ballDirX;
			//if hits the top wall
			if (this.ballPosY <= 0)
				this.ballDirY = -this.ballDirY;
			//if hits the right wall
			if (this.ballPosX >= 680)
				this.ballDirX = -this.ballDirX;	
			}
			
				
	}
	public void restartGame() {
		play =true;
		ballPosX = 120;
		ballPosY = 350;
		ballDirX = 1;
		ballDirY = 2;
		score = 0;
		totalBricks = 21;
		delay = 8;
		playerX = 310;
		level = 1;
		map = new MapGenerator(3,7);
		return;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
 