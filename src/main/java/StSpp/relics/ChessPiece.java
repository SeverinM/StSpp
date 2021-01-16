package StSpp.relics;

import StSpp.CustomTags;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnReceivePowerPatch;
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

import java.util.Iterator;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class ChessPiece extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("ChessPiece");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ChessPiece.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ChessPiece.png"));

    public ChessPiece()
    {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
        this.pulse = true;
    }

    @Override
    public void atBattleStart()
    {
        this.grayscale = false;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard)
    {
        if ( !grayscale && drawnCard.hasTag(CustomTags.NEXT_TURN))
        {
            drawnCard.setCostForTurn(drawnCard.cost - 1);
        }
    }

    @Override
    public boolean canSpawn() {
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        AbstractCard c;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            c = (AbstractCard)var1.next();
        } while(!c.hasTag(CustomTags.NEXT_TURN));

        return true;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        if (!targetCard.hasTag(CustomTags.NEXT_TURN))
            return;

        if ( !grayscale )
        {
            this.pulse = false;
            this.flash();
            this.grayscale = true;

            Iterator var2 = AbstractDungeon.player.hand.group.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                if (c.hasTag(CustomTags.NEXT_TURN) && targetCard != c) {
                    c.isCostModified = false;
                    c.setCostForTurn( c.cost );
                }
            }
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
