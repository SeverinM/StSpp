package StSpp.patches;

import StSpp.CustomTags;
import StSpp.actions.InstantNightmareAction;
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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

public class FreeItemsShop
{
    @SpirePatch(clz=AbstractCard.class, method="getPrice")
    public static class FreeCard
    {
        public static SpireReturn<Integer> Prefix(AbstractCard.CardRarity rarity)
        {
            if (AbstractDungeon.player.hasRelic("StSpp:GiftCard") && rarity == AbstractCard.CardRarity.COMMON)
            {
                return SpireReturn.Return(0);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz= AbstractPotion.class, method="getPrice")
    public static class FreePotion
    {
        @SpireInsertPatch(rloc = 2)
        public static SpireReturn<Integer> ModifyPricePotion()
        {
            if (AbstractDungeon.player.hasRelic("StSpp:GiftCard"))
            {
                return SpireReturn.Return(0);
            }
            return SpireReturn.Continue();
        }
    }
}
