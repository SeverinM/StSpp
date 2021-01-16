package StSpp.cards;

import StSpp.CustomTags;
import StSpp.actions.BladeOfBloodAction;
import StSpp.powers.AbandonFleshPower;
import StSpp.relics.ChessPiece;
import StSpp.relics.XCola;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static StSpp.DefaultMod.makeCardPath;

public class Landslide extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Landslide.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Landslide.png");

    public Landslide()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.magicNumber = this.baseMagicNumber = 10;
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        for( AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            addToBot(new LoseHPAction(m, abstractPlayer, this.magicNumber));
            addToBot(new GainBlockAction(m, this.magicNumber));
            addToBot(new ApplyPowerAction(m, abstractPlayer,new BarricadePower(m)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Landslide();
    }
}
