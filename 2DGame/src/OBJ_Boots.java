import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends SuperObject{

    GamePanel gp;
    public OBJ_Boots(GamePanel gp){

        this.gp = gp;
        name = "Boots";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Objekte/Stiefel.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
    
}
