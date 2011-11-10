package twistter.android.client.ws.interfaces;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

public class HessianServiceProvider {

	private static LoginWebService loginWebService;
	private static RegisterWebService registerWebService;
	private static TimelineWebService timelineWebService;
	private static UpdateStatusWebService updateStatusWebService;
	private static HessianProxyFactory proxyFactory = new HessianProxyFactory();
	
	public static LoginWebService getLoginWebService(String url, ClassLoader classLoader) throws MalformedURLException{
		if(loginWebService == null){
			loginWebService = initializeService(LoginWebService.class, url, classLoader);
		}
		return loginWebService;
	}
	
	public static TimelineWebService getTimelineWebService(String url, ClassLoader classLoader) throws MalformedURLException{
		if(timelineWebService == null){
			timelineWebService = initializeService(TimelineWebService.class, url, classLoader);
		}
		return timelineWebService;
	}
	
	public static UpdateStatusWebService getUpdateStatusWebService(String url, ClassLoader classLoader) throws MalformedURLException{
		if(updateStatusWebService == null){
			updateStatusWebService = initializeService(UpdateStatusWebService.class, url, classLoader);
		}
		return updateStatusWebService;
	}
	
	public static RegisterWebService getRegisterWebService(String url, ClassLoader classLoader) throws MalformedURLException{
		if(registerWebService == null){
			registerWebService = initializeService(RegisterWebService.class, url, classLoader);
		}
		return registerWebService;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T initializeService(Class clazz, String url, ClassLoader classLoader) throws MalformedURLException{
			return (T) proxyFactory.create(clazz, url, classLoader);
	}
}
