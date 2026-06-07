package io.github.Template;

import io.github.Pantallas.PantallaJuego;

public abstract class TemplateHorda {
	public final void armarHorda(PantallaJuego juego) {
        generarEnemigosMelee(juego);	
        generarEnemigosDistancia(juego);
        generarJefe(juego); 
    }
	
	protected abstract void generarEnemigosMelee(PantallaJuego juego);
    protected abstract void generarEnemigosDistancia(PantallaJuego juego);
    protected void generarJefe(PantallaJuego juego) {}

}
