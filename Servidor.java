//Todo lo realizado en esta clase fue hecho por inteligencia artificial

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Servidor {

    static SistemaHotelero hotel = new SistemaHotelero();

    public static void main(String[] args) throws IOException {

        // Crea las habitaciones predeterminadas
        hotel.habitacion.ListaHabitaciones(hotel.habitacion.habitaciones);

        // Crea el servidor web
        HttpServer servidor = HttpServer.create(new InetSocketAddress(8081), 0);

        // Carga los archivos de la página
        servidor.createContext("/", peticion -> {
            MostrarArchivo(peticion, "web/index.html", "text/html");
        });

        servidor.createContext("/estilos.css", peticion -> {
            MostrarArchivo(peticion, "web/estilos.css", "text/css");
        });

        servidor.createContext("/script.js", peticion -> {
            MostrarArchivo(peticion, "web/script.js", "application/javascript");
        });

        servidor.createContext("/hotel.png", peticion -> {
            MostrarArchivo(peticion, "web/hotel.png", "image/png");
        });

        // Consulta las habitaciones
        servidor.createContext("/habitaciones", peticion -> {

            hotel.ActualizarEstados();

            String resultado = "";

            for (Habitacion h : hotel.habitacion.habitaciones) {

                resultado += h.numero + "|"
                        + h.tipo + "|"
                        + h.estado + "\n";
            }

            Responder(peticion, resultado, 200);

        });

        // Recibe los datos de la reserva
        servidor.createContext("/reservar", peticion -> {

            try {

                String contenido = new String(
                    peticion.getRequestBody().readAllBytes(),
                    StandardCharsets.UTF_8
                );

                Map<String, String> datos = new HashMap<>();

                for (String dato : contenido.split("&")) {

                    String[] partes = dato.split("=", 2);

                    if (partes.length == 2) {

                        datos.put(
                            URLDecoder.decode(partes[0], StandardCharsets.UTF_8),
                            URLDecoder.decode(partes[1], StandardCharsets.UTF_8)
                        );
                    }
                }

                String tipo = datos.get("tipo");

                LocalDate entrada = LocalDate.parse(datos.get("entrada"));
                LocalDate salida = LocalDate.parse(datos.get("salida"));

                // Valida las fechas
                if (entrada.isBefore(LocalDate.now())
                        || !salida.isAfter(entrada)) {

                    Responder(peticion, "Las fechas son incorrectas.", 400);
                    return;
                }

                // Busca una habitación disponible
                Habitacion h = hotel.BuscarHabitacion(tipo, entrada, salida);

                if (h != null) {

                    // Crea el huésped con los datos del formulario
                    Huesped huesped = new Huesped();

                    Scanner sc = new Scanner(
                        datos.get("nombre") + "\n"
                        + datos.get("identificacion") + "\n"
                        + datos.get("correo") + "\n"
                        + datos.get("numero") + "\n"
                    );

                    huesped.CrearHuesped(sc);
                    sc.close();

                    // Crea la reserva
                    Reserva reserva = new Reserva();

                    reserva.CrearReserva(huesped, h, entrada, salida);

                    // Registra la reserva y el huésped
                    if (hotel.RegistrarReserva(reserva)) {

                        hotel.RegistrarHuesped(huesped);

                        Responder(
                            peticion,
                            "Reserva exitosa.\n" + reserva.MostrarReserva(),
                            200
                        );

                    } else {

                        Responder(peticion, "No se pudo realizar la reserva.", 400);
                    }

                } else {

                    Responder(peticion, "No hay habitaciones disponibles.", 400);
                }

            } catch (Exception error) {

                Responder(peticion, "Verifique los datos ingresados.", 400);
            }

        });

        servidor.start();

        System.out.println("Servidor iniciado en el puerto 8080");
        System.out.println("http://localhost:8080");
    }

    // Muestra los archivos HTML, CSS y JavaScript
    public static void MostrarArchivo(HttpExchange peticion,
            String archivo, String tipo) throws IOException {

        byte[] contenido = Files.readAllBytes(Path.of(archivo));

        peticion.getResponseHeaders().set(
            "Content-Type", tipo + "; charset=UTF-8"
        );

        peticion.sendResponseHeaders(200, contenido.length);

        peticion.getResponseBody().write(contenido);
        peticion.close();
    }

    // Envía las respuestas de Java a la página
    public static void Responder(HttpExchange peticion,
            String mensaje, int codigo) throws IOException {

        byte[] contenido = mensaje.getBytes(StandardCharsets.UTF_8);

        peticion.getResponseHeaders().set(
            "Content-Type", "text/plain; charset=UTF-8"
        );

        peticion.sendResponseHeaders(codigo, contenido.length);

        peticion.getResponseBody().write(contenido);
        peticion.close();
    }
}