package StSpp.cards;

import StSpp.actions.BladeOfBloodAction;
import StSpp.powers.BetrayalPower;
import basemod.abstracts.CustomCard;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostPotionUseSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;

import static StSpp.DefaultMod.makeCardPath;

public class BladeOfBlood extends CustomCard implements OnPlayerDamagedSubscriber, PostPotionUseSubscriber, OnStartBattleSubscriber
{
    public static final String ID = DefaultMod.makeID(BladeOfBlood.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("BladeOfBlood.png");

    public BladeOfBlood()
    {
        super(ID, cardStrings.NAME, IMG, 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = GetPotentialDamages();
    }

    @Override
    public void upgrade()
    {
        if ( this.canUpgrade())
        {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    int GetPotentialDamages()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if( player == null )
            return 0;

        int maxHP = player.maxHealth;
        int currentHP = player.currentHealth;

        if ( player.hasPower( BetrayalPower.POWER_ID ))
            return maxHP + ( currentHP / 2 );
        return maxHP - ( currentHP / 2 );
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        int HPLoss = 0;
        if ( abstractPlayer.currentHealth > 1 )
        {
            HPLoss = abstractPlayer.currentHealth / 2;
        }

        if ( abstractMonster != null)
        {
            this.addToBot(new VFXAction(new HemokinesisEffect(abstractPlayer.hb.cX, abstractPlayer.hb.cY, abstractMonster.hb.cX, abstractMonster.hb.cY), 0.5F));
        }

        if ( abstractPlayer.hasPower(BetrayalPower.POWER_ID))
        {
            BetrayalPower power = (BetrayalPower)abstractPlayer.getPower(BetrayalPower.POWER_ID);
            if ( power.amount > 1 )
            {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(power.owner, power.owner, BetrayalPower.POWER_ID,1));
            }
            else
            {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(power.owner,power.owner, BetrayalPower.POWER_ID));
            }

            AbstractDungeon.actionManager.addToBottom(new HealAction(power.owner,power.owner, HPLoss));
        }
        else
        {
            this.addToBot(new LoseHPAction(abstractPlayer, abstractPlayer, HPLoss));
        }

        this.addToBot(new BladeOfBloodAction(abstractMonster, abstractPlayer, this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BladeOfBlood();
    }

    @Override
    public int receiveOnPlayerDamaged(int i, DamageInfo damageInfo)
    {
        this.magicNumber = this.baseMagicNumber = GetPotentialDamages();
        return i;
    }

    @Override
    public void receivePostPotionUse(AbstractPotion abstractPotion) {
        this.magicNumber = this.baseMagicNumber = GetPotentialDamages();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c)
    {
        this.magicNumber = this.baseMagicNumber = GetPotentialDamages();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom)
    {
        this.magicNumber = this.baseMagicNumber = GetPotentialDamages();
    }

    @Override
    public void triggerWhenDrawn()
    {
        this.magicNumber = this.baseMagicNumber = GetPotentialDamages();
    }
}
