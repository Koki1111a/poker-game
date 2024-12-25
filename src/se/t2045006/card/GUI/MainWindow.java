package se.t2045006.card.GUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private ScreenMode screenMode = ScreenMode.TITLE;
	private CardLayout layout = new CardLayout();
	private TitlePanel titlePanel;
	private GamePanel gamePanel;
	private final int WIDTH = 700; //フレームの幅
	private final int HEIGHT = 400; //フレームの高さ
	
	public MainWindow(){
		this.setTitle("PokerGame");	//タイトル
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true); //画面サイズの変更について(flse：禁止，true：許可)
		//this.getContentPane().setBackground(Color.green); //背景の色
		this.setLayout(layout);	//画面切り替えの設定
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT)); //画面サイズ
		this.pack(); //自動サイズ調節
		this.setLocationRelativeTo(null); //起動時のスクリーンの位置を中央に置く
		
		this.addComponentListener(new ComponentAdapter_JFrame());
		
	}
	
	public void preparePanels() {
		titlePanel = new TitlePanel();
		this.add(titlePanel, "タイトル画面");
		gamePanel = new GamePanel();
		this.add(gamePanel, "ゲーム画面");
		this.pack();
		
	}
	
	public void prepareComponents() {
		titlePanel.prepareComponents();
		gamePanel.prepareComponents();
	}
	
	/*
	 * スクリーンモードを切り替える
	 */
	public void setFrontScreenAndFocus(ScreenMode s) {
		screenMode = s;
		
		switch(screenMode) {
		case TITLE:
			layout.show(this.getContentPane(), "タイトル画面");
			titlePanel.requestFocus();
			break;
		case GAME:
			layout.show(this.getContentPane(), "ゲーム画面");
			gamePanel.requestFocus();
			gamePanel.prepareGame();
			gamePanel.startPoker();
			break;
		}
	}
	
	/**
	 * 再描画
	 */
	@Override
	public void repaint() {
		if(titlePanel != null) titlePanel.repaint();
		if(gamePanel != null) gamePanel.repaint();
		
		super.repaint();
	}
	
	/**
	 * フレームのサイズ変更に合わせて再描画させる
	 */
	class ComponentAdapter_JFrame extends ComponentAdapter{
	    public void componentResized(ComponentEvent e) {              //フレームのサイズが変更されたとき
	      MainWindow.this.repaint();                                      //フレームを再描画する
	    }
	  }

}
