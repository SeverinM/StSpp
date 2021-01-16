package StSpp.powers;

import StSpp.CustomTags;
import StSpp.cards.MuscleMemory;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;

import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

public class SneakPower extends AbstractPower
{
    public static final String POWER_ID = DefaultMod.makeID("SneakPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power32.png");

    public SneakPower(int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn()
    {
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SneakPower.POWER_ID));
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        if ( card.type != AbstractCard.CardType.SKILL)
        {
            addToBot(new LoseBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount * 2));
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, SneakPower.POWER_ID));
        }
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + ( this.amount * 2 ) + DESCRIPTIONS[1];
    }
}
