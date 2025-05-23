/*@author rober*/
package videojuego.enemigos;


import videojuego.personajes.Personajes;

public abstract class Enemigos {
    private String nombre;
    private String superPoder;
    private double vida=100.00;
    private double daño;

    public Enemigos(String nombre, String superPoder, double vida, double daño) {
        this.nombre = nombre;
        this.superPoder = superPoder;
        this.vida = vida;
        this.daño = daño;
    }

    // métodos abstractos
   // método para recibir daños
    public void recibirDaños(double dañoArma){
        vida = vida-dañoArma;
    }
    // métodos getter y setter
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDaño() {
        return daño;
    }

    public String getSuperPoder() {
        return superPoder;
    }

    public void setSuperPoder(String superPoder) {
        this.superPoder = superPoder;
    }

    public double getVida() {
        return vida;
    }

    public void setVida(double vida) {
        this.vida = vida;
    }

    public void setDaño(double daño) {
        this.daño = daño;
    }

    @Override
    public String toString() {
        return "Enemigos [nombre=" + nombre + ", superPoder=" + superPoder + ", vida=" + vida + "daño"+ daño +"]";
    }

    public void atacar(Personajes jugador){
        jugador.setVida(jugador.getVida() - this.getDaño());
    }
}