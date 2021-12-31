package StSpp.relics;

import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class Metronome extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("Metronome");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Metronome.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MetronomeOutline.png"));

    public Metronome()
    {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
        this.counter = 0;
    }

    @Override
    public void atTurnStart()
    {
        this.flash();
        this.counter++;

        this.grayscale = this.counter % 2 == 0;
        if ( this.counter % 2 == 0)
        {
            this.addToBot(new LoseEnergyAction(1));
        }
        else
        {
            this.addToBot(new GainEnergyAction(1));
        }
    }

    public void onVictory()
    {
        this.counter = 0;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
