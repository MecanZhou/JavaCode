
public class OJLD {		//求公因数

	public static int gcd(int a,int b)
	{
		int x=a;
		int y=b;
		if(y<=0)
			return 0;
		else
		{
			int z=a/b;
			int w=a%b;
			if(w==0)
				return b;
			else
			{
				return gcd(y,w);
			}
		}
	}

}
