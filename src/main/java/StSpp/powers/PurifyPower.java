package StSpp.powers;

import StSpp.CustomTags;
import StSpp.cards.MuscleMemory;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.purple.Collect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.DivinityStance;

import java.util.*;

public class PurifyPower extends AbstractPower
{
    public static final String POWER_ID = DefaultMod.makeID("PurifyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power32.png");

    public PurifyPower(int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance)
    {
        if ( newStance instanceof DivinityStance)
        {
            Hashtable<AbstractCard, CardGroup> allCursesAndStatus = new Hashtable<>();

            for ( AbstractCard c : AbstractDungeon.player.drawPile.group)
            {
                if ( c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS)
                {
                    allCursesAndStatus.put(c, AbstractDungeon.player.drawPile);
                }
            }

            for ( AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if ( c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS)
                {
                    allCursesAndStatus.put(c, AbstractDungeon.player.hand);
                }
            }

            for ( AbstractCard c : AbstractDungeon.player.discardPile.group)
            {
                if ( c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS)
                {
                    allCursesAndStatus.put(c, AbstractDungeon.player.discardPile);
                }
            }

            int size = Math.min(allCursesAndStatus.size(), amount);

            Enumeration<AbstractCard> cards = allCursesAndStatus.keys();

            while ( cards.hasMoreElements() && size > 0)
            {
                AbstractCard c = cards.nextElement();
                new ExhaustSpecificCardAction(c, allCursesAndStatus.get(c) );
                size--;
            }
        }
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
