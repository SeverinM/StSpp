package StSpp.cards;

import StSpp.actions.BladeOfBloodAction;
import StSpp.powers.BetrayalPower;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;

import static StSpp.DefaultMod.makeCardPath;

public class CopyPaste extends CustomCard
{
    public static final String ID = DefaultMod.makeID(CopyPaste.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("CopyPaste.png");

    public CopyPaste()
    {
        super(ID, cardStrings.NAME, IMG, 3, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {
        if ( canUpgrade())
        {
            upgradeName();
            upgradeBaseCost(2);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        AbstractOrb orb = null;

        int emptyCount = 0;
        for (int i = AbstractDungeon.player.orbs.size() - 1; i >= 0; i--)
        {
            if (AbstractDungeon.player.orbs.get(i).ID == null)
            {
                emptyCount++;
            }
            else
            {
                orb = AbstractDungeon.player.orbs.get(i);
                break;
            }
        }

        if (orb == null || orb.ID == null )
        {
            return;
        }

        for (int i = 0 ; i < emptyCount; i++)
        {
            addToBot(new ChannelAction(orb.makeCopy()));
        }
    }
}
