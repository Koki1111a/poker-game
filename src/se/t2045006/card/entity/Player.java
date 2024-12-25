package se.t2045006.card.entity;


/**
 * 	プレイヤークラス
 *
 * @author Koki Akao
 * @version 17
 * @see CardDeck
 */
public abstract class Player {
	protected String name;	//名前
	protected int chip; // 所持チップ
	protected int betChip = 0; // そのフェーズで賭けているチップ
	protected int position = -1; // -1...なし, 0...small blind, 1...big blind, ...
	protected int lastAction = -6; // -6...なし, -5...small blind, -4...big blind, -3...fold, -2,..check, -1...call, 0...all in, 1...bet, 2...raise, 3...3-bet
	protected int increaseOfChip = 0;	//チップの増減
	protected int rank = -1;	//順位
	protected CardDeck holeCard;
	protected boolean isDroppedOut = false;
	
	/**
	 * 名前を指定してプレイヤを作成する
	 * @param name
	 * 		名前
	 */
	public Player(String name) {
		if (name.length()==0 || name==null) {
			this.name = "名無し";
		} else {
			this.name = name;
		}
		holeCard = new CardDeck();
	}
	
	/**
	 * アクションを選択する
	 * 
	 * @param phase
	 * 		フェーズ
	 * @param lastAction
	 * 		ラストアグレッサーがおこなったアクション
	 * @param minRaiseChip
	 * 		レイズできる最少額
	 * @param maxRaiseChip
	 * 		レイズできる最大額
	 * @param neededChip
	 * 		コールするために必要な額
	 * @return	次アクションするプレイヤーがコールするために必要な額
	 */
	public abstract int chooseOption(int phase, int lastAction, int minRaiseChip, int maxRaiseChip, int neededChip);
	
	/**
	 * パラメータをリフレッシュする
	 */
	public void refresh() {
		betChip = 0;
		lastAction = -6;
		holeCard.clear();
		if(isDroppedOut)
			position = -1;
		increaseOfChip = 0;
	}
	
	/**
	 * 同一のプレイヤーかどうかを返す
	 * 
	 * @param player
	 * 		プレイヤー
	 * @return 同一のプレイヤーか
	 */
	public boolean equals(Player player) {
		if(name.equals(player.getName()) &&
			chip == player.getChip() &&
			betChip == player.getBetChip() &&
			position == player.getPosition() &&
			lastAction == player.getLastAction() &&
			increaseOfChip == player.getIncreaseOfChip() &&
			rank == player.getRank() &&
			holeCard.equals(player.getHoleCard()) &&
			isDroppedOut == player.getIsDroppedOut())
			return true;
		else
			return false;
		
	}
	
	/**
	 * 名前を返す
	 * 
	 * @return 名前
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 持っているチップ額を返す
	 * 
	 * @return チップ額
	 */
	public int getChip() {
		return chip;
	}
	
	/**
	 * そのフェーズでかけているチップ額を返す
	 * 
	 * @return そのフェーズでかけているチップ額
	 */
	public int getBetChip() {
		return betChip;
	}
	
	/**
	 * ポジションを返す
	 * 
	 * @return ポジション
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * 最後におこなったアクションを返す
	 * 
	 * @return 最後におこなったアクション
	 */
	public int getLastAction() {
		return lastAction;
	}
	
	/**
	 * チップの増減を返す	
	 * 
	 * @return チップの増減
	 */
	public int getIncreaseOfChip() {
		return increaseOfChip;
	}
	
	/**
	 * ホール(スターティング)カードを返す．
	 * 
	 * @return ホールカード
	 */
	public CardDeck getHoleCard() {
		return holeCard;
	}
	
	/**
	 * 順位を返す
	 * 
	 * @return 順位
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * 破産したかを返す
	 * 
	 * @return 破産したか
	 */
	public boolean getIsDroppedOut() {
		return isDroppedOut;
	}
	
	/**
	 * チップ額を更新する.
	 * 
	 * @param chip
	 * 		更新後のチップ額
	 */
	public void setChip(int chip) {
		this.chip = chip;
	}
	
	/**
	 * ポジションを更新する
	 * 
	 * @param position
	 * 		更新後のポジション
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * 最後におこなったアクションを更新する
	 * 
	 * @param lastAction
	 * 		最後に行ったアクション
	 */
	public void setLastAction(int lastAction) {
		this.lastAction = lastAction;
	}
	
	/**
	 * 順位を更新する
	 * 
	 * @param rank
	 * 		順位
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	/**
	 * ポットからチップを受け取る
	 * 
	 * @param pot
	 * 		ポット
	 */
	public void receiveChip(int pot) {
			this.chip += pot;
			increaseOfChip += pot;
	}
	
	/**
	 * カードを一枚ホールカードとして受け取る
	 * 
	 * @param card
	 * 		カード
	 */
	public void receiveCard(Card card) {
		holeCard.addCard(card);
	}
	
	/**
	 * 破産する
	 */
	public void dropOut() {
		isDroppedOut = true;
	}
	
	/**
	 * ホールカードを返す
	 * 
	 * @return ホールカード
	 */
	public CardDeck takeHoleCard() {
		CardDeck tmp = new CardDeck();
		int time = holeCard.size();
		for(int i = 0; i < time; i++) {
			tmp.addCard(holeCard.takeCard());
		}
			
		holeCard.clear();
		return tmp;
	}
	
	/**
	 * そのフェーズ賭けたチップを渡す
	 * 
	 * @return そのフェーズで賭けたチップ
	 */
	public int payChip() {
		int tmp;
		chip -= betChip;
		increaseOfChip -= betChip; 
		tmp = betChip;
		betChip = 0;
		return tmp;
	}
	
	/**
	 * 余分に賭けたチップを戻す
	 * 
	 * @param chip
	 * 		払い戻しのチップ額
	 */
	public void payBack(int chip) {
		betChip -= chip;
	}
	
	/**
	 * スモールブラインドのアクションを行う
	 * 
	 * @param bigBlind
	 * 		ビッグブラインド額
	 */
	public void smallBlind(int bigBlind) {
		if(chip <= bigBlind / 2) {
			lastAction = 0;
			betChip = chip; 
		}else {
			lastAction = -5;
			betChip += bigBlind / 2; 
		}
	}
	
	/**
	 * ビッグブラインドのアクションを行う
	 * 
	 * @param bigBlind
	 * 		ビッグブラインド額
	 */
	public void bigBlind(int bigBlind) {
		if(chip <= bigBlind) {
			lastAction = 0;
			betChip = chip; 
		}else {
			lastAction = -4;
			betChip += bigBlind; 
		}
	}
	
	/**
	 * フォールドする 
	 */
	public void fold() {
		lastAction = -3;
	}
	
	/**
	 * チェックする
	 */
	public void check() {
		lastAction = -2;
	}
	
	/**
	 * コールする
	 * 
	 * @param chip
	 * 		コールする額
	 */
	public void call(int chip) {
		if(this.chip <= chip) {
			lastAction = 0;
			betChip = this.chip;
		}else {
			lastAction = -1;
			betChip = chip;
		}
	}
	
	/**
	 * ベットする
	 * 
	 * @param chip
	 * 		ベットする額
	 */
	public void bet(int chip) {
		lastAction = 1;
		if(this.chip == chip)
			lastAction = 0;
		betChip = chip;
	}
	
	/**
	 * レイズする
	 * 
	 * @param chip
	 * 		レイズする額
	 * @param count
	 * 		そのフェーズで何回目のレイズになるか
	 */
	public void raise(int chip, int count) {
		lastAction = count;
		if(this.chip == chip)
			lastAction = 0;
		betChip = chip;
	}
	
	
}
