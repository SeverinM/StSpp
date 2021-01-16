package StSpp.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
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

public class JackpotMachine extends CustomRelic implements CustomSavable<Integer>
{
    public static final String ID = DefaultMod.makeID("JackpotMachine");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("LightBulb.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("LightBulbOutline.png"));
    private static int DRAW_REQUIRED = 21;

    public JackpotMachine()
    {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
        this.counter = 0;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard)
    {
        this.counter++;
        if ( this.counter >= DRAW_REQUIRED)
        {
            this.counter = 0;
            flash();
            addToBot( new GainEnergyAction(1));
        }
    }

    @Override
    public Integer onSave() {
        return this.counter;
    }

    @Override
    public void onLoad(Integer integer) {
        this.counter = integer;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DRAW_REQUIRED + DESCRIPTIONS[1];
    }
}
