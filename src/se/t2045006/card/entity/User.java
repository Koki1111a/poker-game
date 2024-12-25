package se.t2045006.card.entity;

import se.t2045006.card.util.KeyBoard;

/**
 * プレイヤークラスを継承したユーザークラス
 *
 * @author Koki Akao
 * @version 17
 * @see CardDeck
 */
public class User extends Player{
	
	/**
	 * 名前を指定してプレイヤを作成する
	 * @param name
	 * 		名前
	 */
	public User(String name) {
		super(name);
	}

	/**
	 * ユーザが手を選ぶ．
	 */
	@Override
	public int chooseOption(int phase, int lastAction, int minRaiseChip, int maxRaiseChip, int neededChip) {
		
		if(chip - betChip == 0)
			return neededChip;
		if(chip < maxRaiseChip)
			maxRaiseChip = chip;
		if(chip < minRaiseChip)
			minRaiseChip = chip;
		
		int action;
		String word;
		if(phase == 0)
			word = "レイズ";
		else
			word = "ベット";
		
		if(neededChip - betChip == 0) {
			System.out.printf(">[チェック...1, " + word + "...2, フォールド...3] ");
			action = KeyBoard.inputNumber();
			while(action < 1 || action > 3) {
				System.out.printf(">");
				System.err.println("対象範囲外です．");
				System.out.printf(">[チェック...1, \" + word + \"...2, フォールド...3] ");
				action = KeyBoard.inputNumber();
			}
			switch(action) {
			case 1:
				check();
				System.out.println(">あなた はチェックしました．");
				return neededChip;
			case 2:
				int value;
				System.out.printf(">" + word + "する額を入力してください．(min：%d, max：%d) ", minRaiseChip, maxRaiseChip);
				value =  KeyBoard.inputNumber();
				while(value < minRaiseChip || value > chip) {
					System.out.printf(">");
					System.err.println("対象範囲外です．");
					System.out.printf(">%sする額を入力してください．(min：%d, max：%d) ", word, minRaiseChip, maxRaiseChip);
					value = KeyBoard.inputNumber();
				}
				if(phase == 0)
					raise(value, 2);
				else
					bet(value);
				if(value == chip)
					System.out.printf(">あなた は $%d にオールインしました．\n", value);
				else
				System.out.printf(">あなた は $%d に" + word +"しました．\n", value);
				return value;
			case 3:
				fold();
				System.out.println(">あなた はフォールドしました．");
				return neededChip;
			}
		}
		
		if(neededChip >= chip) {
			System.out.printf(">[オールイン...1, フォールド...2] ");
			action = KeyBoard.inputNumber();
			while(action < 1 || action > 2) {
				System.out.printf(">");
				System.err.println("対象範囲外です．");
				System.out.printf(">[オールイン...1, フォールド...2] ");
				action = KeyBoard.inputNumber();
			}
			switch(action) {
			case 1:
				call(neededChip);
				System.out.printf(">あなた は $%d にオールインしました．\n", chip);
				return neededChip;
			case 2:
				fold();
				System.out.println(">あなた はフォールドしました．");
				return neededChip;
			}
		}
		
		if(lastAction == 0 && maxRaiseChip == neededChip) { //チェック不可，レイズ不可
			System.out.printf(">[コール...1, フォールド...2] ");
			action = KeyBoard.inputNumber();
			while(action < 1 || action > 2) {
				System.out.printf(">");
				System.err.println("対象範囲外です．");
				System.out.printf(">[コール...1, フォールド...2] ");
				action = KeyBoard.inputNumber();
			}
			switch(action) {
			case 1:
				call(neededChip);
				System.out.printf(">あなた は $%d にコールしました．\n", neededChip);
				return neededChip;
			case 2:
				fold();
				System.out.println(">あなた はフォールドしました．");
				return neededChip;
			}
		}
			
		
		if(neededChip - betChip > 0 && minRaiseChip <= chip) { //チェック不可，レイズ可
			System.out.printf(">[コール...1, レイズ...2, フォールド...3] ");
			action = KeyBoard.inputNumber();
			while(action < 1 || action > 3) {
				System.out.printf(">");
				System.err.println("対象範囲外です．");
				System.out.printf(">[コール...1, レイズ...2, フォールド...3] ");
				action = KeyBoard.inputNumber();
			}
			switch(action) {
			case 1:
				call(neededChip);
				System.out.printf(">あなた は $%d にコールしました．\n", neededChip);
				return neededChip;
			case 2:
				int value;
				System.out.printf(">レイズする額を入力してください．(min：%d, max：%d) ", minRaiseChip, maxRaiseChip);
				value =  KeyBoard.inputNumber();
				while(value < minRaiseChip || value > chip) {
					System.out.printf(">");
					System.err.println("対象範囲外です．");
					System.out.printf(">レイズする額を入力してください．(min：%d, max：%d) ", minRaiseChip, maxRaiseChip);
					value = KeyBoard.inputNumber();
				}
				if(lastAction >= 1)
					raise(value, lastAction+1);
				else
					raise(value, 2);
				if(value == chip)
					System.out.printf(">あなた は $%d にオールインしました．\n", value);
				else
				System.out.printf(">あなた は $%d にレイズしました．\n", value);
				return value;
			case 3:
				fold();
				System.out.println(">あなた はフォールドしました．");
				return neededChip;
			}
		}
		
		
		
		//error
		return -1;
	}
	
	
	
	

}
