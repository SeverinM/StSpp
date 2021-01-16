package StSpp.powers;

import StSpp.CustomTags;
import StSpp.cards.MuscleMemory;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
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

public class InhibitedPower extends AbstractPower
{
    public static final String POWER_ID = DefaultMod.makeID("InhibitedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power32.png");

    Hashtable<AbstractPower, Integer> capturedPowers = new Hashtable<>();

    @Override
    public void atEndOfRound()
    {
        if ( this.amount == 1)
        {
            Enumeration<AbstractPower> keys = capturedPowers.keys();
            while ( keys.hasMoreElements())
            {
                AbstractPower p = keys.nextElement();
                addToBot(new ApplyPowerAction(owner, owner,p, capturedPowers.get(p)));
            }

            capturedPowers.clear();
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, InhibitedPower.POWER_ID));
        }
        else
        {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, InhibitedPower.POWER_ID, 1));
        }
    }

    public void onInitialApplication()
    {
        super.onInitialApplication();

        for ( AbstractPower p : owner.powers)
        {
            if ( p.type == PowerType.BUFF && p.ID != InhibitedPower.POWER_ID)
            {
                if ( capturedPowers.contains(p))
                {
                    capturedPowers.put(p,p.amount + capturedPowers.get(p));
                }
                else
                {
                    capturedPowers.put(p, p.amount);
                }
                this.addToBot(new RemoveSpecificPowerAction(owner, AbstractDungeon.player,p));
            }
        }
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);

        for ( AbstractPower p : owner.powers)
        {
            if ( p.type == PowerType.BUFF && p.ID != InhibitedPower.POWER_ID)
            {
                if ( capturedPowers.contains(p))
                {
                    capturedPowers.put(p,p.amount + capturedPowers.get(p));
                }
                else
                {
                    capturedPowers.put(p, p.amount);
                }
                this.addToBot(new RemoveSpecificPowerAction(owner, AbstractDungeon.player,p));
            }
        }
        updateDescription();
    }

    public InhibitedPower(int amount, AbstractCreature owner)
    {
        capturedPowers = new Hashtable<>();

        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
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

        Enumeration<AbstractPower> keys = capturedPowers.keys();
        while ( keys.hasMoreElements())
        {
            AbstractPower p = keys.nextElement();
            description += p.amount;
            description += " ";
            description += p.name;

            if ( keys.hasMoreElements())
                description += ", ";
        }
    }
}
