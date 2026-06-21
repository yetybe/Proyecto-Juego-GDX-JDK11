package io.github.Personaje;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch; // Importación correcta
import io.github.Pantallas.PantallaJuego;

public class EnemigoMelee extends Enemigo {
    
    private float tiempoEntreAtaques = 1.0f; 
    private float tiempoFaltante = 0f;
    
    // Definimos el tamaño visual fijo que queremos en la pantalla (Ejemplo: 64x64 píxeles)
    private static final float TAMANO_VISUAL = 150f;
    // Hitbox real de 5x5
    private static final float TAMANO_HITBOX = 15f; 

    public EnemigoMelee(int vidaMax , float velocidad , int daño, int dropXp, Texture tx, Sound sonidoAtaque, int x, int y) {         
        super(vidaMax, velocidad, daño, new Sprite(tx), sonidoAtaque, dropXp);
        
        this.spr.setBounds(x, y, TAMANO_HITBOX, TAMANO_HITBOX); 
    }

    @Override
    public void draw(SpriteBatch batch) {

        float offset = (TAMANO_VISUAL - TAMANO_HITBOX) / 2f;

        batch.draw(
            this.spr.getTexture(),              // La textura PNG original
            this.spr.getX() - offset,           // Posición X visual centrada
            this.spr.getY() - offset,           // Posición Y visual centrada
            TAMANO_VISUAL,                      // Ancho real en pantalla
            TAMANO_VISUAL                       // Alto real en pantalla
        );
    }

    @Override
    public void update(PantallaJuego juego) {
        if (muerto) return;

        Jugador jugador = juego.getJugador();
        
        if (jugador != null && !jugador.estaMuerto()) {
            
            if (tiempoFaltante > 0) {
                tiempoFaltante -= Gdx.graphics.getDeltaTime();
            }
            
            if (this.getArea().overlaps(jugador.getArea())) {
                if (tiempoFaltante <= 0) {
                    this.atacar(juego);
                    tiempoFaltante = tiempoEntreAtaques; 
                }
            } else {
                // Cálculo de persecución exacto usando el centro de la hitbox de 5x5
                float centroEnemigoX = this.spr.getX() + (TAMANO_HITBOX / 2f);
                float centroEnemigoY = this.spr.getY() + (TAMANO_HITBOX / 2f);

                float centroJugadorX = jugador.getArea().getX() + (jugador.getArea().getWidth() / 2f);
                float centroJugadorY = jugador.getArea().getY() + (jugador.getArea().getHeight() / 2f);
                
                float dx = centroJugadorX - centroEnemigoX;
                float dy = centroJugadorY - centroEnemigoY;
                
                float angulo = (float) Math.atan2(dy, dx);
                float nuevaX = this.spr.getX() + (float) Math.cos(angulo) * velocidadMax;
                float nuevaY = this.spr.getY() + (float) Math.sin(angulo) * velocidadMax;
                
                this.spr.setPosition(nuevaX, nuevaY);
            }
        }
    }
    
    @Override
    public void atacar(PantallaJuego juego) {
        Jugador pjJugador = juego.getJugador();
        pjJugador.recibirDaño(this.getDañoAtaque());
        
        if (this.getSonidoAtq() != null) {
            this.getSonidoAtq().play();
        }
        
        // Efecto de empuje
        pjJugador.setPosicionSpr(50, 50);
    }
}