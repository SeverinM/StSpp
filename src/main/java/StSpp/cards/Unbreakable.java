package StSpp.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.Iterator;

import static StSpp.DefaultMod.makeCardPath;

public class Unbreakable extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Unbreakable.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public Unbreakable()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = 2;
        this.baseBlock = 6;
    }

    @Override
    public void upgrade() {
        if ( !canUpgrade())
        {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void applyPowersToBlock()
    {
        this.isBlockModified = false;
        float tmp = (float)this.baseBlock;

        AbstractPower p;
        Iterator var2 = AbstractDungeon.player.powers.iterator();
        while (var2.hasNext())
        {
            p = (AbstractPower)var2.next();

            //Not affected by frail
            if ( ( p.ID == FrailPower.POWER_ID || p.ID == VulnerablePower.POWER_ID ) && p.amount > 0)
            {
                tmp *= this.magicNumber;
                continue;
            }

            tmp = p.modifyBlock(tmp, this);
        }

        if (this.baseBlock != MathUtils.floor(tmp)) {
            this.isBlockModified = true;
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        this.block = MathUtils.floor(tmp);
        triggerOnGlowCheck();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        Iterator var1 = AbstractDungeon.player.powers.iterator();

        while(var1.hasNext()) {
            AbstractPower p = (AbstractPower)var1.next();
            if (p.ID == "Frail" && p.amount > 0) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new GainBlockAction(abstractPlayer,abstractPlayer,block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Unbreakable();
    }
}
