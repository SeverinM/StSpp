package StSpp.patches;

import StSpp.CustomTags;
import StSpp.actions.InstantNightmareAction;
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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

public class NextTurnTag
{
    @SpirePatch(clz=AbstractCard.class, method=SpirePatch.CONSTRUCTOR,
            paramtypez =  {
                    String.class,
                    String.class,
                    String.class,
                    int.class,
                    String.class,
                    AbstractCard.CardType.class,
                    AbstractCard.CardColor.class,
                    AbstractCard.CardRarity.class,
                    AbstractCard.CardTarget.class,
                    DamageInfo.DamageType.class
            })
    public static class ConstructorBatch3
    {
        public static void Postfix(AbstractCard c, String id, String name, String imgUrl, int cost, String rawDescription, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, DamageInfo.DamageType dType)
        {
            if ( c.cardID == "Dodge and Roll" || c.cardID == "Flying Knee" || c.cardID == "Outmaneuver" ||
                    c.cardID == "Predator" || c.cardID == "Doppelganger" || c.cardID == "Night Terror" ||
                    c.cardID == "Phantasmal Killer" || c.cardID == "Conserve Battery" || c.cardID == "Collect")
                c.tags.add(CustomTags.NEXT_TURN);
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="applyPowers")
    public static class UsePatch
    {
        public static void Postfix(AbstractCard c)
        {
            if ( c.cardID == "Dodge and Roll" || c.cardID == "Flying Knee" || c.cardID == "Outmaneuver" ||
                    c.cardID == "Predator" || c.cardID == "Doppelganger" || c.cardID == "Nightmare" ||
                    c.cardID == "Phantasmal Killer" || c.cardID == "Conserve Battery" || c.cardID == "Collect"
                    || c.cardID == "StSpp:TimeParadox")
                c.tags.add(CustomTags.NEXT_TURN);
        }
    }

    @SpirePatch(clz= DodgeAndRoll.class, method="use")
    public static class UseDaR
    {
        public static void Postfix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic( "StSpp:ChessPiece") && !AbstractDungeon.player.getRelic("StSpp:ChessPiece").grayscale)
            {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, c.block));
            }
        }
    }

    @SpirePatch(clz= FlyingKnee.class, method="use")
    public static class UseFK
    {
        public static void Postfix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic( "StSpp:ChessPiece") && !AbstractDungeon.player.getRelic("StSpp:ChessPiece").grayscale)
            {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            }
        }
    }

    @SpirePatch(clz= Outmaneuver.class, method="use")
    public static class UseOM
    {
        public static void Postfix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic( "StSpp:ChessPiece") && !AbstractDungeon.player.getRelic("StSpp:ChessPiece").grayscale)
            {
                if (c.upgraded)
                {
                    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(3));
                }
                else {
                        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));
                }
            }
        }
    }

    @SpirePatch(clz= Predator.class, method="use")
    public static class UsePredator
    {
        public static void Postfix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic( "StSpp:ChessPiece") && !AbstractDungeon.player.getRelic("StSpp:ChessPiece").grayscale)
            {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(2));
            }
        }
    }

    @SpirePatch(clz= Doppelganger.class, method="use")
    public static class UseDPG
    {
        public static int bufferedEnergy;
        public static void Prefix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic( "StSpp:ChessPiece") && !AbstractDungeon.player.getRelic("StSpp:ChessPiece").grayscale)
            {
                bufferedEnergy = EnergyPanel.totalCount;
            }
        }

        public static void Postfix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(bufferedEnergy));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(bufferedEnergy));
        }
    }

    @SpirePatch(clz=PhantasmalKiller.class, method="use")
    public static class UsePK
    {
        public static void Postfix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic( "StSpp:ChessPiece") && !AbstractDungeon.player.getRelic("StSpp:ChessPiece").grayscale)
            {
                int energy = EnergyPanel.totalCount;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new DoubleDamagePower(p, 1, false),1));
            }
        }
    }

    @SpirePatch(clz=Nightmare.class, method="use")
    public static class UseNightmare
    {
        public static SpireReturn Prefix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic( "StSpp:ChessPiece") && !AbstractDungeon.player.getRelic("StSpp:ChessPiece").grayscale)
            {
                int energy = EnergyPanel.totalCount;
                AbstractDungeon.actionManager.addToBottom(new InstantNightmareAction(p,p, c.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz= ConserveBattery.class, method="use")
    public static class UseCB
    {
        public static void Postfix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic( "StSpp:ChessPiece") && !AbstractDungeon.player.getRelic("StSpp:ChessPiece").grayscale)
            {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            }
        }
    }

    @SpirePatch(clz= Collect.class, method="use")
    public static class UseCollect
    {
        public static void Prefix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if ( AbstractDungeon.player.hasRelic( "StSpp:ChessPiece") && !AbstractDungeon.player.getRelic("StSpp:ChessPiece").grayscale)
            {
                AbstractCard card = new Miracle();
                card.upgrade();
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
            }
        }
    }
}
