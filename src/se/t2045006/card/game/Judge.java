package se.t2045006.card.game;

import java.util.List;

import se.t2045006.card.entity.CardDeck;
import se.t2045006.card.entity.Player;
import se.t2045006.card.entity.PokerHand;
import se.t2045006.card.entity.Pot;

public class Judge {
	/**
	 * 与えられたプレイヤの手を判定し，勝ち組と負け組に分ける．
	 * @param players プレイヤ
	 * @return 判定結果
	 */
	public void judgePlayers(List<Pot> pots, CardDeck communityCard) {
		
		for(Pot pot: pots) {
			int scoreOfWinHand = 0;
			List<Player> players = pot.getPlayers();
			for(Player p: players) {	//最強のハンドを決める．
				PokerHand hand = new PokerHand(p.getHoleCard(), communityCard);
				int score = hand.getScore();
				if(score > scoreOfWinHand)
					scoreOfWinHand = score;
			}
			
			for(int index = 0; index < players.size(); index++) {
				Player player = players.get(index);
				PokerHand hand = new PokerHand(player.getHoleCard(), communityCard);
				int score = hand.getScore();
				if(score < scoreOfWinHand) {
					pot.removePlayer(player);
					index--;
				}
			}
			pot.devideChips();
		}
		
		
	}

}