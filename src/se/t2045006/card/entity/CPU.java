package se.t2045006.card.entity;

import java.util.Random;

/**
 * プレイヤークラスを継承したCPUのクラス
 *
 * @author Koki Akao
 * @version 17
 */
public class CPU extends Player{

	/**
	 * 名前を指定してプレイヤを作成する
	 * @param name
	 * 		名前
	 */
	public CPU(String name) {
		super(name);
	}

	/**
	 * 乱数を用いて手を選ぶ．
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
		Random rand = new Random();
		if(phase == 0)
			word = "レイズ";
		else
			word = "ベット";
		
		if(neededChip - betChip == 0) {
			action = rand.nextInt(2) + 1;
			switch(action) {
			case 1:
				check();
				System.out.println(name +" はチェックしました．");
				return neededChip;
			case 2:
				int value;
				if(maxRaiseChip > minRaiseChip) {
					value = rand.nextInt(maxRaiseChip - minRaiseChip) + minRaiseChip;
					if(value > minRaiseChip)
						value = rand.nextInt(value - minRaiseChip) + minRaiseChip;
				}else
					value = minRaiseChip;
				if(phase ==0)
					raise(value, 2);
				else
					bet(value);
				if(value == chip)
					System.out.printf("%s は $%d にオールインしました．\n", name, value);
				else
					System.out.printf("%s は $%d に" + word +"しました．\n", name, value);
				return value;
			case 3:
				fold();
				System.out.println(name + " はフォールドしました．");
				return neededChip;
			}
		}
		
		if(neededChip >= chip) {
			action = rand.nextInt(2) + 1;
			switch(action) {
			case 1:
				call(neededChip);
				System.out.printf("%s は $%d にオールインしました．\n", name, chip);
				return neededChip;
			case 2:
				fold();
				System.out.println(name + " はフォールドしました．");
				return neededChip;
			}
		}
		
		if(lastAction == 0 && maxRaiseChip == neededChip) { //チェック不可，レイズ不可
			action = rand.nextInt(2) + 1;
			switch(action) {
			case 1:
				call(neededChip);
				System.out.printf("%s は $%d にコールしました．\n", name, neededChip);
				return neededChip;
			case 2:
				fold();
				System.out.println(name + " はフォールドしました．");
				return neededChip;
			}
		}
		
		if(neededChip - betChip > 0 && minRaiseChip <= chip) {
			action = rand.nextInt(3) + 1;
			switch(action) {
			case 1:
				call(neededChip);
				System.out.printf("%s は $%d にコールしました．\n", name, neededChip);
				return neededChip;
			case 2:
				int value;
				if(maxRaiseChip > minRaiseChip) {
					value = rand.nextInt(maxRaiseChip - minRaiseChip) + minRaiseChip;
					if(value > minRaiseChip)
						value = rand.nextInt(value - minRaiseChip) + minRaiseChip;
				}else
					value = maxRaiseChip;
				if(lastAction >= 1)
					raise(value, lastAction+1);
				else
					raise(value, 2);
				if(value == chip)
					System.out.printf("%s は $%d にオールインしました．\n", name, value);
				else
					System.out.printf("%s は $%d にレイズしました．\n", name, value);
				return value;
			case 3:
				fold();
				System.out.println(name + " はフォールドしました．");
				return neededChip;
			}
		}
		
		
		
		//error
		return -1;
	}
	
	
	
	

}
