package se.t2045006.card.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import se.t2045006.card.entity.CPU;
import se.t2045006.card.entity.Player;
import se.t2045006.card.entity.Pot;

public class PotTest extends TestCase{
	CPU player1;
	CPU player2;
	List<Player> players;
	Pot pot;

	/**
	 * 各テストメソッドの実行の前処理
	 */
	protected void setUp() throws Exception {
		player1 = new CPU("1");
		player2 = new CPU("2");
		players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		pot = new Pot(50, players);
		
	}
	
	/**
	 * 各テストメソッドの実行の後処理
	 */
	protected void tearDown() throws Exception {
		// 後処理は特に無し
	}
	
	public void testGetPot() {
		assertEquals(50, pot.getPot());
	}
	
	public void testRemovePlayer() {
		assertEquals(2, pot.getPlayers().size());
		assertTrue(pot.getPlayers().get(0).equals(player1));
		assertTrue(pot.getPlayers().get(1).equals(player2));
		pot.removePlayer(player1);
		assertEquals(1, pot.getPlayers().size());
		assertTrue(pot.getPlayers().get(0).equals(player2));
	}
}
