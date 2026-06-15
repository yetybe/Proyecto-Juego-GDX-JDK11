package io.github.Template;

import com.badlogic.gdx.Gdx;
import java.util.Random;
import io.github.Pantallas.PantallaJuego;
import io.github.ConstructoresEnemigos.BuilderEnemigo;

public class Horda3 extends TemplateHorda{
	@Override
    protected void generarEnemigosMelee(PantallaJuego juego) {
		Random r = new Random();
        BuilderEnemigo builderMelee = juego.getConstructores().get(0); 
        
        int ancho = Gdx.graphics.getWidth();
        int alto = Gdx.graphics.getHeight();
        int margen = 100;
        
        // i < (cantidad de enemigos)
        for (int i = 0; i < 10; i++) {
        	int x, y;
        	int borde = r.nextInt(4);
            
        	if (borde == 0) { // Arriba
                x = r.nextInt(ancho);
                y = alto + margen;
            } else if (borde == 1) { // Abajo
                x = r.nextInt(ancho);
                y = -margen;
            } else if (borde == 2) { // Izquierda
                x = -margen;
                y = r.nextInt(alto);
            } else { // Derecha
                x = ancho + margen;
                y = r.nextInt(alto);
            }
        	
            juego.agregarEnemigo(builderMelee.setPosicion(x, y).build());
        }
    }

    @Override
    protected void generarEnemigosDistancia(PantallaJuego juego) {} // no se generan  
}
