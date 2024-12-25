package se.t2045006.card.entity;

import java.util.List;

/**
 * ポットを扱うクラス
 *
 * @author Koki Akao
 * @version 17
 * @see CardDeck
 */
public class Pot {
	private int pot = 0; //ポット額
	private List<Player> players; //このポットを争うプレイヤーたち
	
	/**
	 * ポット額と参加プレイヤーを指定してポットを作成する
	 * 
	 * @param pot
	 * 		ポット額
	 * @param players
	 * 		参加プレイヤー
	 */
	public Pot(int pot, List<Player> players){
		this.pot = pot;
		this.players = players;
	}
	
	/**
	 * プレイヤーを取り除く
	 * 
	 * @param player
	 * 		プレイヤー
	 */
	public void removePlayer(Player player) {
		for(int index = 0; index < players.size(); index++) {
			if(players.get(index).equals(player))
				players.remove(index);
		}
	}
	
	/**
	 * ポット額を更新する
	 * 
	 * @param chip
	 * 		更新後のポット額
	 */
	public void updatePot(int chip) {
		pot += chip;
	}
	
	/**
	 * ポットのチップを分配する
	 */
	public void devideChips() {
		int remainder;
		int dividend;
		
		remainder = pot % players.size();
		if(remainder == 0)
			dividend = pot / players.size();
		else
			dividend = (pot - remainder) / players.size();
		
		for(int index = 0; index < players.size(); index++) {
			int profit;
			if(index == 0) {
				profit = dividend + remainder;
			}else
				profit = dividend;
			players.get(index).receiveChip(profit);
		}
	}
	
	/**
	 * ポット額を返す
	 * 
	 * @return ポット額
	 */
	public int getPot() {
		return pot;
	}
	
	/**
	 * 参加プレイヤーを返す
	 * 
	 * @return 参加プレイヤーのリスト
	 */
	public List<Player> getPlayers(){
		return players;
	}
}
