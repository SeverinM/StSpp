package StSpp.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
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

public class PileOfSugar extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("PileOfSugar");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PileOfSugar.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PileOfSugarOutline.png"));

    public PileOfSugar()
    {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void onEquip()
    {
        AbstractDungeon.player.energy.energyMaster += 2;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 2;
    }

    @Override
    public void onPlayerEndTurn()
    {
        if ( EnergyPanel.totalCount > 0)
        {
            flash();
            this.addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 30 * EnergyPanel.totalCount));
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
