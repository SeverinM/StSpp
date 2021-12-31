package StSpp.cards;

import StSpp.actions.BladeOfBloodAction;
import StSpp.powers.MemoryLeakPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;

import java.util.Iterator;

import static StSpp.DefaultMod.makeCardPath;


public class MemoryLeak extends CustomCard
{
    public static final String ID = DefaultMod.makeID(MemoryLeak.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static AbstractCard lastUsed = null;

    public static final String IMG = makeCardPath("MemoryLeak.png");

    public MemoryLeak()
    {
        super(ID, cardStrings.NAME, IMG, 2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void upgrade() {
        if ( canUpgrade())
        {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        lastUsed = this;
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new MemoryLeakPower()));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                MemoryLeakPower ml = (MemoryLeakPower)abstractPlayer.getPower(MemoryLeakPower.POWER_ID);

                Iterator var7 = AbstractDungeon.player.hand.group.iterator();

                AbstractCard c;
                while(var7.hasNext()) {
                    c = (AbstractCard)var7.next();
                    if (c.type == AbstractCard.CardType.POWER && lastUsed != c)
                    {
                        ml.TryApplyDiscount(c);
                    }
                }

                var7 = AbstractDungeon.player.drawPile.group.iterator();

                while(var7.hasNext()) {
                    c = (AbstractCard)var7.next();
                    if (c.type == AbstractCard.CardType.POWER && lastUsed != c)
                    {
                        ml.TryApplyDiscount(c);
                    }
                }

                var7 = AbstractDungeon.player.discardPile.group.iterator();

                while(var7.hasNext()) {
                    c = (AbstractCard)var7.next();
                    if (c.type == AbstractCard.CardType.POWER && lastUsed != c)
                    {
                        ml.TryApplyDiscount(c);
                    }
                }

                var7 = AbstractDungeon.player.exhaustPile.group.iterator();

                while(var7.hasNext()) {
                    c = (AbstractCard)var7.next();
                    if (c.type == AbstractCard.CardType.POWER && lastUsed != c)
                    {
                        ml.TryApplyDiscount(c);
                    }
                }
                this.isDone = true;
            }
        });
    }
}
