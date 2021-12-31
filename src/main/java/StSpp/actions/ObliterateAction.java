package StSpp.actions;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.Iterator;

public class ObliterateAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    AbstractPlayer p;
    AbstractCard c;
    int amm;
    public ObliterateAction(AbstractPlayer player, int amount)
    {
        this.p = player;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amm = amount;
    }

    @Override
    public void update()
    {
        if ( this.p.hand.group.size() == 0 )
        {
            this.isDone = true;
            return;
        }

        if (this.duration == this.startDuration)
        {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amm,true, true);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(var4.hasNext())
            {
                c = (AbstractCard)var4.next();
                this.p.hand.moveToExhaustPile(c);

                AbstractCard toRemove = null;
                Iterator itMaster = this.p.masterDeck.group.iterator();
                while( itMaster.hasNext() )
                {
                    toRemove = (AbstractCard)itMaster.next();
                    if ( IsTheSameCard(toRemove, c) )
                    {
                        AbstractDungeon.player.masterDeck.removeCard(toRemove);
                        break;
                    }
                }
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }

    boolean IsTheSameCard(AbstractCard c , AbstractCard c2)
    {
        if ( c.cardID != c2.cardID )
            return false;

        if ( c.cardID == SearingBlow.ID )
            return c.timesUpgraded == c2.timesUpgraded;
        else
            return c.upgraded == c2.upgraded;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}
