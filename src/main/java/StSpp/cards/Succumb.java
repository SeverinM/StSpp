package StSpp.cards;

import StSpp.actions.BladeOfBloodAction;
import StSpp.actions.MultiDrawPileToHandAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.sun.jndi.ldap.Ber;

import java.util.ArrayList;
import java.util.Iterator;

import static StSpp.DefaultMod.makeCardPath;

public class Succumb extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Succumb.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Succumb.png");

    public Succumb()
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
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        ArrayList<CardType> typeToFetch = new ArrayList<>();
        typeToFetch.add(CardType.CURSE);
        typeToFetch.add(CardType.STATUS);

        Iterator var2 = abstractPlayer.drawPile.group.iterator();

        int cardOrStatusCount = this.magicNumber;
        AbstractCard card;
        while(var2.hasNext() && this.magicNumber > 0) {
            card = (AbstractCard)var2.next();
            if (card.type == CardType.CURSE || card.type == CardType.STATUS ) {
                cardOrStatusCount--;
            }
        }

        this.addToBot(new MultiDrawPileToHandAction(this.magicNumber, typeToFetch));

        if ( cardOrStatusCount > 0)
        {
            addToBot(new DrawCardAction(cardOrStatusCount));
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new Succumb();
    }
}
