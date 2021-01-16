package StSpp.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashSet;
import java.util.Hashtable;

import static StSpp.DefaultMod.makeCardPath;

public class Panic extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Panic.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");
    public static int COST = 3;

    public Panic()
    {
        super(ID, cardStrings.NAME, IMG, 3, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, AbstractCard.CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 2;
    }

    @Override
    public void upgrade()
    {
        if ( this.canUpgrade())
        {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy() {
        return new Panic();
    }

    public void applyPowers()
    {
        HashSet<String> debuffs = new HashSet<>();
        int modifier = 0;
        for (AbstractPower power : AbstractDungeon.player.powers)
        {
            if ( power.type == AbstractPower.PowerType.DEBUFF && !debuffs.contains(power.ID))
            {
                modifier++;
                debuffs.add(power.ID);
            }
        }
        this.cost = Math.max( COST-modifier, 0);
        this.isCostModified = modifier != 0;
        this.costForTurn = Math.max( COST-modifier, 0 );
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new DrawCardAction(abstractPlayer, this.magicNumber));
        addToBot(new GainEnergyAction(1));
    }

}
