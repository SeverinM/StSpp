package StSpp.patches;

import StSpp.CustomTags;
import StSpp.actions.InstantNightmareAction;
import StSpp.cards.MemoryLeak;
import StSpp.powers.FaithPower;
import StSpp.powers.MemoryLeakPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sun.security.provider.ConfigFile;

import java.util.ArrayList;
import java.util.Iterator;

public class FaithPatch
{
    @SpirePatch(clz = AbstractPower.class, method="atDamageGive", paramtypez = {
            float.class, DamageInfo.DamageType.class
    })
    public static class MantraPatch
    {
        public static SpireReturn<Float> Prefix(AbstractPower p, float damage, DamageInfo.DamageType type)
        {
            if ( !(p instanceof MantraPower))
                return SpireReturn.Continue();

            if ( AbstractDungeon.player.hasPower(FaithPower.POWER_ID))
                damage += ( p.amount / (int)3 ) * AbstractDungeon.player.getPower(FaithPower.POWER_ID).amount;
            return SpireReturn.Return(damage);
        }
    }
}
