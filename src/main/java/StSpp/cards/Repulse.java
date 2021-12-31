package StSpp.cards;

import StSpp.DefaultMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.ThroughViolence;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static StSpp.DefaultMod.makeCardPath;

public class Repulse extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Repulse.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Repulse.png");

    public Repulse()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.cardsToPreview = new Remembrance();
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        AbstractCard card = new Remembrance(abstractPlayer.stance.ID);
        this.addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
        this.addToBot(new ChangeStanceAction("Neutral"));
    }
}
