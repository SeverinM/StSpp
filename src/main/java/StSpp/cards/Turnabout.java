package StSpp.cards;

import StSpp.powers.BetrayalPower;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static StSpp.DefaultMod.makeCardPath;

public class Turnabout extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Turnabout.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public Turnabout()
    {
        super(ID,cardStrings.NAME,IMG,1,cardStrings.DESCRIPTION,CardType.SKILL,CardColor.RED,CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 15;
    }

    @Override
    public void upgrade()
    {
        if ( this.canUpgrade())
        {
            this.upgradeName();
            this.upgradeMagicNumber(5);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        if ( abstractPlayer.currentHealth < this.magicNumber)
        {
            addToBot(new HealAction(abstractPlayer,abstractMonster,this.magicNumber - abstractPlayer.currentHealth));
        }
        else
        {
            if ( abstractPlayer.hasPower(BetrayalPower.POWER_ID))
            {
                AbstractPower power = abstractPlayer.getPower(BetrayalPower.POWER_ID);
                power.flash();

                if ( power.amount > 1 )
                {
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(abstractPlayer,abstractPlayer, BetrayalPower.POWER_ID,1));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(abstractPlayer,abstractPlayer, BetrayalPower.POWER_ID));
                }

                AbstractDungeon.actionManager.addToBottom(new HealAction(abstractPlayer,abstractPlayer,this.magicNumber - abstractPlayer.currentHealth));
            }
            else
            {
                addToBot(new LoseHPAction(abstractPlayer,abstractPlayer, abstractPlayer.currentHealth - this.magicNumber));
            }
        }
    }
}
