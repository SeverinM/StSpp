package StSpp.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class DamageEnemiesAction extends AbstractGameAction {
    private int baseDamage;
    AttackEffect eff;
    DamageType dmgType;
    ArrayList<AbstractCreature> allTgts = new ArrayList<>();
    boolean utilizeBaseDamage;

    public DamageEnemiesAction(boolean modifyDmg, int dmgBase, AttackEffect effect, DamageType dmg, ArrayList<AbstractCreature> allTargets, boolean isFast)
    {
        utilizeBaseDamage = modifyDmg;
        baseDamage = dmgBase;
        eff = effect;
        dmgType = dmg;
        allTgts = allTargets;

        if (isFast) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FAST;
        }
    }

    @Override
    public void update()
    {
        boolean playedMusic = false;

        for ( AbstractCreature c : allTgts)
        {
            if (!((AbstractMonster)c).isDying && ((AbstractMonster)c).currentHealth > 0 && !((AbstractMonster)c).isEscaping) {
                if (playedMusic) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)c).hb.cX, ((AbstractMonster)c).hb.cY, this.attackEffect, true));
                } else {
                    playedMusic = true;
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)c).hb.cX, ((AbstractMonster)c).hb.cY, this.attackEffect));
                }
            }
        }

        this.tickDuration();
        if (this.isDone) {
            Iterator var4 = AbstractDungeon.player.powers.iterator();

            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();

            for(AbstractCreature c : allTgts) {
                if (!((AbstractMonster)c).isDeadOrEscaped()) {
                    if (this.attackEffect == AttackEffect.POISON) {
                        ((AbstractMonster)c).tint.color.set(Color.CHARTREUSE);
                        ((AbstractMonster)c).tint.changeColor(Color.WHITE.cpy());
                    } else if (this.attackEffect == AttackEffect.FIRE) {
                        ((AbstractMonster)c).tint.color.set(Color.RED);
                        ((AbstractMonster)c).tint.changeColor(Color.WHITE.cpy());
                    }

                    DamageInfo info = new DamageInfo(AbstractDungeon.player, baseDamage);

                    ((AbstractMonster)c).damage(info);
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }
    }
}
