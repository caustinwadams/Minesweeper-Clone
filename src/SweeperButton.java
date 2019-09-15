import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;


public class SweeperButton extends JButton {
	private JFrame parent;
	private String value;
	private int pos;
	private boolean pressed;
	private boolean flag;
	
	private ArrayList<SweeperButton> neighbors;
	
	private GameManager manager;
	
	
	public void expand() {
		boolean first = true;
		ArrayList<SweeperButton> toVisit = new ArrayList<SweeperButton>();
		ArrayList<SweeperButton> visited = new ArrayList<SweeperButton>();
		SweeperButton cur = null;
		toVisit.add(this);
		while(toVisit.size() != 0) {
			cur = toVisit.remove(0);
			visited.add(cur);
			if (cur.getValue().equals("")) {
				for (SweeperButton b : cur.getNeighbors()) {
					if (!visited.contains(b) && !toVisit.contains(b)) {
						toVisit.add(b);
					}
				}
			}

			if (cur.isEnabled()) {
				cur.setBackground(Color.WHITE);
				cur.setEnabled(false);
				cur.setText(cur.getValue());
				manager.setPressed();
			} else if (first) {
				first = false;
				manager.setPressed();
			}
							
		}
	}
	
	
	
	
	public SweeperButton(GameManager manager) {
		new SweeperButton("", manager);
	}
	
	
	
	public SweeperButton(String s, GameManager manager){
		flag = false;
		this.setBackground(Color.CYAN);
		//this.setBorder(BorderFactory.createEmptyBorder());
		this.pressed = false;
		this.manager = manager;
		this.neighbors = new ArrayList<SweeperButton>();
		value = "";
		
		
		this.addMouseListener(new MouseListener() {
			GameManager m = SweeperButton.this.manager;
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.isShiftDown()) {
					SweeperButton.this.setText("F");
				} else {
					SweeperButton.this.setBackground(Color.WHITE);
					SweeperButton.this.setText(value);
					SweeperButton.this.setEnabled(false);
					if (SweeperButton.this.getText().equals("M")) {
						m.showLoss();
					}else if (!SweeperButton.this.getText().equals("")) {
						m.setPressed();
					} else {
						SweeperButton.this.expand();
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
//		this.addActionListener(new ActionListener() {
//			GameManager m = SweeperButton.this.manager;
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				if (flag) {
//					SweeperButton.this.setText("F");
//				} else {
//					SweeperButton.this.setBackground(Color.WHITE);
//					SweeperButton.this.setText(value);
//					SweeperButton.this.setEnabled(false);
//					if (SweeperButton.this.getText().equals("M")) {
//						m.showLoss();
//					}else if (!SweeperButton.this.getText().equals("")) {
//						m.setPressed();
//					} else {
//						SweeperButton.this.expand();
//					}
//				}
//			}
//			
//		});
		
		this.setText(s);
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String val) {
		value = val;
	}
	
	public ArrayList<SweeperButton> getNeighbors() {
		return this.neighbors;
	}
	
	public int getPos() {
		return pos;
	}
	
	public void setPos(int p) {
		this.pos = p;
	}
	
	public String toString() {
		return value;
	}
	
	public boolean equals(Object o) {
		if (o == null && this != null) {
			return false;
		}
		
		if ((this == null && o != null) || 
			!(o instanceof SweeperButton) || 
			((SweeperButton) o).getPos() != this.getPos()) {
			return false;
		}
		
		return true;
	}
	
	public JFrame getFrame() {
		return parent;
	}
	
	public void setFrame(JFrame f) {
		this.parent = f;
	} 
	
	public boolean isPressed() {
		return pressed;
	}
	
	public void setButtonPressed() {
		pressed = true;
	}
}
