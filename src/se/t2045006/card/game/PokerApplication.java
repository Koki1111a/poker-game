package se.t2045006.card.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import se.t2045006.card.entity.CPU;
import se.t2045006.card.entity.User;
import se.t2045006.card.util.KeyBoard;

public class PokerApplication {
	private User user;	//ユーザ
	private static final String logFile = "poker.log"; //ログファイル
	private PokerGame game;
	
	public PokerApplication() {
		
	}
	
	/**
	 * アプリを開始する
	 */
	public void startApplication() {
		initialize(); //初期設定して
		doGame();     //ポーカーして
		storeRecord(); //ファイルに書き込む

	}
	
	/**
	 * 初期設定
	 */
	public void initialize() {
		// 名前入力
		System.out.println("■ポーカーアプリを起動します．");
		System.out.print("名前を入力してください：");
		String name = KeyBoard.inputString();

		// プレイヤ数を入力
		int n;
		do {
			System.out.print("何人で遊びますか(2以上の整数)：");
			n = KeyBoard.inputNumber();
		} while (n < 2);

		// ゲームを作成
		game = new PokerGame();

		// ユーザを追加
		user = new User(name);
		game.addPlayer(user);

		// CPUを追加 (n-1)人分
		for (int i = 1; i < n; i++) {
			game.addPlayer(new CPU("CPU" + i));
		}
		game.setJudge(new Judge());
	}
	
	/**
	 * ポーカーを行う
	 */
	public void doGame() {
		game.startGame();
	}
	
	/**
	 * 成績を記録する
	 */
	public void storeRecord() {
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
			Calendar cl = Calendar.getInstance();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(cl.getTime());
			int r = user.getRank();
			//ログのフォーマット
			String out = String.format("%s,%s,%d,%d",
					date, //日付　yyyy-MM-dd HH:mm:ss
					user.getName(), //ユーザ名
					game.getPlayers().size(), //プレイヤ数
					r //順位
					);

			System.out.println();
			System.out.printf("■%sさんの対戦成績をファイルに追記します．\n",
					user.getName());
			System.out.println(out);
			//ログを書き出す
			pw.println(out);
			pw.close();
			System.out.println("完了しました．");
		} catch (IOException e) {
			System.out.println(logFile + "：ファイル書き込み中，IO例外です．");
			e.printStackTrace();
		}

	}
}
