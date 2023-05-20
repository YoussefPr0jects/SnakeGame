import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;
import java.awt.Dimension;
import java.awt.*;

public class GamePanel extends JPanel implements ActionListener {
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25; // how big objects are
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE; // how much objects fit in screen
	static final int DELAY = 75; // tested the game and found out the best pace was 75
	final int x[] = new int[GAME_UNITS]; // all the x coordinates of the body parts and head of snake
	final int y[] = new int[GAME_UNITS]; // all of y ...
	int bodyParts = 6; // snake has 6 body parts
	int applesEaten;
	int appleX; // where appear located
	int appleY; // same
	char direction = 'R'; // snake initially goes Right;
	boolean running = false;
	Timer timer;
	Random random;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApple();
			checkCollisions();

		}
		repaint();
	}

	GamePanel() { // constructor
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.ORANGE);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		newApple();// creates a new app on screen
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		if (running) {
			for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); // draws x axis grid
				g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);// draws y axis grid
			}
			g.setColor(Color.RED);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) {
					g.setColor(Color.GREEN);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); // body of snake if i!-0
				} else {
					g.setColor(new Color(45, 180, 0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.RED);
			g.setFont( new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		}
		
		else {
			gameOver(g);
		}
	}

	public void newApple() {
		appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE; // apple size in width fits in a box in
																				// grid
		appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE; // apple size in height fits in a box in
																				// grid
	}

	public void move() {
		for (int i = bodyParts; i > 0; i--) { // shifting the snake
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch (direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}

	}

	public void checkApple() {
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}

	public void checkCollisions() {
		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i]))
				running = false; // checks if head run into body
		}
		// check if head touches left border
		if (x[0] < 0) {
			running = false;
		}
		// check if head touches RIGHT border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		// check if head touches top border
		if (y[0] < 0) {
			running = false;
		}
		// check if head touches bottom border
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		if(!running) {
			timer.stop();
		}
	}

	public void gameOver(Graphics g) {
		//Game Over text
		g.setColor(Color.RED);
		g.setFont( new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}