package StSpp.cards;

import StSpp.DefaultMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.ReachHeaven;
import com.megacrit.cardcrawl.cards.tempCards.ThroughViolence;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static StSpp.DefaultMod.makeCardPath;

public class Remembrance extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Remembrance.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Resurgence.png");
    private String stanceKept = "Neutral";

    String GetText()
    {
        String output = "";
        switch (stanceKept)
        {
            case "Neutral":
                output = cardStrings.EXTENDED_DESCRIPTION[1];
                break;

            case "Wrath":
                output = cardStrings.EXTENDED_DESCRIPTION[2];
                break;

            case "Calm":
                output = cardStrings.EXTENDED_DESCRIPTION[3];
                break;

            case "Divinity":
                output = cardStrings.EXTENDED_DESCRIPTION[4];
                break;

            default:
                output = cardStrings.EXTENDED_DESCRIPTION[5];
                break;
        }

        if ( this.upgraded )
        {
            output += " NL " + cardStrings.EXTENDED_DESCRIPTION[0];
        }

        return output;
    }

    public Remembrance()
    {
        super(ID, cardStrings.NAME, IMG, 0, cardStrings.EXTENDED_DESCRIPTION[5], CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.exhaust = true;
    }

    public Remembrance(String stance)
    {
        super(ID, cardStrings.NAME, IMG, 0, cardStrings.EXTENDED_DESCRIPTION[5], CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        stanceKept = stance;
        this.exhaust = true;
        this.rawDescription = GetText();
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.rawDescription = GetText();
            this.retain = true;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        this.addToBot(new ChangeStanceAction(stanceKept));
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new Remembrance(stanceKept);
    }
}
