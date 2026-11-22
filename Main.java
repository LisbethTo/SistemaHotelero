import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SistemaHotelero hotel = new SistemaHotelero();

        

        hotel.habitacion.ListaHabitaciones(hotel.habitacion.habitaciones);

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("\nSISTEMA HOTELERO");
            System.out.println("1. Realizar reserva");
            System.out.println("2. Consultar habitaciones");
            System.out.println("0. Salir");

            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {

                System.out.print("Tipo de habitacion: ");
                String tipo = sc.nextLine();

                System.out.print("Fecha de entrada (AAAA-MM-DD): ");
                LocalDate entrada = LocalDate.parse(sc.nextLine());

                System.out.print("Fecha de salida (AAAA-MM-DD): ");
                LocalDate salida = LocalDate.parse(sc.nextLine());

                Habitacion h = hotel.BuscarHabitacion(tipo, entrada, salida);

                if (h != null) { //Si se encontró una habitación disponible, continuar con la reserva.

                    System.out.println("Habitacion disponible: " + h.numero);

                    Huesped huesped = new Huesped(); // Crea un nuevo objeto Huesped
                    huesped.CrearHuesped(sc); // Solicita los datos personales del huesped

                    Reserva reserva = new Reserva(); // Crea un nuevo objeto Reserva
                    reserva.CrearReserva(huesped, h, entrada, salida); // Asocia el huesped, la habitacion y las fechas

                    if (hotel.RegistrarReserva(reserva)) {  // Verifica la disponibilidad y registra la reserva

                        hotel.RegistrarHuesped(huesped);

                        System.out.println("Reserva exitosa.");
                        System.out.println(reserva.MostrarReserva());

                    } else {

                        System.out.println("No se pudo realizar la reserva.");
                    }

                } else {

                    System.out.println("No hay habitaciones disponibles o las fechas son invalidas.");
                }

            } else if (opcion == 2) {

                hotel.ActualizarEstados();

                for (Habitacion hab : hotel.habitacion.habitaciones) { //recorre la lista

                    System.out.println("Habitacion: " + hab.numero);
                    System.out.println("Tipo: " + hab.tipo);
                    System.out.println("Estado: " + hab.estado);
                    
                }

            } else if (opcion == 0) {

                System.out.println("Gracias por utilizar el sistema.");

            } else {

                System.out.println("Opcion incorrecta.");
            }
        }

        sc.close(); //cierra el scanner
    }
}