package io.github.Personaje;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.Pantallas.PantallaJuego;

public class EnemigoMelee extends Enemigo{
	
	private float tiempoEntreAtaques = 1.0f; 
    private float tiempoFaltante = 0f;
    
	public EnemigoMelee(int vidaMax , float velocidad , int daño, int dropXp, Texture tx, Sound sonidoAtaque, int x, int y) {         
        //  de vida máxima, 2f de velocidad, 10 de daño de ataque
        super(vidaMax, velocidad, daño, new Sprite(tx), sonidoAtaque , dropXp);
        
        this.spr.setPosition(x, y);
        this.spr.setBounds(x, y, 40, 40); // Tamaño del enemigo
    }
	
	@Override
    public void update(PantallaJuego juego) {
        if (muerto) return;

        // 1. Necesitamos saber dónde está el jugador
        Jugador jugador = juego.getJugador();
        
        if (jugador != null && !jugador.estaMuerto()) {
            
        	if (tiempoFaltante > 0) {
                tiempoFaltante -= Gdx.graphics.getDeltaTime();
            }
        	// 2. El enemigo mismo evalúa la colisión (Overlap)
            if (this.getArea().overlaps(jugador.getArea())) {
                if (tiempoFaltante <= 0) {
                    this.atacar(juego);
                    tiempoFaltante = tiempoEntreAtaques; // Reinicia el temporizador
                }
            } else {
                // 3. Si no están chocando, se mueve hacia el jugador
                float dx = jugador.getX() - this.spr.getX();
                float dy = jugador.getY() - this.spr.getY();
                
                float angulo = (float) Math.atan2(dy, dx);
                
                float nuevaX = this.spr.getX() + (float) Math.cos(angulo) * velocidadMax;
                float nuevaY = this.spr.getY() + (float) Math.sin(angulo) * velocidadMax;
                
                this.spr.setPosition(nuevaX, nuevaY);
            }
        
        }
    }
	
	@Override
	public void atacar(PantallaJuego juego) {
		// Ejecuta el daño
		Jugador pjJugador = juego.getJugador();
		pjJugador.recibirDaño(this.getDañoAtaque());
        
        // Ejecuta su propio sonido
        if (this.getSonidoAtq() != null) {
            this.getSonidoAtq().play();
        }
	}
	
}
