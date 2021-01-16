package StSpp.cards;

import StSpp.actions.BladeOfBloodAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;

import java.util.Iterator;

import static StSpp.DefaultMod.makeCardPath;

public class IntoTheAbyss extends CustomCard
{
    public static final String ID = DefaultMod.makeID(IntoTheAbyss.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("IntoTheAbyss.png");

    public IntoTheAbyss()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        Iterator it = abstractPlayer.drawPile.group.iterator();
        AbstractCard c;
        while (it.hasNext())
        {
            c = (AbstractCard)it.next();
            this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
        }
    }
}
