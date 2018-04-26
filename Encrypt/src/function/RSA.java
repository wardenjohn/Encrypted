package function;
import java.util.*;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.awt.*;
import javax.crypto.*;
import java.io.*;
import java.math.*;
/*
 * OK for use , but will lose \n
 */
public class RSA {
	String file="RSA_privateKey.dat";
	String RSA_Algorithm = "RSA";
	static String inputFile;
	static String outputname;
	
	public static void main(String[] args) throws java.security.NoSuchAlgorithmException,java.lang.Exception{
		RSA rsa = new RSA();
		System.out.println("select the RSA mode");
		System.out.println("1.encrypt");
		System.out.println("2.decrypt");
		Scanner scanner = new Scanner(System.in);
		int i=scanner.nextInt();
		switch (i) {
		case 1:{
			System.out.println("input the source file name");
			scanner.nextLine();
			inputFile = scanner.nextLine();
			System.out.println("input the name of the output file");
			outputname = scanner.nextLine();
			
			System.out.println(inputFile);
			System.out.println(outputname);
			rsa.rsa_encrypt();
			break;
		}
		case 2:{
			System.out.println("input the sourcefile name");
			scanner.nextLine();
			inputFile = scanner.nextLine();
			System.out.println("input the output filename");
			outputname=scanner.nextLine();
			rsa.rsa_decrypt();
			break;
		}
			
		default:
			break;
		}
	}
	
	public boolean creatkey() {
		try{
			KeyPairGenerator key = KeyPairGenerator.getInstance(RSA_Algorithm);
			key.initialize(1024);
			KeyPair keyPair = key.generateKeyPair();
			PublicKey public_key = keyPair.getPublic();
			PrivateKey private_key = keyPair.getPrivate();
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("RSAprivatekey.dat"));
			outputStream.writeObject(private_key);
			outputStream.close();
			
			System.out.println("写入对象private key");
			
			outputStream = new ObjectOutputStream(new FileOutputStream("RSApublickey.dat"));
			outputStream.writeObject(public_key);
			
			outputStream.close();
			
			System.out.println("写入对象public");
			System.out.println("生成秘钥文件成功");
			return true;
		}catch (java.lang.Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("无法生成密钥对");
			return false;
		}
	}
	
	
	public void rsa_encrypt(){
		if(!(new File("RSAprivatekey.dat")).exists()){
			if(creatkey()){
				
			}else {
				System.out.println("Fail to creat the key pair");
			}
		}
		
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("RSApublickey.dat"));
			RSAPublicKey publicKey = (RSAPublicKey)in.readObject();
			in.close();
			
			BigInteger e = publicKey.getPublicExponent();
			BigInteger n = publicKey.getModulus();
			
			System.out.println("Parameter e:"+e);
			System.out.println("Parameter n"+n);
			
			BufferedReader in_clear = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
			StringBuffer buffer = new StringBuffer();
			String s="";
			while((s=in_clear.readLine()) != null){
				buffer.append(s);
			}
			in_clear.close();
			String cleartext = buffer.toString();
			System.out.println(cleartext);
			byte plainbyte[] = cleartext.getBytes("UTF8");
			BigInteger clearBI = new BigInteger(plainbyte);
			System.out.println("clear text: "+cleartext);
			System.out.println("clear code: "+clearBI.toString());
			
			//caculate cipertext
			BigInteger ciperBI = clearBI.modPow(e, n);
			System.out.println("ciper code : "+ciperBI.toString());
			
			//
			String cipertext = ciperBI.toString();
			System.out.println("cipercode : "+cipertext);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputname)));
			out.write(cipertext,0,cipertext.length());
			out.close();
			System.out.println("finish encrypting");
		}catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
			System.out.println("加密过程故障！");
		}
	}
	
	public void rsa_decrypt()
	{
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("RSAprivatekey.dat"));
			RSAPrivateCrtKey private_key = (RSAPrivateCrtKey)in.readObject();
			in.close();//we just need private key
			
			BigInteger d = private_key.getPrivateExponent();
			BigInteger n =private_key.getModulus();
			System.out.println("私钥参数 d"+d);
			System.out.println("私钥参数 n"+n);
			
			//read the ciper text
			BufferedReader in_ciper = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
			StringBuffer content = new StringBuffer();
			String s="";
			while((s=in_ciper.readLine())!=null){
				content.append(s);
			}
			in_ciper.close();
			
			String cipertext=content.toString();
			BigInteger ciperBI = new BigInteger(cipertext);
			
			//decode
			BigInteger clear = ciperBI.modPow(d,n);
			System.out.println("解出的明文编码 : "+clear);
			byte[] clerbyte = clear.toByteArray();
 			System.out.println("解出的明文是：");
 			for(int i=0;i<clerbyte.length;i++){
 				System.out.print((char)clerbyte[i]);
 			}
 			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputname)));
 			for(int i=0;i<clerbyte.length;i++){
 				out.write((char)clerbyte[i]);
 			}
 			System.out.println("");
			out.close();
			System.out.println("finish decrypting");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
