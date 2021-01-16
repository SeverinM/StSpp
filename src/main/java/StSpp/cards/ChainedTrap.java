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
        super(ID,cardStrings.NAME,IMG,1,cardStrings.DESCRIPTION,CardType.SKILL,CardColor.GREEN,CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        int energy = (EnergyPanel.totalCount - this.cost) * this.magicNumber;
        for (int i = 0; i < energy; i++)
        {
            AbstractMonster monster = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
            AbstractPower chosenPower = new PoisonPower(monster, abstractPlayer, 1);
            int rand = new Random().nextInt(6);

            if ( rand == 1 )
            {
                chosenPower = new WeakPower(monster,this.magicNumber, false);
            }
            else if ( rand == 2)
            {
                chosenPower = new VulnerablePower(monster, this.magicNumber, false);
            }
            else if (rand == 3)
            {
                chosenPower = new ChokePower(monster,1);
            }
            else if (rand == 4)
            {
                chosenPower = new StrengthPower(monster, -1);
            }
            else if (rand == 5)
            {
                if (MathUtils.random(10) < 5)
                {
                    i--;
                    continue;
                }
                chosenPower = new BlockReturnPower(monster, 1);
            }

            addToBot(new ApplyPowerAction(monster, abstractPlayer,chosenPower));
            if ( rand == 4)
            {
                this.addToBot(new ApplyPowerAction(monster, abstractPlayer, new GainStrengthPower(monster, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
}
