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

import java.util.Dictionary;
import java.util.Hashtable;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class TwinEgg extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("TwinEgg");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TwoEgg.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TwoEgg.png"));

    Hashtable<AbstractCard, Integer> cardCount = new Hashtable<>();
    public TwinEgg()
    {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        if ( cardCount.get( targetCard ) != null)
        {
            cardCount.put(targetCard, cardCount.get(targetCard) + 1);
            super.onUseCard(targetCard, useCardAction);
            if ( targetCard.canUpgrade())
            {
                targetCard.superFlash();
                targetCard.upgrade();
                flash();
                cardCount.remove(targetCard);
            }
        }
        else
        {
            cardCount.put(targetCard, 1);
        }
    }

    @Override
    public void onVictory()
    {
        cardCount.clear();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
