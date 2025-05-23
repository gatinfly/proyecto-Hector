package utils;

import videojuego.enemigos.*;
import videojuego.personajes.*;
import videojuego.armas.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Clase donde estarán todos los metodos que servirán para el juego en si
public class Juego {
    Textos t = new Textos();
    
    // Metodo principal para el juego
    public void jugar(Personajes jugador, Enemigos enemigo, ArrayList<Personajes> personajes, ArrayList<Armas> equipo, Toolbox tb, Scanner sc, Objetos o) throws FileNotFoundException {
        boolean jugando = true;
        Aleatorio a = new Aleatorio();
        CargarGuardarPartida cg = new CargarGuardarPartida();
        GuardarDB gdb = new GuardarDB();

        tb.introduccion(personajes);
        while (jugando) {
            t.menuJuego();
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1": // menu MUNDO
                    boolean enMundo = true;
                    while (enMundo){
                        t.menuMundo();
                        opcion = sc.nextLine();
                        switch (opcion) {
                            case "1": // explorar cueva 
                                System.out.println("Explorando la cueva...");
                                System.out.println("¡Apareció un enemigo!");
                                Enemigos nuevoEnemigo = new EsbirrosDeLaLuz("Esbirros de la luz", "100", 100, a.numero(10));
                                enfrentarEnemigo(jugador, nuevoEnemigo, tb, sc);
                                break;
                            case "2": // enfrentar al jefe final
                                enfrentarEnemigo(jugador, enemigo, tb, sc);
                                break;
                            case "3": // tienda de armas
                                tb.tiendaArmas(personajes, sc, equipo, o);
                                break;
                            case "4": // tienda de mascotas
                                tb.tiendaMascotas(personajes, sc);
                                break;
                            case "5": // abrir el menú MENÚ
                                enMundo = false;
                                break;
                            default:
                                System.out.println("opcion"+opcion);
                                System.out.println("Valor no válido, intente otra vez");
                        }
                    }
                    break;
                case "2": // mostrar info del jugador
                    mostrarEstado(jugador);
                    break;
                case "3": // guardar partida
                    gdb.guardarPartida(jugador);
                    break;
                case "4": // sale al menú de crear personaje
                    System.out.println("Regresando al menú principal...");
                    jugando = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
    
    // Metodo para enfrentar a un enemigo
    public void enfrentarEnemigo(Personajes jugador, Enemigos enemigo, Toolbox tb, Scanner sc) {
        System.out.println("¡Un " + enemigo.getNombre() + " apareció!");

        while (enemigo.getVida() > 0 && jugador.getVida() > 0) {
            System.out.println("Tu vida: " + jugador.getVida());
            System.out.println(enemigo.getNombre() + " vida: " + enemigo.getVida());
            System.out.println("1. Atacar");
            System.out.println("2. Huir");
            System.out.print("Elige: ");
            String opcion = sc.nextLine();

            if (opcion.equals("1")) {
                if (jugador instanceof Guerrero) {
                    Guerrero g = (Guerrero) jugador;
                    g.atacar();
                    enemigo.setVida(enemigo.getVida() - g.getFuerza());
                    System.out.printf("Has atacado al %s con %.2f de daño\n", enemigo.getNombre(), g.getFuerza());
                } else if (jugador instanceof Mago) {
                    Mago m = (Mago) jugador;
                    m.atacar();
                    enemigo.setVida(enemigo.getVida() - m.getFuerza());
                    System.out.printf("Has atacado al %s con %.2f de daño\n", enemigo.getNombre(), m.getFuerza());
                } else if (jugador instanceof Arquero) {
                    Arquero a = (Arquero) jugador;
                    a.atacar();
                    enemigo.setVida(enemigo.getVida() - a.getFuerza());
                    System.out.printf("Has atacado al %s con %.2f de daño\n", enemigo.getNombre(), a.getFuerza());
                }

                // Ataque del enemigo al jugador
                enemigo.atacar(jugador);
            } else if (opcion.equals("2")) {
                System.out.println("Huiste del combate.");
                break;
            } else {
                System.out.println("Opción no válida.");
            }

            if (jugador.getVida() <= 0) {
                System.out.println("Has sido derrotado...");
                jugador.setVida(100);
                return;
            }

            if (enemigo.getVida() <= 0) {
                System.out.println("¡Has vencido al " + enemigo.getNombre() + "!");
                tb.ganarRecompensas(jugador);
                return;
            }
        }
    }
    
    public void mostrarEstado(Personajes p) {
        System.out.println("=== ESTADO DEL JUGADOR ===");
        System.out.println("Nombre: " + p.getNombre());
        System.out.println("Vida: " + p.getVida());
        System.out.println("Fuerza: " + p.getFuerza());
        System.out.println("Experiencia: " + p.getExperiencia());
        System.out.println("Monedas: " + p.getMoneda());
        System.out.println("Nivel de experiencia: " + p.getNivelExperiencia());
    }

    public Enemigos generarEnemigoAleatorio() {
        Aleatorio a = new Aleatorio();
        int num = a.numero(5);

        switch (num) {
            case 1:
                return new PadreCorredor("Padre corredor", "100", 100, a.numero(50), 100);
            case 2:
                return new PadreLadron("Padre ladron", "100", 100, a.numero(50), true);
            case 3:
                return new PadreHerederoDeSatan("Heredero de satan", "100", 100, a.numero(50), true);
            case 4:
                return new PadreSecretarioDelSenor("Padre secretario del señor", "100", 100, a.numero(50), true);
            default:
                return new PadreSuperior("Padre superior", "x", 100, a.numero(50),
                        new PadreSecretarioDelSenor("Padre secretario del señor", "100", 100, a.numero(50), true));
        }
    }
}
