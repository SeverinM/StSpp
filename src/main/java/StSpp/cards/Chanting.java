package StSpp.cards;

import StSpp.DefaultMod;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.interfaces.PostDrawSubscriber;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static StSpp.DefaultMod.makeCardPath;

public class Chanting extends CustomCard implements PostDrawSubscriber
{
    public static final String ID = DefaultMod.makeID(Chanting.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Chanting.png");

    public Chanting()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = this.damage = 0;
        BaseMod.subscribe(this);
    }

    public void atTurnStart()
    {
        this.baseDamage = this.damage = 0;
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard)
    {
        this.baseDamage++;
    }
}
