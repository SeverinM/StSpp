package StSpp.cards;

import basemod.abstracts.CustomCard;
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
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Random;

import static StSpp.DefaultMod.makeCardPath;

public class Cataclysm extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Cataclysm.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");

    public Cataclysm()
    {
        super(ID,cardStrings.NAME,IMG,5,cardStrings.DESCRIPTION,CardType.ATTACK,CardColor.COLORLESS,CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust = true;
        this.baseDamage = this.damage = 5;
        this.baseBlock = this.block = 5;
    }

    @Override
    public void upgrade()
    {
        if( canUpgrade())
        {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage)));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new DrawCardAction(5));
        addToBot(new GainEnergyAction(5));
    }
}
