package StSpp.powers;

import StSpp.CustomTags;
import StSpp.cards.MuscleMemory;
import basemod.BaseMod;
import basemod.interfaces.OnCreateDescriptionSubscriber;
import basemod.interfaces.PostPowerApplySubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;

import java.util.*;

public class MemoryLeakPower extends AbstractPower implements PostPowerApplySubscriber
{
    public static final String POWER_ID = DefaultMod.makeID("MemoryLeakPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    static final int COST_REDUCTION = 2;
    private static final Texture tex84 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power32.png");

    HashSet<AbstractCard> alreadyAppliedCards = new HashSet<>();
    Hashtable<AbstractPower, Integer> allPowers = new Hashtable<>();

    public MemoryLeakPower()
    {
        BaseMod.subscribe(this);
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

    public void TryApplyDiscount(AbstractCard c)
    {
        if ( alreadyAppliedCards.contains(c) || c.type != AbstractCard.CardType.POWER)
        {
            return;
        }

        alreadyAppliedCards.add(c);

        int before = c.cost;
        c.costForTurn = c.cost = Math.max(c.cost - ( COST_REDUCTION * amount ), 0);
        c.isCostModified = (c.cost != before);
    }

    @Override
    public void onVictory()
    {
        alreadyAppliedCards.clear();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + ( this.amount * COST_REDUCTION ) + DESCRIPTIONS[1];

        Enumeration<AbstractPower> keys = allPowers.keys();
        while(keys.hasMoreElements())
        {
            AbstractPower ap = keys.nextElement();
            description += allPowers.get(ap);
            description += " ";
            description += ap.name;
            description += ", ";
        }
    }

    @Override
    public void atStartOfTurn()
    {
        Enumeration<AbstractPower> keys = allPowers.keys();
        while ( keys.hasMoreElements())
        {
            AbstractPower ap = keys.nextElement();
            addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, ap, allPowers.get(ap)));
        }
        allPowers.clear();
        updateDescription();
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1)
    {
        //Avoid leak power in itself
        if ( !(abstractPower.ID == MemoryLeakPower.POWER_ID && abstractPower.amount == 1) && abstractCreature == abstractCreature1)
        {
            if ( allPowers.contains(abstractPower))
            {
                Integer value = allPowers.get(abstractPower);
                value += abstractPower.amount;
            }
            else
            {
                allPowers.put(abstractPower, abstractPower.amount);
            }
        }
        updateDescription();
    }
}
