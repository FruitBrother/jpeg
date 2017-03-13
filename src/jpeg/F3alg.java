package jpeg;
import java.util.Arrays;
import java.util.Random; 
public class F3alg {
	//生成秘密信息
	public static int[] secret(int bit_len)
	{
		int[] bits_info = new int [bit_len];
		for(int i=0;i<bit_len;i++)
		{
			Random rand = new Random();
			bits_info[i]= rand.nextInt(1);//01的随机数组
		}
		return bits_info;
	}
	//使用f3算法进行隐写
	public static void F3(int []f3_DCT,int bit_len,int []bits_info,int row,int line)
	{
		int line_pos = 0;
		int row_pos = 0;
		int i = 0;
		while (i<bit_len)
		{
		    if (f3_DCT[line_pos*row+row_pos]!=0)
		    {
		        if ((f3_DCT[line_pos*row+row_pos]==1 || f3_DCT[line_pos*row+row_pos]==-1) && bits_info[i]==0)
		        {
		        	f3_DCT[line_pos*row+row_pos] = 0; //视为无效
		        }
		        else
		        {
		        	if (((f3_DCT[line_pos*row+row_pos]%2)==0 && bits_info[i]==1) || ((f3_DCT[line_pos*row+row_pos]%2)==1 && bits_info[i]==0))
		        	{  //绝对值减一，符号不变
		                if (f3_DCT[line_pos*row+row_pos] < 0)
		                {
		                	f3_DCT[line_pos*row+row_pos] = f3_DCT[line_pos*row+row_pos] + 1;//区别负数和整数
		                }
		                else if (f3_DCT[line_pos*row+row_pos] > 0)
		                {
		                	f3_DCT[line_pos*row+row_pos] = f3_DCT[line_pos*row+row_pos] - 1;
		                }
		        	}
		            i = i + 1;
		        }
		     }
		    row_pos = row_pos + 1;
		    if (row_pos == row + 1)
		    {
		        row_pos = 1;
		        if (line_pos < line)
		        {
		        	line_pos = line_pos + 1;
		        }
		        else
		        {
		        	System.out.println("F3隐写: 插入秘密信息过多，部分无法插入");
		            break;
		        }
		    }
		}
	}
	//读取f3算法中的隐写信息,判断写入是否一致
	public static void readF3(int bit_len,int []bits_info,int [][]f3_DCT,int row,int line)
	{
		int[] f3_bits = new int[bit_len];
		int read_len = 1;
		int line_pos = 1;
		int row_pos = 1;
		while (read_len <= bit_len){
		    if (f3_DCT[line_pos][row_pos]!=0)
		    {
		        if ((f3_DCT[line_pos][row_pos]%2)==0)
		        {
		        	f3_bits[read_len] = 0;
		        }
		        else
		        {
		            f3_bits[read_len]= 1;
		        }
		        read_len = read_len + 1;
		    }
		    row_pos = row_pos + 1;
		    if (row_pos == row + 1){
		        row_pos = 1;
		        if (line_pos < line)
		        {
		        	line_pos = line_pos + 1;
		        }
		        else
		        {
		        	System.out.println("F3隐写: 写入的隐秘信息存在缺失");
		            break;
		        }
		    }
		}
		if (Arrays.equals(bits_info,f3_bits))
		{
			System.out.println("F3隐写: 读取与写入一致");
		}
		else
		{
			System.out.println("F3隐写: 读取与写入不一致");
		}
	}
}
	