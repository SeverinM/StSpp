package StSpp.cards;

import StSpp.actions.BladeOfBloodAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.sun.jndi.ldap.Ber;

import java.util.concurrent.BlockingDeque;

import static StSpp.DefaultMod.makeCardPath;

public class Sprint extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Sprint.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Sprint.png");

    public Sprint()
    {
        super(ID, cardStrings.NAME, IMG, 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.COMMON, CardTarget.SELF);
        this.magicNumber = 2;
        this.baseMagicNumber = this.magicNumber;
    }

    @Override
    public void upgrade()
    {
        if ( this.canUpgrade())
        {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new GainEnergyAction(2));

        if ( this.upgraded )
        {
            addToBot(new DrawCardAction(1));
        }

        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer,new EntanglePower(abstractPlayer)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sprint();
    }
}
