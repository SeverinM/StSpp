package StSpp.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.HashSet;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class Balance extends CustomRelic
{
    public static final String ID = DefaultMod.makeID("Balance");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Balance.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BalanceOutline.png"));

    public Balance()
    {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    AbstractCard cheapest = null;
    AbstractCard mostExpensive = null;

    AbstractCard getCheapest()
    {
        if ( AbstractDungeon.player.hand.group.size() == 0)
        {
            return null;
        }

        cheapest = null;
        int cheapestCost = 99;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            int cost = c.costForTurn;

            if ( HaveCost(c) && cost < cheapestCost && cost >= 0)
            {
                cheapestCost = cost;
                cheapest = c;
            }
        }
        return cheapestCost == 99 ? null : cheapest;
    }

    AbstractCard getMostExpensive()
    {
        if ( AbstractDungeon.player.hand.group.size() == 0)
        {
            return null;
        }

        mostExpensive = null;
        int mostExpensiveCost = -2;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            int cost = c.costForTurn;

            if ( HaveCost(c) && cost > mostExpensiveCost && cost >= 0)
            {
                mostExpensiveCost = cost;
                mostExpensive = c;
            }
        }
        return mostExpensiveCost == - 2 ? null : mostExpensive;
    }

    public void onCardDraw(AbstractCard drawnCard)
    {
        UpdateCost();
    }

    boolean HaveCost(AbstractCard c)
    {
        return c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS;
    }

    void UpdateCost()
    {
        if (cheapest != null)
        {
            cheapest.costForTurn = cheapest.costForTurn - 1;
            cheapest.isCostModifiedForTurn = (cheapest.costForTurn != cheapest.cost);
        }

        if ( mostExpensive != null)
        {
            mostExpensive.costForTurn = mostExpensive.costForTurn + 1;
            mostExpensive.isCostModifiedForTurn = (mostExpensive.costForTurn != mostExpensive.cost);
        }

        cheapest = getCheapest();
        mostExpensive = getMostExpensive();
        if ( cheapest == null || mostExpensive == null )
        {
            return;
        }

        if ( !HaveCost(cheapest) || !HaveCost(mostExpensive))
        {
            return;
        }

        if ( cheapest.costForTurn < 0 || mostExpensive.costForTurn < 0)
        {
            return;
        }

        cheapest.costForTurn = cheapest.costForTurn + 1;
        cheapest.isCostModifiedForTurn = (cheapest.costForTurn != cheapest.cost);
        mostExpensive.costForTurn = Math.max(mostExpensive.costForTurn - 1, 0);
        mostExpensive.isCostModifiedForTurn = (mostExpensive.costForTurn != mostExpensive.cost);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
