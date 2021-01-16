package StSpp.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class PetrifiedCaterpillar extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("PetrifiedCaterpillar");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GlassPickaxe.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GlassPickaxeOutline.png"));
    private static int GOLD_GAIN = 12;

    Boolean perfection;

    public PetrifiedCaterpillar()
    {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart()
    {
        perfection = true;
        pulse = true;
        grayscale = false;
    }

    @Override
    public void onLoseHp(int damageAmount)
    {
        if ( damageAmount > 0 && !grayscale)
        {
            pulse = false;
            flash();
            perfection = false;
            grayscale = true;
        }
    }

    @Override
    public void onVictory()
    {
        if ( perfection)
        {
            flash();
            AbstractDungeon.player.gainGold(GOLD_GAIN);
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + " #b" + GOLD_GAIN + " " + this.DESCRIPTIONS[1];
    }
}
