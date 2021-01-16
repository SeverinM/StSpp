package StSpp.patches;

import StSpp.CustomTags;
import StSpp.actions.InstantNightmareAction;
import StSpp.powers.SpareBladePower;
import StSpp.powers.TemporaryFocus;
import StSpp.powers.VitalPointPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.ConserveBattery;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.Collect;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

public class TemporaryFocusPatch {

    @SpirePatch(clz= AbstractOrb.class, method="applyFocus")
    public static class ApplyTemporaryPatch
    {
        public static void Postfix(AbstractOrb orb)
        {
            AbstractPower power = AbstractDungeon.player.getPower(TemporaryFocus.POWER_ID);
            if (power != null && !orb.ID.equals("Plasma")) {
                orb.passiveAmount += power.amount;
                orb.evokeAmount += power.amount;
            }
            return;
        }
    }

    @SpirePatch(clz = ApplyPowerAction.class, method=SpirePatch.CONSTRUCTOR,
    paramtypez =
            {
                    AbstractCreature.class,
                    AbstractCreature.class,
                    AbstractPower.class,
                    int.class,
                    boolean.class,
                    AbstractGameAction.AttackEffect.class
            })
    public static class RemoveFocusPatch
    {
        public static SpireReturn Prefix(ApplyPowerAction p, AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect)
        {
            if ( AbstractDungeon.player.hasPower(VitalPointPower.POWER_ID) && source == AbstractDungeon.player)
            {
                powerToApply.amount *= 2;
                AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, VitalPointPower.POWER_ID, 1));
            }

            if ( !AbstractDungeon.player.hasPower(TemporaryFocus.POWER_ID) || powerToApply.ID != FocusPower.POWER_ID || powerToApply.amount >= 0 )
            {
                return SpireReturn.Continue();
            }

            TemporaryFocus tf = (TemporaryFocus)AbstractDungeon.player.getPower(TemporaryFocus.POWER_ID);

            //Can remove all focus with temporary
            if ( tf.amount >= -powerToApply.amount)
            {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, TemporaryFocus.POWER_ID, powerToApply.amount));
                return SpireReturn.Return(null);
            }
            else
            {
                powerToApply.amount += tf.amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, TemporaryFocus.POWER_ID));
            }

            return SpireReturn.Continue();
        }
    }
}
