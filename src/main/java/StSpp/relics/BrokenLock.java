package StSpp.relics;

import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class BrokenLock extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("BrokenLock");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SailingBoat.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SailingBoatOutline.png"));

    public BrokenLock()
    {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
