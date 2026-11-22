import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SistemaHotelero extends Hotel{
    public Habitacion habitacion = new Habitacion();

    public List<Huesped> huespedes = new ArrayList<>();
    public List<Reserva> reservas = new ArrayList<>();
    
    @Override
    public Habitacion BuscarHabitacion(String tipo, LocalDate entrada, LocalDate salida) {
        for (Habitacion h : habitacion.habitaciones) { //recorre la lista de habitaciones

            if (h.tipo.equals(tipo)) {

                if (VerificarDisponibilidad(h, entrada, salida)) { //llama al metodo

                    return h;
                }
            }
        }

        return null;
    }


    @Override
    public boolean VerificarDisponibilidad(Habitacion habitacion,
                    LocalDate entrada, LocalDate salida) {

        if (!salida.isAfter(entrada)) {
            return false;
        }

        for (Reserva r : reservas) {

            if (r.habitacion.numero == habitacion.numero) {

                if (entrada.isBefore(r.fechaSalida) && salida.isAfter(r.fechaEntrada)) {

                    return false;
                }
            }
        }

        return true;
    }


    @Override
    public void RegistrarHuesped(Huesped huesped) {
         huespedes.add(huesped);
    }


    @Override
    public boolean RegistrarReserva(Reserva reserva) {
         if (VerificarDisponibilidad(reserva.habitacion, reserva.fechaEntrada, reserva.fechaSalida)) {

            reservas.add(reserva);

            ActualizarEstados();

            return true;
        }

        return false;
    }

    @Override
    public void ActualizarEstados() {

        LocalDate hoy = LocalDate.now();

        for (Habitacion h : habitacion.habitaciones) {

            h.estado = "Disponible";

            for (Reserva r : reservas) {

                if (r.habitacion.numero == h.numero) {

                    if (!hoy.isBefore(r.fechaEntrada) && hoy.isBefore(r.fechaSalida)) {

                        h.estado = "Ocupada";
                        break;

                    } else if (hoy.isBefore(r.fechaEntrada)) {

                        h.estado = "Reservada";
                    }
                }
            }
        }
    }
}

    

    