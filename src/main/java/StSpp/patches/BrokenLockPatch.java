package StSpp.patches;

import StSpp.relics.BrokenLock;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BrokenLockPatch
{
    @SpirePatch(clz = AbstractCard.class, method = "canUse")
    public static class canUsePatch
    {
        public static SpireReturn<Boolean> Prefix(AbstractCard c, AbstractPlayer p, AbstractMonster m)
        {
            if (AbstractDungeon.player.hasRelic(BrokenLock.ID) )
            {
                if (c.type == AbstractCard.CardType.STATUS && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Medical Kit"))
                {
                    return SpireReturn.Return(false);
                }
                else if (c.type == AbstractCard.CardType.CURSE && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Blue Candle"))
                {
                    return SpireReturn.Return(false);
                } else
                    {
                    return SpireReturn.Return(c.cardPlayable(m) && c.hasEnoughEnergy());
                }
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }
}
