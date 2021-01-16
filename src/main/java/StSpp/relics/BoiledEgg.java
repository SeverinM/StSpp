package StSpp.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class BoiledEgg extends CustomRelic
{
    //You have X% of chances to upgrade a card when drawn


    public static final String ID = DefaultMod.makeID("BoiledEgg");
    public static int CHANCE_UPGRADE = 10;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CosEgg.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CosEggOutline.png"));

    public BoiledEgg() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard)
    {
        if ( drawnCard.canUpgrade())
        {
            if ( Math.random() * 100 < CHANCE_UPGRADE )
            {
                drawnCard.superFlash();
                drawnCard.upgrade();
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
