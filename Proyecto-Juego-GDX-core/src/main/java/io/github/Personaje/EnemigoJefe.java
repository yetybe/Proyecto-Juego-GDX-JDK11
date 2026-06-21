package io.github.Personaje;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import io.github.Pantallas.PantallaJuego;

public class EnemigoJefe extends EnemigoDistancia {

    public EnemigoJefe(int vida, float vel, int daño, int xp, Texture txJefe, Texture txBala, Sound sonidoAtq, int x, int y) {
        super(vida, vel, daño, xp, txJefe, txBala, sonidoAtq, x, y);
        
        this.distanciaOrbita = 600f; 
        this.tiempoEntreDisparos = 0.5f; 
    }

    @Override
    public void atacar(PantallaJuego juego) {
        float origenX = spr.getX() + spr.getWidth() / 2;
        float origenY = spr.getY() + spr.getHeight() / 2;
        
        Jugador jugador = juego.getJugador();
        float destX = jugador.getX() + jugador.spr.getWidth() / 2;
        float destY = jugador.getY() + jugador.spr.getHeight() / 2;

        float dx = destX - origenX;
        float dy = destY - origenY;
        float anguloBase = (float) Math.atan2(dy, dx); 

        for (int i = -1; i <= 1; i++) {
            float angulo = anguloBase + (i * 0.2f); 
            
            float velX = (float) Math.cos(angulo) * 250f; 
            float velY = (float) Math.sin(angulo) * 250f;
            
            juego.agregarBalaEnemiga(new Bullet(origenX, origenY, velX, velY, txBala, 15, 15));
        }
        if (sonidoAtaque != null) sonidoAtaque.play();
    }
}
