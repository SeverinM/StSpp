package StSpp.actions;

import basemod.BaseMod;
import basemod.interfaces.PostDrawSubscriber;
import basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer.PostDrawHook;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EvolvePower;
import com.megacrit.cardcrawl.powers.FireBreathingPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;

public class MultiDrawPileToHandAction extends AbstractGameAction
{
    private AbstractPlayer p;
    private ArrayList<AbstractCard.CardType> typesToCheck = new ArrayList<>();

    public MultiDrawPileToHandAction(int amount, ArrayList<AbstractCard.CardType> types) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, this.p, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.typesToCheck = types;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            Iterator var2 = this.p.drawPile.group.iterator();

            AbstractCard card;
            while(var2.hasNext()) {
                card = (AbstractCard)var2.next();
                if (this.typesToCheck.contains(card.type) ) {
                    tmp.addToRandomSpot(card);
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }

            for(int i = 0; i < this.amount; ++i) {
                if (!tmp.isEmpty()) {
                    tmp.shuffle();
                    card = tmp.getBottomCard();
                    tmp.removeCard(card);
                    if (this.p.hand.size() == 10) {
                        this.p.drawPile.moveToDiscardPile(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.current_x = CardGroup.DRAW_PILE_X;
                        card.current_y = CardGroup.DRAW_PILE_Y;
                        this.p.drawPile.removeCard(card);
                        AbstractDungeon.player.hand.addToTop(card);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                        AbstractDungeon.player.applyStartOfTurnOrbs();

                        Iterator var1 = AbstractDungeon.player.powers.iterator();

                        while(var1.hasNext()) {
                            AbstractPower p = (AbstractPower)var1.next();
                            p.onCardDraw(card);
                        }

                        var1 = AbstractDungeon.player.relics.iterator();

                        while(var1.hasNext()) {
                            AbstractRelic r = (AbstractRelic)var1.next();
                            r.onDrawOrDiscard();
                        }

                        card.triggerWhenDrawn();
                    }
                }
            }

            this.isDone = true;
        }

        this.tickDuration();
    }
}
