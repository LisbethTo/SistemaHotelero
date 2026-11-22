import java.time.LocalDate;

public abstract class Hotel {
    public abstract Habitacion BuscarHabitacion(String tipo, LocalDate entrada, LocalDate salida);
    public abstract boolean VerificarDisponibilidad(Habitacion habitacion, LocalDate entrada, LocalDate salida);
    public abstract void RegistrarHuesped(Huesped huesped);
    public abstract boolean RegistrarReserva(Reserva reserva);
    public abstract void ActualizarEstados();
    


}
