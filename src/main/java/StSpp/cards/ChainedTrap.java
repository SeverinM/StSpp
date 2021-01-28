package StSpp.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Random;

import static StSpp.DefaultMod.makeCardPath;

public class ChainedTrap extends CustomCard
{
    public static final String ID = DefaultMod.makeID(ChainedTrap.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public ChainedTrap()
    {
        super(ID,cardStrings.NAME,IMG,1,cardStrings.DESCRIPTION,CardType.SKILL,CardColor.GREEN,CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        int energy = (EnergyPanel.totalCount - this.cost) * this.magicNumber;
        Random rng = new Random();
        for (int i = 0; i < this.magicNumber; i++)
        {
            AbstractMonster monster = abstractMonster;
            AbstractPower chosenPower = new PoisonPower(monster, abstractPlayer, 1);
            int rand = rng.nextInt(5);

            if ( rand == 1 )
            {
                chosenPower = new WeakPower(monster,1, false);
            }
            else if ( rand == 2)
            {
                chosenPower = new VulnerablePower(monster, 1, false);
            }
            else if (rand == 3)
            {
                chosenPower = new ChokePower(monster,1);
            }
            else if (rand == 4)
            {
                chosenPower = new StrengthPower(monster, -1);
            }

            addToBot(new ApplyPowerAction(monster, abstractPlayer,chosenPower));
            if ( rand == 4)
            {
                this.addToBot(new ApplyPowerAction(monster, abstractPlayer, new GainStrengthPower(monster, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
}
