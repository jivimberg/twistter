package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtils {
	
	public static void saveToFile(Object object, String fileName) {
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
	
	public static <T> T readFromFile(String fileName) {
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

}
