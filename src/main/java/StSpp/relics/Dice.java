package StSpp.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.powers.StrengthPower;
import StSpp.DefaultMod;
import StSpp.util.TextureLoader;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

import static StSpp.DefaultMod.makeRelicOutlinePath;
import static StSpp.DefaultMod.makeRelicPath;

public class Dice extends CustomRelic implements CustomSavable<String>
{
    public static final String ID = DefaultMod.makeID("Dice");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Dice.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DiceOutline.png"));
    String relicId;

    public Dice()
    {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public String onSave() {
        return relicId;
    }

    @Override
    public void onLoad(String s) {
        relicId = s;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart()
    {
        this.pulse = true;
        if ( relicId == null)
        {
            relicId = GetRandomRelic();
        }

        if ( relicId == null)
            return;

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH * 0.28F, (float)Settings.HEIGHT / 2.0F,
                        RelicLibrary.getRelic(relicId).makeCopy());
                this.isDone = true;
            }
        });
    }

    public String GetRandomRelic()
    {
        ArrayList<String> pool = GetPotentialRelic();
        int indexStart = MathUtils.random(pool.size() - 1);
        int index = ( indexStart + 1 ) % pool.size();

        while ( indexStart != index && AbstractDungeon.player.hasRelic( pool.get(index)))
        {
            index = ( indexStart + 1 ) % pool.size();
        }

        if ( index == indexStart )
        {
            return null;
        }

        return pool.get(index);
    }

    public ArrayList<String> GetPotentialRelic()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add(ArtOfWar.ID);
        list.add(BronzeScales.ID);
        list.add(CentennialPuzzle.ID);
        list.add(HappyFlower.ID);
        list.add(Nunchaku.ID);
        list.add(PenNib.ID);
        list.add(Boot.ID);
        list.add(Orichalcum.ID);
        list.add(ToyOrnithopter.ID);
        list.add(BlueCandle.ID);
        list.add(GremlinHorn.ID);
        list.add(HornCleat.ID);
        list.add(InkBottle.ID);
        list.add(Kunai.ID);
        list.add(LetterOpener.ID);
        list.add(MercuryHourglass.ID);
        list.add(MummifiedHand.ID);
        list.add(OrnamentalFan.ID);
        list.add(Shuriken.ID);
        list.add(StrikeDummy.ID);
        list.add(Sundial.ID);
        list.add(BirdFacedUrn.ID);
        list.add(Calipers.ID);
        list.add(CaptainsWheel.ID);
        list.add(DeadBranch.ID);
        list.add(Ginger.ID);
        list.add(IceCream.ID);
        list.add(IncenseBurner.ID);
        list.add(Pocketwatch.ID);
        list.add(StoneCalendar.ID);
        list.add(Torii.ID);
        list.add(TungstenRod.ID);
        list.add(Turnip.ID);
        list.add(UnceasingTop.ID);
        list.add(ChemicalX.ID);
        list.add(HandDrill.ID);
        list.add(MedicalKit.ID);
        list.add(StrangeSpoon.ID);
        return list;
    }

    public void onVictory()
    {
        if ( relicId != null )
            AbstractDungeon.player.loseRelic(relicId);

        relicId = null;
        this.pulse = false;
    }
}
