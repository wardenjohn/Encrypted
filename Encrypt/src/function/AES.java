package function;
import java.util.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.omg.CORBA.portable.ValueFactory;

import java.security.*;

public class AES {
	String password="123456";
	KeyGenerator keygen;
	SecretKey AESkey;
	String algorithm = "AES";
	public void Encrypt(String in_name,String out_name)
	{
		try{
			if(!(new File("AESkey.dat")).exists()){
				System.out.println("creating AES key");
				KeyGenerator keygen=KeyGenerator.getInstance(algorithm);
				AESkey = keygen.generateKey();
				
				ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("AESkey.dat"));
				outputStream.writeObject(AESkey);
				outputStream.close();
			}
			else{
				System.out.println("read AES key");
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("AESkey.dat"));
				AESkey = (SecretKey)in.readObject();
				in.close();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(in_name)));
			StringBuffer buffer = new StringBuffer();
			String clear="";
			while((clear=reader.readLine()) != null){
				buffer.append(clear);
			}
			reader.close();
			clear = buffer.toString();
			//start the encrypt
//			keygen = KeyGenerator.getInstance(algorithm); 
//			keygen.init(128);
//			SecretKey key= keygen.generateKey();
//			byte[] enCodeFormat = key.getEncoded();
//			SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, algorithm);
//			Cipher cipher=Cipher.getInstance(algorithm);
//			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//			byte[] cipertext = cipher.doFinal(clear.getBytes());
			Cipher c1=Cipher.getInstance(algorithm);
			c1.init(Cipher.ENCRYPT_MODE, AESkey);
			byte[] cipertext = c1.doFinal(clear.getBytes());
			FileOutputStream output = new FileOutputStream(new File(out_name));
			output.write(cipertext);
			output.close();
			System.out.println(cipertext);
			System.out.println("finish");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void Decrypt(String in_name,String out_name)
	{
		try{
			byte[] cipertext = readInbyte(in_name);
//			keygen = KeyGenerator.getInstance("AES");
//	        keygen.init(128);
//	        SecretKey secretKey = keygen.generateKey();
//	        byte[] enCodeFormat = secretKey.getEncoded();
//	        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//	        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//	        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
//	        byte[] result = cipher.doFinal(cipertext);
			if(!(new File("AESkey.dat")).exists()){
				System.out.println("can not find the AES key!");
				return ;
			}
			else{
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("AESkey.dat"));
				AESkey = (SecretKey)in.readObject();
				in.close();
			}
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, AESkey);
			byte[] result = cipher.doFinal(cipertext);
	        FileOutputStream out = new FileOutputStream(new File(out_name));
	        out.write(result);
	        out.close();
	    }catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public byte[] readInbyte(String inputname)
	{
		try{
			File file = new File(inputname);
			FileInputStream in = new FileInputStream(file);
			long filesize = file.length();
			byte[] readin = new byte[(int)filesize];
			
			int offset=0;
			int numRead=0;
			
			while(offset<readin.length&&(numRead = in.read(readin, offset, readin.length-offset))>=0){
				offset+=numRead;
			}
			if(offset!=readin.length){
				throw new IOException("can not read completely of file :"+file.getName());
			}
			in.close();
			return readin;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String args[]){
		System.out.println("Select the running mode :");
		System.out.println("1.Encrypt");
		System.out.println("2.Decrypt");
		AES aes = new AES();
		Scanner scan = new Scanner(System.in);
		int mode = scan.nextInt();
		switch (mode) {
		case 1:{
			System.out.println("input the clear document");
			scan.nextLine();
			String input = scan.nextLine();
			System.out.println("Name the ciper file");
			String output = scan.nextLine();
			aes.Encrypt(input, output);
			break;
		}
		case 2:{
			System.out.println("input the ciper document");
			scan.nextLine();
			String input = scan.nextLine();
			System.out.println("Name the clear file");
			String output = scan.nextLine();
			aes.Decrypt(input, output);
			break;
		}
		default:
			break;
		}
	}
}
