package StSpp.cards;

import StSpp.actions.ThrowFollowUpAction;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.ScrapeEffect;

import java.util.HashSet;

import static StSpp.DefaultMod.makeCardPath;

public class MindBarrier extends CustomCard implements OnStartBattleSubscriber
{
    public static final String ID = DefaultMod.makeID(MindBarrier.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("MindBarrier.png");
    public static HashSet<String> differentCards = new HashSet<>();

    public MindBarrier()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        BaseMod.subscribe(this);
        this.baseMagicNumber = this.magicNumber = 0;
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.upgradeMagicNumber(3);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new GainBlockAction(abstractPlayer, this.magicNumber + ( this.upgraded ? 3 : 0 ) ) );
    }

    @Override
    public AbstractCard makeCopy() {
        return new MindBarrier();
    }

    public void triggerOnCardPlayed(AbstractCard cardPlayed)
    {
        if ( !differentCards.contains(cardPlayed.cardID))
        {
            differentCards.add(cardPlayed.cardID);
            this.baseMagicNumber++;
            this.magicNumber++;
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        differentCards.clear();
        this.baseMagicNumber = 0;
        this.magicNumber = 0;
    }
}
