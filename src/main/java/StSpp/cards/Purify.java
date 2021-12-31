package StSpp.cards;

import StSpp.CustomTags;
import StSpp.actions.InstantNightmareAction;
import StSpp.powers.PurifyPower;
import StSpp.powers.TimeLoopPower;
import basemod.abstracts.CustomCard;
import basemod.devcommands.draw.Draw;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static StSpp.DefaultMod.makeCardPath;

public class Purify extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Purify.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Purify.png");

    public Purify()
    {
        super(ID, cardStrings.NAME, IMG, 2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.updateCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new PurifyPower(this.magicNumber)));
    }
}
