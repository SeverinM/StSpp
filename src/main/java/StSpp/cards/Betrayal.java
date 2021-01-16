package StSpp.cards;

import StSpp.DefaultMod;
import StSpp.powers.BetrayalPower;
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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static StSpp.DefaultMod.makeCardPath;

public class Betrayal extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Betrayal.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Betray.png");

    public Betrayal()
    {
        super(ID, cardStrings.NAME, IMG, -1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void upgrade()
    {
        if ( this.canUpgrade())
        {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (abstractPlayer.hasRelic("Chemical X")) {
            effect += 2;
            abstractPlayer.getRelic("Chemical X").flash();
        }

        if (abstractPlayer.hasRelic(XCola.ID)) {
            this.freeToPlayOnce = true;
        }

        if ( upgraded )
        {
            effect++;
        }

        if (effect > 0) {
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer,new BetrayalPower(effect)));

            if (!this.freeToPlayOnce) {
                abstractPlayer.energy.use(EnergyPanel.totalCount);
            }
        }
    }
}
