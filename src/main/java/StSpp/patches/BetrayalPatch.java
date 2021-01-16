package StSpp.patches;

import StSpp.powers.BetrayalPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Bloodletting;
import com.megacrit.cardcrawl.cards.red.Hemokinesis;
import com.megacrit.cardcrawl.cards.red.Offering;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BrutalityPower;
import com.megacrit.cardcrawl.powers.CombustPower;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;

public class BetrayalPatch
{
    @SpirePatch(clz = Bloodletting.class, method = "use")
    public static class UseBloodlet
    {
        public static SpireReturn Prefix(AbstractCard c,AbstractPlayer p, AbstractMonster m)
        {
            if ( p.hasPower(BetrayalPower.POWER_ID))
            {
                AbstractPower power = p.getPower(BetrayalPower.POWER_ID);
                power.flash();

                if ( power.amount > 1 )
                {
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p,p, BetrayalPower.POWER_ID,1));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p, BetrayalPower.POWER_ID));
                }

                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(c.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new HealAction(p,p,3));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Hemokinesis.class, method = "use")
    public static class UseHemo
    {
        public static SpireReturn Prefix(AbstractCard c,AbstractPlayer p, AbstractMonster m)
        {
            if ( p.hasPower(BetrayalPower.POWER_ID))
            {
                AbstractPower power = p.getPower(BetrayalPower.POWER_ID);
                power.flash();

                if ( power.amount > 1 )
                {
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p,p, BetrayalPower.POWER_ID,1));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p, BetrayalPower.POWER_ID));
                }

                if (m != null) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));
                }

                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, c.damage, c.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new HealAction(p,p,c.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Offering.class, method = "use")
    public static class UseOffering
    {
        @SpireInsertPatch(rloc = 6)
        public static SpireReturn Prefix(AbstractCard c,AbstractPlayer p, AbstractMonster m)
        {
            if ( p.hasPower(BetrayalPower.POWER_ID))
            {
                AbstractPower power = p.getPower(BetrayalPower.POWER_ID);
                power.flash();

                if ( power.amount > 1 )
                {
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p,p, BetrayalPower.POWER_ID,1));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p, BetrayalPower.POWER_ID));
                }

                AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, 6));
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, c.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = CombustPower.class, method = "atEndOfTurn", paramtypez = {
            boolean.class
    })
    public static class PassiveCombust
    {
        @SpireInsertPatch(rloc = 1)
        public static SpireReturn UseCombust(CombustPower power, boolean isPlayer)
        {
            if ( power.owner.hasPower(BetrayalPower.POWER_ID))
            {
                if ( power.amount > 1 )
                {
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(power.owner, power.owner, BetrayalPower.POWER_ID,1));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(power.owner,power.owner, BetrayalPower.POWER_ID));
                }

                power.owner.getPower(BetrayalPower.POWER_ID).flash();
                AbstractDungeon.actionManager.addToBottom(new HealAction(power.owner,power.owner,1));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(power.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = BrutalityPower.class, method = "atStartOfTurnPostDraw")
    public static class PassiveBrutality
    {
        @SpireInsertPatch(rloc = 1)
        public static SpireReturn UseCombust(BrutalityPower power)
        {
            if ( power.owner.hasPower(BetrayalPower.POWER_ID))
            {
                BetrayalPower betray = (BetrayalPower)power.owner.getPower(BetrayalPower.POWER_ID);
                if ( betray.amount > 1 )
                {
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(power.owner, power.owner, BetrayalPower.POWER_ID,1));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(power.owner,power.owner, BetrayalPower.POWER_ID));
                }

                AbstractDungeon.actionManager.addToBottom(new HealAction(power.owner,power.owner,power.amount));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(power.owner, power.amount));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
