package StSpp.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;

import java.util.Iterator;

import static StSpp.DefaultMod.makeCardPath;

public class Resilience extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Resilience.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public Resilience( )
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 6;
        this.baseMagicNumber = 2;
    }

    @Override
    public void applyPowers()
    {
        AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;
        float tmp = (float)this.baseDamage;
        Iterator var3 = player.relics.iterator();

        while(var3.hasNext()) {
            AbstractRelic r = (AbstractRelic)var3.next();
            tmp = r.atDamageModify(tmp, this);
            if (this.baseDamage != (int)tmp) {
                this.isDamageModified = true;
            }
        }

        AbstractPower p;

        Iterator var2 = AbstractDungeon.player.powers.iterator();
        while (var2.hasNext())
        {
            p = (AbstractPower)var2.next();

            //Not affected by weak
            if ( p.ID == "Weakened" && p.amount > 0)
            {
                tmp *= this.baseMagicNumber;
                continue;
            }

            tmp = p.atDamageGive(tmp,this.damageTypeForTurn, this);
        }

        tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
        if (this.baseDamage != (int)tmp) {
            this.isDamageModified = true;
        }

        for(var3 = player.powers.iterator(); var3.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower)var3.next();
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        if (this.baseDamage != MathUtils.floor(tmp)) {
            this.isDamageModified = true;
        }

        this.damage = MathUtils.floor(tmp);
        triggerOnGlowCheck();
        LogManager.getLogger("StSpp").info(this.damage) ;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        Iterator var1 = AbstractDungeon.player.powers.iterator();

        while(var1.hasNext()) {
            AbstractPower p = (AbstractPower)var1.next();
            if (p.ID == "Weakened" && p.amount > 0) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        this.applyPowers();
        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.NORMAL);
        info.applyEnemyPowersOnly(abstractMonster);
        abstractMonster.damage(info);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Resilience();
    }
}
