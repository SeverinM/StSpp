package StSpp.cards;

import basemod.abstracts.CustomCard;
import basemod.devcommands.draw.Draw;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Bludgeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import static StSpp.DefaultMod.makeCardPath;

public class MakeshiftWeapon extends CustomCard
{
    public static final String ID = DefaultMod.makeID(MakeshiftWeapon.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("MakeshiftWeapon.png");

    public MakeshiftWeapon()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = this.damage = 60;
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {
        if ( this.canUpgrade())
        {
            this.upgradeName();
            this.upgradeDamage(10);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        this.addToBot(new DamageAllEnemiesAction(abstractPlayer, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if ( AbstractDungeon.player.relics.size() == 0 )
            return;

        int rng = MathUtils.random(0, AbstractDungeon.player.relics.size() - 1);
        if ( this.upgraded )
        {
            rng = AbstractDungeon.player.relics.size() - 1;
        }

        AbstractDungeon.player.loseRelic(AbstractDungeon.player.relics.get(rng).relicId);
    }
}
