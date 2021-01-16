package StSpp.powers;

import StSpp.CustomTags;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class TimeLoopPower extends AbstractPower
{
    public static final String POWER_ID = DefaultMod.makeID("InLoop");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power32.png");

    public TimeLoopPower()
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = 1;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public TimeLoopPower(int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        if ( this.amount >= 0)
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

            for (int i = 0; i < this.amount; i++)
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

        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}
