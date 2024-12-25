package se.t2045006.card.game;

import java.util.ArrayList;
import java.util.List;

import se.t2045006.card.entity.Card;
import se.t2045006.card.entity.CardDeck;
import se.t2045006.card.entity.Player;
import se.t2045006.card.entity.PokerHand;
import se.t2045006.card.entity.Pot;
import se.t2045006.card.entity.User;

/**
 * ポーカーゲームクラス
 *
 * @author Koki Akao
 * @version 17
 * @see CardDeck
 */
public class PokerGame {
	private List<Player> players; //プレイヤーたち
	private List<Player> activePlayers; //アクション可能なプレイヤー
	private Judge judge; //審判
	private List<Pot> pots; //メインポットとサイドポットのリスト
	private int phase; //0...pre flop, 1...flop, 2...turn, 3...river
	private Player lastAggressor; //ラストアグレッサー
	private CardDeck cardDeck, communityCard; //デッキ，コミュニティーカード
	private int bigBlind = 20; //ビッグブラインド
	private boolean showDownFlag = false; //ショウダウン中か
	private int numberOfCommunityCard = 0; //前のフェーズにおけるプリフロップの枚数
	private boolean winFlag; // 敵を全員降ろしたか	
	
	/**
	 * ポーカーゲームを作成する
	 */
	public PokerGame() {
		players = new ArrayList<Player>();
		activePlayers = new ArrayList<Player>();
		pots = new ArrayList<Pot>();
		cardDeck = new CardDeck();
		cardDeck.createFullDeck();
		communityCard = new CardDeck();
		phase = 0;
	}
	
	/**
	 * プレイヤを追加する
	 * @param player
	 * 		ポーカーに参加するプレイヤ
	 */
	public void addPlayer(Player player) {
		players.add(player);
		System.out.printf("「%s」さんをゲームに登録しました．\n", player.getName());
	}
	
	/**
	 * ゲームを開始する．一人が勝ち残るまで繰り返し，最後に成績を表示する．
	 */
	public void startGame() {
		//パラメータチェック．エラー処理(ポーカーは2から10で行うゲーム)
		if(players.size() < 2) {
			System.out.println("×プレイヤが足りません．ゲームを開始できません");
			return;
		}
		if(players.size() > 10) {
			System.out.println("×プレイヤが多すぎます．ゲームを開始できません");
			return;
		}
		
		boolean flag = true;
		System.out.println("■ポーカーを開始します．レートは$10/$20です．");
		initializeChip();
		initializePosition();
		while (flag) {
			refresh();
			doPoker();
			for(Player p1: players) {
				if(p1.getChip() == 0 && !p1.getIsDroppedOut()) {
					p1.setRank(getNumberOfSurviver());
					p1.dropOut();
				}
			}
			if(getNumberOfSurviver() == 1) {
				for(Player p2: players)
					if(!p2.getIsDroppedOut()) {
						p2.setRank(1);
						p2.dropOut();
					}
				flag = false;
			}
			rotatePositon();
		}
		showAllRecords();

	}
	
	/**
	 * 一回のポーカーを行う
	 */
	public void doPoker(){
		try {
			Thread.sleep(2000); // 10秒(1万ミリ秒)間だけ処理を止める
		} catch (InterruptedException e) {
		}
		System.out.println("----------------------------------------------------------------");
		System.out.println();
		for(Player p: players) {
			if(p instanceof User)
				System.out.printf("・%s(あなた)：$%d\n",p.getName(), p.getChip());
			else {
				if(p.getIsDroppedOut())
					System.out.printf("\u001b[00;37m・%s：$0\u001b[00m\n",p.getName());
				else
					System.out.printf("・%s：$%d\n",p.getName(), p.getChip());
			}
		}
		System.out.println();
		System.out.println("ポジションは以下の通りです．");
		System.out.println();
		for(Player p: players)
			if(p.getPosition() >= 0) {
				if(p instanceof User)
					System.out.printf("・%s(あなた) - %s\n",p.getName(), getStringOfPosition(p.getPosition()));
				else
					System.out.printf("・%s - %s\n",p.getName(), getStringOfPosition(p.getPosition()));
			}

		System.out.println();
		System.out.println("カードを配ります．");
		
		cardDeck.shuffle();
		dealCards();
		PlayPreflop();
		if(!winFlag) {
			addCommunityCard(3);
			Playflop();
		}
		if(!winFlag) {
		addCommunityCard(1);
		PlayTurn();
		}
		if(!winFlag) {
		addCommunityCard(1);
		PlayRiver();
		}
		
		judge.judgePlayers(pots, communityCard);
		
		showResult();
		CollectCards();
	}
	
	/**
	 * 結果を表示する
	 */
	public void showResult() {
		try {
			Thread.sleep(3000); // 10秒(1万ミリ秒)間だけ処理を止める
		} catch (InterruptedException e) {
		}
		System.out.println("----------------------------------------------------------------");
		System.out.println("結果");
		System.out.println();
		
		if(showDownFlag) {
			System.out.printf("ボード");
			
			PokerHand handOfUser = new PokerHand(players.get(0).getHoleCard(), communityCard);
			boolean handCards[] = handOfUser.getHandCards();
			for(int index = 0; index < communityCard.size(); index++) {
				Card card = communityCard.seeCard(index+1); 
				if(handCards[index+2])
					System.out.print(" " + "\u001b[00;36m" + card.toString() + "\u001b[00;00m");
				else
					System.out.printf(" %s", card.toString());
			}
			
			System.out.println();
			System.out.println();
			
			for(Player p: players) {
				if( !p.getIsDroppedOut() && (p instanceof User) ) {
					System.out.printf("・%s(あなた)のカード",p.getName());
					CardDeck holeCard = p.getHoleCard();
					for(int index = 0; index < holeCard.size(); index++) {
						Card card = holeCard.seeCard(index+1);
						if(handCards[index])
							System.out.print(" " + "\u001b[00;36m" + card.toString() + "\u001b[00;00m");
						else
							System.out.printf(" %s", card.toString());	
					}
					System.out.printf(" (%s)",handOfUser.getString());
				}else {
					if(p.getIsDroppedOut() || p.getLastAction() == -3)
						continue;
					System.out.printf("・%sのカード",p.getName());
					CardDeck holeCard = p.getHoleCard();
					for(int index = 0; index < holeCard.size(); index++) {
						System.out.printf(" %s", holeCard.seeCard(index+1).toString());	
					}
					PokerHand hand = new PokerHand(p.getHoleCard(), communityCard);
					System.out.printf(" (%s)",hand.getString());
				}
				System.out.println();
			}
			
			System.out.println();
		}	
		
		for(Player p: players) {
			if(p instanceof User) {
				int increaseOfChip = p.getIncreaseOfChip();
				if(increaseOfChip > 0)
				System.out.printf("・%s(あなた)：$%d (+$%d)\n",p.getName(), p.getChip(), increaseOfChip);
				if(increaseOfChip == 0)
					System.out.printf("・%s(あなた)：$%d\n",p.getName(), p.getChip());
				if(increaseOfChip < 0)
					System.out.printf("・%s(あなた)：$%d (-$%d)\n",p.getName(), p.getChip(), -increaseOfChip);
			}else {
				if(p.getIsDroppedOut())
					System.out.printf("\u001b[00;37m・%s：$0\u001b[00m\n",p.getName());
				else {
					int increaseOfChip = p.getIncreaseOfChip();
					if(increaseOfChip > 0)
					System.out.printf("・%s：$%d (+$%d)\n",p.getName(), p.getChip(), increaseOfChip);
					if(increaseOfChip == 0)
						System.out.printf("・%s：$%d\n",p.getName(), p.getChip());
					if(increaseOfChip < 0)
						System.out.printf("・%s：$%d (-$%d)\n",p.getName(), p.getChip(), -increaseOfChip);
				}
			}
		}
		System.out.println();
	}
	
	/**
	 * ゲームの情報(敵のアクション，ポット額，...)を表示する
	 * 
	 * @param player プレイヤー
	 */
	public void displayPoker(Player player) {
		try {
			Thread.sleep(3000); // 10秒(1万ミリ秒)間だけ処理を止める
		} catch (InterruptedException e) {
		}
		System.out.println("----------------------------------------------------------------");

		System.out.printf(getStringOfPhase(phase));		
		if(!showDownFlag) {
			System.out.printf(" (");
			if(lastAggressor.getLastAction() != -6) {
				System.out.printf(" %s %s ", lastAggressor.getName(), 
						getEnglishStringOfAction(lastAggressor.getLastAction()));
				if(lastAggressor.getLastAction() != -2)
					System.out.printf("$%s ", lastAggressor.getBetChip());
				System.out.printf("->");
			}
			System.out.printf(" %s ", player.getName());
			//if( lastAggressor.getLastAction() < -1 && (player.getLastAction() != -5 || player.getLastAction() == -4) )
			if(lastAggressor.getBetChip() == player.getBetChip())
				System.out.printf("option )\n");
			else
				System.out.printf("%d to call )\n", lastAggressor.getBetChip() - player.getBetChip());
		}
		
		System.out.println();
		
		if(pots.size() == 0)
			System.out.println("・ポット：$0");
		else {
			System.out.printf("・ポット：$%d", pots.get(0).getPot());
			for(int index = 1; index < pots.size(); index++) {
				System.out.printf(" + $%d", pots.get(index).getPot());
			}
			System.out.println();
		}

		for(Player p: players) {
			if(p instanceof User) {
				if(p.getIsDroppedOut())
					System.out.printf("\u001b[00;37m・%s(あなた)：$0 ( )\u001b[00m\n",p.getName());
				else
					System.out.printf("・%s(あなた)：$%d (%d)\n",p.getName(), p.getChip(), p.getBetChip());
			
			}else {
				if(p.getIsDroppedOut())
					System.out.printf("\u001b[00;37m・%s：$0 ( )\u001b[00m\n",p.getName());
				else
					System.out.printf("・%s：$%d (%d)\n",p.getName(), p.getChip(), p.getBetChip());
			}
		}
		
		System.out.println();
		
		System.out.printf("ボード");
		
		PokerHand handOfUser = new PokerHand(players.get(0).getHoleCard(), communityCard);
		boolean handCards[] = handOfUser.getHandCards();
		for(int index = 0; index < communityCard.size(); index++) {
			Card card = communityCard.seeCard(index+1); 
			if(handCards[index+2] && !players.get(0).getIsDroppedOut())
				System.out.print(" " + "\u001b[00;36m" + card.toString() + "\u001b[00;00m");
			else
				System.out.printf(" %s", card.toString());
		}
		
		System.out.println();
		System.out.println();
		
		for(Player p: players) {
			if( !p.getIsDroppedOut() && (p instanceof User) ) {
				System.out.printf("・%s(あなた)のカード",p.getName());
				CardDeck holeCard = p.getHoleCard();
				for(int index = 0; index < holeCard.size(); index++) {
					Card card = holeCard.seeCard(index+1);
					if(handCards[index])
						System.out.print(" " + "\u001b[00;36m" + card.toString() + "\u001b[00;00m");
					else
						System.out.printf(" %s", card.toString());	
				}
				System.out.printf(" (%s)",handOfUser.getString());
			}else {
				if(p.getIsDroppedOut() || p.getLastAction() == -3)
					continue;
				System.out.printf("・%sのカード",p.getName());
				CardDeck holeCard = p.getHoleCard();
				if(showDownFlag) {
					for(int index = 0; index < holeCard.size(); index++) {
						System.out.printf(" %s", holeCard.seeCard(index+1).toString());	
					}
					PokerHand hand = new PokerHand(p.getHoleCard(), communityCard);
					System.out.printf(" (%s)",hand.getString());
				}else {
					System.out.printf(" ? ?");
				}
			}
			System.out.println();
		}
		
		System.out.println();
		if(numberOfCommunityCard < communityCard.size()) {
			setNumberOfCommunityCard(communityCard.size());
			System.out.printf("%sカードが追加されました．",getStringOfPhase(phase));
		}
		if((player instanceof User) && (!showDownFlag))
			System.out.println("あなたのアクションを選択してください．");
		System.out.println();
	}
	
	/**
	 * インデックスをもとにポシションを初期化する
	 */
	public void initializePosition() {
		for(int index = 0; index < players.size(); index++) {
			Player player = players.get(index);
			player.setPosition(index);
		}
	}
	
	/**
	 * ビッグブラインドの値から所持チップの初期化を行う
	 */
	public void initializeChip() {
		for(Player p: players) {
			p.setChip(bigBlind*100);
		}
	}
	
	/**
	 * 参加プレイヤーのリフレッシュ
	 */
	public void refresh() {
		activePlayers.clear();
		List<Player> list = new ArrayList<>();
		for(Player p: players) {
			p.refresh();
			if(!p.getIsDroppedOut()) {
				activePlayers.add(p);
				list.add(p);
			}
			
		}
		pots.clear();
		Pot pot = new Pot(0, list);
		pots.add(pot);
		showDownFlag = false;
		winFlag = false;
		numberOfCommunityCard = 0;
	}
	
	/**
	 * ポジションを回す
	 */
	public void rotatePositon() {
		int numberOfPlayers = players.size();
		int min = 10;
		int indexOfBTN = 11;
		
		for(int index = 0; index < numberOfPlayers; index++) {
			Player p = players.get(index);
			if(p.getIsDroppedOut())
				continue;
			int position = p.getPosition();
			if(min > position) {
				min = position;
				indexOfBTN = index;
			}
			
		}
		
		int index = (indexOfBTN + 1) % numberOfPlayers;
		int position = 0;
		
		for(int i = 0; i < numberOfPlayers; i++) {
			Player p = players.get(index);
			index = (index + 1) % numberOfPlayers;
			if(p.getIsDroppedOut())
				continue;
			p.setPosition(position);
			position++;
		}
	}
	
	/**
	 * カードを配る
	 */
	public void dealCards() {
		for(Player p: players) {
			Card card1 = cardDeck.takeCard();
			Card card2 = cardDeck.takeCard();
			p.receiveCard(card1);
			p.receiveCard(card2);
		}
	}
	
	/**
	 * ホールカードとコミュニティカードを回収する
	 */
	public void CollectCards() {
		int time;
		for(Player p: players) {
			CardDeck holeCard = p.takeHoleCard();
			time = holeCard.size();
			for(int i = 0; i < time; i++) {
				Card card = holeCard.takeCard();
				cardDeck.addCard(card);
			}
		}
		time = communityCard.size();
		for(int i = 0; i < time; i++) {
			Card card = communityCard.takeCard();
			cardDeck.addCard(card);
		}
	}
	
	/**
	 * 指定の枚数コミュニティカードを追加する
	 * 
	 * @param number 枚数
	 */
	public void addCommunityCard(int number) {
		for(int i = 0; i < number; i++) {
			Card card = cardDeck.takeCard();
			communityCard.addCard(card);
		}
	}
	
	/**
	 * 特定のプレイヤーからアクションを行う
	 * 
	 * @param index フェーズの初めにアクションを行うプレイヤーのインデックス
	 */
	public void doActions(int index) {
		int neededChip = 0;
		int minRaiseChip = bigBlind;
		int tmp;
		int allInChip = 0;
		Player p;
		
		if(phase == 0) {
			neededChip = bigBlind;
			minRaiseChip = bigBlind * 2;
		}
		
		if(showDownFlag) {
			displayPoker(players.get(index));
			return;
		}

		for(int i = 0; i < players.size(); i++) {
			p = players.get(index);
			index = (index + 1) % players.size();
			if(p.getIsDroppedOut() || p.getLastAction() == -3 || p.getLastAction() == 0)
				continue;
			displayPoker(p);
			tmp = p.chooseOption(phase, lastAggressor.getLastAction(), minRaiseChip, getMaxRaiseChip(p), neededChip);
			if(tmp > neededChip) {
				lastAggressor = p;
				minRaiseChip = tmp + (tmp - neededChip);
				neededChip = tmp;
				i = 0;
			}
			if(p.getLastAction() == -2 || p.getLastAction() == -1)
				lastAggressor = p;
			if(p.getLastAction() == -3) {	//フォールドの場合
				for(int j = 0; j < activePlayers.size(); j++) {
					if(activePlayers.get(j).equals(p))
						activePlayers.remove(j);
				}
				for(Pot pot: pots)	//ポットの獲得権利はく奪
					pot.removePlayer(p);
				if(activePlayers.size() == 1)
					break;
			}	
		}
		
		List<Player> playersList = new ArrayList<>();
		for(index = 0; index < players.size(); index++) {
			p = players.get(index);
			if(!p.getIsDroppedOut())
				playersList.add(p);
		}
		allInChip =0;
		boolean flag;	//特定の額でオールインした人がいるか
		while(playersList.size() > 0) {
			int min = Integer.MAX_VALUE;
			flag = false;
			for(Player player: playersList) {	//最小のベット額を探す．
				if(player.getBetChip() < min)
					min = player.getBetChip();
			}
			
			if(playersList.size() == 1) {
				playersList.get(0).payBack(min - allInChip);
				break;
			}else {
				pots.get(0).updatePot((min - allInChip) * playersList.size());	//メインポットの更新
				allInChip = min;
			}
			
			
			for(index = 0; index < playersList.size(); index++) {
				p = playersList.get(index);
				if(p.getBetChip() == min) {
					if(p.getLastAction() == -3) {
						playersList.remove(index);
						index--;
					}
					if(p.getLastAction() == 0 && p.getBetChip() > 0) {	//オールインしたプレイヤーがいる場合は新たにメインポットを作る必要あり．
						flag = true;
						playersList.remove(index);
						index--;
						for(int j = 0; j < activePlayers.size(); j++) {
							if(activePlayers.get(j).equals(p))
								activePlayers.remove(j);
						}
					}
				}
			}
			
			if(flag && playersList.size() <= 1 && !showDownFlag ) {
				showDownFlag = true;
				System.out.println("ショーダウンです．");
			}
			
			if(flag && playersList.size() >= 2) {
				List<Player> list = new ArrayList<>();
				for(Player player: activePlayers) {
					list.add(player);
				}
				Pot mainPot = new Pot(0, list);
				pots.add(0, mainPot);
			}
			
			for(int j = 0; j < playersList.size(); j++) {
				if(playersList.get(j).getBetChip() == min) {
					playersList.remove(j);
					j--;
				}
			}
		}
		
		int count = 0;
		for(Player player: players) {	//一人を除いて全員フォールドしているか，ラストアクションの更新
			if(!player.getIsDroppedOut() && player.getLastAction() != -3) {
				count++;
				if(player.getLastAction() != 0)
					player.setLastAction(-6);
			}
			player.payChip();
		}
		if(count == 1)
			winFlag = true;

	}
	
	/**
	 * プリフロップを行う
	 */
	public void PlayPreflop() {
		phase = 0;
		int index;

		for(index = 0; index < players.size(); index++) {
			Player p = players.get(index);
			if(p.getPosition() == 0) {
				p.smallBlind(bigBlind);
				break;
			}
		}
		
		for(index = 0; index < players.size(); index++) {
			Player p = players.get(index);
			if(p.getPosition() == 1) {
				p.bigBlind(bigBlind);
				lastAggressor = p;
				break;
			}
		}
		
		index = (index + 1) % players.size();
		
		doActions(index);
		
	}
	
	/**
	 * フロップを行う
	 */
	public void Playflop() {
		phase = 1;
		int index;

		for(index = 0; index < players.size(); index++) {
			Player p = players.get(index);
			if(getNumberOfSurviver() == 2 && p.getPosition() == 1) {
				lastAggressor = p;
				break;
			}
			if(getNumberOfSurviver() > 2 && p.getPosition() == 0) {
				lastAggressor = p;
				break;
			}
		}
		
		doActions(index);
	}
	
	/**
	 * ターンを行う
	 */
	public void PlayTurn() {
		phase = 2;
		int index;

		for(index = 0; index < players.size(); index++) {
			Player p = players.get(index);
			if(getNumberOfSurviver() == 2 && p.getPosition() == 1) {
				lastAggressor = p;
				break;
			}
			if(getNumberOfSurviver() > 2 && p.getPosition() == 0) {
				lastAggressor = p;
				break;
			}
		}
		
		doActions(index);
	}
	
	/**
	 * リバーを行う
	 */
	public void PlayRiver() {
		phase = 3;
		int index;

		for(index = 0; index < players.size(); index++) {
			Player p = players.get(index);
			if(getNumberOfSurviver() == 2 && p.getPosition() == 1) {
				lastAggressor = p;
				break;
			}
			if(getNumberOfSurviver() > 2 && p.getPosition() == 0) {
				lastAggressor = p;
				break;
			}
		}
		
		doActions(index);
		
		if(!showDownFlag && !winFlag) {
			showDownFlag = true;
			System.out.println("ショーダウンです．");
		}
	}
	
	/**
	 * 参加プレイヤーのリストを返す
	 * 
	 * @return 参加プレイヤーのリスト
	 */
	public List<Player> getPlayers(){
		return players;
	}
	
	/**
	 * 審判を獲得する
	 * 
	 * @return 審判
	 */
	public Judge getJudge() {
		return judge;
	}
	
	/**
	 * ポットのリストを返す
	 * 
	 * @return ポットのリスト
	 */
	public List<Pot> getPots(){
		return pots;
	}
	
	/**
	 * フェーズを返す
	 * 
	 * @return フェーズのリスト
	 */
	public int getPhase() {
		return phase;
	}
	
	/**
	 * ビッグブラインドを返す
	 * 
	 * @return ビッグブラインド
	 */
	public int getBigBlind() {
		return bigBlind;
	}
	
	/**
	 * 破産していない人数を返す
	 * 
	 * @return 破産していない人数
	 */
	public int getNumberOfSurviver() {
		int count = 0;
		for(Player p: players) {
			if(!p.getIsDroppedOut()) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * ポジションの文字列を返す
	 * 
	 * @param position
	 * 		ポジション
	 * @return ポジションの文字列
	 */
	public String getStringOfPosition(int position) {
		switch(getNumberOfSurviver()) {
		case 2:
			String[] positions1 = {"SB", "BB"};
			return positions1[position];
		case 3:
			String[] positions2 = {"SB", "BB", "BTN"};
			return positions2[position];
			
		case 4:
			String[] positions3 = {"SB", "BB", "UTG", "BTN"};
			return positions3[position];
		case 5:
			String[] positions4 = {"SB", "BB", "UTG", "MP2", "BTN"};
			return positions4[position];
		case 6:
			String[] positions5 = {"SB", "BB", "UTG", "MP2", "LP1", "BTN"};
			return positions5[position];
		case 7:
			String[] positions6 = {"SB", "BB", "UTG", "MP1", "MP2", "LP1", "BTN"};
			return positions6[position];
		case 8:
			String[] positions7 = {"SB", "BB", "UTG", "MP1", "MP2", "MP3", "LP1", "BTN"};
			return positions7[position];
		case 9:
			String[] positions8 = {"SB", "BB", "UTG", "MP1", "MP2", "MP3", "LP1", "LP2", "BTN"};
			return positions8[position];
		case 10:
			String[] positions9 = {"SB", "BB", "UTG", "EP2", "MP1", "MP2", "MP3", "LP1", "LP2", "BTN"};
			return positions9[position];
		default:
			return " × 非対応なポシション";
		}
	}
	
	/**
	 * アクションの文字列を英語で返す
	 * 
	 * @param action
	 * 		アクション
	 * @return アクション
	 */
	public String getEnglishStringOfAction(int action) {
		if(action <= 2) {
			String[] NameOfAction = {"non-action", "small blind", "big blind", "fold", "check", "call", "all in", "bet", "raise"};
			int index = action + 6;
			if(index < 0)
				index = 0;
			return NameOfAction[index];
		}
		return Integer.valueOf(action).toString() + "-bet";
	}
	
	/**
	 * アクションの文字列を日本語で返す
	 * 
	 * @param action
	 * 		アクション
	 * @return アクション
	 */
	public String getJapaneseStringOfAction(int action) {
		if(action <= 1) {
			String[] NameOfAction = {"なし", "スモールブラインド", "ビッグブラインド", "フォールド", "チェック",
										"コール", "オールイン", "ベット"};
			int index = action + 6;
			if(index < 0)
				index = 0;
			return NameOfAction[index];
		}
		return "レイズ";
	}
	
	/**
	 * フェーズの文字列を返す
	 * 
	 * @param phase
	 * 		フェーズ
	 * @return フェーズ
	 */
	public String getStringOfPhase(int phase) {
		String[] phaseList = {"プリフロップ", "フロップ", "ターン", "リバー"};
		return phaseList[phase];
	}
	
	/**
	 * レイズできる最大額を返す
	 * 
	 * @param player
	 * 		アクションを行うプレイヤー
	 * @return レイズできる最大額
	 */
	public int getMaxRaiseChip(Player player) {
		int maxRaiseChip = 0;
		
		for(Player p: players)
			if(p.getChip() > maxRaiseChip && p.getLastAction() != -3 && p != player)
				maxRaiseChip = p.getChip();

		return maxRaiseChip;
	}
		
	/**
	 * 審判をセットする
	 * 
	 * @param judge
	 * 		審判
	 */
	public void setJudge(Judge judge) {
		this.judge = judge;
	}
	
	/**
	 * コミュニティカードの枚数をセットする
	 * 
	 * @param numberOfCommunityCard
	 * 		コミュニティカードの枚数
	 */
	public void setNumberOfCommunityCard(int numberOfCommunityCard) {
		this.numberOfCommunityCard = numberOfCommunityCard;
	}
	
	/**
	 * プレイヤ全員の対戦成績を表示する
	 */
	public void showAllRecords() {
		System.out.println(" - 総合成績 -");
		int rank = 1;
		while(rank <= players.size())
		for(Player p: players) {
			if(p.getRank() == rank) {
				System.out.printf("%d位：%s\n", rank, p.getName());
				rank++;
			}
		}
	}
}
