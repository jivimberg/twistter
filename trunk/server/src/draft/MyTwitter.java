package draft;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


public class MyTwitter {
	
	private Twitter myTwitter;
	
	
	private RequestToken requestToken;
	
	public void authorizeApp(){
		final TwitterFactory myTwitterFactory = new TwitterFactory();
		myTwitter = myTwitterFactory.getInstance();
		myTwitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		try {
			//Consumer request Token
			requestToken = myTwitter.getOAuthRequestToken();
			
			//Authorization URL
			System.out.println(requestToken.getAuthorizationURL());

			System.out.println(requestToken.getToken());
			System.out.println(requestToken.getTokenSecret());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void createAccessToken(String pin){
		
		AccessToken accessToken;
		try {
			accessToken = myTwitter.getOAuthAccessToken(requestToken, pin);
			myTwitter.setOAuthAccessToken(accessToken);
			
			saveToFile(accessToken, "accessToken.dat");

			long userId = accessToken.getUserId();
			User user = myTwitter.showUser(userId);
			System.out.println(user.getName());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void useAccessToken(String fileName){
		final TwitterFactory myTwitterFactory = new TwitterFactory();
		myTwitter = myTwitterFactory.getInstance();
		myTwitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		
		AccessToken accessToken;
		try {
			accessToken = readFromFile(fileName);
			myTwitter.setOAuthAccessToken(accessToken);
			
			long userId = accessToken.getUserId();
			User user = myTwitter.showUser(userId);
			System.out.println(user.getName());
			System.out.println("***********************************");
			
			List<Status> timeline = myTwitter.getHomeTimeline();
			for (Status status : timeline) {
				System.out.println(status.getUser().getName());
				System.out.println(status.getText());
				System.out.println("------------------------------------");
			}
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	private void saveToFile(Object object, String fileName) {
		FileOutputStream fout;
		ObjectOutputStream objectOutputStream = null;
		try {
			fout = new FileOutputStream(fileName);
			objectOutputStream = new ObjectOutputStream(fout);
			objectOutputStream.writeObject(object);
			objectOutputStream.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private <T> T readFromFile(String fileName) {
		FileInputStream fout;
		ObjectInputStream objectInputStream = null;
		
		try {
			fout = new FileInputStream(fileName);
			objectInputStream = new ObjectInputStream(fout);
			T t = (T) objectInputStream.readObject();
			objectInputStream.close();
			return t;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}


	public void updateStatus(String msg){
		try {
			myTwitter.updateStatus(msg);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		MyTwitter myTwitter = new MyTwitter();
		
//		myTwitter.authorizeApp();
//		
//		System.out.println("Please insert your pin");
//		Scanner in = new Scanner(System.in);
//
//       // Reads a single line from the console 
//       // and stores into name variable
//       String pin = in.nextLine();
//	       
//       myTwitter.createAccessToken(pin);
		
		myTwitter.useAccessToken("accessToken.dat");
       
	}

	private static final String CONSUMER_KEY = "1fJQ3sBPrFtXCPYQIgIW9w";
	private static final String CONSUMER_SECRET = "Y9ESDVow06aPd4v8uuP5mmVoEYAoq32QjhBPzz9u24";
}
