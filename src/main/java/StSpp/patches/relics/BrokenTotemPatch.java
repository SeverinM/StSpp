package StSpp.patches.relics;

import StSpp.relics.BrokenTotem;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class BrokenTotemPatch
{
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.curses.Decay", method="use")
    public static class UsePatchDecay
    {
        public static void Postfix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic(BrokenTotem.ID ))
            {
                AbstractDungeon.player.getRelic(BrokenTotem.ID ).flash();
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.cards.curses.Doubt", method="use")
    public static class UsePatchDoubt
    {
        public static void Postfix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic(BrokenTotem.ID ))
            {
                AbstractDungeon.player.getRelic(BrokenTotem.ID).flash();
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 1, false), 1));
            }
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.cards.curses.Pain", method="triggerOnOtherCardPlayed")
    public static class UsePatchPain
    {
        public static void Postfix(AbstractCard instance,AbstractCard c)
        {
            if ( AbstractDungeon.player.hasRelic(BrokenTotem.ID ))
            {
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
                AbstractDungeon.actionManager.addToTop(new LoseHPAction(target, AbstractDungeon.player, 1));
            }
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.cards.curses.Regret", method="use")
    public static class UsePatchRegret
    {
        public static void Postfix(AbstractCard instance,AbstractPlayer player, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic(BrokenTotem.ID ))
            {
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
                AbstractDungeon.actionManager.addToTop(new LoseHPAction(target, AbstractDungeon.player, AbstractDungeon.player.hand.size()));
            }
        }
    }

    @SpirePatch(cls="com.megacrit.cardcrawl.cards.status.Burn", method="use")
    public static class UsePatchShame
    {
        public static void Postfix(AbstractCard instance,AbstractPlayer player, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic(BrokenTotem.ID ))
            {
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, instance.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }
}
