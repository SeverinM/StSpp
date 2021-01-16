package StSpp.cards;

import StSpp.CustomTags;
import StSpp.actions.InstantNightmareAction;
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

public class Anticipation extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Anticipation.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public Anticipation()
    {
        super(ID, cardStrings.NAME, IMG, 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 8;
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        if ( abstractPlayer.hasPower(TimeLoopPower.POWER_ID))
        {
            AbstractPower p = abstractPlayer.getPower(TimeLoopPower.POWER_ID);

            if ( p.amount >= 0)
            {
                ArrayList<AbstractCard> cards = AbstractDungeon.commonCardPool.group;
                cards.addAll(AbstractDungeon.uncommonCardPool.group);
                cards.addAll(AbstractDungeon.rareCardPool.group);

                ArrayList<AbstractCard> filtered = new ArrayList<>();
                Iterator it = cards.iterator();
                AbstractCard card;
                while (it.hasNext())
                {
                    card = (AbstractCard) it.next();
                    if ( card.hasTag( CustomTags.NEXT_TURN ))
                    {
                        filtered.add(card);
                    }
                }

                for (int i = 0; i < p.amount; i++)
                {
                    AbstractCard selectedCard = filtered.get(new Random().nextInt(filtered.size()) ).makeCopy();
                    if ( AbstractDungeon.player.hand.size() + 1 <= AbstractDungeon.player.masterHandSize )
                    {
                        addToBot(new MakeTempCardInHandAction(selectedCard, 1,false));
                    }
                    else
                    {
                        addToBot(new MakeTempCardInDrawPileAction(selectedCard, 1,true, true));
                    }
                }
            }
        }

        if ( abstractPlayer.hasPower(EnergizedPower.POWER_ID))
        {
            AbstractPower p = abstractPlayer.getPower(EnergizedPower.POWER_ID);
            addToBot(new GainEnergyAction(p.amount));
        }

        if ( abstractPlayer.hasPower(DrawCardNextTurnPower.POWER_ID))
        {
            AbstractPower p = abstractPlayer.getPower(DrawCardNextTurnPower.POWER_ID);
            addToBot(new DrawCardAction(p.amount));
        }

        if ( abstractPlayer.hasPower(PhantasmalPower.POWER_ID))
        {
            AbstractPower p = abstractPlayer.getPower(PhantasmalPower.POWER_ID);
            this.addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DoubleDamagePower(abstractPlayer, 1, false), 1));
        }

        if ( abstractPlayer.hasPower(NextTurnBlockPower.POWER_ID))
        {
            AbstractPower p = abstractPlayer.getPower(NextTurnBlockPower.POWER_ID);
            this.addToBot(new GainBlockAction(abstractPlayer, p.amount));
        }

        if ( abstractPlayer.hasPower(NightmarePower.POWER_ID))
        {
            NightmarePower p = (NightmarePower)abstractPlayer.getPower(NightmarePower.POWER_ID);
            p.atStartOfTurn();
        }

        if ( upgraded )
        {
            addToBot(new DrawCardAction(1));
        }

    }
}
