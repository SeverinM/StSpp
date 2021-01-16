package StSpp.cards;

import StSpp.actions.BladeOfBloodAction;
import StSpp.powers.BetrayalPower;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;

import java.util.ArrayList;

import static StSpp.DefaultMod.makeCardPath;

public class Converter extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Converter.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");

    public static final int ICE_UPGRADE = 1;
    public static final int LIGHT_UPGRADE = 1;
    public static final int DARK_UPGRADE = 2;
    public static final int PLASMA_UPGRADE = 4;

    public Converter()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = this.damage = 1;
        if ( AbstractDungeon.player != null)
        {
            this.baseMagicNumber = this.magicNumber = estimateDamageUpgrade();
        }
    }

    public int GetDamageUpgrade(AbstractOrb orb)
    {
        if (orb == null || orb.ID == null)
        {
            return 0;
        }

        int output = 0;

        switch (orb.ID)
        {
            case Lightning.ORB_ID:
                output = LIGHT_UPGRADE;
                break;

            case Frost.ORB_ID:
                output = ICE_UPGRADE;
                break;

            case Dark.ORB_ID:
                output = DARK_UPGRADE;
                break;

            case Plasma.ORB_ID:
                output = PLASMA_UPGRADE;
                break;
        }

        if ( upgraded)
        {
            output *= 2;
        }

        return output;
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    int estimateDamageUpgrade()
    {
        int sum = 0;
        for ( AbstractOrb o : AbstractDungeon.player.orbs)
        {
            sum += GetDamageUpgrade(o);
        }
        return sum;
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c)
    {
        this.baseMagicNumber = this.magicNumber = estimateDamageUpgrade();
    }

    @Override
    public void triggerWhenDrawn()
    {
        this.baseMagicNumber = this.magicNumber = estimateDamageUpgrade();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        upgradeDamage(this.magicNumber);
        addToBot(new EvokeAllOrbsAction());
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}
