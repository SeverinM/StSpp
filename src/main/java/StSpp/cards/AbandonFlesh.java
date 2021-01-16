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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
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

public class AbandonFlesh extends CustomCard
{
    public static final String ID = DefaultMod.makeID(AbandonFlesh.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("AbandonFlesh.png");

    public AbandonFlesh()
    {
        super(ID, cardStrings.NAME, IMG, 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF);
        tags.add(CustomTags.NEXT_TURN);
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            this.upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new AbandonFleshPower(1)));

        if ( AbstractDungeon.player.hasRelic(ChessPiece.ID) && !AbstractDungeon.player.getRelic(ChessPiece.ID).grayscale )
        {
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer,new IntangiblePower(abstractPlayer,1)));
        }
    }
}
