package se.t2045006.card.GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import se.t2045006.card.entity.Card;

public class CardLabel extends JLabel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private Image faceImage;
	private Image backImage;
	private Card card;
	private int x;
	private int y;
	public static final int FACEUPSTEP = 10;
	private int faceUpCounter;
	private static final Card JOKER = new Card(-1, 0);
	AnimationMode animationMode = AnimationMode.WAITING;
	Timer timer;
	
	public enum AnimationMode {
		//アニメーションのモード
		WAITING,
		FACEUP,
		FACEDOWN
	}
	
	public CardLabel() {
		this.card = JOKER;
		this.x = 0;
		this.y = 0;
		faceImage = card.getFaceImage();
		backImage = card.getBackImage();
		super.setBounds(x, y, faceImage.getWidth(null), faceImage.getHeight(null));
		this.faceUpCounter =1;
		timer = new Timer(50, this);
	}
	
	public Card getCard() {
		return card;
	}
	
	public void setCard(Card card) {
		this.card = card;
		faceImage = card.getFaceImage();
		backImage = card.getBackImage();
		this.setLocation(x, y);
		super.setSize(faceImage.getWidth(null), faceImage.getHeight(null));
	}
	
	public void resetCard() {
		this.card = JOKER;
		faceImage = card.getFaceImage();
		backImage = card.getBackImage();
		super.setBounds(x, y, faceImage.getWidth(null), faceImage.getHeight(null));
		this.faceUpCounter =1;
		this.setBorder(null);

	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		double ratio;
		if(this.card.getSuit() == -1) {
			g2D.drawImage(backImage, 0, 0, 0, 0, null);
		}else if(faceUpCounter <= FACEUPSTEP/2) {
			ratio = (1.0 - (faceUpCounter - 1.0) * 2.0 / FACEUPSTEP);
			g2D.drawImage(backImage, (int)(this.getWidth()*(1.0-ratio)/2.0), 0, (int)(ratio * this.getWidth()), this.getHeight(), null);
		}else {
			ratio = (faceUpCounter - 5.0) * 2.0 / FACEUPSTEP;
			g2D.drawImage(faceImage, (int)(this.getWidth()*(1.0-ratio)/2.0), 0, (int)(ratio * this.getWidth()), this.getHeight(), null);
		}
	}
	
	public void faceUp() {
		timer.start();
		animationMode = AnimationMode.FACEUP;
	}
	
	public void faceDown() {
		timer.start();
		animationMode = AnimationMode.FACEDOWN;
	}
	
	public void faceUpOne() {
		faceUpCounter++;
		repaint();
	}
	
	public void faceDownOne() {
		faceUpCounter--;
		repaint();
	}
	
	public void setFaceUpCounter(int i) {
		if(1 <= i && i <= FACEUPSTEP)
			faceUpCounter = i;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(this.animationMode) {
		case FACEUP:
			if(faceUpCounter == FACEUPSTEP)
				timer.stop();
			if(faceUpCounter < FACEUPSTEP)
				this.faceUpOne();
			break;
		case FACEDOWN:
			if(faceUpCounter == 0)
				timer.stop();
			if(faceUpCounter > 0)
				this.faceDownOne();
			break;
		default:
			break;
		}
		
	}
	
	
	/**
	 * サイズを適切な比率に調節するsetSize
	 * 
	 * @param width
	 * 		設定したいカードの幅
	 * @param height
	 * 		設定したいカードの高さ
	 */
	public void setSize(int width, int height) {
		double imageSizeRatio = (double)faceImage.getWidth(null) / (double)faceImage.getHeight(null);
		double ratio = (double)width / (double)height;
		
		if(imageSizeRatio > ratio) {
			super.setSize((int)width, (int)(width / imageSizeRatio));
		}else {
			super.setSize((int)(height * imageSizeRatio), (int)height);
		}
	}

	/**
	 * サイズを適切な比率に調節するsetBounds
	 * 
	 * @param x
	 * 		カードの位置x
	 * @param y
	 * 		カードの位置y
	 * @param width
	 * 		設定したいカードの幅
	 * @param height
	 * 		設定したいカードの高さ
	 */
	public void setBounds(int x, int y, int width, int height) {
		double imageSizeRatio = (double)faceImage.getWidth(null) / (double)faceImage.getHeight(null);
		double ratio = (double)width / (double)height;
		
		if(imageSizeRatio > ratio) {
			super.setBounds(x, y, (int)width, (int)(width / imageSizeRatio));
		}else {
			super.setBounds(x, y, (int)(height * imageSizeRatio), (int)height);
		}
	}

}
