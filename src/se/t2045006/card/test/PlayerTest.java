package se.t2045006.card.test;

import junit.framework.TestCase;
import se.t2045006.card.entity.CPU;

/**
 * プレイヤークラスのテストクラス
 */
public class PlayerTest extends TestCase{
	CPU player1;
	CPU player2;

	/**
	 * 各テストメソッドの実行の前処理
	 */
	protected void setUp() throws Exception {
		player1 = new CPU("1");
		player2 = new CPU("2");
		player1.setChip(20);
		player1.setRank(2);
		player1.setPosition(1);
		
	}
	
	/**
	 * 各テストメソッドの実行の後処理
	 */
	protected void tearDown() throws Exception {
		// 後処理は特に無し
	}
	
	public void testEquals() {
		assertTrue(player1.equals(player1));
		assertFalse(player1.equals(player2));
	}
	
	public void testGetChip() {
		assertEquals(20,player1.getChip());
	}
	
	public void testGetIsDroppedOut() {
		assertFalse(player1.getIsDroppedOut());
		player1.dropOut();
		assertTrue(player1.getIsDroppedOut());
	}
	
	public void testGetRank() {
		assertEquals(2,player1.getRank());
	}
	
	public void testGetPosition() {
		assertEquals(1,player1.getPosition());
	}
	
	public void testPayChip() {
		assertEquals(0,player1.payChip());
	}
}
