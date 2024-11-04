import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends  SuperObject{

    GamePanel gp;
     public OBJ_Key(GamePanel gp){

         this.gp = gp;
         name = "Key";

         try{
             image = ImageIO.read(getClass().getResourceAsStream("/Objekte/Schlüssel.png"));
             image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
         }catch (IOException e){
             e.printStackTrace();
         }
     }
}
