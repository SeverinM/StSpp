package StSpp.patches;

import StSpp.CustomTags;
import StSpp.actions.InstantNightmareAction;
import StSpp.powers.SpareBladePower;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.ConserveBattery;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.Collect;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

public class ShivPatch
{
    /*
    @SpirePatch(clz=AbstractCard.class, method="triggerOnManualDiscard")
    public static class ShivDiscardPatch
    {
        public static void Prefix(AbstractCard c)
        {
            if ( AbstractDungeon.player.hasPower(SpareBladePower.POWER_ID))
            {
                int amount = AbstractDungeon.player.getPower(SpareBladePower.POWER_ID).amount;
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), amount));
            }
        }
    }*/

    @SpirePatch(clz=Shiv.class, method = SpirePatch.CONSTRUCTOR)
    public static class ShivCreatePatch
    {
        public static void Postfix(Shiv s)
        {
            if ( AbstractDungeon.player != null && AbstractDungeon.player.hasPower(SpareBladePower.POWER_ID ) )
            {
                s.selfRetain = true;
                SpareBladePower.allShivs.add(s);
            }
        }
    }

    @SpirePatch(clz=AbstractCard.class, method = "triggerWhenDrawn")
    public static class ShivDrawPatch
    {
        public static void Postfix(AbstractCard c)
        {
            if ( c.cardID == Shiv.ID && AbstractDungeon.player.hasPower(SpareBladePower.POWER_ID))
            {
                c.selfRetain = true;
                SpareBladePower.allShivs.add((Shiv)c);
            }
        }
    }
}
