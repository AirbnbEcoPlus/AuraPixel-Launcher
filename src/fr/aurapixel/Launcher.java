package fr.aurapixel;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import fr.litarvan.openauth.AuthPoints;
import fr.litarvan.openauth.AuthenticationException;
import fr.litarvan.openauth.Authenticator;
import fr.litarvan.openauth.model.AuthAgent;
import fr.litarvan.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.internal.InternalLaunchProfile;
import fr.theshark34.openlauncherlib.internal.InternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;




@SuppressWarnings("deprecation")
public class Launcher {

	
	public static final GameVersion AP_VERSION = new GameVersion("1.12.2", GameType.V1_8_HIGHER);
	public static final GameInfos AP_INFOS = new GameInfos("AuraPixel", AP_VERSION, new GameTweak[] {GameTweak.FORGE});
	public static final File AP_DIR = AP_INFOS.getGameDir();
	public static final GameFolder AP_FOLDER = new GameFolder("resources/assets/", "resources/libs/", "resources/natives/", "jar/aurapixel.jar");
	
	private static AuthInfos authInfos;
	
	public static void auth(String username, String password) throws AuthenticationException{
		Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
		AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, username, password, "");
		authInfos = new AuthInfos(response.getSelectedProfile().getName(), response.getAccessToken(), response.getSelectedProfile().getId());
		
	}
	@SuppressWarnings("deprecation")
	public static void launch() throws LaunchException {
		ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(AP_INFOS, AP_FOLDER, authInfos);
		ExternalLauncher launcher = new ExternalLauncher(profile);
		profile.getVmArgs().addAll(Arrays.asList(LauncherFrame.getInstance().getLauncherPanel().getRamSelector().getRamArguments()));
		LauncherFrame.getInstance().setVisible(false);
		launcher.launch();
		
		
		System.exit(0);
	}
}
