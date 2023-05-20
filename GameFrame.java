import javax.swing.JFrame;

public class GameFrame extends JFrame {
GameFrame() { // Constructor 
//	GamePanel Panel = neew GamePanel();
//	this.add(Panel); equivalent statement below.
	this.add(new GamePanel());
	this.setTitle("Snake");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	this.pack(); //fits all comp in JFrame
	this.setVisible(true);
	this.setLocationRelativeTo(null);
		
	

}
}
