package Sprites.Armas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.w3c.dom.Text;

public class AK extends Sprite {
    public AK(){
        super(new Texture("ak.png"));
        setBounds(0, 0, 30, 10);
        setScale(0.7f);
        //setOrigin(getWidth()/2,getHeight()/2);
        //setOriginCenter();
    }
}
