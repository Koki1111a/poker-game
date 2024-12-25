package se.t2045006.card.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import se.t2045006.card.entity.CardDeck;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private ActionPanel actionPanel;
	private JLabel opponentNameLabel;
	private JLabel myNameLabel;
	private JLabel opponentHandLabel;
	private JLabel myHandLabel;
	private JLabel opponentChipLabel;
	private JLabel myChipLabel;
	private JLabel opponentBetChipLabel;
	private JLabel myBetChipLabel;
	private ChipImageLabel opponentChipImageLabel;
	private ChipImageLabel myChipImageLabel;
	private JLabel opponentActionLabel;
	private JLabel potLabel;
	private JLabel stateLabel;
	private JLabel blindLabel;
	private CardLabel[] opponentCardLabels = new CardLabel[2];
	private CardLabel[] myCardLabels = new CardLabel[2];
	private CardLabel[] communityCardLabels = new CardLabel[5];
	private MyActionListener myActionListener;
	private Timer timer;
	//アプリ
	private PokerGame game;
	
	public GamePanel(){
		this.setLayout(null); //レイアウトの設定（nullだとコンポーネントを細かく位置調節できる）
		this.setBackground(Color.yellow); //背景の色
	}
	
	public void startPoker() {
		if(!game.playGame(-1)) {
			actionPanel.update();
		}
	}
	
	public void prepareGame() {
		game = new PokerGame(this, actionPanel.getUser());
		actionPanel.setGame(game);
	}
	
	public JLabel getOpponentChipImageLabel() {
		return opponentChipImageLabel;
	}
	
	public JLabel getMyChipImageLabel() {
		return myChipImageLabel;
	}
	
	public CardLabel[] getMyCardLabels() {
		return myCardLabels;
	}
	
	public CardLabel[] getCommunityCardLabels() {
		return communityCardLabels;
	}
	
	
	public void setCards(CardDeck opponentCards, CardDeck myCards, CardDeck communityCard) {
		for(int i=0; i<2; i++) {
			if(i < opponentCards.size()) {
				opponentCardLabels[i].setCard(opponentCards.seeCard(i+1));
				opponentCardLabels[i].setFaceUpCounter(0);
			}else {
				opponentCardLabels[i].resetCard();
			}
		}
		
		for(int i=0; i<2; i++) {
			if(i < myCards.size()) {
				myCardLabels[i].setCard(myCards.seeCard(i+1));
				opponentCardLabels[i].setFaceUpCounter(0);
			}else {
				myCardLabels[i].resetCard();
			}
		}
		
		for(int i=0; i<5; i++) {
			if(i < communityCard.size()) {
				communityCardLabels[i].setCard(communityCard.seeCard(i+1));
				communityCardLabels[i].setFaceUpCounter(0);
			}else {
				communityCardLabels[i].resetCard();
			}
		}
		this.repaint();
	}
	
	/**
	 * コンポーネントを準備する
	 */
	public void prepareComponents() {
		LineBorder blackBorder = new LineBorder(Color.BLACK, 1, true); //コンポーネントの枠線（黒）
		
		
		/*アクション用のパネル*/
		actionPanel = new ActionPanel();
		actionPanel.prepareComponents();
		actionPanel.setComponentsAndFocus(ActionPanel.ButtonMode.STAY);
		
		/*「相手」のラベル*/
		opponentNameLabel = new JLabel();
		opponentNameLabel.setText("相手");
		opponentNameLabel.setHorizontalAlignment(JLabel.CENTER); //テキストは真ん中に表示
		opponentNameLabel.setBorder(blackBorder);
		opponentNameLabel.setBackground(Color.PINK);
		opponentNameLabel.setOpaque(true);
		
		/*「あなた」のラベル*/
		myNameLabel = new JLabel();
		myNameLabel.setText("あなた");
		myNameLabel.setHorizontalAlignment(JLabel.CENTER); //テキストは真ん中に表示
		myNameLabel.setBorder(blackBorder);
		myNameLabel.setBackground(Color.ORANGE);
		myNameLabel.setOpaque(true);
		
		/*相手のハンドの役のラベル*/
		opponentHandLabel = new JLabel();
		opponentHandLabel.setText("ロイヤルストレートフラッシュ");
		opponentHandLabel.setHorizontalAlignment(JLabel.CENTER); //テキストは真ん中に表示
		
		/*あなたの役のラベル*/
		myHandLabel = new JLabel();
		myHandLabel.setText("ロイヤルストレートフラッシュ");
		myHandLabel.setHorizontalAlignment(JLabel.CENTER); //テキストは真ん中に表示
		
		/*相手のチップ量のラベル*/
		opponentChipLabel = new JLabel();
		opponentChipLabel.setText("4800");
		opponentChipLabel.setHorizontalAlignment(JLabel.CENTER); //テキストは真ん中に表示
		
		/*あなたのチップ量のラベル*/
		myChipLabel = new JLabel();
		myChipLabel.setText("4600");
		myChipLabel.setHorizontalAlignment(JLabel.CENTER); //テキストは真ん中に表示
		
		/*相手の賭けたチップのラベル*/
		opponentBetChipLabel = new JLabel();
		opponentBetChipLabel.setText("200");
		opponentBetChipLabel.setHorizontalAlignment(JLabel.LEFT); //テキストは真ん中に表示
		
		/*あなたの賭けたチップのラベル*/
		myBetChipLabel = new JLabel();
		myBetChipLabel.setText("600");
		myBetChipLabel.setHorizontalAlignment(JLabel.LEFT); //テキストは真ん中に表示
		
		/*相手のアクションを示すラベル*/
		opponentActionLabel = new JLabel();
		opponentActionLabel.setText("相手は200をベットしました．");
		opponentActionLabel.setHorizontalAlignment(JLabel.CENTER); //テキストは真ん中に表示
		
		/*相手のチップのイラストのラベル*/
		opponentChipImageLabel = new ChipImageLabel();
		
		/*あなたのチップのイラストのラベル*/
		myChipImageLabel = new ChipImageLabel();
		
		/*ポットのラベル*/
		potLabel = new JLabel();
		potLabel.setText(" pot : 100");
		potLabel.setHorizontalAlignment(JLabel.LEFT); //テキストは真ん中に表示
		potLabel.setBorder(blackBorder);
		
		/*状態のラベル*/
		stateLabel = new JLabel();
		stateLabel.setText("プリフロップ ( 相手 big blind 20 → あなた 10 to call )");
		stateLabel.setHorizontalAlignment(JLabel.CENTER); //テキストは真ん中に表示
		stateLabel.setBorder(blackBorder);
		stateLabel.setBackground(Color.LIGHT_GRAY);
		stateLabel.setOpaque(true);
		
		/*ブラインドのラベル*/
		blindLabel = new JLabel();
		blindLabel.setText("SB/BB : 25/50");
		blindLabel.setHorizontalAlignment(JLabel.CENTER); //テキストは真ん中に表示
		blindLabel.setBorder(blackBorder); //開発用
		
		/*相手と自分のカードのラベル*/
		for(int i=0; i<2; i++) {
			opponentCardLabels[i] = new CardLabel();
			myCardLabels[i] = new CardLabel();
		}
		
		/*コミュニティカードのラベル*/
		for(int i=0; i<5; i++) {
			communityCardLabels[i] = new CardLabel();
		}
		
		this.resize();
		
		//コンポーネントをこのパネルに貼る
		this.add(actionPanel);
		this.add(opponentNameLabel);
		this.add(myNameLabel);
		this.add(opponentHandLabel);
		this.add(myHandLabel);
		this.add(opponentChipLabel);
		this.add(myChipLabel);
		this.add(opponentBetChipLabel);
		this.add(myBetChipLabel);
		this.add(opponentChipImageLabel);
		this.add(myChipImageLabel);
		this.add(opponentActionLabel);
		this.add(potLabel);
		this.add(stateLabel);
		this.add(blindLabel);
		for(int i=0; i<2; i++) {
			this.add(opponentCardLabels[i]);
			this.add(myCardLabels[i]);
		}
		for(int i=0; i<5; i++) {
			this.add(communityCardLabels[i]);
		}

		myActionListener = new MyActionListener();
		timer = new Timer(5, myActionListener);
		timer.start();
	}
	
	/**
	 * コンポーネントのサイズ変更
	 */
	public void resize() {
		
		setBoundsOfComponent(actionPanel, 0.725, 0.92, 0.55, 0.15, true);
		
		setBoundsOfComponent(opponentNameLabel, 0.1, 0.175, 0.15, 0.1, true);
		setFontSize(opponentNameLabel, 6);
		
		setBoundsOfComponent(myNameLabel, 0.1, 0.85, 0.15, 0.1, true);
		setFontSize(myNameLabel, 6);
		
		setBoundsOfComponent(opponentHandLabel, 0.325, 0.3, 0.25, 0.05, true);
		setFontSize(opponentHandLabel, 4);
		
		setBoundsOfComponent(myHandLabel, 0.325, 0.975, 0.25, 0.05, true);
		setFontSize(myHandLabel, 4);
		
		setBoundsOfComponent(opponentChipLabel, 0.1, 0.25, 0.15, 0.05, true);
		setFontSize(opponentChipLabel, 5);
		
		setBoundsOfComponent(myChipLabel, 0.1, 0.925, 0.15, 0.05, true);
		setFontSize(myChipLabel, 5);
		
		setBoundsOfComponent(opponentBetChipLabel, 0.7, 0.25, 0.15, 0.1, true);
		setFontSize(opponentBetChipLabel, 10);
		
		setBoundsOfComponent(myBetChipLabel, 0.7, 0.775, 0.15, 0.1, true);
		setFontSize(myBetChipLabel, 10);
		
		setBoundsOfComponent(opponentChipImageLabel, 0.585, 0.25, 0.1, 0.1, true);
		
		setBoundsOfComponent(myChipImageLabel, 0.585, 0.775, 0.1, 0.1, true);
		
		setBoundsOfComponent(opponentActionLabel, 0.65, 0.15, 0.45, 0.075, true);
		setFontSize(opponentActionLabel, 5);
		
		setBoundsOfComponent(potLabel, 0.5, 0.41, 0.15, 0.055, true);
		setFontSize(potLabel, 7);
		
		setBoundsOfComponent(stateLabel, 0.71, 0.035, 0.56, 0.05, true);
		setFontSize(stateLabel, 6);
		
		setBoundsOfComponent(blindLabel, 0.125, 0.55, 0.2, 0.075, true);
		setFontSize(blindLabel, 8);
		
		if(opponentCardLabels != null && myCardLabels != null)
			for(int i=0; i<2; i++) {
				setBoundsOfComponent(opponentCardLabels[i], 0.3+i*0.05, 0.175, 0.225, 0.225, true);	
				setBoundsOfComponent(myCardLabels[i], 0.3+i*0.05, 0.85, 0.225, 0.225, true);
			}
		
		if(communityCardLabels != null)
			for(int i=0; i<5; i++) {
				setBoundsOfComponent(communityCardLabels[i], 0.3+i*0.1, 0.55, 0.2, 0.2, true);
			}
		
	}
	
	public void setTextOfComponent(String componentName, String text) {
		switch(componentName) {
		
		case "opponentNameLabel":
			opponentNameLabel.setText(text);
			break;
			
		case "myNameLabel":
			myNameLabel.setText(text);
			break;
		
		case "opponentHandLabel":
			opponentHandLabel.setText(text);
			break;
			
		case "myHandLabel":
			myHandLabel.setText(text);
			break;
			
		case "opponentChipLabel":
			opponentChipLabel.setText(text);
			break;
			
		case "myChipLabel":
			myChipLabel.setText(text);
			break;
			
		case "opponentBetChipLabel":
			opponentBetChipLabel.setText(text);
			break;
			
		case "myBetChipLabel":
			myBetChipLabel.setText(text);
			break;
			
		case "opponentActionLabel":
			opponentActionLabel.setText(text);
			break;
			
		case "potLabel":
			potLabel.setText(text);
			break;
			
		case "stateLabel":
			stateLabel.setText(text);
			break;

		case "blindLabel":
			blindLabel.setText(text);
			break;
		
		default:
			System.err.println("\"" + componentName + "\"というComponentは存在しません．");
			break;
		}
	}
	
	/**
	 * パネルの再描画
	 */
	@Override
	public void repaint() {
		this.resize();
		if(actionPanel != null) actionPanel.repaint();
		super.repaint();
	}
	
	private class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

			for(int i=0; i<myCardLabels.length; i++) {
				if(myCardLabels[i].getCard().getSuit() != -1)
					myCardLabels[i].faceUp();
			}
			for(int i=0; i<opponentCardLabels.length; i++) {
				if(game != null && game.getShowDownFlag())
					opponentCardLabels[i].faceUp();
			}
			for(int i=0; i<communityCardLabels.length; i++) {
				if(communityCardLabels[i].getCard().getSuit() != -1)
					communityCardLabels[i].faceUp();
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
	private void setBoundsOfComponent(JComponent c, double r1, double r2, double r3, double r4, boolean flag) {
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
	private void setFontSize(JComponent c, double r) {
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
