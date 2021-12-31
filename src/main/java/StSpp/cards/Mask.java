package StSpp.cards;

import StSpp.actions.DamageEnemiesAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Random;

import static StSpp.DefaultMod.makeCardPath;

public class Mask extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Mask.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Mask.png");

    public Mask()
    {
        super(ID,cardStrings.NAME,IMG,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardColor.BLUE,CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = this.damage = 10;
    }

    @Override
    public void upgrade() {
        if (canUpgrade())
        {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        ArrayList<AbstractCreature> allTargets = new ArrayList<>();
        for ( AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if ( m != abstractMonster)
            {
                allTargets.add(m);
                //addToBot(new DamageAction(m,new DamageInfo(abstractPlayer,this.damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }

        addToBot(new DamageEnemiesAction(true,this.damage, AbstractGameAction.AttackEffect.BLUNT_LIGHT, DamageInfo.DamageType.NORMAL, allTargets, true));
    }
}
