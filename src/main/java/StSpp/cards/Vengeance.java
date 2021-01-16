package StSpp.cards;

import basemod.abstracts.CustomCard;
import basemod.devcommands.draw.Draw;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Bludgeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import static StSpp.DefaultMod.makeCardPath;

public class Vengeance extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Vengeance.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");
    public static final int MIN_CURSES= 4;

    public Vengeance()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
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
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        if (!super.canUse(p, m))
        {
            return false;
        }

        int count = 0;
        for(AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if ( c.type != CardType.CURSE && c.type != CardType.STATUS && c != this)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        AbstractCard bludgeon = new Bludgeon();
        bludgeon.cost = 0;
        bludgeon.costForTurn = 0;
        bludgeon.isCostModified = true;
        addToBot(new MakeTempCardInHandAction( bludgeon ));
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new Vengeance();
    }
}
