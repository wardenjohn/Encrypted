package function;

import javax.sql.rowset.FilteredRowSet;
/*
 * 解方程的问题，主要在得到了逆以后，需要解密，这里卡住了
 */
public class mixture_classic {
	int k0=5;
	int k1=7;
	int s,t;
	/*
	 * info may be a file path or some clear text
	 */
	public StringBuffer classic_encrypt(String info,int use_area){
		StringBuffer tem_encrypt = Affine_Cipher(info);
		StringBuffer encryptd = new StringBuffer();
		encryptd = change_sit(tem_encrypt.toString());
		System.out.println(find_reverse(7, 26)+"thiss");
		return encryptd;
	}
	
	/*public StringBuffer classic_decrypt() {
		
	}*/
	
	
	/*
	 * This function,I use 5 character as one group to change situation.and the left is not a group
	 */
	public StringBuffer change_sit(String cleartext){
		StringBuffer encrypted = new StringBuffer();
		int group = cleartext.length()/5;   //the number of the group we change sit
		int pointer=0;//pointer to point out the now character
		System.out.println(group);
		for(int i=0;i<group;i++){
			char t[] = new char[5];
			for(int j=0;j<5;j++){
				t[j]=cleartext.charAt(pointer);
				pointer++;
			}
			System.out.println(encrypted);
			//start to reverse
			encrypted.append(t[4]);
			encrypted.append(t[3]);
			encrypted.append(t[1]);
			encrypted.append(t[2]);
			encrypted.append(t[0]);
		}
		System.out.println(pointer);
		System.out.println(encrypted);
		for(;pointer<cleartext.length();pointer++){
			encrypted.append(cleartext.charAt(pointer));
		}
		System.out.println(encrypted);
		return encrypted;
	}
	
	public StringBuffer Affine_Cipher(String cleartext) {
		StringBuffer clear = new StringBuffer(); 
		for(int i=0;i<cleartext.length();i++){
			int val = cleartext.charAt(i);
			val = (val*k1+k0)%94+32;
			char c = (char)val;
			clear.append(c);
			//System.out.println(c);
		}
		//System.out.println(clear.toString());
		return clear;
	}
	
	
	/*
	 * The following function is going to decrypt the ciper text
	 */
	public StringBuffer restore_sit(String cipertext){
		StringBuffer clear = new StringBuffer();
		int group = 0;
		group = cipertext.length()/5;
		int pointer = 0;
		
		for(int i=0;i<group;i++){
			char t[] = new char[5];
			for(int j=0;j<5;j++){
				t[j] = cipertext.charAt(pointer);
				pointer++;
			}
			clear.append(t[4]);
			clear.append(t[2]);
			clear.append(t[3]);
			clear.append(t[1]);
			clear.append(t[0]);
		}
		/*
		 * Try another way to en/decrypt
		 * clear = new StringBuffer(cipertext);
		 * for(int i=0;i<cipertext.length()/2;i++){
		 * 		char t;
		 * 		t=cipertext.charAt(cipertext.length()/2+i);
		 * 		cipertext.replace(cipertext.length()/2+i,cipertext.length()/2,cipertext.charAt(cipertext.length()/2-i));
		 * 		cipertext.replace(cipertext.length()/2-i,cipertext.length()/2,t);
		 * }
		 */
		for(;pointer<cipertext.length();pointer++){
			clear.append(cipertext.charAt(pointer));
		}
		return clear;
	}
	
	public int caculate_inverse(int a,int b){
		int r1 = a, r2 = b , s1 = 1, s2 = 0, t1 = 0, t2 = 1;//初始化
	    int q,r;     
	    while(r2 > 0)    
	    {    
	        q = r1 / r2;    
	 
	        r = r1 - q * r2; //也就是r = r1%r2;    
	        r1 = r2;    
	        r2 = r;    
	    
	        s = s1 - q * s2;    
	        s1 = s2;    
	        s2 = s;    
	          
	        t = t1 - q * t2;    
	        t1 = t2;    
	        t2 = t;     
	    }    
	    //gcd(a,b) = r1;     
	    s = s1;     
	    t = t1; 
	    return 0;
	}
	
	public int find_reverse(int a,int n) {
		caculate_inverse(n, a);
		int ans = (t >= 0) ? (t % n) : ((t-t*n)%n);
		return ans;
	}
	
	public StringBuffer decrypt_Affine(String cipertext) {
		StringBuffer clear = new StringBuffer();
		int re_k = find_reverse(5,26); //(j-k0)*rek mod n == c
		
		for(int i=0;i<cipertext.length();i++){
			
		}
		return clear;
	}
}
