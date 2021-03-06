package StSpp.cards;

import StSpp.powers.SneakPower;
import basemod.abstracts.CustomCard;
import basemod.devcommands.draw.Draw;
import com.badlogic.gdx.math.MathUtils;
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
import static StSpp.DefaultMod.makeCardPath;

public class Lure extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Lure.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Lure.png");
    public static final int TURN_LIMIT = 5;

    public Lure()
    {
        super(ID, cardStrings.NAME, IMG, 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 8;
        this.baseMagicNumber = this.magicNumber = 0;
    }

    public void atTurnStart()
    {
        this.baseMagicNumber++;
        this.magicNumber++;
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            upgradeName();
            upgradeBlock(3);
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        if ( !super.canUse(p,m))
        {
            return false;
        }

        return this.baseMagicNumber <= TURN_LIMIT;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new DrawCardAction(1));
    }
}
