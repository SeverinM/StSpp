package StSpp.patches.relics;

import StSpp.relics.SpiranhaBones;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HPLossPatches {

    @SpirePatch(clz= LoseHPAction.class, method="update")
    public static class UsePatch
    {
        @SpireInsertPatch(rloc = 6)
        public static SpireReturn InsertHPLoss(AbstractGameAction action)
        {
            if ( AbstractDungeon.player.hasRelic(SpiranhaBones.ID) && action.source == AbstractDungeon.player && action.target == AbstractDungeon.player && AbstractDungeon.player.currentBlock > 0 )
            {
                int block = AbstractDungeon.player.currentBlock;
                int blockCost = 0;
                while ( action.amount > 0 && block > 0)
                {
                    action.amount--;

                    int cost = 1;
                    block -= cost;
                    blockCost += cost;
                }

                AbstractDungeon.actionManager.addToTop(new DamageAction( AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, blockCost, DamageInfo.DamageType.THORNS)));

                action.isDone = true;
                if ( action.amount == 0)
                {
                    return SpireReturn.Return(null);
                }
            }

            return SpireReturn.Continue();
        }
    }
}
