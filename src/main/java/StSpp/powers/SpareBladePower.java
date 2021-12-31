package StSpp.powers;

import StSpp.CustomTags;
import StSpp.cards.MuscleMemory;
import basemod.devcommands.draw.Draw;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

public class SpareBladePower extends AbstractPower implements PostBattleSubscriber, OnStartBattleSubscriber
{
    public static final String POWER_ID = DefaultMod.makeID("SpareBladePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    static final int REQUIRED = 6;

    private static final Texture tex84 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("StSppResources/images/powers/placeholder_power32.png");

    public static ArrayList<Shiv> allShivs = new ArrayList<>();

    int draw = 0;

    public SpareBladePower(int amount)
    {
        allShivs.clear();
        draw = amount;
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = REQUIRED;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if ( card.cardID != Shiv.ID )
            return;

        --this.amount;
        if (this.amount == 0) {
            this.flash();
            this.amount = REQUIRED;
            addToBot(new DrawCardAction(draw));
        }

        this.updateDescription();
    }

    public void OnExhaust(AbstractCard c)
    {
        if ( c.cardID == Shiv.ID && allShivs.contains(c))
        {
            allShivs.remove(c);
        }
    }

    void OnRemove()
    {
        for (Shiv s : allShivs)
        {
            if ( s != null )
                s.retain = false;
        }
        allShivs.clear();
    }

    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        draw++;
        this.updateDescription();
    }

    @Override
    public void receivePostBattle(AbstractRoom var1)
    {
        allShivs.clear();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom var1)
    {
        allShivs.clear();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
