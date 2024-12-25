package se.t2045006.card.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	//コンポーネント
	private JLabel titleLabel;
	private JButton startButton;
	//リスナー
	private StartButtonListener startButtonListener;
	
	public TitlePanel(){
		this.setLayout(null); //レイアウトの設定（nullだとコンポーネントを細かく位置調節できる）
		this.setBackground(Color.yellow); //背景の色
	}
	
	public void prepareComponents() {

		/*タイトル*/
		titleLabel = new JLabel(); //ラベル生成
		titleLabel.setText("テキサスホールデム"); //ラベルに文字を記入
		titleLabel.setHorizontalAlignment(JLabel.CENTER); //文字をラベル内の真ん中に配置
		
		/*スタートボタン*/
		startButton = new JButton("ゲームスタート"); //ボタン生成
		startButton.setFocusable(true); //カーソルあり.
		startButton.setHorizontalAlignment(JButton.CENTER); //文字をボタン内の真ん中に配置
		
		resize();
		
		this.add(titleLabel); //ラベルをこのパネルに貼る
		this.add(startButton); //ボタンをこのパネルに貼る
		
		
		startButtonListener = new StartButtonListener();
		startButton.addActionListener(startButtonListener);
		
	}
	
	/**
	 * コンポーネントのサイズ変更
	 */
	public void resize() {
		setBoundsOfComponent(titleLabel, 0.5, 0.1, 1, 0.4, true);
		setFontSize(titleLabel, 20);
		
		setBoundsOfComponent(startButton, 0.5, 0.7, 0.4, 0.2, true);
		setFontSize(startButton, 10);
	}
	
	/**
	 * パネルの再描画
	 */
	@Override
	public void repaint() {
		this.resize();
		super.repaint();
	}
	
	private class StartButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Main.mainWindow.setFrontScreenAndFocus(ScreenMode.GAME);
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
	 * 		フォントのサイズ(推奨：10前後)
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
