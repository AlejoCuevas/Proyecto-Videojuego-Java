package Escenas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.javaknight.game.JavaKnight;
import com.javaknight.game.entity.PlayableCharacter;

public class Hud implements Disposable{

	public Stage escenario;
	private Viewport viewport;
	
	private Integer tiempoMundo;
	private float contadorTiempo;
	private Integer puntuacion;
	
	Label cuentaregresivaLabel;
	Label puntuacionLabel;
	Label tiempoLabel;
	Label nivelLabel;
	Label mundoLabel;
	Label javaLabel;


	// VIDA

	Texture healthEmptyBar;
	Texture health;
	
	
	public Hud(SpriteBatch sb) {

		healthEmptyBar = new Texture("health_empty.png");
		health = new Texture("health_point.png");

		tiempoMundo = 300;
		contadorTiempo = 0;
		puntuacion = 0;
		
		viewport = new FitViewport(JavaKnight.V_WIDTH, JavaKnight.V_HEIGHT, new OrthographicCamera());
		escenario = new Stage(viewport, sb);
		
		Table tabla = new Table();
		tabla.top();
		tabla.setFillParent(true);
		
		cuentaregresivaLabel = new Label(String.format("%03d", tiempoMundo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		puntuacionLabel = new Label(String.format("%06d", puntuacion), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		tiempoLabel = new Label("TIEMPO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		nivelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		mundoLabel = new Label("MUNDO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		javaLabel = new Label("JAVA", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		tabla.add(javaLabel).expandX().padTop(10);
		tabla.add(mundoLabel).expandX().padTop(10);
		tabla.add(tiempoLabel).expandX().padTop(10);
		tabla.row();
		tabla.add(puntuacionLabel).expandX();
		tabla.add(nivelLabel).expandX();
		tabla.add(cuentaregresivaLabel).expandX();
		
		escenario.addActor(tabla);

	}


	@Override
	public void dispose() {
		escenario.dispose();
		
	}

	public void render(Batch b, float life, float maxLife) {
		float per = life * 100 / maxLife / 100;
		b.begin();
		int size = 2;
		int x = 30;
		float y = 30;
		b.draw(healthEmptyBar, x+0,y,53*size,15*size);
		b.draw(health, x+16*size,y+6*size,(35*size) * per,6*size);
		b.end();
	}


}
