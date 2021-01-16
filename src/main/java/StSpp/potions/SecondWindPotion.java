package StSpp.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomPotion;

public class SecondWindPotion extends CustomPotion
{
    public static final String POTION_ID = StSpp.DefaultMod.makeID("SecondWindPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public SecondWindPotion()
    {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOLT, PotionColor.WHITE);

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0];

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;

        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature abstractCreature)
    {
        for(AbstractPower p : AbstractDungeon.player.powers)
        {
            if ( p.type == AbstractPower.PowerType.DEBUFF)
            {
                addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player,p) );
            }

            else if ( ( p.ID == DexterityPower.POWER_ID || p.ID == StrengthPower.POWER_ID ) && p.amount < 0 )
            {
                addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player,p) );
            }
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new SecondWindPotion();
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }
}
