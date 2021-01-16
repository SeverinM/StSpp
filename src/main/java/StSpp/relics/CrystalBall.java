package StSpp.relics;

import StSpp.CustomTags;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnReceivePowerPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.*;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class CrystalBall extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("CrystalBall");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CrystalBall.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CrystalBallOutline.png"));

    public CrystalBall()
    {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onEquip()
    {
        HashSet<String> existingCards = new HashSet<>();

        for (AbstractCard c : AbstractDungeon.player.hand.group )
        {
            existingCards.add(c.cardID);
        }

        HashSet<AbstractCard> cardToBrowse = new HashSet<>();
        cardToBrowse.addAll(AbstractDungeon.rareCardPool.group);

        int iCardToShowBriefly = 10;
        for (AbstractCard c : cardToBrowse)
        {
            AbstractDungeon.player.masterDeck.group.add(c);

            if  ( iCardToShowBriefly > 0 && MathUtils.random(10) < 5 )
            {
                iCardToShowBriefly--;
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT));
            }
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
