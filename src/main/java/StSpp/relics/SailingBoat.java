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

public class SailingBoat extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("SailingBoat");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SailingBoat.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SailingBoatOutline.png"));

    public SailingBoat() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void atTurnStart()
    {
        if ( !grayscale)
            this.counter++;

        if ( this.counter == 4 && !grayscale)
        {
            flash();
            this.counter = -1;
            addToBot(new ApplyPowerAction( AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player,2),2 ));
            grayscale = true;
        }
    }

    @Override
    public void atBattleStart()
    {
        this.counter = 0;
    }

    public void onVictory()
    {
        this.counter = -1;
        this.grayscale = false;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
