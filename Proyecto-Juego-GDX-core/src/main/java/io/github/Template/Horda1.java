package io.github.Template;

import com.badlogic.gdx.Gdx;
import java.util.Random;
import io.github.Pantallas.PantallaJuego;
import io.github.ConstructoresEnemigos.BuilderEnemigo;
	
public class Horda1 extends TemplateHorda{
	@Override
    protected void generarEnemigosMelee(PantallaJuego juego) {
		Random r = new Random();
        BuilderEnemigo builderMelee = juego.getConstructores().get(0); 
        
        // i < (cantidad de enemigos)
        for (int i = 0; i < 10; i++) {
            int x = r.nextInt(Gdx.graphics.getWidth());
            int y = Gdx.graphics.getHeight() + 50 + r.nextInt(100);

            juego.agregarEnemigo(builderMelee.setPosicion(x, y).build());
        }
    }

    @Override
    protected void generarEnemigosDistancia(PantallaJuego juego) {}
}
