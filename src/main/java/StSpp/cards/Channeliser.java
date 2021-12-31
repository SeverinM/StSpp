package StSpp.cards;

import StSpp.powers.TemporaryFocus;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Random;

import static StSpp.DefaultMod.makeCardPath;

public class Channeliser extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Channeliser.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Igniter.png");

    public Channeliser() {
        super(ID,cardStrings.NAME,IMG,-2,cardStrings.DESCRIPTION,CardType.SKILL,CardColor.BLUE,CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void triggerWhenDrawn()
    {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TemporaryFocus(this.magicNumber)));
        addToBot(new AbstractGameAction() {
            @Override
            public void update()
            {
                for (AbstractOrb orb : AbstractDungeon.player.orbs)
                {
                    if (orb.ID != null)
                    {
                        orb.applyFocus();
                    }
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if ( canUpgrade())
        {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
