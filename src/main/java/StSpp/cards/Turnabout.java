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

    public static final String IMG = makeCardPath("BrushWithDeath.png");

    public Turnabout()
    {
        super(ID,cardStrings.NAME,IMG,1,cardStrings.DESCRIPTION,CardType.SKILL,CardColor.RED,CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
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
        if ( abstractPlayer.currentHealth < this.magicNumber)
        {
            addToBot(new HealAction(abstractPlayer,abstractPlayer,999));
        }
    }
}
