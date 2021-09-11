package fr.aurapixel;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame {

	
	private static LauncherFrame instance;
	private LauncherPanel launcherPanel;
	public LauncherFrame() {
		this.setTitle("AuraPixel Launcher");
		this.setSize(677, 676);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setContentPane(launcherPanel = new LauncherPanel());
		
		
		WindowMover mover = new WindowMover (this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Swinger.setResourcePath("/fr/aurapixel/resources/");
				instance = new LauncherFrame();

	}
	public static LauncherFrame getInstance() {
		return instance;
	}
	public LauncherPanel getLauncherPanel() {
		return this.launcherPanel;
	}
}
