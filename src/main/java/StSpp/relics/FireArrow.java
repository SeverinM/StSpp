package StSpp.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class FireArrow extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("FireArrow");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FireArrow.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FireArrowOutline.png"));

    public FireArrow()
    {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void onPlayerEndTurn()
    {
        for ( AbstractCard c : AbstractDungeon.player.hand.group)
        {
            addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
