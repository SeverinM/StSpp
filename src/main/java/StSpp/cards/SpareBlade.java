package StSpp.cards;

import StSpp.DefaultMod;
import StSpp.powers.BetrayalPower;
import StSpp.powers.SpareBladePower;
import StSpp.relics.XCola;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.unique.WhirlwindAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static StSpp.DefaultMod.makeCardPath;

public class SpareBlade extends CustomCard
{
    public static final String ID = DefaultMod.makeID(SpareBlade.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("SpareBlade.png");

    public SpareBlade()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
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
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new SpareBladePower(1)));
        for ( AbstractCard c : abstractPlayer.hand.group)
        {
            if ( c.cardID == Shiv.ID )
            {
                c.selfRetain = true;
                SpareBladePower.allShivs.add((Shiv)c);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SpareBlade();
    }
}
