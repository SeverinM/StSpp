package StSpp.cards;

import basemod.abstracts.CustomCard;
import basemod.devcommands.draw.Draw;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.EnergyBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Bludgeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import org.apache.logging.log4j.LogManager;

import java.util.Iterator;

import static StSpp.DefaultMod.makeCardPath;

public class NonchalentStrike extends CustomCard
{
    public static final String ID = DefaultMod.makeID(NonchalentStrike.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");

    public NonchalentStrike()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = this.damage = 9;
    }

    @Override
    public void applyPowers()
    {
        AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;
        this.damage = this.baseDamage;
        triggerOnGlowCheck();
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        DamageInfo info = new DamageInfo(abstractPlayer, this.damage);
        info.output = this.baseDamage;
        TrueDamage(info, abstractMonster);
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(abstractMonster.hb.cX, abstractMonster.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));
    }

    private void TrueBrokeBlock(AbstractCreature c) {
        if (c instanceof AbstractMonster) {
            Iterator var1 = AbstractDungeon.player.relics.iterator();

            while(var1.hasNext()) {
                AbstractRelic r = (AbstractRelic)var1.next();
                r.onBlockBroken(c);
            }
        }

        AbstractDungeon.effectList.add(new HbBlockBrokenEffect(c.hb.cX - this.hb.width / 2.0F, c.hb.cY - c.hb.height / 2.0F));
        CardCrawlGame.sound.play("BLOCK_BREAK");
    }

    protected int TrueDecrementBlock(DamageInfo info, int damageAmount, AbstractCreature c) {
        if (info.type != DamageInfo.DamageType.HP_LOSS && c.currentBlock > 0) {
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
            if (damageAmount > c.currentBlock) {
                damageAmount -= c.currentBlock;
                if (Settings.SHOW_DMG_BLOCK) {
                    AbstractDungeon.effectList.add(new BlockedNumberEffect(c.hb.cX, c.hb.cY + c.hb.height / 2.0F, Integer.toString(c.currentBlock)));
                }

                c.loseBlock();
                TrueBrokeBlock(c);
            } else if (damageAmount == c.currentBlock) {
                damageAmount = 0;
                c.loseBlock();
                TrueBrokeBlock(c);
                AbstractDungeon.effectList.add(new BlockedWordEffect(c, c.hb.cX, c.hb.cY, TEXT[1]));
            } else {
                CardCrawlGame.sound.play("BLOCK_ATTACK");
                c.loseBlock(damageAmount);

                for(int i = 0; i < 18; ++i) {
                    AbstractDungeon.effectList.add(new BlockImpactLineEffect(c.hb.cX, c.hb.cY));
                }

                if (Settings.SHOW_DMG_BLOCK) {
                    AbstractDungeon.effectList.add(new BlockedNumberEffect(c.hb.cX, c.hb.cY + c.hb.height / 2.0F, Integer.toString(damageAmount)));
                }

                damageAmount = 0;
            }
        }

        return damageAmount;
    }

    public void TrueDamage(DamageInfo info, AbstractCreature c) {
        int damageAmount = info.output;
        if (!c.isDying && !c.isEscaping) {
            if (damageAmount < 0) {
                damageAmount = 0;
            }

            boolean hadBlock = true;
            if (c.currentBlock == 0) {
                hadBlock = false;
            }

            boolean weakenedToZero = damageAmount == 0;
            damageAmount = TrueDecrementBlock(info, damageAmount,c);

            Iterator var5 = c.powers.iterator();
            AbstractPower p;

            while(var5.hasNext()) {
                p = (AbstractPower)var5.next();
                p.wasHPLost(info, damageAmount);
            }

            if (info.owner != null) {
                var5 = info.owner.powers.iterator();

                while(var5.hasNext()) {
                    p = (AbstractPower)var5.next();
                    p.onAttack(info, damageAmount, c);
                }
            }

            for(var5 = c.powers.iterator(); var5.hasNext(); damageAmount = p.onAttacked(info, damageAmount)) {
                p = (AbstractPower)var5.next();
            }

            c.lastDamageTaken = Math.min(damageAmount, c.currentHealth);
            boolean probablyInstantKill = c.currentHealth == 0;
            if (damageAmount > 0) {
                if (info.owner != c) {
                    c.useStaggerAnimation();
                }

                if (damageAmount >= 99 && !CardCrawlGame.overkill) {
                    CardCrawlGame.overkill = true;
                }

                c.currentHealth -= damageAmount;
                if (!probablyInstantKill) {
                    AbstractDungeon.effectList.add(new StrikeEffect(c, c.hb.cX, c.hb.cY, damageAmount));
                }

                if (c.currentHealth < 0) {
                    c.currentHealth = 0;
                }

                c.healthBarUpdatedEvent();
            } else if (!probablyInstantKill) {
                if (weakenedToZero && c.currentBlock == 0) {
                    if (hadBlock) {
                        AbstractDungeon.effectList.add(new BlockedWordEffect(c, c.hb.cX, c.hb.cY, TEXT[30]));
                    } else {
                        AbstractDungeon.effectList.add(new StrikeEffect(c, c.hb.cX, c.hb.cY, 0));
                    }
                } else if (Settings.SHOW_DMG_BLOCK) {
                    AbstractDungeon.effectList.add(new BlockedWordEffect(c, c.hb.cX, c.hb.cY, TEXT[30]));
                }
            }

            if (c.currentHealth <= 0) {
                ((AbstractMonster)c).die();
                if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.cleanCardQueue();
                    AbstractDungeon.effectList.add(new DeckPoofEffect(64.0F * Settings.scale, 64.0F * Settings.scale, true));
                    AbstractDungeon.effectList.add(new DeckPoofEffect((float)Settings.WIDTH - 64.0F * Settings.scale, 64.0F * Settings.scale, false));
                    AbstractDungeon.overlayMenu.hideCombatPanels();
                }

                if (c.currentBlock > 0) {
                    c.loseBlock();
                    AbstractDungeon.effectList.add(new HbBlockBrokenEffect(c.hb.cX - c.hb.width / 2.0F, c.hb.cY - c.hb.height / 2.0F));
                }
            }

        }
    }
}
