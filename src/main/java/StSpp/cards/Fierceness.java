package StSpp.cards;

import StSpp.actions.BladeOfBloodAction;
import StSpp.actions.MultiDrawPileToHandAction;
import StSpp.powers.BetrayalPower;
import StSpp.relics.XCola;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.sun.jndi.ldap.Ber;

import java.util.ArrayList;

import static StSpp.DefaultMod.makeCardPath;

public class Fierceness extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Fierceness.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Fierceness.png");

    public Fierceness()
    {
        super(ID, cardStrings.NAME, IMG, -1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.magicNumber = 2;
        this.baseMagicNumber = this.magicNumber;
        this.baseDamage = this.damage = 5;
    }

    @Override
    public void upgrade()
    {
        if( canUpgrade())
        {
            this.upgradeName();
            this.upgradeMagicNumber(1);
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

        this.freeToPlayOnce = abstractPlayer.hasRelic(XCola.ID);

        if (effect > 0) {
            for (int i = 0; i < effect; i++)
            {
                addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer, this.damage + ( i * magicNumber)), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }

            if (!this.freeToPlayOnce) {
                abstractPlayer.energy.use(EnergyPanel.totalCount);
            }
        }
    }
}
