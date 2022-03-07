package engine;

import java.util.ArrayList;

import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.heroes.Hero;

public interface GameListener {
	
	public void onGameOver();

	public void endTurn(Game game);


	public void PlayMinion(Game game);

	public void onUseHeroPower(Game game);

	public void onDrawCard(Game game);

	public void onCastSpell(FieldSpell s);

	public void onCastSpell(AOESpell s, ArrayList<Minion> oppField);

	public void onAttackWithMinion(Minion attacker, Minion target);

	public void onAttackWithMinion(Minion attacker, Hero target);

	public void onCastSpell(MinionTargetSpell s, Minion m);

	public void onCastSpell(HeroTargetSpell s, Hero h);

	public void onCastSpell(LeechingSpell s, Minion m);

	

}
