package io.github.Strat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import io.github.Personaje.Bullet;
import io.github.Pantallas.PantallaJuego;

public class DisparoNormal implements ModoDisparo {

    @Override
    public void disparar(PantallaJuego juego, float origenX, float origenY, Texture txBala) {
        Vector3 posicionMouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        juego.getCamera().unproject(posicionMouse);

        float dx = posicionMouse.x - origenX;
        float dy = posicionMouse.y - origenY;

        float angulo = (float) Math.atan2(dy, dx);

        float velocidadBala = 400f; 
        float velX = (float) Math.cos(angulo) * velocidadBala;
        float velY = (float) Math.sin(angulo) * velocidadBala;

        Bullet bala = new Bullet(origenX - 5, origenY - 5, velX, velY, txBala, 10, 30);
        juego.agregarBala(bala);
    }
}
