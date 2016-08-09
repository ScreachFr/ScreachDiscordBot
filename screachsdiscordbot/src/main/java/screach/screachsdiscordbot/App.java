package screach.screachsdiscordbot;

import org.apache.commons.io.FilenameUtils;

import screach.screachsdiscordbot.listener.MainListener;
import screach.screachsdiscordbot.util.Debug;
import screach.screachsdiscordbot.util.FailedToLoadSettingsException;
import screach.screachsdiscordbot.util.Settings;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.RateLimitException;

public class App {

	public static void main(String[] args) {
		IDiscordClient bot;
		MainListener mListener;
		String token;
		
		
		
		try {
			Settings.init();
			Debug.init();
			System.out.println("Settings loaded.");
		} catch (FailedToLoadSettingsException e1) {
			System.err.println("Failed to load settings : " + e1.getNotice());
			System.err.println("Stopping program...");
			return;
		}
		
		try {
			token = Settings.crtInstance.getValue("token");
			bot = getClient(token);
			
			mListener = new MainListener();
			bot.getDispatcher().registerListener(mListener);
			
			

		} catch (DiscordException e) {
			e.printStackTrace();
		}




	}


	public static IDiscordClient getClient(String token) throws DiscordException {
		return new ClientBuilder().withToken(token).login();
	}    

	public static void setupBot(IDiscordClient bot) throws RateLimitException, DiscordException {
		Status status;
		String email;
		String avatar;
		Image img;
		
		
		avatar = Settings.crtInstance.getValue("botimage");
		status = Status.game(Settings.crtInstance.getValue("botstatus"));
		email = Settings.crtInstance.getValue("botemail");
		img = Image.forUrl(FilenameUtils.getExtension(avatar), avatar);
		
		bot.changeStatus(status);
		bot.changeEmail(email);
	    bot.changeAvatar(img);
		
		
	}
	
	public static void init() {
		
	}

}
