package io.github.Template;

import java.util.Random;

import com.badlogic.gdx.Gdx;

import io.github.ConstructoresEnemigos.BuilderEnemigo;
import io.github.Pantallas.PantallaJuego;

public class Horda4 extends TemplateHorda{
	@Override
    protected void generarEnemigosMelee(PantallaJuego juego) {}

    @Override
    protected void generarEnemigosDistancia(PantallaJuego juego) {
    	Random r = new Random();
        BuilderEnemigo builderMelee = juego.getConstructores().get(0); 
        
        // i < (cantidad de enemigos)
        for (int i = 0; i < 5; i++) {
            int x = r.nextInt(Gdx.graphics.getWidth());
            int y = r.nextInt(Gdx.graphics.getWidth());
            juego.agregarEnemigo(builderMelee.setPosicion(x, y).build());
        }
    }
}
