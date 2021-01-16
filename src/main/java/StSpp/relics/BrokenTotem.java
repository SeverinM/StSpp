package StSpp.relics;

import basemod.abstracts.CustomRelic;
import basemod.interfaces.OnCardUseSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class BrokenTotem extends CustomRelic
{
    //Curses / Status effect are also applied to all ennemies

    public static final String ID = DefaultMod.makeID("BrokenTotem");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BrokenTotem.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BrokenTotemOutline.png"));

    public BrokenTotem()
    {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
