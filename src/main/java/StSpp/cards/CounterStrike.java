package StSpp.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static StSpp.DefaultMod.makeCardPath;

public class CounterStrike extends CustomCard
{
    public static final String ID = DefaultMod.makeID(CounterStrike.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public CounterStrike()
    {
        super(ID,cardStrings.NAME,IMG,0,cardStrings.DESCRIPTION,CardType.SKILL,CardColor.RED,CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        this.baseBlock = 14;
    }

    @Override
    public void upgrade()
    {
        if ( this.canUpgrade())
        {
            this.upgradeName();
            this.upgradeBlock(4);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new StrengthPower(abstractPlayer, -1), -1));
        addToBot(new GainBlockAction(abstractPlayer,this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CounterStrike();
    }
}
