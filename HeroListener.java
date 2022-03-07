package model.heroes;

import java.util.ArrayList;

import exceptions.FullHandException;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;

public interface HeroListener {
	public void onHeroDeath();

	public void damageOpponent(int amount);

	public void endTurn() throws FullHandException, CloneNotSupportedException;

	public void onUseHeroPower();

	public void PlayMinion(Minion m);

	public void onDrawCard();

	public void onCastSpell(FieldSpell s);

	public void onCastSpell(MinionTargetSpell s, Minion m);

	public void onAttackWithMinion(Minion attacker, Minion target);

	public void onAttackWithMinion(Minion attacker, Hero target);

	public void onCastSpell(AOESpell s, ArrayList<Minion> oppField);

	public void onCastSpell(HeroTargetSpell s, Hero h);

	public void onCastSpell(LeechingSpell s, Minion m);


}
