package StSpp.cards;

import StSpp.CustomTags;
import StSpp.powers.TimeLoopPower;
import StSpp.relics.ChessPiece;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static StSpp.DefaultMod.makeCardPath;

public class TimeParadox extends CustomCard
{
    public static final String ID = DefaultMod.makeID(TimeParadox.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("TimeParadox.png");

    public TimeParadox()
    {
        super(ID, cardStrings.NAME, IMG, 0, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.GREEN, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        tags.add(CustomTags.NEXT_TURN);

        this.baseMagicNumber = this.magicNumber = 2;
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
        this.addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new TimeLoopPower(this.magicNumber)));

        if ( AbstractDungeon.player.hasRelic(ChessPiece.ID) && !AbstractDungeon.player.getRelic(ChessPiece.ID).grayscale)
        {
            ArrayList<AbstractCard> cards = AbstractDungeon.commonCardPool.group;
            cards.addAll(AbstractDungeon.uncommonCardPool.group);
            cards.addAll(AbstractDungeon.rareCardPool.group);

            ArrayList<AbstractCard> filtered = new ArrayList<>();
            Iterator it = cards.iterator();
            AbstractCard card;
            while (it.hasNext())
            {
                card = (AbstractCard) it.next();
                if ( card.hasTag( CustomTags.NEXT_TURN ))
                {
                    filtered.add(card);
                }
            }

            for (int i = 0; i < this.magicNumber; i++)
            {
                AbstractCard selectedCard = filtered.get(new Random().nextInt(filtered.size()) ).makeCopy();
                if ( AbstractDungeon.player.hand.size() + 1 <= AbstractDungeon.player.masterHandSize )
                {
                    addToBot(new MakeTempCardInHandAction(selectedCard, 1,false));
                }
                else
                {
                    addToBot(new MakeTempCardInDrawPileAction(selectedCard, 1,true, true));
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TimeParadox();
    }
}
