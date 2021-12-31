package StSpp.cards;

import StSpp.powers.IgniterPower;
import StSpp.powers.MuscleMemoryPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;

import static StSpp.DefaultMod.makeCardPath;
public class Igniter extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Igniter.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Channeliser.png");

    public Igniter()
    {
        super(ID, cardStrings.NAME, IMG, 2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, AbstractCard.CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void upgrade() {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new IgniterPower(1)));
    }
}
