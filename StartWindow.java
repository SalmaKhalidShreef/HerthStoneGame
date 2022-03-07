package hearthStone.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import exceptions.FullHandException;
import hearthStone.controller.Controller;
import model.heroes.Hero;
import model.heroes.*;

public class StartWindow extends javax.swing.JFrame implements ActionListener {
	private JComboBox Hero1;
	private JComboBox Hero2;
	private JButton StartGame;
	private JTextArea Player1;
	private JTextArea Player2;
	private Image backgroundImage;
	private JOptionPane p1;
	private JOptionPane p2;
	private String P1Name;
	private String P2Name;
	private Controller control;
	Hero o;
	Hero c;

	public StartWindow() throws IOException, CloneNotSupportedException, FullHandException {
		Image imge = Toolkit.getDefaultToolkit().getImage("Hearthstone.jpg");
		Image newImage = imge.getScaledInstance(1000, 800, Image.SCALE_DEFAULT);
		this.setContentPane(new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(newImage, 0, 0, null);
			}
		});
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle("Start Window");
		this.setBounds(200, 200, 1000, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		ImageIcon img = new ImageIcon("Hearthstone.jpg");
		this.setIconImage(img.getImage());
		this.revalidate();
		this.repaint();
		p1 = new JOptionPane();
		p2 = new JOptionPane();
		Player1 = new JTextArea();
		Player2 = new JTextArea();
		Player1.setText("Choose a Hero to play With : ");
		Player1.setForeground(Color.white);
		Player1.setOpaque(false);
		Player1.setBounds(20, 20, 170, 30);
		this.add(Player1);
		Player2.setBounds(620, 20, 170, 30);
		Player2.setText("Choose a Hero to play With : ");
		Player2.setForeground(Color.white);
		Player2.setOpaque(false);

		this.add(Player2);

		String heros[] = { "Mage", "Hunter", "Paladin", "Priest", "Warlock" };
		Hero1 = new JComboBox(heros);
		Hero1.setBounds(200, 20, 100, 100);
		Hero1.setForeground(Color.black);

		Hero1.setOpaque(false);

		Hero2 = new JComboBox(heros);
		Hero2.setBounds(800, 20, 100, 100);
		this.add(Hero1);
		this.add(Hero2);
		this.revalidate();
		Hero1.setSelectedIndex(-1);
		Hero2.setSelectedIndex(-1);
		StartGame = new JButton("Click to Start");
		StartGame.setFont(new Font("Dialog", Font.PLAIN, 20));
		// StartGame.setForeground(Color.white);
		// StartGame.setIcon(startIcon);
		StartGame.setBounds(350, 550, 300, 50);
		StartGame.setOpaque(false);
		this.add(StartGame);
		this.revalidate();
		this.repaint();
		// JTextArea clicktoStart = new JTextArea("Click the Button to Start");
		// clicktoStart.setBounds(300, 500, 300, 50);
		// clicktoStart.setBackground(Color.BLACK);
		// clicktoStart.setOpaque(false);
		// clicktoStart.setForeground(Color.WHITE);
		// clicktoStart.setFont(Font.BOLD);
		// this.add(clicktoStart);

		// StartGame.setIcon(new ImageIcon("/HS-M2/src/photos/check.png"));
		StartGame.addActionListener(this);
		this.revalidate();
		this.repaint();

		Hero1.addActionListener(this);
		Hero2.addActionListener(this);
		// this.pack();
		if (Hero1.getSelectedItem() != null && Hero2.getSelectedItem() != null) {
			Hero c = getHero((String) Hero1.getSelectedItem());
			Hero o = getHero((String) Hero2.getSelectedItem());

			StartGame.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
//						Hero c = getHero((String) Hero1.getSelectedItem());
//						Hero o = getHero((String) Hero2.getSelectedItem());

						new Controller(c, o);

					} catch (FullHandException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});

		}

	}

	public Hero getHero(String s) throws IOException, CloneNotSupportedException {
		Mage Mage = new Mage();
		Hunter Hunter = new Hunter();
		Paladin Paladin = new Paladin();
		Priest Priest = new Priest();
		Warlock Warlock = new Warlock();
		if (s.equalsIgnoreCase("Mage"))
			return Mage;
		else if (s.equalsIgnoreCase("Hunter"))
			return Hunter;
		else if (s.equalsIgnoreCase("Paladin"))
			return Paladin;
		else if (s.equalsIgnoreCase("Priest"))
			return Priest;

		else if (s.equalsIgnoreCase("Warlock"))
			return Warlock;
		else
			return null;

	}

	public StartWindow getStartWind() {
		return this;
	}

	public static void main(String[] args) throws FullHandException, IOException, CloneNotSupportedException {
		new StartWindow();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if (Hero1.getSelectedItem() != null) {
//			String P1Name = p1.showInputDialog("Enter your name");
//			String heroName=Hero1.getName();
//			Hero1.setName(p1Name +""+ heroName);
//			
//
//		}
//		if (Hero2.getSelectedItem() != null) {
//			String P2Name = p2.showInputDialog("Enter your name");
//			String heroName=Hero2.getName();
//			Hero2.setName(p1Name +""+ heroName);
//
//		}

		o = null;
		c = null;
		if (Hero1.getSelectedItem() != null && Hero2.getSelectedItem() != null) {
			try {

				c = getHero((String) Hero1.getSelectedItem());
			} catch (IOException | CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				o = getHero((String) Hero2.getSelectedItem());
			} catch (IOException | CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (e.getSource() == StartGame) {

				try {

					Controller cont = new Controller(c, o);

					getStartWind().setVisible(false);
				} catch (FullHandException | CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

//		
		// TODO Auto-generated method stub

	}

	public String getP1Name() {
		return this.P1Name;
	}

	public String getP2Name() {
		return this.P2Name;
	}

}
