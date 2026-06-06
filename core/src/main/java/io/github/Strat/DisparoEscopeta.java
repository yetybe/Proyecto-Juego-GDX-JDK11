package io.github.Strat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import io.github.Personaje.Bullet;
import io.github.Pantallas.PantallaJuego;

public class DisparoEscopeta implements EstrategiaDisparo {

    @Override
    public void disparar(PantallaJuego juego, float origenX, float origenY, Texture txBala) {
        Vector3 posicionMouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        juego.getCamera().unproject(posicionMouse);
        
        float dx = posicionMouse.x - origenX;
        float dy = posicionMouse.y - origenY;
        
        float anguloCentral = (float) Math.atan2(dy, dx);
        float anguloIzquierda = anguloCentral + 0.25f;
        float anguloDerecha = anguloCentral - 0.25f;
        
        float velocidadBala = 400f; 

        float velX1 = (float) Math.cos(anguloCentral) * velocidadBala;
        float velY1 = (float) Math.sin(anguloCentral) * velocidadBala;
        Bullet bala1 = new Bullet(origenX - 5, origenY - 5, velX1, velY1, txBala);

        float velX2 = (float) Math.cos(anguloIzquierda) * velocidadBala;
        float velY2 = (float) Math.sin(anguloIzquierda) * velocidadBala;
        Bullet bala2 = new Bullet(origenX - 5, origenY - 5, velX2, velY2, txBala);

        float velX3 = (float) Math.cos(anguloDerecha) * velocidadBala;
        float velY3 = (float) Math.sin(anguloDerecha) * velocidadBala;
        Bullet bala3 = new Bullet(origenX - 5, origenY - 5, velX3, velY3, txBala);

        juego.agregarBala(bala1);
        juego.agregarBala(bala2);
        juego.agregarBala(bala3);
    }
}
