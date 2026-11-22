import java.util.ArrayList;
import java.util.List;

public class Habitacion{
    public int numero;
    public String tipo;
    public String estado;

    public void CrearHabitacion(int numero, String tipo) {

        this.numero = numero;
        this.tipo = tipo;
        this.estado = "Disponible";
    }
    
    public List<Habitacion> habitaciones = new ArrayList<>();



    public void ListaHabitaciones(List <Habitacion> habitaciones){
        for (int i = habitaciones.size(); i < 9; i++) {

            Habitacion h = new Habitacion();

            if (i <= 3) {
                h.CrearHabitacion(i, "Individual");

            } else if (i <= 6) {
                h.CrearHabitacion(i, "Doble");

            } else {
                h.CrearHabitacion(i, "Familiar");
            }

            habitaciones.add(h);
   
        }

        
    }



        






    



}