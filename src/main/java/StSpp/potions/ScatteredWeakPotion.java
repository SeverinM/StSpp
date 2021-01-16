package StSpp.potions;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomPotion;

public class ScatteredWeakPotion extends CustomPotion implements CustomSavable<Integer>
{
    public static final String POTION_ID = StSpp.DefaultMod.makeID("ScatteredWeakPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final int BASE_USAGE = 3;
    public static final int BASE_WEAK = 1;
    int usageLeft;

    public ScatteredWeakPotion()
    {
        this(BASE_USAGE);
    }

    ScatteredWeakPotion(int usageBase)
    {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.WHITE);

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        usageLeft = usageBase;

        // Initialize the Description
        if (usageLeft > 1)
        {
            description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + DESCRIPTIONS[2] + usageLeft + DESCRIPTIONS[3];
        }
        else
        {
            description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        }

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = true;
        targetRequired = true;

        // Initialize the on-hover name + description
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void initializeData() {
        this.potency = this.getPotency();
        if (usageLeft > 1)
        {
            description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + DESCRIPTIONS[2] + usageLeft + DESCRIPTIONS[3];
        }
        else
        {
            description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        this.addToBot(new ApplyPowerAction(abstractCreature, AbstractDungeon.player,
                new WeakPower(AbstractDungeon.player,this.potency,false)));
        if ( usageLeft > 1 )
        {
            addToBot(new ObtainPotionAction(new ScatteredWeakPotion(usageLeft - 1)));
        }
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ScatteredWeakPotion();
    }

    @Override
    public Integer onSave() {
        return usageLeft;
    }

    @Override
    public void onLoad(Integer integer) {
        usageLeft = integer;
        initializeData();
    }
}
