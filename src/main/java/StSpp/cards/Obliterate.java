package StSpp.cards;

import StSpp.DefaultMod;
import StSpp.actions.ObliterateAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static StSpp.DefaultMod.makeCardPath;

public class Obliterate extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Obliterate.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Obliterate.png");

    public Obliterate()
    {
        super(ID,cardStrings.NAME,IMG,3,cardStrings.DESCRIPTION,CardType.SKILL,CardColor.RED,CardRarity.RARE,CardTarget.SELF);
        this.cost = 3;
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void upgrade()
    {
        if ( !this.upgraded )
        {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new ObliterateAction(abstractPlayer, this.baseMagicNumber));
    }
}
