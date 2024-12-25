package se.t2045006.card.GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ChipImageLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	private Image chipImage;
	private int x;
	private int y;
	
	ChipImageLabel(){
		chipImage = new ImageIcon(getClass().getClassLoader().getResource("chip.png")).getImage();
		this.x = 0;
		this.y = 0;
		super.setBounds(x, y, chipImage.getWidth(null), chipImage.getHeight(null));
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		//g2D.drawImage(faceImage, 0, 0, faceImage.getWidth(null), faceImage.getHeight(null), null);
		g2D.drawImage(chipImage, 0, 0, this.getWidth(), this.getHeight(), null);
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
		double imageSizeRatio = (double)chipImage.getWidth(null) / (double)chipImage.getHeight(null);
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
		double imageSizeRatio = (double)chipImage.getWidth(null) / (double)chipImage.getHeight(null);
		double ratio = (double)width / (double)height;
		
		if(imageSizeRatio > ratio) {
			super.setBounds(x, y, (int)width, (int)(width / imageSizeRatio));
		}else {
			super.setBounds(x, y, (int)(height * imageSizeRatio), (int)height);
		}
	}
}
