package se.t2045006.card.test;

import junit.framework.TestCase;
import se.t2045006.card.entity.CPU;
import se.t2045006.card.game.Judge;
import se.t2045006.card.game.PokerGame;

public class PokerGameTest extends TestCase{
	private PokerGame game;
	Judge judge;
	CPU player1;
	CPU player2;
	/**
	 * 各テストメソッドの実行の前処理
	 */
	protected void setUp() throws Exception {
		game = new PokerGame();
		judge = new Judge();
		player1 = new CPU("1");
		player2 = new CPU("2");
		game.addPlayer(player1);
		game.addPlayer(player2);
		game.setJudge(judge);
		game.initializeChip();
		
	}
	
	/**
	 * 各テストメソッドの実行の後処理
	 */
	protected void tearDown() throws Exception {
		// 後処理は特に無し
	}
	
	public void testGetEnglishStringOfAction() {
		assertEquals("non-action", game.getEnglishStringOfAction(-6));
		assertEquals("small blind", game.getEnglishStringOfAction(-5));
		assertEquals("big blind", game.getEnglishStringOfAction(-4));
		assertEquals("fold", game.getEnglishStringOfAction(-3));
		assertEquals("check", game.getEnglishStringOfAction(-2));
		assertEquals("call", game.getEnglishStringOfAction(-1));
		assertEquals("all in", game.getEnglishStringOfAction(0));
		assertEquals("bet", game.getEnglishStringOfAction(1));
		assertEquals("raise", game.getEnglishStringOfAction(2));
		assertEquals("5-bet", game.getEnglishStringOfAction(5));
	}
	
	
	public void testGetJapanesehStringOfAction() {
		assertEquals("なし", game.getJapaneseStringOfAction(-6));
		assertEquals("スモールブラインド", game.getJapaneseStringOfAction(-5));
		assertEquals("ビッグブラインド", game.getJapaneseStringOfAction(-4));
		assertEquals("フォールド", game.getJapaneseStringOfAction(-3));
		assertEquals("チェック", game.getJapaneseStringOfAction(-2));
		assertEquals("コール", game.getJapaneseStringOfAction(-1));
		assertEquals("オールイン", game.getJapaneseStringOfAction(0));
		assertEquals("ベット", game.getJapaneseStringOfAction(1));
		assertEquals("レイズ", game.getJapaneseStringOfAction(2));
		assertEquals("レイズ", game.getJapaneseStringOfAction(5));
	}
	
	public void testGetJudje() {
		assertEquals(judge, game.getJudge());
	}
	
	public void testGetMaxRaiseChip() {
		assertEquals(2000, game.getMaxRaiseChip(player1));
	}
	
	public void testGetNumberOfSurviver() {
		assertEquals(2, game.getNumberOfSurviver());
	}
	
	public void testGetPhase() {
		assertEquals(0, game.getPhase());
	}
	
	public void testGetStringOfPhase() {
		assertEquals("プリフロップ", game.getStringOfPhase(0));
		assertEquals("フロップ", game.getStringOfPhase(1));
		assertEquals("ターン", game.getStringOfPhase(2));
		assertEquals("リバー", game.getStringOfPhase(3));
	}
	
	public void testGetStringOfPosition() {
		assertEquals("SB", game.getStringOfPosition(0));
		assertEquals("BB", game.getStringOfPosition(1));
	}
	
}
