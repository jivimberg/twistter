package deprecated.servlet;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.TwitterService;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public class RegisterServlet extends GenericServlet {

	private static final long serialVersionUID = 4003175649047885489L;

	private TwitterService service = TwitterService.getInstance();
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		System.out.println(".register.");
		try {
			InputStream inputStream = req.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			Object obj = null;
			while ((obj = ois.readObject()) != null) {
				AccessToken accessToken = (AccessToken) obj;
				service.persistAccessToken(accessToken); // TODO save token
			}
		} catch (EOFException ex) { 
			System.out.println("End of file reached.");
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
