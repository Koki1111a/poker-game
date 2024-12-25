package se.t2045006.card.entity;


/**
 * 複数枚のカードから成立する役を見つけるクラス
 *
 * @author Koki Akao
 * @version 17
 * @see CardDeck
 */
public class PokerHand {
	private CardDeck cards = new CardDeck(); //cards = hole1, hole2, community1, community2, ...
	private boolean handCards[] = new boolean[7]; //ハンドに関与するか，handCards = hole1, hole2, community1, community2, ...
	int handValue[] = new int[6]; //handValue = hand, kicker1, kicker2, ... 
	int score;
	
	/**
	 * ホールカードとコミュニティーカードからインスタンスを作成する
	 * 
	 * @param holeCard
	 * 		ホールカード
	 * @param communityCard
	 * 		コミュニティーカード
	 */
	public PokerHand(CardDeck holeCard, CardDeck communityCard) {
		update(holeCard, communityCard);
	}
	
	/**
	 * 
	 * 
	 * @param holeCard
	 * 		ホールカード
	 * @param communityCard
	 * 		コミュニティカード
	 */
	public void update(CardDeck holeCard, CardDeck communityCard){
		
		if(!cards.isEmpty()) cards.clear();
		
		for(int i = 1; i <= holeCard.size(); i++) {
			Card card = holeCard.seeCard(i);
			cards.addCard(card);
		}
		for(int i = 1; i <= communityCard.size(); i++) {
			Card card = communityCard.seeCard(i);
			cards.addCard(card);
		}
		setHand();
	}
	
	/**
	 * ハンドの文字列を返す
	 * 
	 * @return ハンドの文字列
	 */
	public String getString() {
		switch(handValue[0]){
			case 10:
				return "ロイヤルストレートフラッシュ";
			case 9:
				return "ストレートフラッシュ";
			case 8:
				return "フォーカード";
			case 7:
				return "フルハウス";
			case 6:
				return "フラッシュ";
			case 5:
				return "ストレート";
			case 4:
				return "スリーカード";
			case 3:
				return "ツーペア";
			case 2:
				return "ワンペア";
			case 1:
				return "ハイカード";
			default:
				return "";
		}
	}
	
	/**
	 * カードデッキを返す
	 * 
	 * @return カードデッキ
	 */
	public CardDeck getCards() {
		return cards;
	}
	
	/**
	 * ハンドに関与するカードかの情報が入った列を返す
	 * 
	 * @return ハンドに関与するカードかの情報が入った列
	 */
	public boolean[] getHandCards() {
		return handCards;
	}
	
	/**
	 * ハンドの強さを示す列を返す
	 * 
	 * @return ハンドの強さを示す列
	 */
	public int[] getHandValue(){
		return handValue;
	}
	
	/**
	 * ハンドの強さを数値にして返す
	 * 
	 * @return ハンドの強さ
	 */
	public int getScore() {
		score = 0;
		for(int i = 0; i <= 5;i++) {
			score += handValue[i] * Math.pow(15, 5-i); 
		}
		return score;
	}
	
	/**
	 * ハンドに関与するカードかの情報を初期化する
	 */
	public void clearHandCards() {
		for(int index = 0; index <= 6; index++)
			handCards[index] = false;
	}
	
	/**
	 * ハンドの強さを示す列を初期化する
	 */
	public void clearHandValue() {
		for(int index = 0; index <= 5; index++)
			handValue[index] = 0;
	}
	
	
	/**
	 * 各フィールド関数をセットする
	 */
	public void setHand() {
		int suit, number, counter, index, tmp;
		
		//Preflop
		if(cards.size() == 2) {
			//A pair
			clearHandValue();
			for(number = 13; number >= 1; number--) {
				counter = 0;
				clearHandCards();
				for(suit = 0; suit <= 3; suit++) {
					index = cards.searchCard(suit, number) - 1;
					if(index >= 0) {
						counter++;
						handCards[index] = true;
					}
				}
				if(counter == 2) {
					handValue[0] = 2;
					return;
				}
			}
			//High Card
			clearHandValue();
			clearHandCards();
			handValue[0] = 1;
			return;
		}
		
		//Royal Flush -> handValue[0] = 10
		clearHandValue();
		for(suit = 0; suit <= 3; suit++) {
			counter = 0;
			clearHandCards();
			index = cards.searchCard(suit, 1) - 1;
			if(index >= 0) {
				counter++;
				handCards[index] = true;
				for(number = 13; number >= 10; number--) {
					index = cards.searchCard(suit, number) - 1;
					if(index >= 0) {
						counter++;
						handCards[index] = true;
					}
				}
			}
			if(counter == 5) {
				handValue[0] = 10;
				return;
			}
		}
		
		//Straight Flush -> handValue[0] = 9
		clearHandValue();
		for(suit = 0; suit <= 3; suit++) {
			counter = 0;
			clearHandCards();
			for(number = 13; number >= 1; number--) {
				index = cards.searchCard(suit, number) - 1;
				if(index >= 0) {
					counter++;
					handCards[index] = true;
					if(counter == 5) {
						handValue[0] = 9;
						handValue[1] = number + 4;
						return;
					}
				}else {
					counter = 0;
					clearHandCards();
				}
			}
		}
		
		//Quads -> handValue[0] = 8
		clearHandValue();
		for(number = 13; number >= 1; number--) {
			counter = 0;
			clearHandCards();
			for(suit = 0; suit <= 3; suit++) {
				index = cards.searchCard(suit, number) - 1;
				if(index >= 0) {
					counter++;
					handCards[index] = true;
				}
			}
			if(counter == 4) {
				handValue[0] = 8;
				if(number == 1)
					handValue[1] = 14;
				else
					handValue[1] = number;
				for(number = 14; number >= 2; number--) {
					if(number == handValue[1]) 
						continue;
					for(suit = 0; suit <= 3; suit++) {
						if(number == 14)
							index = cards.searchCard(suit, 1) - 1;
						else
							index = cards.searchCard(suit, number) - 1;
						if(index >= 0) {
							handCards[index] = true;
							handValue[2] = number;
							return;
						}
					}
				}
			}
		}

		
		//A Full House -> handValue[0] = 7
		clearHandValue();
		for(number = 14; number >= 2; number--) {
			counter = 0;
			clearHandCards();
			for(suit = 0; suit <= 3; suit++) {
				if(number == 14)
					index = cards.searchCard(suit, 1) - 1;
				else
					index = cards.searchCard(suit, number) - 1;
				if(index >= 0) {
					counter++;
					handCards[index] = true;
				}
			}
			if(counter == 3) {
				handValue[0] = 7;
				handValue[1] = number;
				for(number = 14; number >= 2; number--) {
					counter = 0;
					tmp = -1;
					if(number == handValue[1]) 
						continue;
					for(suit = 0; suit <= 3; suit++) {
						if(number == 14)
							index = cards.searchCard(suit, 1) - 1;
						else
							index = cards.searchCard(suit, number) - 1;
						if(index >= 0) {
							counter++;
							if(counter == 1)
								tmp = index;
							if(counter == 2) {
								handCards[index] = true;
								handCards[tmp] = true;
								handValue[2] = number;
								return;
							}
						}
					}
				}
			}
		}
		
		
		//Flush -> handValue[0] = 6
		clearHandValue();
		for(suit = 0; suit <= 3; suit++) {
			counter = 0;
			clearHandCards();
			for(number = 14; number >= 2; number--) {
				if(number == 14)
					index = cards.searchCard(suit, 1) - 1;
				else
					index = cards.searchCard(suit, number) - 1;
				if(index >= 0) {
					counter++;
					handCards[index] = true;
					handValue[counter] = number;
					if(counter == 5) {
						handValue[0] = 6;
						return;
					}
				}
			}
		}
		
		//Straight -> handValue[0] = 5
		clearHandValue();
		clearHandCards();
		counter = 0;
		for(number = 14; number >= 1; number--) {
			for(suit = 0; suit <= 3; suit++) {
				if(number == 14)
					index = cards.searchCard(suit, 1) - 1;
				else
					index = cards.searchCard(suit, number) - 1;
				if(index >= 0) {
					counter++;
					handCards[index] = true;
					break;
				}
			}
			if(counter == 5) {
				handValue[0] = 5;
				handValue[1] = number + 4;
				return;
			}
			if(suit == 4) {
				counter = 0;
				clearHandCards();
			}
		}
		
		//Three Of A Kind -> handValue[0] = 4
		clearHandValue();
		for(number = 14; number >= 2; number--) {
			counter = 0;
			clearHandCards();
			for(suit = 0; suit <= 3; suit++) {
				if(number == 14)
					index = cards.searchCard(suit, 1) - 1;
				else
					index = cards.searchCard(suit, number) - 1;
				if(index >= 0) {
					counter++;
					handCards[index] = true;
				}
			}
			if(counter == 3) {
				handValue[0] = 4;
				handValue[1] = number;
				counter = 0;
				for(number = 14; number >= 2; number--) {
					if(number == handValue[1]) 
						continue;
					for(suit = 0; suit <= 3; suit++) {
						if(number == 14)
							index = cards.searchCard(suit, 1) - 1;
						else
							index = cards.searchCard(suit, number) - 1;
						if(index >= 0) {
							counter++;
							handCards[index] = true;
							if(counter == 1) {
								handValue[2] = number;
								break;
							}
							if(counter == 2) {
								handValue[3] = number;
								return;
							}
						}
					}
				}
			}
		}
		
		//Two Pair ->handValue[0] = 3
		clearHandValue();
		for(number = 14; number >= 2; number--) {
			counter = 0;
			clearHandCards();
			for(suit = 0; suit <= 3; suit++) {
				if(number == 14)
					index = cards.searchCard(suit, 1) - 1;
				else
					index = cards.searchCard(suit, number) - 1;
				if(index >= 0) {
					counter++;
					handCards[index] = true;
				}
			}
			if(counter == 2) {
				handValue[0] = 3;
				handValue[1] = number;
				for(number = handValue[1] - 1; number >= 2; number--) {
					counter = 0;
					tmp = -1;
					for(suit = 0; suit <= 3; suit++) {
						if(number == 14)
							index = cards.searchCard(suit, 1) - 1;
						else
							index = cards.searchCard(suit, number) - 1;
						if(index >= 0) {
							counter++;
							if(counter == 1)
								tmp = index;
							if(counter == 2) {
								handCards[index] = true;
								handCards[tmp] = true;
								handValue[2] = number;
								for(number = 14; number >= 2; number--) {
									if(number == handValue[1] || number == handValue[2]) 
										continue;
									for(suit = 0; suit <= 3; suit++) {
										if(number == 14)
											index = cards.searchCard(suit, 1) - 1;
										else
											index = cards.searchCard(suit, number) - 1;
										if(index >= 0) {
											handCards[index] = true;
											handValue[3] = number;
											return;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		//A Pair -> handValue[0] = 2
		clearHandValue();
		for(number = 14; number >= 2; number--) {
			counter = 0;
			clearHandCards();
			for(suit = 0; suit <= 3; suit++) {
				if(number == 14)
					index = cards.searchCard(suit, 1) - 1;
				else
					index = cards.searchCard(suit, number) - 1;
				if(index >= 0) {
					counter++;
					handCards[index] = true;
				}
			}
			if(counter == 2) {
				handValue[0] = 2;
				handValue[1] = number;
				counter = 0;
				for(number = 14; number >= 2; number--) {
					if(number == handValue[1]) 
						continue;
					for(suit = 0; suit <= 3; suit++) {
						if(number == 14)
							index = cards.searchCard(suit, 1) - 1;
						else
							index = cards.searchCard(suit, number) - 1;
						if(index >= 0) {
							counter++;
							handCards[index] = true;
							handValue[counter+1] = number;
							if(counter == 3)
								return;
							break;
						}
					}
				}
			}
		}
		
		//High Card -> handValue[0] = 1
		clearHandValue();
		clearHandCards();
		handValue[0] = 1;
		counter = 0;
		for(number = 14; number >= 2; number--) {
			if(number == handValue[1]) 
				continue;
			for(suit = 0; suit <= 3; suit++) {
				if(number == 14)
					index = cards.searchCard(suit, 1) - 1;
				else
					index = cards.searchCard(suit, number) - 1;
				if(index >= 0) {
					counter++;
					handCards[index] = true;
					handValue[counter] = number;
					if(counter == 5)
						return;
					break;
				}
			}
		}
	}
	
	
}
