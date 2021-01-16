package StSpp.cards;

import StSpp.actions.BladeOfBloodAction;
import StSpp.powers.BetrayalPower;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;

import static StSpp.DefaultMod.makeCardPath;

public class BladeOfBlood extends CustomCard
{
    public static final String ID = DefaultMod.makeID(BladeOfBlood.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");

    public BladeOfBlood()
    {
        super(ID, cardStrings.NAME, IMG, 3, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {
        if ( this.canUpgrade())
        {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        int HPLoss = 0;
        if ( abstractPlayer.currentHealth > 1 )
        {
            HPLoss = abstractPlayer.currentHealth / 2;
        }

        if ( abstractMonster != null)
        {
            this.addToBot(new VFXAction(new HemokinesisEffect(abstractPlayer.hb.cX, abstractPlayer.hb.cY, abstractMonster.hb.cX, abstractMonster.hb.cY), 0.5F));
        }

        if ( abstractPlayer.hasPower(BetrayalPower.POWER_ID))
        {
            BetrayalPower power = (BetrayalPower)abstractPlayer.getPower(BetrayalPower.POWER_ID);
            if ( power.amount > 1 )
            {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(power.owner, power.owner, BetrayalPower.POWER_ID,1));
            }
            else
            {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(power.owner,power.owner, BetrayalPower.POWER_ID));
            }

            AbstractDungeon.actionManager.addToBottom(new HealAction(power.owner,power.owner, HPLoss));
        }
        else
        {
            this.addToBot(new LoseHPAction(abstractPlayer, abstractPlayer, HPLoss));
        }

        this.addToBot(new BladeOfBloodAction(abstractMonster, abstractPlayer, this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BladeOfBlood();
    }
}
