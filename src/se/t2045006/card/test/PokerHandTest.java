package se.t2045006.card.test;

import junit.framework.TestCase;
import se.t2045006.card.entity.Card;
import se.t2045006.card.entity.CardDeck;
import se.t2045006.card.entity.PokerHand;

/**
 * 複数枚のカードから成立する役を見つけるクラスのテストクラス
 *
 * @author Koki Akao
 * @version 17
 */
public class PokerHandTest extends TestCase {
	private Card spadeA, spadeK, spadeQ, spadeJ, spade10, spade9;
	private Card diamondA, diamondK;
	private Card heartA, heart2;
	private Card clubA, club3;
	private CardDeck royalFlush = new CardDeck();
	private CardDeck straightFlush = new CardDeck();
	private CardDeck quads = new CardDeck();
	private CardDeck aFullHouse = new CardDeck();
	private CardDeck flush = new CardDeck();
	private CardDeck straight = new CardDeck();
	private CardDeck threeOfAKind = new CardDeck();
	private CardDeck twoPair = new CardDeck();
	private CardDeck APair = new CardDeck();
	private CardDeck highCard = new CardDeck(); 
	private CardDeck holeCard = new CardDeck();
	private PokerHand hand10;
	private PokerHand hand9;
	private PokerHand hand8;
	private PokerHand hand7;
	private PokerHand hand6;
	private PokerHand hand5;
	private PokerHand hand4;
	private PokerHand hand3;
	private PokerHand hand2;
	private PokerHand hand1;
	
	/**
	 * 各テストメソッドの実行の前処理
	 */
	protected void setUp() throws Exception {
		spadeA = new Card(0, 1); // スペードA
		spadeK = new Card(0, 13); //スペードK
		spadeQ = new Card(0, 12); //スペードQ
		spadeJ = new Card(0, 11); //スペードJ
		spade10 = new Card(0, 10); //スペード10
		spade9 = new Card(0, 9); //スペード9
		diamondA = new Card(1, 1); // ダイヤA
		diamondK = new Card(1, 13); // ダイヤK
		heartA = new Card(2, 1); // ハートA
		heart2 = new Card(2, 2); // ハート2
		clubA = new Card(3, 1); // クラブA
		club3 = new Card(3, 3); // クラブ3
		
		royalFlush.addCard(spadeA);
		royalFlush.addCard(spadeK);
		royalFlush.addCard(spadeQ);
		royalFlush.addCard(spadeJ);
		royalFlush.addCard(spade10);

		straightFlush.addCard(spadeK);
		straightFlush.addCard(spadeQ);
		straightFlush.addCard(spadeJ);
		straightFlush.addCard(spade10);
		straightFlush.addCard(spade9);

		quads.addCard(spadeA);
		quads.addCard(diamondA);
		quads.addCard(heartA);
		quads.addCard(clubA);
		quads.addCard(spadeK);
		
		aFullHouse.addCard(spadeA);
		aFullHouse.addCard(diamondA);
		aFullHouse.addCard(heartA);
		aFullHouse.addCard(spadeK);
		aFullHouse.addCard(diamondK);
		
		flush.addCard(spadeA);
		flush.addCard(spadeQ);
		flush.addCard(spadeJ);
		flush.addCard(spade10);
		flush.addCard(spade9);
		
		straight.addCard(diamondA);
		straight.addCard(spadeK);
		straight.addCard(spadeQ);
		straight.addCard(spadeJ);
		straight.addCard(spade10);
		
		threeOfAKind.addCard(spadeA);
		threeOfAKind.addCard(diamondA);
		threeOfAKind.addCard(heartA);
		threeOfAKind.addCard(spadeK);
		threeOfAKind.addCard(spadeQ);
		
		twoPair.addCard(spadeA);
		twoPair.addCard(diamondA);
		twoPair.addCard(spadeK);
		twoPair.addCard(diamondK);
		twoPair.addCard(spadeQ);
		
		APair.addCard(spadeA);
		APair.addCard(spadeK);
		APair.addCard(spadeQ);
		APair.addCard(spadeJ);
		APair.addCard(diamondA);
		
		highCard.addCard(spadeA);
		highCard.addCard(diamondK);
		highCard.addCard(spadeQ);
		highCard.addCard(spadeJ);
		highCard.addCard(spade9);
		
		holeCard.addCard(heart2);
		holeCard.addCard(club3);
		
		hand10 = new PokerHand(holeCard, royalFlush);
		hand9 = new PokerHand(holeCard, straightFlush);
		hand8 = new PokerHand(holeCard, quads);
		hand7 = new PokerHand(holeCard, aFullHouse);
		hand6 = new PokerHand(holeCard, flush);
		hand5 = new PokerHand(holeCard, straight);
		hand4 = new PokerHand(holeCard, threeOfAKind);
		hand3 = new PokerHand(holeCard, twoPair);
		hand2 = new PokerHand(holeCard, APair);
		hand1 = new PokerHand(holeCard, highCard);

	}
	
	/**
	 * 各テストメソッドの実行の後処理
	 */
	protected void tearDown() throws Exception {
		// 後処理は特に無し
	}
	
	
	/**
	 * getString()のテスト
	 */
	public void testGetString() {
		assertEquals("ロイヤルストレートフラッシュ", hand10.getString());
		assertEquals("ストレートフラッシュ", hand9.getString());
		assertEquals("フォーカード", hand8.getString());
		assertEquals("フルハウス", hand7.getString());
		assertEquals("フラッシュ", hand6.getString());
		assertEquals("ストレート", hand5.getString());
		assertEquals("スリーカード", hand4.getString());
		assertEquals("ツーペア", hand3.getString());
		assertEquals("ワンペア", hand2.getString());
		assertEquals("ハイカード", hand1.getString());
	}
	
	public void testGetCards() {
		holeCard.getAllCards().addAll(royalFlush.getAllCards());
		assertEquals(holeCard.getAllCards(), hand10.getCards().getAllCards()); 
	}
	
	/**
	 * getHandCards()のテスト
	 */
	public void testGetHandCards() {
		for(int i = 0; i <= 1; i++) {
			assertEquals(false, hand10.getHandCards()[i]);
			assertEquals(false, hand9.getHandCards()[i]);
			assertEquals(false, hand8.getHandCards()[i]);
			assertEquals(false, hand7.getHandCards()[i]);
			assertEquals(false, hand6.getHandCards()[i]);
			assertEquals(false, hand5.getHandCards()[i]);
			assertEquals(false, hand4.getHandCards()[i]);
			assertEquals(false, hand3.getHandCards()[i]);
			assertEquals(false, hand2.getHandCards()[i]);
			assertEquals(false, hand1.getHandCards()[i]);
		}
		for(int i = 2; i <= 6; i++) {
			assertEquals(true, hand10.getHandCards()[i]);
			assertEquals(true, hand9.getHandCards()[i]);
			assertEquals(true, hand8.getHandCards()[i]);
			assertEquals(true, hand7.getHandCards()[i]);
			assertEquals(true, hand6.getHandCards()[i]);
			assertEquals(true, hand5.getHandCards()[i]);
			assertEquals(true, hand4.getHandCards()[i]);
			assertEquals(true, hand3.getHandCards()[i]);
			assertEquals(true, hand2.getHandCards()[i]);
			assertEquals(true, hand1.getHandCards()[i]);
		}
		
	}
	
	/**
	 * getHandValueのテストメソッド
	 */
	public void testGetHandValue() {
		assertEquals(10, hand10.getHandValue()[0]);
		assertEquals(9, hand9.getHandValue()[0]);
		assertEquals(8, hand8.getHandValue()[0]);
		assertEquals(7, hand7.getHandValue()[0]);
		assertEquals(6, hand6.getHandValue()[0]);
		assertEquals(5, hand5.getHandValue()[0]);
		assertEquals(4, hand4.getHandValue()[0]);
		assertEquals(3, hand3.getHandValue()[0]);
		assertEquals(2, hand2.getHandValue()[0]);
		assertEquals(1, hand1.getHandValue()[0]);
		
		assertEquals(0, hand10.getHandValue()[0]);
		assertEquals(13, hand9.getHandValue()[1]);
		assertEquals(14, hand8.getHandValue()[1]);
		assertEquals(14, hand7.getHandValue()[1]);
		assertEquals(14, hand6.getHandValue()[1]);
		assertEquals(14, hand5.getHandValue()[1]);
		assertEquals(14, hand4.getHandValue()[1]);
		assertEquals(14, hand3.getHandValue()[1]);
		assertEquals(14, hand2.getHandValue()[1]);
		assertEquals(14, hand1.getHandValue()[1]);
		
		assertEquals(0, hand10.getHandValue()[2]);
		assertEquals(0, hand9.getHandValue()[2]);
		assertEquals(13, hand8.getHandValue()[2]);
		assertEquals(13, hand7.getHandValue()[2]);
		assertEquals(12, hand6.getHandValue()[2]);
		assertEquals(0, hand5.getHandValue()[2]);
		assertEquals(13, hand4.getHandValue()[2]);
		assertEquals(13, hand3.getHandValue()[2]);
		assertEquals(13, hand2.getHandValue()[2]);
		assertEquals(13, hand1.getHandValue()[2]);
		
		assertEquals(0, hand10.getHandValue()[3]);
		assertEquals(0, hand9.getHandValue()[3]);
		assertEquals(0, hand8.getHandValue()[3]);
		assertEquals(0, hand7.getHandValue()[3]);
		assertEquals(11, hand6.getHandValue()[3]);
		assertEquals(0, hand5.getHandValue()[3]);
		assertEquals(12, hand4.getHandValue()[3]);
		assertEquals(12, hand3.getHandValue()[3]);
		assertEquals(12, hand2.getHandValue()[3]);
		assertEquals(12, hand1.getHandValue()[3]);
		
		assertEquals(0, hand10.getHandValue()[4]);
		assertEquals(0, hand9.getHandValue()[4]);
		assertEquals(0, hand8.getHandValue()[4]);
		assertEquals(0, hand7.getHandValue()[4]);
		assertEquals(10, hand6.getHandValue()[4]);
		assertEquals(0, hand5.getHandValue()[4]);
		assertEquals(0, hand4.getHandValue()[4]);
		assertEquals(0, hand3.getHandValue()[4]);
		assertEquals(11, hand2.getHandValue()[4]);
		assertEquals(11, hand1.getHandValue()[4]);
		
		assertEquals(0, hand10.getHandValue()[5]);
		assertEquals(0, hand9.getHandValue()[5]);
		assertEquals(0, hand8.getHandValue()[5]);
		assertEquals(0, hand7.getHandValue()[5]);
		assertEquals(9, hand6.getHandValue()[5]);
		assertEquals(0, hand5.getHandValue()[5]);
		assertEquals(0, hand4.getHandValue()[5]);
		assertEquals(0, hand3.getHandValue()[5]);
		assertEquals(0, hand2.getHandValue()[5]);
		assertEquals(9, hand1.getHandValue()[5]);
	}

}
