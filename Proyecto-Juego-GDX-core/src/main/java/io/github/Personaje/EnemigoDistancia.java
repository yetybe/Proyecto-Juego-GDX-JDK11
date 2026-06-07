package io.github.Personaje;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.Pantallas.PantallaJuego;

public class EnemigoDistancia extends Enemigo {
        
    private float rangoDeTiro = 250f; 
    private float tiempoEntreDisparos = 1.5f; 
    private float tiempoFaltante = 0f;
    private Texture txBala; 

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
            
            // DISPARO CONSTANTE e INDEPENDIENTE (Controlado por cooldown desde que spawnea)
            if (tiempoFaltante > 0) {
                tiempoFaltante -= Gdx.graphics.getDeltaTime();
            }
            
            if (tiempoFaltante <= 0) {
                this.atacar(juego);
                tiempoFaltante = tiempoEntreDisparos; 
            }

            // PATRÓN DE MOVIMIENTO DINÁMICO (Aproximación + Órbita)
            float dx = jugador.getX() - this.spr.getX();
            float dy = jugador.getY() - this.spr.getY();
            float distancia = (float) Math.sqrt((dx * dx) + (dy * dy));
            float anguloBase = (float) Math.atan2(dy, dx);
            
            float nuevaX = this.spr.getX();
            float nuevaY = this.spr.getY();

            // Si está excesivamente lejos del mapa, se aproxima
            if (distancia > 400f) {
                nuevaX += (float) Math.cos(anguloBase) * velocidadMax;
                nuevaY += (float) Math.sin(anguloBase) * velocidadMax;
            } else {
                //Si ya está en rango, le suma 90 grados (PI / 2) 
                //al ángulo matemático para empezar a orbitar lateralmente alrededor del jugador
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
            this.sonidoAtaque.play(); // Sonido de disparo
        }

        Jugador jugador = juego.getJugador();

        // Calcular el centro del enemigo (origen)
        float origenX = spr.getX() + spr.getWidth() / 2;
        float origenY = spr.getY() + spr.getHeight() / 2;
        
        // Calcular el centro del jugador (destino)
        float destX = jugador.getX() + jugador.spr.getWidth() / 2;
        float destY = jugador.getY() + jugador.spr.getHeight() / 2;

        // Obtener el ángulo de disparo
        float dx = destX - origenX;
        float dy = destY - origenY;
        float angulo = (float) Math.atan2(dy, dx);
        
        // Calcular la velocidad en X e Y
        float velocidadBala = 300f; //qué tan rápido viaja la bala enemiga
        float velX = (float) Math.cos(angulo) * velocidadBala;
        float velY = (float) Math.sin(angulo) * velocidadBala;
        
        Bullet bala = new Bullet(origenX - 5, origenY - 5, velX, velY, txBala, 30,30);
        juego.agregarBalaEnemiga(bala); 
    }
}