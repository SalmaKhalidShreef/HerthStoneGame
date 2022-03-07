package hearthStone.controller;

import model.*;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;

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
import java.util.ArrayList;

import javax.swing.*;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import hearthStone.view.StartWindow;
import hearthStone.view.view;

public class Controller implements GameListener, ActionListener {

	private JButton startGame;
	private Game model;
	private view view;
	private JButton opponentHeroDetails;
	private JTextArea onGameOverDetails;
	private ArrayList<JButton> CurrentHeroHand;
	private ArrayList<JButton> CurrentHeroHandSpells;
	private ArrayList<JButton> OpponentHeroHand;
	private ArrayList<JButton> currentHeroField;
	private ArrayList<JButton> oppHeroField;
	private int alaa;
	private int tasneem;
	private boolean flag;
	private boolean attack;
	private int integerofspell;
	private boolean oppFieldflag;
	private boolean oppHeroFlag;
	private int magepower;
	private boolean magepowerminion;
	public boolean heropower;
	public boolean hunterheropower;

	public Controller(Hero currentHero, Hero OpponentHero) throws FullHandException, CloneNotSupportedException {
		model = new Game(currentHero, OpponentHero);
		model.setListener(this);
		view = new view();

		CurrentHeroHand = new ArrayList<JButton>();
		for (int i = 0; i < model.getCurrentHero().getHand().size(); i++) {
			JButton b = new JButton();
			Card c = model.getCurrentHero().getHand().get(i);
			if (c instanceof Minion) {
				// ====================here
				b.setToolTipText(OpponentMinionDetails(c));
				b.addActionListener(this);
				setIcon(b, c);

			} else if (c instanceof Spell) {
				b.setToolTipText(SpellDetails(c));
				b.addActionListener(this);
				setIcon(b, c);

			}
			view.getCurrentHand().add(b);
			CurrentHeroHand.add(b);

		}
		view.getEndTurn().addActionListener(this);
		view.getUseHeroPower().addActionListener(this);
		// view.getDrawCard().addActionListener(this);
		view.getCurrentHeroDetails().addActionListener(this);

		// --------------------------------------- opponent Hero panel

		opponentHeroDetails = new JButton();

		String x = "<html><font color=\"#000\" " + "size=\"5\">" + "Player Name " + "Opponent Hero Name : "
				+ model.getOpponent().getName() + "<br> Current Health Points : " + model.getOpponent().getCurrentHP()
				+ "<br>Total ManaCrystals :  " + model.getOpponent().getTotalManaCrystals()
				+ "<br> Current ManaCrystals : " + model.getOpponent().getCurrentManaCrystals()
				+ "<br> Number of Cards left in Deck : " + model.getOpponent().getDeck().size()
				+ "<br>Number of cards in hand : " + model.getOpponent().getHand().size() + "</font></p></html>";
		// ====================here
		opponentHeroDetails.setToolTipText(x);
		opponentHeroDetails.setOpaque(false);

		setHeroIcon(opponentHeroDetails, model.getOpponent());

		opponentHeroDetails.setBackground(Color.WHITE);
		opponentHeroDetails.addActionListener(this);

		view.getOpponentHeroPanel().add(opponentHeroDetails, BorderLayout.CENTER);
		// ------------------------------------------current hero panel

		String v = "<html><font color=\"#000\" " + "size=\"5\"> " + "Player name :" + "Current Hero Name : "
				+ model.getCurrentHero().getName() + "<br> Current Health Points : "
				+ model.getCurrentHero().getCurrentHP() + "<br> Total ManaCrystals "
				+ model.getCurrentHero().getTotalManaCrystals() + "<br> Current ManaCrystals : "
				+ model.getCurrentHero().getCurrentManaCrystals() + "<br> Number of Cards left  : "
				+ model.getCurrentHero().getDeck().size() + "</font></p></html>";
		// ============================here
		view.getCurrentHeroDetails().setToolTipText(v);
		setHeroIcon(view.getCurrentHeroDetails(), model.getCurrentHero());

		view.getCurrentHeroDetails().setBackground(Color.white);

		view.revalidate();
		view.repaint();

		// ---------------------------------------------------------------curr Field
		currentHeroField = new ArrayList<JButton>();
		for (int i = 0; i < model.getCurrentHero().getField().size(); i++) {
			Card c = model.getCurrentHero().getField().get(i);
			JButton b = new JButton();
			if (c instanceof Minion) {
				b.setToolTipText(OpponentMinionDetails(c));
				b.addActionListener(this);
				setIcon(b, c);
				System.out.print("index:");
			}

			view.getCurrFieldPanel().add(b);
			currentHeroField.add(b);
		}
		// --------------------------------------------- opp field
		oppHeroField = new ArrayList<JButton>();

		for (int i = 0; i < model.getOpponent().getField().size(); i++) {
			Card c = model.getOpponent().getField().get(i);
			JButton b = new JButton();
			if (c instanceof Minion) {
				b.setToolTipText(OpponentMinionDetails(c));
				setIcon(b, c);
			}

			view.getOppFieldPanel().add(b);
			oppHeroField.add(b);
		}

	}

	// ------------------------------method for getting minion info
	public String OpponentMinionDetails(Card c) {
		Minion m = (Minion) c;
		if (m.isSleeping() == true) {
			return "<html><font color=\"#000\"" + "size=\"5\"> Minion name : " + m.getName() + "<br>Mana Cost : "
					+ m.getManaCost() + "<br>Rarity : " + m.getRarity() + "<br>attack points : " + m.getAttack()
					+ "<br>Current health points : " + m.getCurrentHP() + "<br>Taunt: " + m.isTaunt() + "<br>Divine:"
					+ m.isDivine() + "<br>Minion is not charged</font></p></html>";

		} else
			return "<html><font color=\"#000\"" + "size=\"5\">Minion name : " + m.getName() + "<br>Mana Cost : "
					+ m.getManaCost() + "<br>Rarity : " + m.getRarity() + "<br>attack points : " + m.getAttack()
					+ "<br>Current health points : " + m.getCurrentHP() + "<br>Taunt: " + m.isTaunt() + "<br>Divine:"
					+ m.isDivine() + "<br>Minion is charged</font></p></html>";

	}

	public String CurrentMinionDetails(Card c) {
		Minion m = (Minion) c;
		if (m.isSleeping() == true) {
			return "<html><font color=\"#000\"" + "size=\"5\">Minion name : " + m.getName() + "<br>Mana Cost : "
					+ m.getManaCost() + "<br>Rarity : " + m.getRarity() + "<br>attack points : " + m.getAttack()
					+ "<br>Current health points : " + m.getCurrentHP() + "<br>Taunt: " + m.isTaunt() + "<br>Divine:"
					+ m.isDivine() + "<br>Minion is not charged" + "<br>Minion is still sleeping</font></p></html>";

		} else
			return "<html><font color=\"#000\"" + "size=\"5\">Minion name : " + m.getName() + "<br>Mana Cost : "
					+ m.getManaCost() + "<br>Rarity : " + m.getRarity() + "<br>attack points : " + m.getAttack()
					+ "<br>Current health points : " + m.getCurrentHP() + "<br>Taunt: " + m.isTaunt() + "<br>Divine:"
					+ m.isDivine() + "<br>Minion is charged" + "<br>Minion can attack</font></p></html>";

	}

	// ---------------------------------------------getting spell info
	public String SpellDetails(Card c) {
		Spell s = (Spell) c;
		String x = "<html><font color=\"#000\"" + "size=\"5\">Spell's name : " + s.getName()
				+ "<br>Spell's Mana Cost : " + s.getManaCost() + "<br>Spell's rarity : " + s.getRarity()
				+ "</font></p></html>";
		return x;
	}

	@Override
	public void onGameOver() {
		if (model.getCurrentHero().getCurrentHP() == 0 || model.getOpponent().getCurrentHP() == 0) {
//			System.out.println("current hero" + model.getCurrentHero().getCurrentHP());
//			System.out.println("opp" + model.getOpponent().getCurrentHP());

			view.setLayout(new BorderLayout());
			view.setTitle("Game Over");
			view.setBounds(0, 0, 1920, 1080);

			// JPanel gameEnd = new JPanel();
			// JLabel gameEnd = new JLabel();
			// gameEnd.createImage((ImageProducer) (new
			// ImageIcon("images/background.png").getImage()));

			Image Gameover = Toolkit.getDefaultToolkit().getImage("herosofwarcragt.jpg");
			Image GameOver1 = Gameover.getScaledInstance(1920, 1080, Image.SCALE_DEFAULT);
			view.setContentPane(new JPanel() {
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(GameOver1, 0, 0, this);
				}
			});
			// view.add(gameEnd, BorderLayout.SOUTH);
			JTextArea onGameOverDetails = new JTextArea();
			onGameOverDetails.setFont(new Font("Dialog", Font.PLAIN, 20));

			onGameOverDetails.setText(" GAME OVER " + '\n' + model.getCurrentHero().getName() + " " + "win the game"
					+ '\n' + model.getOpponent().getName() + " " + "hard luck next time :( ");
			onGameOverDetails.setEditable(false);
			onGameOverDetails.setForeground(Color.white);
			onGameOverDetails.setOpaque(false);
			onGameOverDetails.setSize(300, 100);
			view.add(onGameOverDetails, BorderLayout.CENTER);
			view.setVisible(true);
			view.revalidate();
			view.repaint();
		}

//		view.setLayout(new BorderLayout()); 
//		JTextArea gameover = new JTextArea();
//		gameover.setText(" GAME OVER " + '\n' + model.getCurrentHero().getName() + " " + "win the game" + '\n'
//				+ model.getOpponent().getName() + " " + "hard luck next time :( ");
//		view.getCenter().add(gameover);
//		view.getExceptions().setText(" GAME OVER " + '\n' + model.getCurrentHero().getName() + " " + "win the game" + '\n'
//				+ model.getOpponent().getName() + " " + "hard luck next time :( ");
//		
//		//---OppField
//		view.getOppFieldPanel().removeAll();
//		oppHeroField.clear();
//		for (int i = 0; i < model.getOpponent().getField().size(); i++) {
//			Card c = model.getOpponent().getField().get(i);
//			JButton b = new JButton();
//			if (c instanceof Minion) {
//				b.setText(OpponentMinionDetails(c));
//			} else if (c instanceof Spell) {
//				b.setText(SpellDetails(c));
//			}
//			oppHeroField.add(b);
//			view.getOppFieldPanel().add(b);
//			view.revalidate();
//			view.repaint();
//			}
//		//---CurrField
//		view.getCurrFieldPanel().removeAll();
//		currentHeroField.clear();
//		for (int i = 0; i < model.getCurrentHero().getField().size(); i++) {
//			Card c = model.getCurrentHero().getField().get(i);
//			JButton b = new JButton();
//			if (c instanceof Minion) {
//				b.setText(OpponentMinionDetails(c));
//				} else if (c instanceof Spell) {
//				b.setText(SpellDetails(c));
//			}
//			view.getCurrFieldPanel().add(b);
//			currentHeroField.add(b);
//			view.revalidate();
//			view.repaint();
//		}
//		//---CurrentHeroDetails
//		view.getCurrentHeroDetails().removeAll();
//		view.getCurrentHeroDetails()
//				.setText("<html>Current Hero Name : " + model.getCurrentHero().getName()
//						+ "<br>Current Health Points : " + model.getCurrentHero().getCurrentHP()
//						+ "<br>Total ManaCrystals " + model.getCurrentHero().getTotalManaCrystals()
//						+ "<br> Current ManaCrystals : " + model.getCurrentHero().getCurrentManaCrystals()
//						+ "<br> Number of Cards left  : " + model.getCurrentHero().getDeck().size() + "</html>");
//		view.revalidate();
//		view.repaint();
//		//---OpponentHeroDetails
//		String x = "<html>Opponent Hero Name : " + model.getOpponent().getName() + "<br> Current Health Points : "
//				+ model.getOpponent().getCurrentHP() + "<br>Total ManaCrystals :  "
//				+ model.getOpponent().getTotalManaCrystals() + "<br> Current ManaCrystals : "
//				+ model.getOpponent().getCurrentManaCrystals() + "<br> Number of Cards left in Deck : "
//				+ model.getOpponent().getDeck().size() + "<br>Number of cards in hand : "
//				+ model.getOpponent().getHand().size() + "</html>";
//		opponentHeroDetails.setText(x);
//		view.revalidate();
//		view.repaint();
//		//---CurrentHand
//		view.getCurrentHand().removeAll();
//		CurrentHeroHand.clear();
//		for (int i = 0; i < model.getCurrentHero().getHand().size(); i++) {
//			Card c = model.getCurrentHero().getHand().get(i);
//			JButton b = new JButton();
//			if (c instanceof Minion) {
//				b.setText(OpponentMinionDetails(c));
//			} else if (c instanceof Spell) {
//				b.setText(SpellDetails(c));
//			}
//		view.getCurrentHand().add(b);		
//		CurrentHeroHand.add(b);
//		view.revalidate();
//		view.repaint();
//	}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();

		String s = view.getTitle(b);
		if (s == "End Turn") {
			try {
				model.getCurrentHero().endTurn();

			} catch (FullHandException e1) {
				Card c = e1.getBurned();

				if (c instanceof Minion) {

					Minion m = (Minion) c;
					view.getExceptions().setText("Your hand is FUll! . Here is the burned card ,  " + "Minion name : "
							+ m.getName() + '\n' + "Mana Cost : " + m.getManaCost() + '\n' + "Rarity : " + m.getRarity()
							+ '\n' + "attack points : " + m.getAttack() + '\n' + "Current health points : "
							+ m.getCurrentHP() + '\n' + "Taunt: " + m.isTaunt() + '\n' + "Divine:" + m.isDivine()
							+ " , Minion is not charged ,  " + " " + " Minion is still sleeping");
				} else {
					Spell sp = (Spell) c;
					view.getExceptions()
							.setText("Your hand is FUll! . Here is the burned card " + '\n' + "Spell's name : "
									+ sp.getName() + '\n' + "Spell's Mana Cost : " + sp.getManaCost() + '\n'
									+ "Spell's rarity : " + sp.getRarity());
				}

			} catch (CloneNotSupportedException e1) {
				view.getExceptions().setText("Clone is not supported");
			}
		} else if (s == "Use Hero Power") {
			try {
				if (model.getCurrentHero() instanceof Hunter) {
					model.getCurrentHero().useHeroPower();

				} else if (model.getCurrentHero() instanceof Mage) {
					Mage Mage = (Mage) model.getCurrentHero();
					view.getExceptions().setText(
							"Choose a minion or the opponent hero to use your hero power on then click use hero power");

					if (heropower == true) {
						Mage.useHeroPower(model.getOpponent());
						heropower = false;
					} else if (magepowerminion == true) {
						Mage.useHeroPower(model.getOpponent().getField().get(magepower));
						magepowerminion = false;
					}

				}

				else if (model.getCurrentHero() instanceof Paladin) {
					model.getCurrentHero().useHeroPower();

				} else if (model.getCurrentHero() instanceof Priest) {
					Priest Priest = (Priest) model.getCurrentHero();
					view.getExceptions().setText(
							"Choose a minion in your Field or click on yourself to use your hero power on then click use hero power");
					if (hunterheropower == true) {
						Priest.useHeroPower(model.getCurrentHero());
						hunterheropower = false;

					} else if (attack == true) {
						Priest.useHeroPower(model.getCurrentHero().getField().get(alaa));
						attack = false;

					}

				} else if (model.getCurrentHero() instanceof Warlock) {
					//Warlock warlock = (Warlock) model.getCurrentHero();
					model.getCurrentHero().useHeroPower();
				}

			}

			catch (NotEnoughManaException e1) {

				view.getExceptions().setText("YOU DON't have enough Mana Crystals");

			} catch (HeroPowerAlreadyUsedException e1) {
				view.getExceptions().setText("You already used your Hero Power");

			} catch (NotYourTurnException e1) {
				view.getExceptions().setText("Its not your turn");

			} catch (FullHandException e1) {

				Card c = e1.getBurned();
				if (c instanceof Minion) {
					Minion m = (Minion) c;
					view.getExceptions()
							.setText("Your hand is FUll! . Here is the burned card ," + " " + "Minion name : "
									+ m.getName() + '\n' + "Mana Cost : " + m.getManaCost() + '\n' + "Rarity : "
									+ m.getRarity() + '\n' + "attack points : " + m.getAttack() + '\n'
									+ "Current health points : " + m.getCurrentHP() + '\n' + "Taunt: " + m.isTaunt()
									+ '\n' + "Divine:" + m.isDivine() + " , Minion is not charged , " + " "
									+ " Minion is still sleeping");
				} else {
					Spell sp = (Spell) c;
					view.getExceptions()
							.setText("Your hand is FUll! . Here is the burned card " + '\n' + "Spell's name : "
									+ sp.getName() + '\n' + "Spell's Mana Cost : " + sp.getManaCost() + '\n'
									+ "Spell's rarity : " + sp.getRarity());
				}

			} catch (FullFieldException e1) {
				view.getExceptions().setText("Yor field is full ");

			} catch (CloneNotSupportedException e1) {
				view.getExceptions().setText("You cant make a clone");
			}
			updateCurrentHeroDetails();
			updateOpponentHeroDetails();
			updateCurrHand();
			updateCurrentField();
			updateOpponentField();
		} 
		
		else if (s == "Draw card") {
			try {
				model.getCurrentHero().drawCard();
			} catch (FullHandException e1) {
				view.getExceptions().setText("Your hand is FUll!");

				view.getExceptions().setText("Your hand is FUll! . Here is the burned card ");
				Card c = e1.getBurned();
				if (c instanceof Minion) {
					Minion m = (Minion) c;
					view.getExceptions()
							.setText("Your hand is FUll! . Here is the burned card , " + " " + "Minion name : "
									+ m.getName() + '\n' + "Mana Cost : " + m.getManaCost() + '\n' + "Rarity : "
									+ m.getRarity() + '\n' + "attack points : " + m.getAttack() + '\n'
									+ "Current health points : " + m.getCurrentHP() + '\n' + "Taunt: " + m.isTaunt()
									+ '\n' + "Divine:" + m.isDivine() + " , Minion is not charged ,  " + " "
									+ " Minion is still sleeping");
				} else {
					Spell sp = (Spell) c;
					view.getExceptions()
							.setText("Your hand is FUll! . Here is the burned card " + '\n' + "Spell's name : "
									+ sp.getName() + '\n' + "Spell's Mana Cost : " + sp.getManaCost() + '\n'
									+ "Spell's rarity : " + sp.getRarity());

				}
			} catch (CloneNotSupportedException e1) {
				view.getExceptions().setText("Clone is not supported");
			}
		}

		else if (CurrentHeroHand.contains(b)) {
			int r = CurrentHeroHand.indexOf(b);
			Card c = model.getCurrentHero().getHand().get(r);
			if (c instanceof Minion) {
				try {
					model.getCurrentHero().playMinion((Minion) c);

				} catch (NotYourTurnException e1) {
					view.getExceptions().setText("It's not your turn");
				} catch (NotEnoughManaException e1) {
					view.getExceptions().setText("You don't have enough Mana");
				} catch (FullFieldException e1) {
					view.getExceptions().setText("your field is full");
				}
			} else if (c instanceof FieldSpell) {
				try {
					FieldSpell spell = (FieldSpell) c;
					model.getCurrentHero().castSpell(spell);
					//System.out.print("Field");

				}

				catch (NotYourTurnException e1) {
					view.getExceptions().setText("Not Your Turn");
				} catch (NotEnoughManaException e1) {
					view.getExceptions().setText("You don't have enough Mana");
				}
			} else if (c instanceof AOESpell) {
				try {
					AOESpell spell = (AOESpell) c;
					ArrayList<Minion> oppField = model.getOpponent().getField();
					model.getCurrentHero().castSpell(spell, oppField);
					//System.out.print("AOE");

				}

				catch (NotYourTurnException e1) {
					view.getExceptions().setText("Not Your Turn");
				} catch (NotEnoughManaException e1) {
					view.getExceptions().setText("You don't have enough Mana");
				}
			} else if (c instanceof MinionTargetSpell && oppFieldflag == true) {
				try {
					view.getExceptions()
							.setText("choose a minion from the opponent field then cast click on the spell");
					Minion m = model.getOpponent().getField().get(integerofspell);
					MinionTargetSpell attacker = (MinionTargetSpell) c;
					model.getCurrentHero().castSpell(attacker, m);
					//System.out.print("MinionTargetspell");
				} catch (NotYourTurnException e1) {
					view.getExceptions().setText("Not Your Turn");
				} catch (NotEnoughManaException e1) {
					view.getExceptions().setText("You don't have enough Mana");
				} catch (InvalidTargetException e1) {
					view.getExceptions().setText("Invalid Target");
				}
				oppFieldflag = false;
			} else if (c instanceof HeroTargetSpell && oppHeroFlag == true) {
				try {
					view.getExceptions().setText("choose the opponent hero then cast click on the spell");
					HeroTargetSpell attacker = (HeroTargetSpell) c;
					Hero h = model.getOpponent();
					model.getCurrentHero().castSpell(attacker, h);
					System.out.print("HeroTargetspell");

				} catch (NotYourTurnException e1) {
					view.getExceptions().setText("Not Your Turn");
				} catch (NotEnoughManaException e1) {
					view.getExceptions().setText("You don't have enough Mana");
				}
				oppHeroFlag = false;
			} else if (c instanceof LeechingSpell && oppFieldflag == true) {
				try {
					view.getExceptions()
							.setText("choose a minion from the opponent field then cast click on the spell");
					Minion m = model.getCurrentHero().getField().get(integerofspell);
					LeechingSpell attacker = (LeechingSpell) c;
					model.getOpponent().castSpell(attacker, m);
					//System.out.print("Leeching");

				} catch (NotYourTurnException e1) {
					view.getExceptions().setText("Not Your Turn");
				} catch (NotEnoughManaException e1) {
					view.getExceptions().setText("You don't have enough Mana");
				}
				oppFieldflag = false;
			}
		}

		if (currentHeroField.contains(b)) {
			int r = currentHeroField.indexOf(b);
			alaa = r;
			attack = true;
		}

		// System.out.print(flag);
		else if (oppHeroField.contains(b)) {
			magepowerminion = true;
			magepower = oppHeroField.indexOf(b);

			if (attack != true) {

				int r = oppHeroField.indexOf(b);
				oppFieldflag = true;
				integerofspell = r;
			} else if (attack == true) {
				try {
					tasneem = oppHeroField.indexOf(b);
//			    Card c= model.getOpponent().getField().get(tasneem);
//			    if (c instanceof Minion) {
					Minion attacker = model.getCurrentHero().getField().get(alaa);
					Minion target = model.getOpponent().getField().get(tasneem);
					model.getCurrentHero().attackWithMinion(attacker, target);
				}

				catch (CannotAttackException e1) {
					view.getExceptions().setText("Cannot Attack");
				} catch (NotYourTurnException e1) {
					view.getExceptions().setText("Not Your Turn");
				} catch (TauntBypassException e1) {
					view.getExceptions().setText("Taunt By pass ");
				} catch (InvalidTargetException e1) {
					view.getExceptions().setText("Invalid Target");
				} catch (NotSummonedException e1) {
					view.getExceptions().setText("Not Summoned");
				}
				attack = false;

			}
		}

		else if (opponentHeroDetails.equals(b)) {
			heropower = true;

			if (attack != true) {
				oppHeroFlag = true;
			} else if (attack == true) {
				try {

					Minion attacker = model.getCurrentHero().getField().get(alaa);
					Hero target = model.getOpponent();
					model.getCurrentHero().attackWithMinion(attacker, target);
				}

				catch (CannotAttackException e1) {
					view.getExceptions().setText("Cannot Attack");
				} catch (NotYourTurnException e1) {
					view.getExceptions().setText("Not Your Turn");
				} catch (TauntBypassException e1) {
					view.getExceptions().setText("Taunt By pass ");
				} catch (InvalidTargetException e1) {
					view.getExceptions().setText("Invalid Target");
				} catch (NotSummonedException e1) {
					view.getExceptions().setText("Not Summoned");
				}
				attack = false;
			}

		} else if (getCurrentHeroDetails().equals(b)) {
			hunterheropower = true;
		}

	}

	// ------------------------------------------------------
	public JButton getCurrentHeroDetails() {
		return view.getCurrentHeroDetails();
	}

	public JButton getOpponentHeroDetails() {
		return opponentHeroDetails;
	}

//------------------------------------------------update Opponent Field
	public void updateOpponentField() {
		view.getOppFieldPanel().removeAll();
		oppHeroField.clear();
		for (int i = 0; i < model.getOpponent().getField().size(); i++) {

			Card c = model.getOpponent().getField().get(i);
			JButton b = new JButton();

			if (c instanceof Minion) {
				b.setToolTipText(OpponentMinionDetails(c));
				setIcon(b, c);
				b.addActionListener(this);

			} else if (c instanceof Spell) {
				b.setToolTipText(SpellDetails(c));
				setIcon(b, c);
				b.addActionListener(this);

			}
			// this.oppHeroField.add(b);
			oppHeroField.add(b);
			view.getOppFieldPanel().add(b);
			view.revalidate();
			view.repaint();

		}

	}
	// ------------------------------------------------update Current Field

	public void updateCurrentField() {
		view.getCurrFieldPanel().removeAll();
		currentHeroField.clear();
		for (int i = 0; i < model.getCurrentHero().getField().size(); i++) {
			Card c = model.getCurrentHero().getField().get(i);
			JButton b = new JButton();
			if (c instanceof Minion) {
				b.setToolTipText(OpponentMinionDetails(c));
				setIcon(b, c);
				b.addActionListener(this);

			} else if (c instanceof Spell) {
				b.setToolTipText(SpellDetails(c));
				setIcon(b, c);
				b.addActionListener(this);

			}
			view.getCurrFieldPanel().add(b);
			currentHeroField.add(b);
			view.revalidate();
			view.repaint();
		}
		// view.getcurrentHeroPanel().add(getCurrentHeroDetails());

	}
	// ------------------------------------------------update CurrentHeroDetails

	public void updateCurrentHeroDetails() {
		view.getCurrentHeroDetails().removeAll();
		view.getCurrentHeroDetails()
				.setToolTipText("<html><font color=\"#000\" " + "size=\"5\">Current Hero Name : "
						+ model.getCurrentHero().getName() + "<br>Current Health Points : "
						+ model.getCurrentHero().getCurrentHP() + "<br>Total ManaCrystals "
						+ model.getCurrentHero().getTotalManaCrystals() + "<br> Current ManaCrystals : "
						+ model.getCurrentHero().getCurrentManaCrystals() + "<br> Number of Cards left  : "
						+ model.getCurrentHero().getDeck().size() + " </font></p></html>");
		setHeroIcon(view.getCurrentHeroDetails(), model.getCurrentHero());
		view.revalidate();
		view.repaint();

	}
	// ------------------------------------------------update OpponentHeroDetails

	public void updateOpponentHeroDetails() {
		String x = "<html><font color=\"#000\" " + "size=\"5\">Opponent Hero Name : " + model.getOpponent().getName()
				+ "<br> Current Health Points : " + model.getOpponent().getCurrentHP() + "<br>Total ManaCrystals :  "
				+ model.getOpponent().getTotalManaCrystals() + "<br> Current ManaCrystals : "
				+ model.getOpponent().getCurrentManaCrystals() + "<br> Number of Cards left in Deck : "
				+ model.getOpponent().getDeck().size() + "<br>Number of cards in hand : "
				+ model.getOpponent().getHand().size() + "</font></p></html>";
		opponentHeroDetails.setToolTipText(x);
		setHeroIcon(opponentHeroDetails, model.getOpponent());

		view.revalidate();
		view.repaint();

	}
	// ------------------------------------------------update Current Hand

	public void updateCurrHand() {
		view.getCurrentHand().removeAll();
		CurrentHeroHand.clear();
		for (int i = 0; i < model.getCurrentHero().getHand().size(); i++) {
			Card c = model.getCurrentHero().getHand().get(i);
			JButton b = new JButton();
			if (c instanceof Minion) {
				b.setToolTipText(OpponentMinionDetails(c));
				setIcon(b, c);
				b.addActionListener(this);

			} else if (c instanceof Spell) {
				b.setToolTipText(SpellDetails(c));
				setIcon(b, c);
				b.addActionListener(this);

			}

			view.getCurrentHand().add(b);
			CurrentHeroHand.add(b);
			view.revalidate();
			view.repaint();
		}
	}

	@Override
	public void endTurn(Game game) {
		updateCurrentHeroDetails();
		updateOpponentHeroDetails();
		updateCurrHand();
		updateOpponentField();
		updateCurrentField();
		view.getExceptions().setText("  ");
		view.revalidate();
		view.repaint();

	}

	@Override
	public void PlayMinion(Game game) {
		updateCurrHand();
		updateCurrentField();
		updateCurrentHeroDetails();
		updateOpponentHeroDetails();
		updateOpponentField();
		view.getExceptions().setText("  ");
		view.revalidate();
		view.repaint();
	}

	@Override
	public void onUseHeroPower(Game game) {
		updateCurrentHeroDetails();
		updateOpponentHeroDetails();
		updateCurrHand();
		updateCurrentField();
		updateOpponentField();
		view.getExceptions().setText("  ");
		view.revalidate();
		view.repaint();
	}

	@Override
	public void onDrawCard(Game game) {
		updateCurrentHeroDetails();
		updateOpponentHeroDetails();
		updateCurrHand();
		updateCurrentField();
		updateOpponentField();
		view.getExceptions().setText("  ");
		view.revalidate();
		view.repaint();
	}

	@Override
	public void onCastSpell(FieldSpell s) {
		updateCurrentHeroDetails();
		updateOpponentHeroDetails();
		updateCurrHand();
		updateCurrentField();
		updateOpponentField();
		view.getExceptions().setText("  ");
		view.revalidate();
		view.repaint();

	}

	@Override
	public void onCastSpell(AOESpell s, ArrayList<Minion> oppField) {
		updateCurrentHeroDetails();
		updateOpponentHeroDetails();
		updateCurrHand();
		updateCurrentField();
		updateOpponentField();
		view.getExceptions().setText("  ");
		view.revalidate();
		view.repaint();
	}

	@Override
	public void onAttackWithMinion(Minion attacker, Minion target) {
		updateCurrentHeroDetails();
		updateOpponentHeroDetails();
		updateCurrHand();
		updateCurrentField();
		updateOpponentField();
		view.getExceptions().setText("  ");
		view.revalidate();
		view.repaint();

	}

	@Override
	public void onAttackWithMinion(Minion attacker, Hero target) {
		updateCurrentHeroDetails();
		updateOpponentHeroDetails();
		updateCurrHand();
		updateCurrentField();
		updateOpponentField();
		view.getExceptions().setText("  ");
		view.revalidate();
		view.repaint();

	}

	@Override
	public void onCastSpell(MinionTargetSpell s, Minion m) {
		updateCurrentHeroDetails();
		updateOpponentHeroDetails();
		updateCurrHand();
		updateCurrentField();
		updateOpponentField();
		view.getExceptions().setText("  ");
		view.revalidate();
		view.repaint();

	}

	@Override
	public void onCastSpell(HeroTargetSpell s, Hero h) {
		updateCurrentHeroDetails();
		updateOpponentHeroDetails();
		updateCurrHand();
		updateCurrentField();
		updateOpponentField();
		view.getExceptions().setText("  ");
		view.revalidate();
		view.repaint();

	}

	@Override
	public void onCastSpell(LeechingSpell s, Minion m) {
		updateCurrentHeroDetails();
		updateOpponentHeroDetails();
		updateCurrHand();
		updateCurrentField();
		updateOpponentField();
		view.getExceptions().setText("  ");
		view.revalidate();
		view.repaint();
	}

	public void setIcon(JButton b, Card c) {
		double height = (double) view.getOpponentHeroPanel().getHeight();
		double width = (double) view.getOpponentHeroPanel().getWidth();
		ImageIcon icon = new ImageIcon();
		if (c.getName().equalsIgnoreCase("Icehowl")) {
			icon = new ImageIcon("minionImages/icehowl.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Goldshire Footman")) {
			icon = new ImageIcon("minionImages/goldshire.jpg");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("Stonetusk Boar")) {
			icon = new ImageIcon("minionImages/stonetuskboar.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("Bloodfen Raptor"))

		{
			icon = new ImageIcon("minionImages/bloodfenraptor.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("Frostwolf Grunt")) {
			icon = new ImageIcon("minionImages/frostwolf-grunt.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("Wolfrider")) {
			icon = new ImageIcon("minionImages/wolfrider.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("Chilwind Yeti")) {
			icon = new ImageIcon("minionImages/Chillwind_Yeti.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("Boulderfist Ogre")) {
			icon = new ImageIcon("minionImages/boulderfist-ogre.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Chromaggus")) {
			icon = new ImageIcon("minionImages/chromaggus.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("Core Hound")) {
			icon = new ImageIcon("minionImages/core-hound.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("Argent Commander")) {
			icon = new ImageIcon("minionImages/argent-commander.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("Sunwalker")) {
			icon = new ImageIcon("minionImages/sunwalker.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("The LichKing")) {
			icon = new ImageIcon("minionImages/the-lich-king.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("Colossus of the Moon")) {
			icon = new ImageIcon("minionImages/colossus-of-the-moon.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Curse of Weakness")) {
			icon = new ImageIcon("spells/curse-of-weakness.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		}
		// ===========================From here
		else if (c.getName().equalsIgnoreCase("Divine Spirit")) {
			icon = new ImageIcon("spells/divine-spirit.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (c.getName().equalsIgnoreCase("Flamestrike")) {
			icon = new ImageIcon("spells/flamestrike.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Holy Nova")) {
			icon = new ImageIcon("spells/holy-nova.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Kill Command")) {
			icon = new ImageIcon("spells/kill-command.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Level Up!")) {
			icon = new ImageIcon("spells/level-up.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Multi-Shot")) {
			icon = new ImageIcon("spells/multi-shot.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Polymorph")) {
			icon = new ImageIcon("spells/polymorph.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Pyroblast")) {
			icon = new ImageIcon("spells/pyroblast.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Seal of Champions")) {
			icon = new ImageIcon("spells/seal-of-champions.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Shadow Word: Death")) {
			icon = new ImageIcon("spells/shadow-word-death.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Siphon Soul")) {
			icon = new ImageIcon("spells/siphon-soul.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Twisting Nether")) {
			icon = new ImageIcon("spells/twisting-nether.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		}
		/////
		else if (c.getName().equalsIgnoreCase("King Krush")) {
			icon = new ImageIcon("specialMinions/king-krush.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		}

		else if (c.getName().equalsIgnoreCase("Kalycgos")) {
			icon = new ImageIcon("specialMinions/kalecgos.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Tirion Fordring")) {
			icon = new ImageIcon("specialMinions/tirion-fordring.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Prophet Velen")) {
			icon = new ImageIcon("specialMinions/prophet-velen.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (c.getName().equalsIgnoreCase("Wilfred Fizzlebang")) {
			icon = new ImageIcon("specialMinions/wilfred-fizzlebang.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		}
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);

	}

	public void setHeroIcon(JButton b, Hero h) {
		double height = (double) view.getOpponentHeroPanel().getHeight();
		double width = (double) view.getOpponentHeroPanel().getWidth();

		ImageIcon icon = new ImageIcon();
		b.setOpaque(false);

		if (h instanceof Hunter) {
			icon = new ImageIcon("heros/hunter.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (h instanceof Mage) {
			icon = new ImageIcon("heros/mage.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		} else if (h instanceof Paladin) {
			icon = new ImageIcon("heros/Paladin.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (h instanceof Priest) {
			icon = new ImageIcon("heros/Priest.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));

		} else if (h instanceof Warlock) {
			icon = new ImageIcon("heros/warlock.png");
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance((int) ((150 / width) * width), (int) ((200 / height) * height),
					java.awt.Image.SCALE_SMOOTH);
			b.setIcon(new ImageIcon(newimg));
		}
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
	}

//	public static void main(String[] args) throws FullHandException, CloneNotSupportedException, IOException {
//		Mage h1 = new Mage();
//		Hunter h2 = new Hunter();
////		h1.setCurrentHP(1);
////		h2.setCurrentHP(1);
////		
//
//		new Controller(h1, h2);
//
//	}

}
