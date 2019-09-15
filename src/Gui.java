import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.Border;


public class Gui {
	int pos = 0;
	
	
	private JFrame frame;
	private ArrayList<SweeperButton> buttons;
	private SweeperButton[][] butArr;
	
	private JMenuBar menuBar;
	private JMenu gameItem;
	private JMenuItem newGame;
	
	private JPanel gamePanel;
		
	private int button_size = 30;
	private int num_mines = 17;
	private int board_size = 12;
	
	private GameManager manager;
	private JPanel notMenu;
	
	public int mines_help(int m, int n) {
		if (m >= 0 && m < board_size && n >= 0 && n < board_size) {
			return butArr[m][n].getValue().equals("M") ? 1 : 0;
		}
		return 0;
	}
	
	public int get_mines(int n, int m) {
		int total = 0;
		total += mines_help(n+1, m);
		total += mines_help(n+1, m+1);
		total += mines_help(n+1, m-1);
		total += mines_help(n-1, m);
		total += mines_help(n-1, m+1);
		total += mines_help(n-1, m-1);
		total += mines_help(n, m+1);
		total += mines_help(n, m-1);
		frame.repaint();
		frame.validate();
		return total;
	}
	
	
	
	public void set_neighbors(int m, int n) {
		ArrayList<SweeperButton> list = butArr[m][n].getNeighbors();

		if (m + 1 < board_size) {
			butArr[m][n].getNeighbors().add(butArr[m+1][n]);
			if (n + 1 < board_size) {
				butArr[m][n].getNeighbors().add(butArr[m+1][n+1]);
			}
			if (n - 1 >= 0) {
				butArr[m][n].getNeighbors().add(butArr[m+1][n-1]);
			}
		}
		
		
		if (m - 1 >= 0) {
			butArr[m][n].getNeighbors().add(butArr[m-1][n]);
			if (n + 1 < board_size) {
				butArr[m][n].getNeighbors().add(butArr[m-1][n+1]);
			}
			if (n - 1 >= 0) {
				butArr[m][n].getNeighbors().add(butArr[m-1][n-1]);
			}
		}
		
		if (n - 1 >= 0) {
			butArr[m][n].getNeighbors().add(butArr[m][n-1]);
		}
		
		if (n + 1 < board_size) {
			butArr[m][n].getNeighbors().add(butArr[m][n+1]);
		}
		

		
		
	}
	

	
	public Gui(Point locPoint, int size, int numMines){
		
		board_size = size;
		num_mines = numMines;
		
		UIManager.put("OptionPane.okButtonText", "New Game");
		frame = new JFrame("Austin's Minesweeper");
		int total_size = (button_size * board_size) + 150;
		frame.setSize(total_size, total_size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocation(locPoint);
		
		menuBar = new JMenuBar();
		gameItem = new JMenu("Game");
		menuBar.add(gameItem);
		newGame = new JMenuItem("New");
		gameItem.add(newGame);
		frame.add(menuBar, BorderLayout.NORTH);
		
		
		
		notMenu = new JPanel();
		notMenu.setLayout(new BoxLayout(notMenu, BoxLayout.Y_AXIS));
		notMenu.setBackground(Color.GRAY);
		
		
		gamePanel = new JPanel();
		gamePanel.setBackground(Color.BLACK);
		gamePanel.setPreferredSize(new Dimension((button_size * board_size),(button_size * board_size)));
		gamePanel.setMaximumSize(gamePanel.getPreferredSize());
		buttons = new ArrayList<SweeperButton>();
		
		manager = new GameManager(board_size, num_mines, frame);
		
		butArr = new SweeperButton[board_size][board_size];
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				SweeperButton b = new SweeperButton("", manager);
				b.setMargin(new Insets(0,0,0,0));
				b.setPreferredSize(new Dimension(40,40));
				b.setPos(pos);
				pos++;
				b.setFrame(this.frame);
				buttons.add(b);
				butArr[i][j] = b;
			}
		}
		
		gamePanel.setLayout(new GridLayout(board_size,board_size,2,2));
		gamePanel.setBorder(BorderFactory.createEmptyBorder(0,1,1,1));
		
		
		int mines = num_mines;
		while (mines > 0) {
			int r1 = (int)(Math.random() * (board_size - 1));
			int r2 = (int)(Math.random() * (board_size - 1));
			if (butArr[r1][r2].getValue().equals("M")) {
				continue;
			} else {
				butArr[r1][r2].setValue("M"); 
				mines--;
			}
		}
		
		int tot_mines = 0;
		for (int n = 0; n < board_size; n++) {
			for (int m = 0; m < board_size; m++) {
				if (!butArr[m][n].getValue().equals("M")) {
					set_neighbors(m,n);
					tot_mines = get_mines(m,n);
					butArr[m][n].setValue(tot_mines > 0 ? ((Integer)tot_mines).toString() : "");
				}
			}
		}
		
		
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				gamePanel.add(butArr[i][j]);
			}
		}
		
		notMenu.add(Box.createVerticalStrut(50));
		notMenu.add(gamePanel, BorderLayout.CENTER);
		notMenu.add(Box.createVerticalStrut(50));
		
		frame.add(notMenu, BorderLayout.CENTER);
		frame.repaint();
		frame.validate();
		
		
	}
	
	
	public Gui() {
		new Gui(new Point(400,200), 12, 15);
	}
	
	public Gui(int x, int y) {
		new Gui(new Point(x,y), 12, 15);
	}
	
}
