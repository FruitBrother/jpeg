package jpeg;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
public class Main {
	public static void writeToFile(String file, int[] data, int num){
		try{
			OutputStream os = new FileOutputStream(file);  
			DataOutputStream out = new DataOutputStream(os); 
			for (int i = 0; i<num; i++){
				out.write(data[i]);
			}
			out.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static void main(String[] args){
		String infile = "C:\\Users\\lining\\Desktop\\jpeg\\data\\lena.bmp";
		String outfile = "C:\\Users\\lining\\Desktop\\jpeg\\data\\lena.txt";
		int bit_len = 5;
		BmpReader reader = new BmpReader();
		
		img TheImg = reader.getTheData(infile);
		System.out.println(TheImg.width + " " + TheImg.height);
		
		int[] outdata = new int[(TheImg.width+8)*(TheImg.height+8)];
		
		int sx,sy;
		int[][] temp = new int[8][8]; 
		for (int i = 0 ; i<TheImg.height/8+1; i++){
			for (int j = 0; j<TheImg.width/8+1; j++){
				sx = 8*i;
				sy = 8*j;
				for (int p = sx; p<sx+8; p++){
					for (int q = sy; q<sy+8; q++){
						if (p<TheImg.height && q<TheImg.width)
							temp[p-sx][q-sy] = TheImg.data[q+p*TheImg.width];
						else 
							temp[p-sx][q-sy] = 0;
					}
				}
				JPEG.DCTandSCH(temp);
				for (int p = sx; p<sx+8; p++){
					for (int q = sy; q<sy+8; q++){
						outdata[q+p*TheImg.width] = temp[p-sx][q-sy];
					}
				}
			}
		}
		
		//here width && height need calculate
		F3alg.F3(outdata, bit_len, F3alg.secret(bit_len), TheImg.width, TheImg.height);
		
		Main.writeToFile(outfile,outdata,TheImg.width*TheImg.height);
	}
}
