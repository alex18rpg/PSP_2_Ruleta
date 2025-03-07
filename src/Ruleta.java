import java.util.Scanner;

public class Ruleta {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("¿Cuántas veces quieres girar la ruleta? ");
        int numeroDeGiros = scanner.nextInt();

        for (int giro = 1; giro <= numeroDeGiros; giro++) {
            System.out.println("\n--- Giro " + giro + " ---");
            Thread[] jugadores = new Thread[4];

            for (int i = 0; i < 4; i++) {
                String nombre = "Jugador " + (i + 1);
                jugadores[i] = new Thread(new Jugador(nombre));
                jugadores[i].start();
            }
            for (int i = 0; i < 4; i++) {
                try {
                    jugadores[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Jugador.reset();
        }

        Jugador.mostrarEstadisticas();
        System.out.println("El juego ha terminado.");
    }
}