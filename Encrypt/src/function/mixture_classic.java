package function;

import javax.sql.rowset.FilteredRowSet;
/*
 * 解方程的问题，主要在得到了逆以后，需要解密，这里卡住了
 */
public class mixture_classic {
	int k0=3;
	int k1=7;
	int n=26;
	int s,t;
	/*
	 * info may be a file path or some clear text
	 */
	public StringBuffer classic_encrypt(String info,int use_area){
		StringBuffer tem_encrypt = Affine_Cipher(info);
		StringBuffer encryptd = new StringBuffer();
		encryptd = change_sit(tem_encrypt.toString());
		return encryptd;
	}
	
	public StringBuffer classic_decrypt(String info , int use_area) {
		StringBuffer tem_decrypt = restore_sit(info);
		StringBuffer clear = decrypt_Affine(tem_decrypt.toString());
		return clear;
	}
	
	
	/*
	 * This function,I use 5 character as one group to change situation.and the left is not a group
	 */
	public StringBuffer change_sit(String cleartext){
		StringBuffer encrypted = new StringBuffer();
		int group = cleartext.length()/5;   //the number of the group we change sit
		int pointer=0;//pointer to point out the now character

		for(int i=0;i<group;i++){
			char t[] = new char[5];
			for(int j=0;j<5;j++){
				t[j]=cleartext.charAt(pointer);
				pointer++;
			}

			//start to reverse
			encrypted.append(t[4]);
			encrypted.append(t[3]);
			encrypted.append(t[1]);
			encrypted.append(t[2]);
			encrypted.append(t[0]);
		}
		for(;pointer<cleartext.length();pointer++){
			encrypted.append(cleartext.charAt(pointer));
		}
		return encrypted;
	}
	
	public StringBuffer Affine_Cipher(String cleartext) {
		StringBuffer clear = new StringBuffer(); 
		for(int i=0;i<cleartext.length();i++){
			int val = cleartext.charAt(i);
			val = (val*k1+k0)%n+32;
			System.out.println("encrypted  :  "+val);
			char c = (char)val;
			clear.append(c);
		}
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
	
	public int caculate_inverse(int r0,int r1){//r0 = n
		int a=r0,b=r1;
		int c=0,d=1;
		int q=a/b,r=a-q*b;
		while(r>0){
			int temp=c-q*d;
			if(temp>0)
				temp=temp%a;
			else
				temp=(n+temp)%a;
			c=d;
			d=temp;
			a=b;
			b=r;
			q=a/b;
			r=a-q*b;
		}
		if(b!=1)
			return 0;
		else
			return d;
	}
	
	public int find_reverse(int a,int n) {
		int ans = caculate_inverse(n, a);
		//int ans = (t >= 0) ? (t % n) : ((t-t*n)%n);
		System.out.println("reverse is :"+ans);
		return ans;
	}
	
	/*
	 * *m_str = (_a(逆元)*(*m_str - b + n))%n;//解密核心算法  
	 * */
	public StringBuffer decrypt_Affine(String cipertext) {
		StringBuffer clear = new StringBuffer();
		//int n=94;
		int re_k = find_reverse(k1,n); //(j-k0)*rek mod n == c
		 System.out.println(re_k+"???");
		for(int i=0;i<cipertext.length();i++){
			int tem = (int)cipertext.charAt(i);
			tem -=32;
			//tem = (re_k * (tem - k0 + n))%n;
			tem = ((tem-k0)*re_k)%n;
			System.out.println(tem);
			clear.append((char)(tem));
		}
		return clear;
	}
}


/*
 * 计算的问题，但是到底是求逆元的时候出错了还是在求解的时候出错了还是需要想想
 * */
