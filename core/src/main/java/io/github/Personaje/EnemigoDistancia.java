package io.github.Personaje;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.Pantallas.PantallaJuego;

public class EnemigoDistancia extends Enemigo {
        
	protected float rangoDeTiro = 250f; 
    protected float tiempoEntreDisparos = 1.5f; 
    protected float tiempoFaltante = 0f;
    protected Texture txBala;
    protected float distanciaOrbita = 400f;

    public EnemigoDistancia(int vidaMax, float velocidad, int daño, int dropXp, Texture tx, Texture txBala, Sound sonidoAtaque, int x, int y) {         
        super(vidaMax, velocidad, daño, new Sprite(tx), sonidoAtaque, dropXp);
        this.txBala = txBala; 
        this.spr.setPosition(x, y);
        this.spr.setBounds(x, y, 60, 60); 
    }
    
    @Override
    public void update(PantallaJuego juego) {
        if (muerto) return;

        Jugador jugador = juego.getJugador();
        
        if (jugador != null && !jugador.estaMuerto()) {
            
            if (tiempoFaltante > 0) {
                tiempoFaltante -= Gdx.graphics.getDeltaTime();
            }
            
            if (tiempoFaltante <= 0) {
                this.atacar(juego);
                tiempoFaltante = tiempoEntreDisparos; 
            }

            float dx = jugador.getX() - this.spr.getX();
            float dy = jugador.getY() - this.spr.getY();
            float distancia = (float) Math.sqrt((dx * dx) + (dy * dy));
            float anguloBase = (float) Math.atan2(dy, dx);
            
            float nuevaX = this.spr.getX();
            float nuevaY = this.spr.getY();

            if (distancia > 400f) {
                nuevaX += (float) Math.cos(anguloBase) * velocidadMax;
                nuevaY += (float) Math.sin(anguloBase) * velocidadMax;
            } else {

                float anguloOrbita = anguloBase + (float) Math.PI / 2f;
                
                nuevaX += (float) Math.cos(anguloOrbita) * velocidadMax;
                nuevaY += (float) Math.sin(anguloOrbita) * velocidadMax;
            }
            
            this.spr.setPosition(nuevaX, nuevaY);
        }
    }  
    
    
    @Override
    public void atacar(PantallaJuego juego) {
        if (this.sonidoAtaque != null) {
            this.sonidoAtaque.play();
        }

        Jugador jugador = juego.getJugador();

        float origenX = spr.getX() + spr.getWidth() / 2;
        float origenY = spr.getY() + spr.getHeight() / 2;
        
        float destX = jugador.getX() + jugador.spr.getWidth() / 2;
        float destY = jugador.getY() + jugador.spr.getHeight() / 2;

        float dx = destX - origenX;
        float dy = destY - origenY;
        float angulo = (float) Math.atan2(dy, dx);
        
        float velocidadBala = 300f;
        float velX = (float) Math.cos(angulo) * velocidadBala;
        float velY = (float) Math.sin(angulo) * velocidadBala;
        
        Bullet bala = new Bullet(origenX - 5, origenY - 5, velX, velY, txBala, 30,30);
        juego.agregarBalaEnemiga(bala); 
    }
}