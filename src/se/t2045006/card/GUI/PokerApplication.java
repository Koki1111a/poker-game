package se.t2045006.card.GUI;

import se.t2045006.card.entity.CPU;
import se.t2045006.card.entity.User;
import se.t2045006.card.game.Judge;
import se.t2045006.card.game.PokerGame;

public class PokerApplication {
	private User user;	//ユーザ
	private PokerGame game;
	
	public PokerApplication() {
		
	}
	
	/**
	 * アプリを開始する
	 */
	public void startApplication() {
		initialize(); //初期設定して
		doGame();     //ポーカーをする
	}
	
	/**
	 * 初期設定
	 */
	public void initialize() {
		// ゲームを作成
		game = new PokerGame();

		// ユーザを追加
		user = new User("あなた");
		game.addPlayer(user);

		// CPUを1人追加
		game.addPlayer(new CPU("相手"));
		game.setJudge(new Judge());
	}
	
	/**
	 * ポーカーを行う
	 */
	public void doGame() {
		game.startGame();
	}
}

