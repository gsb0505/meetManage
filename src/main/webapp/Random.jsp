<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*" %> 
<html> 
<body> 
 <%  
    response.setHeader("Pragma","No-cache");  
    response.setHeader("Cache-Control","No-cache");  
    response.setHeader("Expires","0");  
      
      
    Random r=new Random();  
      
    int width=90,height=40;  
    BufferedImage pic=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  
      
    Graphics gc=pic.getGraphics();  
      
    gc.setColor(new Color(200+r.nextInt(50), 200+r.nextInt(50), 200+r.nextInt(50)));  
    gc.fillRect(0,0,width,height);  
      
   gc.setFont(new Font("MV Boli",Font.PLAIN,30));  
      String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
      
      String rs="";  
      String rn="";  
      for(int k=0;k<4;k++){  
        int index=r.nextInt(62);  
       	 rn = data.substring(index, index + 1);
         rs = rs+ rn;
         gc.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));  
         gc.drawString(rn,20*k,30);  
        
      }  
     
      session.setAttribute("random",rs.toLowerCase());  
      for (int i = 0; i < 3; i++) {
          gc.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
          gc.drawLine(r.nextInt(5), r.nextInt(height), r.nextInt(85), r.nextInt(height));
      }
      gc.dispose();  
      ImageIO.write(pic,"JPEG",response.getOutputStream());  
      out.clear();  
      out=pageContext.pushBody();  
  %> 
</body> 
</html> 