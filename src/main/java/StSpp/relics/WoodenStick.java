package StSpp.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class WoodenStick extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("WoodenStick");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Bow.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BowOutline.png"));

    public WoodenStick()
    {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        if ( targetCard.hasTag(AbstractCard.CardTags.STARTER_DEFEND))
        {
            targetCard.baseBlock++;
        }
        if (targetCard.hasTag(AbstractCard.CardTags.STARTER_STRIKE))
        {
            targetCard.baseDamage++;
        }
    }
}
