package se.t2045006.card.GUI;

import se.t2045006.card.entity.CardDeck;
import se.t2045006.card.entity.Player;

/**
 * プレイヤークラスを継承したユーザークラス
 *
 * @author Koki Akao
 * @version 17
 * @see CardDeck
 */
public class User extends Player{
	ActionPanel actionPanel;
	
	/**
	 * 名前を指定してプレイヤを作成する
	 * @param name
	 * 		名前
	 */
	public User(String name, ActionPanel actionPanel) {
		super(name);
		this.actionPanel = actionPanel;
	}

	/**
	 * ユーザが手を選ぶ．
	 */
	@Override
	public int chooseOption(int phase, int lastAction, int minRaiseChip, int maxRaiseChip, int neededChip) {
		
		if(chip - betChip == 0) {
			actionPanel.setBottonMode(ActionPanel.ButtonMode.STAY);
			actionPanel.setComponentsAndFocus(ActionPanel.ButtonMode.STAY);
			return 0;
		}
		
		if(chip < maxRaiseChip)
			maxRaiseChip = chip;
		if(chip < minRaiseChip)
			minRaiseChip = chip;
		
		String word;
		if(phase == 0)
			word = "レイズ";
		else
			word = "ベット";
		
		if(neededChip - betChip == 0) {
			System.out.printf("> ");
			actionPanel.setBottonMode(ActionPanel.ButtonMode.SELECT1);
			actionPanel.setComponentsAndFocus(ActionPanel.ButtonMode.SELECT1);
			actionPanel.getCallButton().setText("チェック");
			actionPanel.getSelectRaiseButton().setText(word);
			actionPanel.getRaiseButton().setText(word + minRaiseChip);
			return 0;
		}
		
		if(neededChip >= chip) {
			System.out.printf("> ");
			actionPanel.setBottonMode(ActionPanel.ButtonMode.SELECT2);
			actionPanel.setComponentsAndFocus(ActionPanel.ButtonMode.SELECT2);
			actionPanel.getCallButton().setText("オールイン" + chip);
			return 0;
		}
		
		if(lastAction == 0 && maxRaiseChip == neededChip) { //チェック不可，レイズ不可
			System.out.printf("> ");
			actionPanel.setBottonMode(ActionPanel.ButtonMode.SELECT2);
			actionPanel.setComponentsAndFocus(ActionPanel.ButtonMode.SELECT2);
			actionPanel.getCallButton().setText("コール" + neededChip);
			return 0;
		}
			
		
		if(neededChip - betChip > 0 && minRaiseChip <= chip) { //チェック不可，レイズ可
			System.out.printf("> ");
			actionPanel.setBottonMode(ActionPanel.ButtonMode.SELECT1);
			actionPanel.setComponentsAndFocus(ActionPanel.ButtonMode.SELECT1);
			actionPanel.getCallButton().setText("コール" + neededChip);
			actionPanel.getSelectRaiseButton().setText("レイズ");
			actionPanel.getRaiseButton().setText(word + minRaiseChip);
			return 0;
		}
		
		
		
		//error
		return -1;
	}
	
	
	
	

}
