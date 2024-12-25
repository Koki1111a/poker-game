package se.t2045006.card.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import se.t2045006.card.entity.Card;
import se.t2045006.card.entity.CardDeck;

/**
 * トランプゲームのカードデッキクラスのテストクラス
 */
public class CardDeckTest extends TestCase {
	
	// すべてのテストメソッドで用いるカードのインスタンス
	private Card spadeA, diamondA, clubK, joker;
	
	private List<Card> emptyDeck = new ArrayList<>();
	private List<Card> fourCards = new ArrayList<>();
	private List<Card> onlySpadeA = new ArrayList<>();
	private List<Card> onlyDiamondA = new ArrayList<>();
	private List<Card> onlyClubK = new ArrayList<>();
	private List<Card> onlyJoker = new ArrayList<>();

	/**
	 * 各テストメソッドの実行の前処理
	 */
	protected void setUp() throws Exception {
		spadeA = new Card(0, 1); // スペードA
		clubK = new Card(3, 13); // クラブK
		diamondA = new Card(1, 1); // ダイヤA
		joker = new Card(-1, 0); // ジョーカー

		fourCards.add(spadeA);
		fourCards.add(diamondA);
		fourCards.add(clubK);
		fourCards.add(joker);
		
		onlySpadeA.add(spadeA);
		onlyDiamondA.add(diamondA);
		onlyClubK.add(clubK);
		onlyJoker.add(joker);
		
	}
	
	/**
	 * 各テストメソッドの実行の後処理
	 */
	protected void tearDown() throws Exception {
		// 後処理は特に無し
	}
	
	/**
	 * getAllCards()をテストする．
	 */
	public void testGetAllCards(){
		CardDeck cd = new CardDeck();
		
		cd.getAllCards().add(spadeA);
		assertEquals(onlySpadeA, cd.getAllCards());
		cd.getAllCards().add(diamondA);
		cd.getAllCards().add(clubK);
		cd.getAllCards().add(joker);
		assertEquals(fourCards, cd.getAllCards());
	}
	
	/**
	 * addCard(Card)をテストする．
	 */
	public void testAddCard1() {
		CardDeck onlySpadeA = new CardDeck();
		CardDeck onlyDiamondA = new CardDeck();
		CardDeck onlyClubK = new CardDeck();
		CardDeck onlyJoker = new CardDeck();
		CardDeck cd = new CardDeck();
		onlySpadeA.addCard(spadeA);
		onlyDiamondA.addCard(diamondA);
		onlyClubK.addCard(clubK);
		onlyJoker.addCard(joker);
		cd.addCard(spadeA);
		cd.addCard(diamondA);
		cd.addCard(clubK);
		cd.addCard(joker);
		assertEquals(fourCards, cd.getAllCards());
		assertEquals(this.onlySpadeA, onlySpadeA.getAllCards());
		assertEquals(this.onlyDiamondA, onlyDiamondA.getAllCards());
		assertEquals(this.onlyClubK, onlyClubK.getAllCards());
		assertEquals(this.onlyJoker, onlyJoker.getAllCards());
		assertEquals(fourCards, cd.getAllCards());
	}
	
	/**
	 * takeCard()をテストする．
	 */
	public void testTakeCard1() {
		CardDeck cd = new CardDeck();
		cd.addCard(1, spadeA);
		cd.addCard(2, diamondA);
		cd.addCard(3, clubK);
		cd.addCard(4, joker);
		assertEquals(spadeA, cd.takeCard());
		assertEquals(diamondA, cd.takeCard());
		assertEquals(clubK, cd.takeCard());
		assertEquals(joker, cd.takeCard());
	}
	
	/**
	 * createFullDeck()をテストする．
	 */
	public void testCreateFullDeck() {
		CardDeck cd = new CardDeck();
		cd.createFullDeck();
		for (int index = 0; index <= 51; index++) {// 各絵柄について
				assertEquals(index, cd.takeCard().toIndex());
		}
	}
	
	/**
	 * clear()をテストする．
	 */
	public void testClear() {
		CardDeck cd = new CardDeck();
		cd.createFullDeck();
		cd.clear();
		assertEquals(emptyDeck, cd.getAllCards());
	}
	
	/**
	 * searchCard()をテストする．
	 */
	public void testSearchCard() {
		CardDeck cd = new CardDeck();
		cd.addCard(1, spadeA);
		cd.addCard(2, diamondA);
		cd.addCard(3, clubK);
		cd.addCard(4, joker);
		assertEquals(1, cd.searchCard(0, 1));
		assertEquals(2, cd.searchCard(1, 1));
		assertEquals(3, cd.searchCard(3, 13));
		assertEquals(4, cd.searchCard(-1, 0));
	}
	
	/**
	 * shuffle()をテストする．
	 */
	public void testShuffle() {
		CardDeck cd = new CardDeck();
		cd.createFullDeck();
		cd.shuffle();
		assertEquals(52, cd.size());
		for (int suit = 0; suit <= 3; suit++) {// 各絵柄について
			for (int number = 1; number <= 13; number++) {// 各数字について
				assertFalse(0 == cd.searchCard(suit, number));
			}
		}
	}
	
	/**
	 * addCard(int, Card)をテストする．
	 */
	public void testAddCard2() {
		CardDeck cd = new CardDeck();
		cd.addCard(1, spadeA);
		cd.addCard(2, clubK);
		cd.addCard(2, diamondA);
		cd.addCard(4, joker);
		assertEquals(fourCards, cd.getAllCards());
	}
	
	/**
	 * takeCard(int)をテストする．
	 */
	public void testTakeCard2() {
		CardDeck cd = new CardDeck();
		cd.addCard(1, spadeA);
		cd.addCard(2, diamondA);
		cd.addCard(3, clubK);
		cd.addCard(4, spadeA);
		cd.addCard(5, joker);
		assertEquals(spadeA, cd.takeCard(4));
		assertEquals(diamondA, cd.takeCard(2));
		assertEquals(clubK, cd.takeCard(2));
		assertEquals(joker, cd.takeCard(2));
		assertEquals(spadeA, cd.takeCard(1));
	}
	
	/**
	 * seeCard()をテストする．
	 */
	public void testSeeCard() {
		CardDeck cd = new CardDeck();
		cd.addCard(1, spadeA);
		cd.addCard(2, diamondA);
		cd.addCard(3, clubK);
		cd.addCard(4, spadeA);
		cd.addCard(5, joker);
		assertEquals(spadeA, cd.seeCard(1));
		assertEquals(diamondA, cd.seeCard(2));
		assertEquals(clubK, cd.seeCard(3));
		assertEquals(spadeA, cd.seeCard(4));
		assertEquals(joker, cd.seeCard(5));
	}
	
	/**
	 * isEmpty()をテストする．
	 */
	public void testIsEmpty() {
		assertTrue(emptyDeck.isEmpty());
	}
	
	/**
	 * size()をテストする．
	 */
	public void testSize() {
		CardDeck cd = new CardDeck();
		cd.addCard(1, spadeA);
		cd.addCard(2, diamondA);
		cd.addCard(3, clubK);
		cd.addCard(4, joker);
		assertEquals(4, cd.size());
	}
	
	public void testEquals() {
		CardDeck cd1 = new CardDeck();
		CardDeck cd2 = new CardDeck();
		cd1.addCard(1, spadeA);
		cd1.addCard(2, diamondA);
		cd1.addCard(3, clubK);
		cd1.addCard(4, spadeA);
		cd1.addCard(5, joker);
		cd2.addCard(1, spadeA);
		cd2.addCard(2, diamondA);
		cd2.addCard(3, clubK);
		cd2.addCard(4, spadeA);
		cd2.addCard(5, joker);
		assertTrue(cd1.equals(cd2));
	}

}
