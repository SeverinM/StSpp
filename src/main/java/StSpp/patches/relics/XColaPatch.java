package StSpp.patches.relics;

import StSpp.relics.XCola;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class XColaPatch
{
    @SpirePatch(clz=AbstractCard.class, method="applyPowers")
    public static class UsePatch
    {
        public static void Postfix(AbstractCard c)
        {
            if ( c.cost == -1 )
            {
                c.freeToPlayOnce = AbstractDungeon.player.hasRelic(XCola.ID) ? true : false;
            }
        }
    }
}
