package se.t2045006.card.entity;

/**
 * トランプのカードデッキクラス
 *
 * @author Koki Akao
 * @version 17
 * @see CardDeck
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * プレイヤークラスを継承したCPUのクラス
 *
 * @author Koki Akao
 * @version 17
 */
public class CardDeck {
	private List<Card> cards = new ArrayList<>();	//デッキ
	
	/**
	 * デフォルトコンストラクタ．
	 */
	public CardDeck() {
		
	}
	
	/**
	 * フルデッキにする
	 */
	public void createFullDeck() {
		if(!isEmpty()) 
			clear();
		for(int index = 0; index <= 51; index++) {
			int suit = index / 13;
			int number = index - suit * 13 + 1;
			Card card = new Card(suit, number);
			addCard(card);
		}
	}
	
	/**
	 * 空デッキにする
	 */
	public void clear() {
		cards.clear();
	}
	
	/**
	 * シャッフルする
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	/**
	 * デッキの一番最後に，任意のカードcardを追加する
	 * 
	 * @param card
	 * 			カード
	 */
	public void addCard(Card card) {
		cards.add(card);
	}
	
	/**
	 * デッキの i 番目に，任意のカードcardを追加する
	 * 
	 * @param i
	 * 			何番目に追加するか
	 * @param card
	 *			カード
	 */
	public void addCard(int i, Card card) {
		int index = i-1;
		cards.add(index, card);
	}
	
	/**
	 * デッキの一番上の（１番目の）カードを取る
	 * 
	 * @return デッキの一番上のカード
	 */
	public Card takeCard() {
		Card card = cards.get(0);
		cards.remove(0);
		return card;
	}
	
	/**
	 * デッキの i 番目から，カードを抜き取る
	 * 
	 * @param i
	 * @return
	 */
	public Card takeCard(int i) {
		int index = i-1;
		Card card = cards.get(index);
		cards.remove(index);
		return card;
	}
	
	/**
	 * デッキのi番目にあるカードを見る
	 * 
	 * @param i
	 * @return
	 */
	public Card seeCard(int i) {
		int index = i-1; 
		return cards.get(index);
	}
	
	/**
	 * 特定のカードがデッキの何番目にあるか
	 * 
	 * @param suit
	 * 			絵柄 (0:スペード，1:ダイヤ，2:ハート，3:クラブ, -1:ジョーカー)
	 * @param number
	 * 			数字 (1-13, 0)
	 */
	public int searchCard(int suit, int number) {
		for(int index = 0; index < size(); index++) {
			Card card = seeCard(index+1);
			if(card.getSuit() == suit && card.getNumber() == number)
				return index + 1;
		}
		return 0;
	}
	
	/**
	 * デッキが空かどうか
	 * 
	 * @return デッキが空か
	 */
	public boolean isEmpty() {
		return cards.size() == 0;
	}
	
	/**
	 * デッキにあるカード枚数を返す
	 * 
	 * @return デッキのカード枚数
	 */
	public int size() {
		if(!cards.isEmpty())
			return cards.size();
		else
			return 0;
	}
	
	/**
	 * すべてのカードを画面に表示する
	 */
	public void showAllCards() {
		for(int index = 0; index < size(); index++) {
			Card card = cards.get(index);
			int suit = card.getSuit();
			int number = card.getNumber();
			System.out.print(index+1 + "番目のカード：");
			System.out.println(Card.getString(suit, number));
		}
	}
	
	/**
	 * デッキにある全てのカードを返す
	 * 
	 * @return デッキ
	 */
	public List<Card> getAllCards(){
		return cards;
	}
	
	/**
	 * 特定のデッキと同じ内容かつ順番かを返す
	 * 
	 * @param cardDeck
	 * @return 同じデッキか
	 */
	public boolean equals(CardDeck cardDeck) {
		if(cards.size() == cardDeck.size()) {
			for(int index = 0; index < cards.size(); index++) {
				if(!cards.get(index).toString().equals( cards.get(index).toString() ) )
					return false;
			}
			return true;
		}else
			return false;
	}
}
