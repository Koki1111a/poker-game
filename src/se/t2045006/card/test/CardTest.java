package se.t2045006.card.test;

import junit.framework.TestCase;
import se.t2045006.card.entity.Card;

/**
 * トランプゲームのカードクラスのテストクラス
 */
public class CardTest extends TestCase {
	// すべてのテストメソッドで用いるカードのインスタンス
	private Card spadeA, diamond10, heartQ, clubK, spade5, diamondA, heartA, clubA, joker;

	/**
	 * 各テストメソッドの実行の前処理
	 */
	protected void setUp() throws Exception {
		// いくつかテスト用のカードインスタンスを作っておく．
		spadeA = new Card(0, 1); // スペードA
		diamond10 = new Card(1, 10); // ダイヤ10
		heartQ = new Card(2, 12); // ハートQ
		clubK = new Card(3, 13); // クラブK

		// 他にも作っておく．
		spade5 = new Card(0, 5); // スペード5
		diamondA = new Card(1, 1); // ダイヤA
		heartA = new Card(2, 1); // ハートA
		clubA = new Card(3, 1); // クラブA
		joker = new Card(-1, 0); // ジョーカー

	}

	/**
	 * 各テストメソッドの実行の後処理
	 */
	protected void tearDown() throws Exception {
		// 後処理は特に無し
	}

	/**
	 * getSuit()をテストする．
	 */
	public void testGetSuit() {
		assertEquals(0, spadeA.getSuit()); // spadeAの絵柄はスペード(=0)のはず．
		assertEquals(1, diamond10.getSuit()); // diamond10の絵柄はダイヤ(=1)のはず．
		assertEquals(2, heartQ.getSuit()); // heartQの絵柄はハート(=2)のはず．
		assertEquals(3, clubK.getSuit()); // clubKの絵柄はクラブ(=3)のはず．

		assertEquals(-1, joker.getSuit()); // ジョーカーは絵柄は-1である．

		// ↓のように書くと，全てのカードの絵柄をチェックできるが，慎重に
		// ロジックを考えないとテストコードにバグを混入させることになる．
		for (int suit = 0; suit <= 3; suit++) {// 各絵柄について
			for (int number = 1; number <= 13; number++) {// 各数字について
				Card c = new Card(suit, number); // カードを作りgetSuit()をテストする．
				assertEquals("絵柄のチェック:", suit, c.getSuit());
			}
		}
	}

	/**
	 * getNumber()をテストする．
	 */
	public void testGetNumber() {
		// getNumber()のテスト
		assertEquals(1, spadeA.getNumber()); // spadeAの数字は1のはず．
		assertEquals(10, diamond10.getNumber()); // diamond10の数字は1のはず．
		assertEquals(12, heartQ.getNumber());// heartQの数字は12のはず．
		assertEquals(13, clubK.getNumber()); // clubKの数字は13のはず．

		assertEquals(0, joker.getNumber()); // ジョーカーの数字は0である．

		// 全テスト．一般には下記のように全てのケースをテストできるとは限らない
		for (int suit = 0; suit <= 3; suit++) {// 各絵柄について
			for (int number = 1; number <= 13; number++) {// 各数字について
				Card c = new Card(suit, number); // カードを作りgetSuit()をテストする．
				assertEquals("数字のチェック:", number, c.getNumber());
			}
		}

	}

	/**
	 * toIndex()をテストする．
	 */
	public void testToIndex() {
		// 各インスタンスについて，整数インデクス表現を検査する．
		assertEquals(0, spadeA.toIndex());
		assertEquals(22, diamond10.toIndex());
		assertEquals(37, heartQ.toIndex());
		assertEquals(51, clubK.toIndex());
		assertEquals(4, spade5.toIndex());
		assertEquals(13, diamondA.toIndex());
		assertEquals(26, heartA.toIndex());
		assertEquals(39, clubA.toIndex());
		assertEquals(-1, joker.toIndex());
	}

	/**
	 * toString()をテストする．
	 */
	public void testToString() {
		// 各インスタンスについて，文字列表現を検査する．
		assertEquals("♠A", spadeA.toString());
		assertEquals("♢10", diamond10.toString());
		assertEquals("♡Q", heartQ.toString());
		assertEquals("♣K", clubK.toString());
		assertEquals("♠5", spade5.toString());
		assertEquals("♢A", diamondA.toString());
		assertEquals("♡A", heartA.toString());
		assertEquals("♣A", clubA.toString());
		assertEquals("ジョーカー", joker.toString());
	}

	/**
	 * getIndex()のテスト
	 */
	public void testGetIndex() {
		// いくつかのインデクス表現を検査する．

		assertEquals(0, Card.getIndex(0, 1)); // スペードAのインデクス
		assertEquals(22, Card.getIndex(1, 10)); // ダイヤ10のインデクス
		assertEquals(37, Card.getIndex(2, 12)); // ハートQのインデクス
		assertEquals(51, Card.getIndex(3, 13)); // クラブKのインデクス
		assertEquals(-1, Card.getIndex(-1, 0)); // ジョーカーのインデクス
	}

	/**
	 * getString()のテスト
	 */
	public void testGetString() {
		assertEquals("♠A", Card.getString(0, 1)); // スペードAのインデクス
		assertEquals("♢10", Card.getString(1, 10)); // ダイヤ10のインデクス
		assertEquals("♡Q", Card.getString(2, 12)); // ハートQのインデクス
		assertEquals("♣K", Card.getString(3, 13)); // クラブKのインデクス
		assertEquals("ジョーカー", Card.getString(-1, 0)); // ジョーカーのインデクス
	}

}