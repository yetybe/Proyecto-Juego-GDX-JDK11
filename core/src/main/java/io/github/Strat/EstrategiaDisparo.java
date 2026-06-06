package io.github.Strat;

import com.badlogic.gdx.graphics.Texture;
import io.github.Pantallas.PantallaJuego;

public interface EstrategiaDisparo {
    void disparar(PantallaJuego juego, float origenX, float origenY, Texture txBala);
}
