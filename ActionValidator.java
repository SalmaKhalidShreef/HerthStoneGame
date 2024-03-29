package engine;

import java.util.ArrayList;

import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.MinionTargetSpell;
import model.heroes.Hero;

public interface ActionValidator {
	public void validateTurn(Hero user) throws NotYourTurnException;

	public void validateAttack(Minion a, Minion t)
			throws TauntBypassException, InvalidTargetException, NotSummonedException, CannotAttackException;

	public void validateAttack(Minion m, Hero t)
			throws TauntBypassException, NotSummonedException, InvalidTargetException, CannotAttackException;

	public void validateManaCost(Card card) throws NotEnoughManaException;

	public void validatePlayingMinion(Minion m) throws FullFieldException;

	public void validateUsingHeroPower(Hero h) throws NotEnoughManaException, HeroPowerAlreadyUsedException;

	void onCastSpell(MinionTargetSpell s, Minion m);

	void onCastSpell(FieldSpell s);

	void onCastSpell(AOESpell s, ArrayList<Minion> oppField);
}
