package print;
import java.awt.Graphics;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.*;
public class PrintTest implements Printable{   
   /** 
 
   * @param Graphicָ����ӡ��ͼ�λ��� 
 
   * @param PageFormatָ����ӡҳ��ʽ��ҳ���С�Ե�Ϊ������λ��1��Ϊ1Ӣ�ŵ�1/72��1Ӣ��Ϊ25.4���ס�A4ֽ����Ϊ595��842�㣩 
 
   * @param pageIndexָ��ҳ�� 
 
   **/  
  
   public int print(Graphics gra, PageFormat pf, int pageIndex) throws PrinterException {  
       
       System.out.println("pageIndex="+pageIndex);   
       Component c = null;  
       
      //print string    
      String str = "������������";  
  
      //ת����Graphics2D
      Graphics2D g2 = (Graphics2D) gra;  
  
      //���ô�ӡ��ɫΪ��ɫ  
      g2.setColor(Color.black);  
  
      //��ӡ�������  
      double x = pf.getImageableX();  
      double y = pf.getImageableY();  
   
      switch(pageIndex){  
         case 0:  
           //���ô�ӡ���壨�������ơ���ʽ�͵��С�����������ƿ�������������߼����ƣ�  
           //Javaƽ̨���������������ϵ�У�Serif��SansSerif��Monospaced��Dialog �� DialogInput  
           Font font = new Font("������", Font.PLAIN, 8);  
           g2.setFont(font);//��������  
  
           //BasicStroke bs_3=new BasicStroke(0.5f);     
  
           float[] dash1 = {2.0f};   
  
           //���ô�ӡ�ߵ����ԡ�   
           //1.�߿� 2��3����֪����4���հ׵Ŀ�ȣ�5�����ߵĿ�ȣ�6��ƫ����  
           g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2.0f, dash1, 0.0f));    
           //g2.setStroke(bs_3);//�����߿� 
           float heigth = font.getSize2D();//����߶�   
           System.out.println("x="+x);  
           // -1- ��Graphics2Dֱ�����   
           //���ַ��Ļ���(���²�)λ���û��ռ��е� (x, y) λ�ô�   
           //g2.drawLine(10,10,200,300);   
               
           Image src = Toolkit.getDefaultToolkit().getImage("F:\\workspace\\QQ.png");  
           g2.drawImage(src,(int)x,(int)y,c);    
           int img_Height=src.getHeight(c);  
           int img_width=src.getWidth(c);  
           //System.out.println("img_Height="+img_Height+"img_width="+img_width) ;  
  
          // g2.drawString(str, (float)x, (float)y+1*heigth+img_Height);
           g2.drawString(str, (float)x, (float)y+1*heigth+img_Height);  
           g2.drawLine((int)x,(int)(y+1*heigth+img_Height+10),(int)x+200,(int)(y+1*heigth+img_Height+10));       
           g2.drawString("���ţ�G400",(float)x, (float)y+1*heigth+img_Height+20);          
           g2.drawString("��ɫ����ɫ",(float)x, (float)y+1*heigth+img_Height+30);  

  
                return PAGE_EXISTS;  
  
         default:  
  
             return NO_SUCH_PAGE;  
  
      }  
         
   }  
    
public static void main(String[] args) {  
         
    //    ͨ���������顢�ĵ�   
    Book book = new Book();  
  
    //    ���ó�����   
    PageFormat pf = new PageFormat();    
    pf.setOrientation(PageFormat.PORTRAIT);  
  
    //ͨ��Paper����ҳ��Ŀհױ߾�Ϳɴ�ӡ���򡣱�����ʵ�ʴ�ӡֽ�Ŵ�С�����  
    Paper p = new Paper();  
    p.setSize(110,110);//ֽ�Ŵ�С   
    p.setImageableArea(10,0, 110,110);//A4(595 X 842)���ô�ӡ������ʵ0��0Ӧ����72��72����ΪA4ֽ��Ĭ��X,Y�߾���72  
    pf.setPaper(p);  
    
    //    �� PageFormat �� Printable ��ӵ����У����һ��ҳ��    
    book.append(new PrintTest(), pf); 
    
     //��ȡ��ӡ�������  
     PrinterJob job = PrinterJob.getPrinterJob();        
  
     // ���ô�ӡ��   
     job.setPageable(book);  
         
     try {
    	 
         //������printDialog��ʾ��ӡ�Ի������û�ȷ�Ϻ��ӡ��Ҳ����ֱ�Ӵ�ӡ  
         boolean a=job.printDialog(); 
         job.print();   
         if(a)
         {
             //job.print();   
         
         }else{
             job.cancel();
         }
     } catch (PrinterException e) {
         e.printStackTrace();  
     }  
  
   }  
  
}  
