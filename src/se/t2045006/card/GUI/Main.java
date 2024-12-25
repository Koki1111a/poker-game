package se.t2045006.card.GUI;

public class Main {
	static MainWindow mainWindow;
	
	public static void main(String[] arg) {
		mainWindow = new MainWindow(); //ウィンドウの生成
		mainWindow.preparePanels(); //ペインに貼るパネルの生成
		mainWindow.prepareComponents(); //コンポーネントの生成
		mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);
		mainWindow.setVisible(true); //この時点でウィンドウを可視化
	}
}
