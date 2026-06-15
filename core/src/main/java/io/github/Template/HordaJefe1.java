package io.github.Template;

import java.util.Random;

import com.badlogic.gdx.Gdx;

import io.github.ConstructoresEnemigos.BuilderEnemigo;
import io.github.Pantallas.PantallaJuego;



public class HordaJefe1 extends TemplateHorda{
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
    protected void generarEnemigosDistancia(PantallaJuego juego) {} //no se generan
	
	
	@Override
    protected void generarJefe(PantallaJuego juego) {
		BuilderEnemigo builderJefe = juego.getConstructores().get(2);
		int x = Gdx.graphics.getWidth() / 2 - 130; 
		int y = Gdx.graphics.getHeight();

		juego.agregarEnemigo(builderJefe.setPosicion(x, y).build());
		juego.jefesVivos++;
		
    }
}
