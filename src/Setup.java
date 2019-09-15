import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Setup {
	
	enum types {
		EASY, MEDIUM, HARD, CUSTOM
	};
	
	Object[] levels = {"Easy", "Medium", "Hard", "Custom"};
	private JFrame setupFrame;
	private JPanel buttons;
	private JButton btnStart;
	private JButton btnExit;
	private JComboBox<Object> cmbLevel;
	private JPanel labelPanel;
	private JLabel gridLabel;
	private JTextField gridText;
	private JLabel minesLabel;
	private JTextField minesText;
	
	private int gridSize;
	private int numMines;
	private final int FRAME_WIDTH = 300;
	private final int FRAME_HEIGHT = 175;
	
	public Setup() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		new Setup((d.width - FRAME_WIDTH) / 2, (d.height - FRAME_HEIGHT) / 2);
	}
	
	public Setup(int x, int y) {
		gridSize = 10;
		numMines = 15;
		
		setupFrame = new JFrame("Start Game");
		setupFrame.setLocation(x, y);
		setupFrame.setSize(300, 175);
		setupFrame.getContentPane().setLayout(new BoxLayout(setupFrame.getContentPane(), BoxLayout.Y_AXIS));
		setupFrame.setResizable(false);
		
		cmbLevel = new JComboBox<Object>(levels);
		cmbLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				types type = getType((String)Setup.this.cmbLevel.getSelectedItem());

				switch(type) {
				case EASY:
					numMines = 15;
					gridSize = 10;
					break;
				case MEDIUM:
					numMines = 40;
					gridSize = 16;
					break;
				case HARD:
					numMines = 60;
					gridSize = 22;
					break;
				case CUSTOM:
					Setup.this.minesText.setEditable(true);
					Setup.this.minesText.setText("");
					Setup.this.gridText.setEditable(true);
					Setup.this.gridText.setText("");
				}
				
				if (type != types.CUSTOM) {
					Setup.this.minesText.setEditable(false);
					Setup.this.minesText.setText("" + numMines);
					Setup.this.gridText.setEditable(false);
					Setup.this.gridText.setText(gridSize + "x" + gridSize);
				}
				
			}
			
		});
		cmbLevel.setPreferredSize(new Dimension(250,25));
		cmbLevel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cmbLevel.setMaximumSize(cmbLevel.getPreferredSize());
		
		setupFrame.add(Box.createVerticalGlue());
		setupFrame.add(cmbLevel);
		setupFrame.add(Box.createVerticalGlue());
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout());
		gridLabel = new JLabel("Grid: ");
		labelPanel.add(gridLabel);
		gridText = new JTextField(gridSize + "x" + gridSize);
		gridText.setPreferredSize(new Dimension(50,20));
		gridText.setMaximumSize(gridText.getPreferredSize());
		gridText.setEditable(false);
		labelPanel.add(gridText);
		
		minesLabel = new JLabel("# Mines: ");
		minesText = new JTextField(((Integer)numMines).toString());
		minesText.setPreferredSize(new Dimension(50,20));
		minesText.setMaximumSize(minesText.getPreferredSize());
		minesText.setEditable(false);
		
		
		labelPanel.add(Box.createHorizontalStrut(20));
		labelPanel.add(minesLabel);
		labelPanel.add(minesText);
		
		setupFrame.add(labelPanel);
		
		setupFrame.add(Box.createVerticalGlue());
		
		buttons = new JPanel();
		
		btnStart = new JButton("Start");
		
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int x = (((30 * gridSize) + 150) / 2);
				int y = (((30 * gridSize) + 150) / 2);
				
				Dimension setupSize = Setup.this.setupFrame.getSize();
				int setup_offset_x = setupSize.width / 2;
				int setup_offset_y = setupSize.height / 2;
				
				Point p = new Point(Setup.this.setupFrame.getLocation().x - x + setup_offset_x, 
								    Setup.this.setupFrame.getLocation().y - y + setup_offset_y);
				new Gui(p, gridSize, numMines);
				Setup.this.setupFrame.dispose();
			}
			
		});
		
		buttons.add(btnStart);
		buttons.add(Box.createHorizontalStrut(20));
		
		btnExit = new JButton("Exit");
		
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Setup.this.setupFrame.dispose();
			}
			
		});
		
		buttons.add(btnExit);
		setupFrame.add(buttons);
		setupFrame.add(Box.createVerticalGlue());
		
		setupFrame.setVisible(true);
	}
	
	public types getType(String s) {
		if (s.equals("Easy"))
			return types.EASY;
		else if (s.equals("Medium"))
			return types.MEDIUM;
		else if (s.equals("Hard"))
			return types.HARD;
		return types.CUSTOM;	
	}
	
	
}
