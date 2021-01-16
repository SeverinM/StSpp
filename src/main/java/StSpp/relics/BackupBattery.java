package StSpp.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.StrengthPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class BackupBattery extends CustomRelic{

    public static final String ID = DefaultMod.makeID("BackupBattery");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BackupBattery.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BackupBatteryOutline.png"));

    public BackupBattery() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void atTurnStartPostDraw()
    {
        Iterator it = AbstractDungeon.player.orbs.iterator();
        AbstractOrb relic;
        while ( it.hasNext())
        {
            relic = (AbstractOrb)it.next();
            if ( !(relic instanceof EmptyOrbSlot))
            {
                return;
            }
        }

        flash();
        this.addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
        this.addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
