package StSpp.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class WoodenPendulum extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("WoodenPendulum");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("pendulum.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("pendulumOutline.png"));
    public static final int BASE_TURN = 3;
    public static final int TURN_GROWTH = 1;

    public int GetMaxTurn()
    {
        return BASE_TURN + ( TURN_GROWTH * Math.min(3, AbstractDungeon.actNum));
    }

    @Override
    public void atBattleStart()
    {
        this.counter = GetMaxTurn();
    }

    @Override
    public void onVictory()
    {
        this.counter = 0;
        this.pulse = false;
        if ( !grayscale )
        {
            flash();
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + "#b" + GetMaxTurn() + this.DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStart()
    {
        this.counter--;
        if ( this.counter == 0)
        {
            this.pulse = true;
        }

        if ( this.counter < 0 && !grayscale)
        {
            flash();
            grayscale = true;
            this.pulse = false;
        }
    }

    public int changeNumberOfCardsInReward(int numberOfCards)
    {
        return numberOfCards + ( this.counter >= 0 ? 2 : 0 );
    }

    public WoodenPendulum()
    {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }
}
