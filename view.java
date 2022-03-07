package hearthStone.view;

import javax.swing.*;
import java.awt.Color;
import java.awt.Container;

import javax.swing.border.Border;
import javax.swing.text.View;

import engine.Game;
import exceptions.FullHandException;
import hearthStone.controller.Controller;
import model.heroes.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class view extends JFrame {
	private JFrame frame;
	private JPanel currentHero;
	private JPanel currentHeroCards;
	private JPanel opponentHero;
	private JPanel center;
	private JPanel OppField;
	private JPanel currField;
	private JButton endTurn;
	private JButton useHeroPower;
	private JButton opponentHeroDetails;
	private JTextArea exceptions;
	private JButton currentHeroDetails;
	private JPanel CurrentHand;
	private JButton drawCard;
	private JButton burned;

	public view() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double w = (double) screenSize.getWidth();
		double h = (double) screenSize.getHeight();
		// this.setResizable(true);
		Image imge = Toolkit.getDefaultToolkit().getImage("herosofwarcragt.jpg");
		Image newImage = imge.getScaledInstance((int) w, (int) h, Image.SCALE_DEFAULT);
		this.setContentPane(new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(newImage, 0, 0, this);
			}
		});
		this.setTitle("HearthStone");
		this.setBounds(0, 0, (int) w, (int) h);
		this.setLayout(new BorderLayout());

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.revalidate();
		this.repaint();
		// ------------------------------------------------------OPPonent Hero Panel
		opponentHero = new JPanel();
		opponentHero.setOpaque(false);
		double rh = 5.0/27.0;
		double rw = 200.0 / 1920.0;
		opponentHero.setPreferredSize(new Dimension((int) (rw * w), (int) (rh * h)));
		opponentHero.setLayout(new BorderLayout());
		opponentHero.setBackground(handColor);
		opponentHero.setOpaque(false);
		this.add(opponentHero, BorderLayout.NORTH);

		// =====================draw card
//		drawCard = new JButton("Draw card");
//		drawCard.setForeground(Color.white);
//		drawCard.setOpaque(false);
//		drawCard.setBackground(new Color(17, 48, 94));
//		opponentHero.add(drawCard, BorderLayout.WEST);
//
//		this.revalidate();
//		this.repaint();
//
//		setVisible(true);
//		this.revalidate();
//		this.repaint();

		// -------------------------------------- Current Hero PANEL
		currentHero = new JPanel();
		currentHero.setOpaque(false);
		currentHero.setLayout(new BorderLayout());
		double ph = 250.0 / 1080.0;
		double pw = 250.0 / 1920.0;
		currentHero.setPreferredSize(new Dimension((int) (pw * w), (int) (ph * h)));
		this.revalidate();
		this.repaint();
		currentHero.setOpaque(false);
		currentHero.setBackground(handColor);
		this.add(currentHero, BorderLayout.SOUTH);
		setVisible(true);
		// ===========================adding current hero hand
		CurrentHand = new JPanel();
		CurrentHand.setLayout(new GridLayout(0, 11, 10, 2));
		rh = 250 / h;
		rw = 250 / w;
		CurrentHand.setPreferredSize(new Dimension((int) (0.9* w), (int) (ph * h)));
		CurrentHand.setBackground(handColor);
		CurrentHand.setOpaque(false);
		currentHero.add(CurrentHand, BorderLayout.EAST);

		// ====================adding currentHero details
		currentHeroDetails = new JButton();
		currentHeroDetails.setOpaque(false);
		currentHero.add(currentHeroDetails, BorderLayout.WEST);
		currentHeroDetails.setForeground(fieldColor);
		currentHeroDetails.setBackground(new Color(231, 238, 249));
		currentHeroDetails.setOpaque(false);

		this.revalidate();
		this.repaint();
		// currentHeroCards = new JPanel();

		// ------------------------------------------Center
		center = new JPanel();
		center.setLayout(new BorderLayout());
		double ch = 80.0/ 1080.0;
		rw = 250 / w;
		center.setPreferredSize(new Dimension((int) w, (int) (ch * h)));
		center.setBackground(Color.WHITE);
		center.setOpaque(false);
		this.add(center, BorderLayout.CENTER);
//		burned = new JButton();
//		burned.setFont(new Font("Dialog", Font.PLAIN, 20));
//
//		burned.setForeground(Color.white);
//		burned.setBackground(new Color(17, 48, 94));
//		burned.setPreferredSize(new Dimension(50, 50));
//		burned.setOpaque(false);
//		
//		//center.add(burned);
//		center.add(burned, BorderLayout.NORTH);

		this.revalidate();
		this.repaint();
		// ====================================================OPPField

		OppField = new JPanel(new GridLayout(0, 11, 2, 2));
		OppField.setBackground(fieldColor);
		center.add(OppField, BorderLayout.NORTH);
		rh = 200 / h;
		rw = 200 / w;
		OppField.setOpaque(false);
		OppField.setPreferredSize(new Dimension((center.getWidth() / 2) - (int) (rw * w), (int) (rh * h)));

		this.revalidate();
		this.repaint();

		// =================================================CurrField
		currField = new JPanel(new GridLayout(0, 11, 2, 2));
		currField.setBackground(fieldColor);
		currField.setPreferredSize(new Dimension((center.getWidth() / 2) - (int) (rw * w), (int) (rh * h)));
		currField.setOpaque(false);
		center.add(currField, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
//==============================================End Turn
		endTurn = new JButton("End Turn");
		endTurn.setFont(new Font("Dialog", Font.PLAIN, 20));

		endTurn.setForeground(Color.white);
		endTurn.setBackground(new Color(17, 48, 94));

		endTurn.setOpaque(false);
		center.add(endTurn, BorderLayout.WEST);

//===============================================use hero power
		useHeroPower = new JButton("Use Hero Power");
		useHeroPower.setFont(new Font("Dialog", Font.PLAIN, 20));

		useHeroPower.setBackground(new Color(17, 48, 94));
		useHeroPower.setForeground(Color.white);
		useHeroPower.setOpaque(false);
		center.add(useHeroPower, BorderLayout.EAST);

//================================================exceptions
		exceptions = new JTextArea();
		exceptions.setForeground(Color.white);
		exceptions.setFont(new Font("Dialog", Font.PLAIN, 20));

		exceptions.setBackground(handColor);
		exceptions.setOpaque(false);
		center.add(exceptions, BorderLayout.CENTER);
		this.setResizable(false);

	}

	public static final Color fieldColor = new Color(231, 238, 249);
	public static final Color handColor = new Color(203, 218, 242);

	public void setCurrentHeroPanel(JPanel i) {
		this.currentHero = i;

	}

	public void setOppPanel(JPanel i) {
		this.opponentHero = i;
	}

	public JPanel getOpponentHeroPanel() {
		return this.opponentHero;

	}

	public JPanel getcurrentHeroPanel() {
		return this.currentHero;

	}

	public JPanel getCenter() {
		return this.center;
	}

	public JButton getEndTurn() {
		return this.endTurn;
	}

	public JButton getUseHeroPower() {
		return this.useHeroPower;
	}

	public JTextArea getExceptions() {
		return this.exceptions;
	}

	public JPanel getCurrFieldPanel() {
		return this.currField;
	}

	public void setCurrField(JPanel i) {
		this.currField = i;
	}

	public JPanel getOppFieldPanel() {
		return this.OppField;
	}

	public JButton getCurrentHeroDetails() {
		return this.currentHeroDetails;
	}

	public JPanel getCurrentHand() {
		return this.CurrentHand;
	}

	public JButton getOpponentHeroDetails() {
		return this.opponentHeroDetails;
	}

	public String getTitle(JButton b) {
		String s = b.getText();
		return s;
	}

	public JButton getDrawCard() {
		return this.drawCard;
	}

	public JButton getBurned() {
		return this.burned;
	}

//	public static void main(String[] args) {
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		double w = (double) screenSize.getWidth();
//		double h = (double) screenSize.getHeight();
//		new view();
//////		double r = (200.0 / 800.0);		double rh = 200 / h;
//////		double rw = 200 / w;
//		double rh = 250 / 900;
//		double rw = 250 / w;
//		double tr = 200.0/1920.0;
//		int x = (int)(tr*1920);
//				
//		System.out.println("width is " +w+ "height is " + h);
//		System.out.println(x);}

}
