package io.github.Template;

import com.badlogic.gdx.Gdx;
import java.util.Random;
import io.github.Pantallas.PantallaJuego;
import io.github.ConstructoresEnemigos.BuilderEnemigo;

public class HordaJefe1 extends TemplateHorda{
	@Override
    protected void generarEnemigosMelee(PantallaJuego juego) {
        // Genera muchos enemigos para acorralar
    }
	@Override
    protected void generarEnemigosDistancia(PantallaJuego juego) {
        // Genera artillería
    }
	@Override
    protected void generarJefe(PantallaJuego juego) {
        // Aquí es donde sobrescribes el gancho.
        // En el futuro, si creas un BuilderJefe (índice 2), lo llamas aquí.
        // Por ahora, puedes hacer que salgan 5 enemigos a distancia juntos para simular dificultad.
    }

}
