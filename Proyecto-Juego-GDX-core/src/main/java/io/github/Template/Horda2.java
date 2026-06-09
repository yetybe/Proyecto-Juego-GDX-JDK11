package io.github.Template;

import com.badlogic.gdx.Gdx;
import java.util.Random;
import io.github.Pantallas.PantallaJuego;
import io.github.ConstructoresEnemigos.BuilderEnemigo;
	
public class Horda2 extends TemplateHorda{
	@Override
    protected void generarEnemigosMelee(PantallaJuego juego) {
		Random r = new Random();
        BuilderEnemigo builderMelee = juego.getConstructores().get(0); 
        
        // i < (cantidad de enemigos)
        for (int i = 0; i < 6; i++) {
            int x = r.nextInt(Gdx.graphics.getWidth());
            int y = Gdx.graphics.getHeight() + 50;
            juego.agregarEnemigo(builderMelee.setPosicion(x, y).build());
        }
    }

    @Override
    protected void generarEnemigosDistancia(PantallaJuego juego) {
    	Random r = new Random();
        BuilderEnemigo builderDistancia = juego.getConstructores().get(1); // Índice 1
        
        // i < (cantidad de enemigos)
        for (int i = 0; i < 3; i++) {
            int x = r.nextInt(Gdx.graphics.getWidth());
            int y = Gdx.graphics.getHeight() + 100;
            juego.agregarEnemigo(builderDistancia.setPosicion(x, y).build());
        }
    }
    
    @Override
    protected void generarJefe(PantallaJuego juego) {
        // todo
    }
}
