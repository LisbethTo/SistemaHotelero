import java.time.LocalDate;

public class Reserva {

    public int idReserva;
    public int contador = 0;

    public LocalDate fechaEntrada;
    public LocalDate fechaSalida;

    public Huesped huesped;
    public Habitacion habitacion;

    public void CrearReserva(Huesped huesped,Habitacion habitacion, LocalDate entrada, LocalDate salida) {

        this.huesped = huesped;
        this.habitacion = habitacion;
        this.fechaEntrada = entrada;
        this.fechaSalida = salida;

        contador++;
        idReserva = contador;
    }

    public String MostrarReserva() {

        return "ID Reserva: " + idReserva + "\nHuesped: " + huesped.nombre + "\nHabitacion: " + habitacion.numero + "\nTipo: " + habitacion.tipo + "\nEntrada: " + fechaEntrada + "\nSalida: " + fechaSalida;
        
    }
}