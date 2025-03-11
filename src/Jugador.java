import java.util.Random;

class Jugador implements Runnable {
    private String nombre;
    private int apuesta;
    private static int numeroGanador;
    private static boolean ganadorDeterminado = false;
    private static int totalApuestas = 0;
    private static int totalGanados = 0;
    private static int totalPerdidos = 0;

    public Jugador(String nombre) {
        this.nombre = nombre ;
    }

    @Override
    public void run() {
        // número aleatorio entre 0 y 50
        Random random = new Random();
        apuesta = random.nextInt(51);
        System.out.println(nombre + " ha apostado al número: " + apuesta);
        totalApuestas++;

        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Determinar el número ganador una vez que todos han apostado
        synchronized (Jugador.class) {
            if (!ganadorDeterminado) {
                numeroGanador = random.nextInt(51);
                System.out.println("La ruleta ha girado y el número ganador es: " + numeroGanador);
                ganadorDeterminado = true;
            }
        }

        // Verificar si el jugador ha ganado
        synchronized (Jugador.class) {
            if (ganadorDeterminado && apuesta == numeroGanador) {
                System.out.println(nombre + " ha ganado!");
                totalGanados++;
            } else if (ganadorDeterminado) {
                System.out.println(nombre + " no ha ganado.");
                totalPerdidos++;
            }
        }
    }

    public static void reset() {
        ganadorDeterminado = false;
    }

    public static void mostrarEstadisticas() {
        System.out.println("Estadísticas de los jugadores:");
        System.out.println("Total Apuestas: " + totalApuestas);
        System.out.println("Total Ganados: " + totalGanados);
        System.out.println("Total Perdidos: " + totalPerdidos);
    }
}