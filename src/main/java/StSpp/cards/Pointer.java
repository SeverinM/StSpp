package StSpp.cards;

import StSpp.actions.BladeOfBloodAction;
import StSpp.relics.XCola;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.interfaces.PostBattleSubscriber;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static StSpp.DefaultMod.makeCardPath;

public class Pointer extends CustomCard
{
    public static final String ID = DefaultMod.makeID(Pointer.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Redirection.png");

    public Pointer()
    {
        super(ID, cardStrings.NAME, IMG, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void upgrade()
    {
        if (canUpgrade())
        {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        int startIndex = MathUtils.random( AbstractDungeon.player.hand.group.size() - 1 );
        for (int i = startIndex + 1; i != startIndex; i = ( i + 1) % AbstractDungeon.player.hand.group.size() )
        {
            AbstractCard c = AbstractDungeon.player.hand.group.get(i);
            if ( !(c == this || c.type == CardType.CURSE || c.type == CardType.STATUS) )
            {
                AbstractCreature creat = AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
                this.addToTop(new NewQueueCardAction(c, creat, false, true));
                break;
            }
        }
    }
}
