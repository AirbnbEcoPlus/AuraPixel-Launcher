package fr.aurapixel;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fr.litarvan.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;



@SuppressWarnings("serial")
public class LauncherPanel extends JPanel implements SwingerEventListener{

	private Saver saver = new Saver(new File(Launcher.AP_DIR, "launcher.properties"));
	private JTextField usernameField = new JTextField(saver.get("username"));
	private JPasswordField passwordField = new JPasswordField();
	
	private STexturedButton playButton = new STexturedButton(Swinger.getResource("play.png"));
	private STexturedButton settingsButton = new STexturedButton(Swinger.getResource("settings.png"));
	private STexturedButton discordButton = new STexturedButton(Swinger.getResource("discord.png"));
	private STexturedButton exitButton = new STexturedButton(Swinger.getResource("exit.png"));

	private RamSelector ramSelector = new RamSelector(new File(Launcher.AP_DIR, "ram.txt"));
	public LauncherPanel() {
		this.setLayout(null);
		usernameField.setForeground(Color.RED);
		usernameField.setFont(usernameField.getFont().deriveFont(20F));
		usernameField.setCaretColor(Color.WHITE);
		usernameField.setOpaque(false);
		usernameField.setBorder(null);
		usernameField.setBounds(260, 458, 160, 24);
		this.add(usernameField);
		
		passwordField.setForeground(Color.RED);
		passwordField.setFont(passwordField.getFont().deriveFont(20F));
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setOpaque(false);
		passwordField.setBorder(null);
		passwordField.setBounds(259, 508, 160, 24);
		this.add(passwordField);
		
		playButton.setBounds(258, 540, 160 ,60);
		playButton.addEventListener(this);
		this.add(playButton);
		
		settingsButton.setBounds(268, 608);
		settingsButton.addEventListener(this);
		this.add(settingsButton);
		
		discordButton.setBounds(348, 612);
		discordButton.addEventListener(this);
		this.add(discordButton);
		
		exitButton.setBounds(638, 10, 30, 30);
		exitButton.addEventListener(this);
		this.add(exitButton);
	}
	@Override
	public void onEvent(SwingerEvent e) {
		if(e.getSource() == exitButton) {
		System.exit(1);
		}
		if(e.getSource() == settingsButton) {
			ramSelector.display();
		}
		if(e.getSource() == discordButton) {
			String url_open ="https://discord.gg/EmTFBUA";
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		if(e.getSource() == playButton) {
			
			setFieldsEnabled(false);
			
			if(usernameField.getText().replaceAll(" ", "").length() == 0 || passwordField.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "Email ou mot de passe invalide", "ERROR", JOptionPane.ERROR_MESSAGE);
				setFieldsEnabled(true);
				
				return;
			}
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						Launcher.auth(usernameField.getText(), passwordField.getText());
					} catch (AuthenticationException e) {
						JOptionPane.showMessageDialog(LauncherPanel.this, "Mojang Serveur : " + e.getErrorModel().getErrorMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
						setFieldsEnabled(true);
						return;
					}
					try {
						Launcher.launch();
					}catch (LaunchException e) {
						JOptionPane.showMessageDialog(LauncherPanel.this, "Impossible de lancer le jeu", "ERROR", JOptionPane.ERROR_MESSAGE);
					    setFieldsEnabled(true);
						
					}
				}
			};
			t.start();;
			
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		try {
			g.drawImage(ImageIO.read(getClass().getResourceAsStream("/fr/aurapixel/resources/menu.png")), 0, 0, this.getWidth(), this.getHeight(), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unused")
	private void setFieldsEnabled(boolean enabled) {
		usernameField.setEnabled(enabled);
		passwordField.setEnabled(enabled);
		playButton.setEnabled(enabled);
	}
	public RamSelector getRamSelector() {
		return ramSelector;
	}
	
}
