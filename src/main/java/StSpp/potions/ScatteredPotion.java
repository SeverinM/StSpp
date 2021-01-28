package StSpp.potions;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.PostPotionUseSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
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
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomPotion;

public class ScatteredPotion extends CustomPotion implements CustomSavable<Integer>
{

    public static final String POTION_ID = StSpp.DefaultMod.makeID("ScatteredPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final int BASE_USAGE = 3;
    public static final int BASE_DAMAGE = 5;
    int usageLeft;

    public ScatteredPotion()
    {
        this(BASE_USAGE);
    }

    ScatteredPotion(int usageBase)
    {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionColor.WHITE);

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
    public void use(AbstractCreature abstractCreature)
    {
        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.potency, DamageInfo.DamageType.THORNS);
        info.applyEnemyPowersOnly(abstractCreature);
        if ( usageLeft > 1 )
        {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for ( AbstractPotion ap : AbstractDungeon.player.potions)
                    {
                        if ( ap instanceof PotionSlot )
                        {
                            this.isDone = true;
                            AbstractDungeon.player.obtainPotion(new ScatteredPotion(usageLeft - 1));
                            break;
                        }
                    }
                    this.tickDuration();
                }
            });
        }
        this.addToBot(new DamageAction(abstractCreature, info, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public int getPotency(int i) {
        return BASE_DAMAGE;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ScatteredPotion();
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
