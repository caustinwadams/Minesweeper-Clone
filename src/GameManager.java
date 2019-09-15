import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GameManager {
	
	private int buttonsPressed;
	private int totalButtons;
	private int buttonsRemaining;
	private int numMines;
	private JFrame frame;
	
	public GameManager(int numButtons, int mines, JFrame frame) {
		this.frame = frame;
		totalButtons = numButtons * numButtons;
		numMines = mines;
		buttonsPressed = 0;
		buttonsRemaining = totalButtons;
	}
	
	public void setPressed() {
		buttonsPressed++;
		buttonsRemaining--;
		if (buttonsRemaining == numMines) {
			showWin();
		}
	}
	
	public int getPressed() {
		return buttonsPressed;
	}
	
	public int getMines() {
		return numMines;
	}
	
	public int getTotal() {
		return totalButtons;
	}
	
	
	public void showLoss() {
		final JDialog loser = new JDialog(frame, "You Lose.", true);
		loser.setSize(300, 200);
		int x = (frame.getWidth() / 2) - 150;
		int y = (frame.getHeight() / 2) - 100;
		loser.setLocation(frame.getLocation().x + x, frame.getLocation().y + y);
		
		loser.setAlwaysOnTop(true);
		loser.setResizable(false);
		
		loser.getContentPane().setLayout(new BoxLayout(loser.getContentPane(), BoxLayout.Y_AXIS));
		JLabel lossLabel = new JLabel("You Lose.");
		lossLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		loser.add(Box.createVerticalStrut(30));
		loser.add(lossLabel);
		
		JButton butNewGame = new JButton("New Game");
		butNewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		butNewGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				loser.dispose();
				int x = (frame.getWidth() / 2) - 150;
				int y = (frame.getHeight() / 2) - 100;
				Setup s = new Setup(frame.getLocation().x + x, 
									frame.getLocation().y + y);
				frame.dispose();
				
			}
			
		});
		
		JButton butQuit = new JButton("Quit");
		butQuit.setAlignmentX(Component.CENTER_ALIGNMENT);
		butQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loser.dispose();
				frame.dispose();
			}
			
		});
		loser.add(Box.createVerticalStrut(30));
		loser.add(butNewGame);
		loser.add(Box.createVerticalStrut(5));
		loser.add(butQuit);
		
		
		loser.setVisible(true);
	}
	
	
	
	
	public void showWin() {
		final JDialog winner = new JDialog(frame, "You Win!", true);
		winner.setSize(300, 200);
		winner.setLocation(frame.getLocation().x + 105, frame.getLocation().y + 160);
		
		winner.setAlwaysOnTop(true);
		winner.setResizable(false);
		
		winner.getContentPane().setLayout(new BoxLayout(winner.getContentPane(), BoxLayout.Y_AXIS));
		JLabel winLabel = new JLabel("Congratulations! You Won!!");
		winLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		winner.add(Box.createVerticalStrut(30));
		winner.add(winLabel);
		
		JButton butNewGame = new JButton("New Game");
		butNewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		butNewGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				winner.dispose();
				int x = winner.getLocation().x - 105;
				int y = winner.getLocation().y - 160;
				Setup s = new Setup(x,y);
				frame.dispose();
				
			}
			
		});
		
		JButton butQuit = new JButton("Quit");
		butQuit.setAlignmentX(Component.CENTER_ALIGNMENT);
		butQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				winner.dispose();
				frame.dispose();
			}
			
		});
		winner.add(Box.createVerticalStrut(30));
		winner.add(butNewGame);
		winner.add(Box.createVerticalStrut(5));
		winner.add(butQuit);
		
		
		winner.setVisible(true);
	}
	
	
	
	
}
