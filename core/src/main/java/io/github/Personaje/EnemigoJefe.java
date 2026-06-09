package io.github.Personaje;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.Pantallas.PantallaJuego;

public class EnemigoJefe extends Enemigo {
    private Texture txBala;
    private float temporizadorDisparo = 0f;

    public EnemigoJefe(int vida, float vel, int daño, int xp, Sprite spr, Sound sonidoAtq, Texture txBala) {
        super(vida, vel, daño, spr, sonidoAtq, xp);
        this.txBala = txBala;
    }

    @Override
    public void atacar(PantallaJuego juego) {
        float origenX = spr.getX() + spr.getWidth() / 2;
        float origenY = spr.getY();
        
        for (int i = -1; i <= 1; i++) {
            float angulo = (float) (-Math.PI / 2) + (i * 0.2f); 
            float velX = (float) Math.cos(angulo) * 300f;
            float velY = (float) Math.sin(angulo) * 300f;
            juego.agregarBalaEnemiga(new Bullet(origenX - 5, origenY - 5, velX, velY, txBala, 30, 30));
        }
        if (sonidoAtaque != null) sonidoAtaque.play();
    }
    
    @Override
    public void update(PantallaJuego juego) {
        if (muerto) return;

        spr.setY(spr.getY() - velocidadMax); 

        temporizadorDisparo += Gdx.graphics.getDeltaTime();
        if (temporizadorDisparo >= 0.5f) { 
            atacar(juego);
            temporizadorDisparo = 0f;
        }
    }
}
