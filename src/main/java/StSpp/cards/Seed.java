package StSpp.cards;

import StSpp.DefaultMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static StSpp.DefaultMod.makeCardPath;

public class Seed extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Seed.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Seed.png");

    public Seed()
    {
        super(ID,cardStrings.NAME,IMG,-2,cardStrings.DESCRIPTION,CardType.SKILL,CardColor.COLORLESS,CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 5;
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
    public void onRemoveFromMasterDeck()
    {
        AbstractRelic rel = null;
        if ( this.upgraded )
        {
            rel = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE);
        }
        else
        {
            rel = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
        }

        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH * 0.28F, (float)Settings.HEIGHT / 2.0F, rel);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {

    }
}
