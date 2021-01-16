package StSpp.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BladeOfBloodAction extends AbstractGameAction {

    AbstractPlayer p;
    AbstractMonster m;
    AbstractCard card;

    public BladeOfBloodAction(AbstractMonster m, AbstractPlayer p,AbstractCard card )
    {
        this.p = p;
        this.m = m;
        this.card = card;
    }

    @Override
    public void update()
    {
        card.baseDamage = p.maxHealth - p.currentHealth;
        card.applyPowers();
        DamageInfo info = new DamageInfo(p, card.damage, DamageInfo.DamageType.NORMAL );
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, info, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.isDone = true;
    }
}
