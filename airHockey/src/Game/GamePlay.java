package Game;
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

	//Ball variables
	private int ballPosX = 400;
	private int ballPosY = 300;
	private int ballSpeedX = 2;
	private int ballSpeedY = 1;
	private Color ballColor = Color.white;
	private int ballWidth = 20;
	private int ballHeight = 20;
	//Two players data
	private int rScore = 0;
	private int lScore = 0;
	private int rPosX = 720;
	private int lPosX = 60;
	private int rPosY = 200;
	private int lPosY = 200;
	private int rPaddleHeight = 150;
	private int lPaddleHeight = 150;
	private int paddleWidth = 15;
	//Game variables
	private boolean play = false;
	private Timer timer;
	private int delay = 8;
	private int unit = 3;
	
	
	public GamePlay() {
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
		g.fillRect(0, 0, 800, 600);
		
		//Players' Goals
		g.setColor(Color.cyan);
		g.fillArc(-50, 75, 100, 100, 90, -180);
		g.fillArc(743, 75, 100, 100, 90, 180);
		g.fillArc(-50, 240, 100, 100, 90, -180);
		g.fillArc(743, 240, 100, 100, 90, 180);
		g.fillArc(-50, 400, 100, 100, 90, -180);
		g.fillArc(743, 400, 100, 100, 90, 180);
		
		//Player's paddles
		g.setColor(Color.green);
		g.fillRect(rPosX, rPosY, paddleWidth, rPaddleHeight);
		g.fillRect(lPosX, lPosY, paddleWidth, lPaddleHeight);
		
		//The ball
		g.setColor(this.ballColor);
		g.fillOval(ballPosX, ballPosY, ballWidth, ballHeight);
		
		//Draw Score
		g.setColor(Color.white);
		g.setFont(new Font ("serif",Font.BOLD,25));
		g.drawString("SCORE: "+this.rScore,650,30);
		g.setColor(Color.white);
		g.setFont(new Font ("serif",Font.BOLD,25));
		g.drawString("SCORE: "+this.lScore,50,30);
		
		if (play) {
			g.setColor(Color.green);
			g.setFont(new Font ("serif",Font.BOLD,25));
			g.drawString("Play",350,30);
		}else {
			g.setColor(Color.red);
			g.setFont(new Font ("serif",Font.BOLD,25));
			g.drawString("Pause",350,30);
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
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_W && this.lPosY >= 80)
			this.lPosY -= 30;
		if (e.getKeyCode() == KeyEvent.VK_S && this.lPosY <= 400)
			this.lPosY += 30;
		if (e.getKeyCode() == KeyEvent.VK_UP && this.rPosY >= 80)
			this.rPosY -= 30;
		if (e.getKeyCode() == KeyEvent.VK_DOWN && this.rPosY <= 400)
			this.rPosY += 30;
		if (e.getKeyCode() == KeyEvent.VK_P)
			play = !play;
		
	}

	public void moveBall() {
		if (play) {
			//Intersection with paddles
			Rectangle ballRect = new Rectangle(this.ballPosX,this.ballPosY,ballWidth,ballHeight);
			if (ballRect.intersects(new Rectangle (rPosX,rPosY,paddleWidth,rPaddleHeight))) {
				this.ballSpeedX = -this.ballSpeedX;
			}
			if (ballRect.intersects(new Rectangle (lPosX,lPosY,paddleWidth,lPaddleHeight))) {
				this.ballSpeedX = -this.ballSpeedX;
			}
			//Intersection with goals
			Rectangle lRect1 = new Rectangle (-50, 75, 100,100);
			Rectangle rRect1 = new Rectangle (743, 75, 100,100);
			Rectangle lRect2 = new Rectangle (-50, 240, 100,100);
			Rectangle rRect2 = new Rectangle (743, 240, 100,100);
			Rectangle lRect3 = new Rectangle (-50, 400, 100,100);
			Rectangle rRect3 = new Rectangle (743, 400, 100,100);
			
			if (ballRect.intersects(lRect1)||ballRect.intersects(lRect2)||ballRect.intersects(lRect3))
			{
				this.rScore ++;
				this.ballPosX = 400;
				this.ballPosY = 300;
				this.ballSpeedX = 2;
				this.ballSpeedY = 2;
				if (lScore > 0)
					checkPenaltyRight();
			}
			if (ballRect.intersects(rRect1)||ballRect.intersects(rRect2)||ballRect.intersects(rRect3))
			{
				this.lScore ++;
				this.ballPosX = 400;
				this.ballPosY = 300;
				this.ballSpeedX = -2;
				this.ballSpeedY = 2;
				if (rScore > 0)
					checkPenaltyLeft();
			}
			
				
			
			this.ballPosX += this.ballSpeedX;
			this.ballPosY += this.ballSpeedY;
			//if hits the top wall
			if (this.ballPosY <= 5 || this.ballPosY >= 550)
				this.ballSpeedY = -this.ballSpeedY;
		}
		
		
		
	}
	
	public void checkPenaltyRight(){
		if (this.rPaddleHeight >= 40)
			{
			this.rPaddleHeight -= ((rScore/lScore)) * 5;
			this.lPaddleHeight += ((rScore/lScore)) * 3;
			}
		
	}
	public void checkPenaltyLeft(){
		if (this.lPaddleHeight >= 40)
			{
				this.lPaddleHeight -= ((lScore/rScore)) * 5;
				this.rPaddleHeight += ((lScore/rScore)) * 3;
			}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	


}
