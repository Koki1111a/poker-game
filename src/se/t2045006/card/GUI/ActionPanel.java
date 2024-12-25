package se.t2045006.card.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ActionPanel extends JLabel {
	private static final long serialVersionUID = 1L;
	private JButton foldButton;
	private JButton callButton;
	private JButton selectRaiseButton;
	private JButton returnButton;
	private JButton minusButton;
	private JButton plusButton;
	private JTextField raiseInput;
	private JButton raiseButton;
	private ButtonMode buttonMode;
	private User user;
	private PokerGame game;
	private int chooseOption;
	
	private int lastAction;
	private int minRaiseChip;
	private int maxRaiseChip;
	private int neededChip;
	private int deltaChip;
	
	private ButtonsListener buttonsListener;
	
	public enum ButtonMode {
		//ボタンのモード
		STAY,
		SELECT1,
		SELECT2,
		RAISE
	}
	
	public void update() {
		lastAction = game.getLastAction();
		minRaiseChip = game.getMinRaiseChip();
		maxRaiseChip = game.getMaxRaiseChip();
		if(user.getChip() < maxRaiseChip) maxRaiseChip = user.getChip();
		if(user.getChip() < minRaiseChip) minRaiseChip = user.getChip();
		neededChip = game.getNeededChip();
		chooseOption = minRaiseChip;
	}
	
	public void setGame(PokerGame game) {
		this.game = game;
		deltaChip = game.getBigBlind();
	}
	
	public ActionPanel() {
		this.setLayout(null); //レイアウトの設定（nullだとコンポーネントを細かく位置調節できる）
		this.setBackground(Color.blue); //背景の色
		buttonMode = ButtonMode.STAY;
		user = new User("あなた", this);
	}
	
	public void setBottonMode(ButtonMode b) {
		buttonMode = b;
	}
	
	public JButton getFoldButton() {
		return foldButton;
	}
	
	public JButton getCallButton() {
		return callButton;
	}
	
	public JButton getSelectRaiseButton() {
		return selectRaiseButton;
	}
	
	public JButton getReturnButton() {
		return returnButton;
	}
	
	public JButton getMinusButton() {
		return minusButton;
	}
	
	public JButton getPlusButton() {
		return plusButton;
	}
	
	public JTextField getRaiseInput() {
		return raiseInput;
	}
	
	public JButton getRaiseButton() {
		return raiseButton;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setTextOfComponents(String componentName, String text) {
		switch(componentName) {
		case "foldButton":
			foldButton.setText(text);
			break;
		case "callButton":
			callButton.setText(text);
			break;
		case "selectRaiseButton":
			selectRaiseButton.setText(text);
			break;
		case "returnButton":
			returnButton.setText(text);
			break;
		case "raiseButton":
			raiseButton.setText(text);
			break;
		default:
			System.err.println("\"" + text + "\"というComponentは存在しません．");
			break;
		}
	}
	
	public void setEnableOfComponents(String componentName, String text) {
		switch(componentName) {
		case "foldButton":
			foldButton.setText(text);
			break;
		case "callButton":
			callButton.setText(text);
			break;
		case "selectRaiseButton":
			selectRaiseButton.setText(text);
			break;
		case "returnButton":
			returnButton.setText(text);
			break;
		case "raiseButton":
			raiseButton.setText(text);
			break;
		default:
			System.err.println("\"" + text + "\"というComponentは存在しません．");
			break;
		}
	}
	
	public void prepareComponents() {
		
		/*フォールドボタン*/
		foldButton = new JButton("フォールド"); //ボタン生成
		foldButton.setFocusable(true); //カーソルあり.
		foldButton.setHorizontalAlignment(JButton.CENTER); //文字をボタン内の真ん中に配置
		
		/*コールボタン*/
		callButton = new JButton("チェック(コール 100)"); //ボタン生成
		callButton.setFocusable(true); //カーソルあり.
		callButton.setHorizontalAlignment(JButton.CENTER); //文字をボタン内の真ん中に配置
		
		/*レイズを選択するボタン*/
		selectRaiseButton = new JButton("レイズ(ベット)"); //ボタン生成
		selectRaiseButton.setFocusable(true); //カーソルあり.
		selectRaiseButton.setHorizontalAlignment(JButton.CENTER); //文字をボタン内の真ん中に配置
		
		/*レイズ状態から戻るボタン*/
		returnButton = new JButton("戻る"); //ボタン生成
		returnButton.setFocusable(true); //カーソルあり.
		returnButton.setHorizontalAlignment(JButton.CENTER); //文字をボタン内の真ん中に配置
		
		/*レイズ額を減少させるボタン*/
		minusButton = new JButton("ー"); //ボタン生成
		minusButton.setFocusable(true); //カーソルあり.
		minusButton.setHorizontalAlignment(JButton.CENTER); //文字をボタン内の真ん中に配置
		
		/*レイズ額を増加させるボタン*/
		plusButton = new JButton("＋"); //ボタン生成
		plusButton.setFocusable(true); //カーソルあり.
		plusButton.setHorizontalAlignment(JButton.CENTER); //文字をボタン内の真ん中に配置
		
		/*レイズ額を入力するテキストフィールド*/
		raiseInput = new JTextField();
		raiseInput.setHorizontalAlignment(JTextField.CENTER); //文字をボタン内の真ん中に配置
		
		/*レイズを確定させるボタン*/
		raiseButton = new JButton("レイズ 22200"); //ボタン生成
		raiseButton.setFocusable(true); //カーソルあり.
		raiseButton.setHorizontalAlignment(JButton.CENTER); //文字をボタン内の真ん中に配置
		
		this.setComponentsAndFocus(buttonMode);
		this.resize();
		
		this.add(foldButton);
		this.add(callButton);
		this.add(selectRaiseButton);
		this.add(returnButton);
		this.add(plusButton);
		this.add(minusButton);
		this.add(raiseInput);
		this.add(raiseButton);
		
		buttonsListener = new ButtonsListener();
		
		foldButton.addActionListener(buttonsListener);
		callButton.addActionListener(buttonsListener);
		selectRaiseButton.addActionListener(e->setComponentsAndFocus(ButtonMode.RAISE));
		returnButton.addActionListener(e->setComponentsAndFocus(buttonMode));
		minusButton.addActionListener(buttonsListener);
		plusButton.addActionListener(buttonsListener);
		//raiseInputのリスナはここにかく
		raiseButton.addActionListener(buttonsListener);
	}
	
	public void resize() {
		setBoundsOfComponent(foldButton, 0.175, 0.5, 0.3, 0.9, true);
		setFontSize(foldButton, 30);
		
		setBoundsOfComponent(callButton, 0.5, 0.5, 0.3, 0.9, true);
		setFontSize(callButton, 30);
		
		setBoundsOfComponent(selectRaiseButton, 0.825, 0.5, 0.3, 0.9, true);
		setFontSize(selectRaiseButton, 30);
		
		setBoundsOfComponent(returnButton, 0.115, 0.7, 0.2, 0.5, true);
		setFontSize(returnButton, 30);
		
		setBoundsOfComponent(minusButton, 0.29, 0.65, 0.12, 0.6, true);
		setFontSize(minusButton, 40);
		
		setBoundsOfComponent(plusButton, 0.42, 0.65, 0.12, 0.6, true);
		setFontSize(plusButton, 40);
		
		setBoundsOfComponent(raiseInput, 0.58, 0.65, 0.17, 0.6, true);
		setFontSize(raiseInput, 30);
		
		setBoundsOfComponent(raiseButton, 0.825, 0.5, 0.3, 0.9, true);
		setFontSize(raiseButton, 30);
	}
	
	@Override
	public void repaint() {
		this.resize();
		super.repaint();
	}
	
	/**
	 * アクションモードを切り替える
	 * 
	 * @param a
	 * 		アクションモード
	 */
	public void setComponentsAndFocus(ButtonMode a) {
		switch(a) {
		case STAY:
			foldButton.setText("フォールド");
			foldButton.setEnabled(false);
			foldButton.setVisible(true);
			callButton.setText("チェック/コール");
			callButton.setEnabled(false);
			callButton.setVisible(true);
			selectRaiseButton.setText("ベット/レイズ");
			selectRaiseButton.setEnabled(false);
			selectRaiseButton.setVisible(true);
			returnButton.setEnabled(false);
			returnButton.setVisible(false);
			minusButton.setEnabled(false);
			minusButton.setVisible(false);
			plusButton.setEnabled(false);
			plusButton.setVisible(false);
			raiseInput.setEnabled(false);
			raiseInput.setVisible(false);
			raiseButton.setText("レイズ");
			raiseButton.setEnabled(false);
			raiseButton.setVisible(false);
			break;
		case SELECT1:
			foldButton.setEnabled(true);
			foldButton.setVisible(true);
			callButton.setEnabled(true);
			callButton.setVisible(true);
			selectRaiseButton.setEnabled(true);
			selectRaiseButton.setVisible(true);
			returnButton.setEnabled(false);
			returnButton.setVisible(false);
			minusButton.setEnabled(false);
			minusButton.setVisible(false);
			plusButton.setEnabled(false);
			plusButton.setVisible(false);
			raiseInput.setEnabled(false);
			raiseInput.setVisible(false);
			raiseButton.setEnabled(false);
			raiseButton.setVisible(false);
			break;
		case SELECT2:
			foldButton.setEnabled(true);
			foldButton.setVisible(true);
			callButton.setEnabled(true);
			callButton.setVisible(true);
			selectRaiseButton.setEnabled(false);
			selectRaiseButton.setVisible(false);
			returnButton.setEnabled(false);
			returnButton.setVisible(false);
			minusButton.setEnabled(false);
			minusButton.setVisible(false);
			plusButton.setEnabled(false);
			plusButton.setVisible(false);
			raiseInput.setEnabled(false);
			raiseInput.setVisible(false);
			raiseButton.setEnabled(false);
			raiseButton.setVisible(false);
			break;
		case RAISE:
			foldButton.setEnabled(false);
			foldButton.setVisible(false);
			callButton.setEnabled(false);
			callButton.setVisible(false);
			selectRaiseButton.setEnabled(false);
			selectRaiseButton.setVisible(false);
			returnButton.setEnabled(true);
			returnButton.setVisible(true);
			minusButton.setEnabled(true);
			minusButton.setVisible(true);
			plusButton.setEnabled(true);
			plusButton.setVisible(true);
			raiseInput.setEnabled(true);
			raiseInput.setVisible(true);
			raiseButton.setEnabled(true);
			raiseButton.setVisible(true);
			break;
		}
	}
	
	private class ButtonsListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == foldButton) {
				System.out.println("フォールド");
				user.fold();
				chooseOption = neededChip;
				setComponentsAndFocus(ButtonMode.STAY);
				if(game.playGame(chooseOption)) update();
			}
			if(e.getSource() == callButton) {
				switch(callButton.getText().substring(0, 3)) {
				case "チェッ":
					System.out.println("チェック");
					chooseOption = neededChip;
					user.check();
					break;
				case "コール":
					System.out.println("コール");
					chooseOption = neededChip;
					user.call(neededChip);
					break;
				case "オール":
					System.out.println("オールイン");
					chooseOption = user.getChip();
					user.bet(user.getChip());
					break;
				}
				buttonMode = ButtonMode.STAY;
				setComponentsAndFocus(ButtonMode.STAY);
				if(game.playGame(chooseOption)) update();
			}
			if(e.getSource() == minusButton) {
				chooseOption = chooseOption - deltaChip;
				if(chooseOption < minRaiseChip) chooseOption = minRaiseChip;
				switch(raiseButton.getText().substring(0, 3)) {
				case "ベット":
					raiseButton.setText("ベット" + chooseOption);
					break;
				case "レイズ":
					raiseButton.setText("レイズ" + chooseOption);
				}
			}
			if(e.getSource() == plusButton) {
				chooseOption = chooseOption + deltaChip;
				if(chooseOption > maxRaiseChip) chooseOption = maxRaiseChip;
				switch(raiseButton.getText().substring(0, 3)) {
				case "ベット":
					raiseButton.setText("ベット" + chooseOption);
					break;
				case "レイズ":
					raiseButton.setText("レイズ" + chooseOption);
					break;
				}
			}
			if(e.getSource() == raiseButton) {
				switch(raiseButton.getText().substring(0, 3)) {
				case "ベット":
					System.out.println("ベット");
					user.bet(chooseOption);
					break;
				case "レイズ":
					System.out.println("レイズ");
					user.raise(chooseOption, lastAction+1);
					break;
				}
				buttonMode = ButtonMode.STAY;
				setComponentsAndFocus(ButtonMode.STAY);
				if(game.playGame(chooseOption)) update();
			}
		}
	}
	
	/**
	 * コンポーネントの位置と大きさの変更
	 * 
	 * @param c
	 * 		コンポーネント
	 * @param r1
	 * 		コンポーネントのx位置(左：0，真ん中：0.5，右：1)
	 * @param r2
	 * 		コンポーネントのy位置(上：0，真ん中：0.5，下：1)
	 * @param r3
	 * 		コンポーネントの幅(パネルの幅に対する比率)
	 * @param r4
	 * 		コンポーネントの高さ(パネルの高さに対する比率)
	 * @param flag
	 * 		コンポーネントが枠からはみ出ないように調節するか
	 */
	public void setBoundsOfComponent(JComponent c, double r1, double r2, double r3, double r4, boolean flag) {
		if(c == null) return;
		
		int width = this.getSize().width;
		int height = this.getSize().height;
		c.setSize((int)(r3 * width), (int)(r4 * height));
		
		int cWidth = c.getWidth();
		int cHeight = c.getHeight();
		
		int x = (int)(width * r1 - cWidth / 2.0);
		int y = (int)(height * r2 - cHeight / 2.0);
		if(flag) {
			if(x < 0) x = 0;
			if(x+cWidth > width) x = width - cWidth;
			if(y < 0) y = 0;
			if(y+cHeight > height) y = height - cHeight;
		}
		
		c.setLocation(x, y);
	}
	
	/**
	 * コンポーネントのフォントサイズを変更する
	 * 
	 * @param c
	 * 		コンポーネント
	 * @param r
	 * 		フォントのサイズ(基準：10前後)
	 */
	public void setFontSize(JComponent c, double r) {
		if(c == null) return;
		
		int width = this.getSize().width;
		int height = this.getSize().height;
		int size;
		Font font = c.getFont();
		
		String name = font.getName();
		int style = font.getStyle();
		if(width > height) {
			size = (int)(height * r * 0.005);
		}else {
			size = (int)(width * r * 0.005);
		}
		
		c.setFont(new Font(name, style, size));
	}
}
