package StSpp.cards;

import StSpp.actions.BladeOfBloodAction;
import StSpp.relics.XCola;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import StSpp.DefaultMod;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import java.util.ArrayList;
import java.util.Collections;

import static StSpp.DefaultMod.makeCardPath;

public class SwitchWeapon extends CustomCard implements PostBattleSubscriber
{
    public static final String ID = DefaultMod.makeID(SwitchWeapon.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Power.png");
    String createdRelic = "";

    public SwitchWeapon()
    {
        super(ID, cardStrings.NAME, IMG, 3, cardStrings.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF);
        BaseMod.subscribe(this);
    }

    @Override
    public void upgrade() {
        if ( canUpgrade())
        {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    public ArrayList<String> GetRelicPool()
    {
        ArrayList<String> allRelics = new ArrayList<>();
        allRelics.add(Nunchaku.ID);
        allRelics.add(PenNib.ID);
        allRelics.add(Kunai.ID);
        allRelics.add(LetterOpener.ID);
        allRelics.add(OrnamentalFan.ID);
        allRelics.add(Shuriken.ID);
        allRelics.add(WristBlade.ID);
        return allRelics;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        ArrayList<String> relics = GetRelicPool();
        Collections.shuffle(relics, new java.util.Random(AbstractDungeon.shuffleRng.randomLong()));
        for (String relicId : relics)
        {
            if ( !AbstractDungeon.player.hasRelic(relicId))
            {
                createdRelic = relicId;
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH * 0.28F, (float)Settings.HEIGHT / 2.0F,
                        RelicLibrary.getRelic(createdRelic).makeCopy());
                break;
            }
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom)
    {
        if ( createdRelic != "" )
        {
            AbstractDungeon.player.loseRelic(createdRelic);
        }
    }
}
