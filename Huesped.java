import java.util.Scanner;

public class Huesped extends Persona{
    public int idHuesped;
    public int contador = 0;


    public void CrearHuesped(Scanner sc){
        System.out.print("Nombre completo: ");
        nombre = sc.nextLine();

        System.out.print("Identificacion: ");
        identificacion = sc.nextLine();

        System.out.print("Correo: ");
        correo = sc.nextLine();

        System.out.print("Telefono: ");
        numero = sc.nextLine();

        contador++;
        idHuesped = contador;
   

    }
    public String MostrarHuesped() {

        return "ID Huesped: " + idHuesped
            + "\nNombre: " + nombre
            + "\nIdentificacion: " + identificacion
            + "\nCorreo: " + correo
            + "\nTelefono: " + numero;
    }








}


