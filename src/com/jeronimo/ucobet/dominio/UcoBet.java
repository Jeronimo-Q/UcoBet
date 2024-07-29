package com.jeronimo.ucobet.dominio;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UcoBet {
    Scanner scanner = new Scanner(System.in);
    private List<Usuario> usuarios;
    private final static int LONGITUD_NUMERO_GANADOR = 4;
    private final static int ACIERTO = 0;
    private final static LocalTime HORA_CIERRE = LocalTime.of(20, 40);

    public UcoBet() {
        usuarios = new ArrayList<>();
    }

    //Se rectifica la hora de cierre de las apuestas
    private boolean verificadorTiempo() {
        LocalTime horaActual = LocalTime.now();
        return horaActual.isAfter(HORA_CIERRE);
    }

    //Se crean los usuarios ingresados en el main y se incorporan a la lista de usuarios
    public void ingreso(){
        boolean controlNombre;
        boolean controlIdentificacion;
        Usuario usuario;

        if(!verificadorTiempo()) {
            System.out.print("Ingresa tu nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingresa tu correo: ");
            String correo = scanner.nextLine();
            System.out.print("Ingresa tu identificacion: ");
            String identificacion = scanner.nextLine();
            System.out.print("Ingresa tu celular: ");
            String celular = scanner.nextLine();
            scanner.nextLine();

            //Control de no contener mas de un usuario con la misma identificacion
            controlNombre = usuarios.stream().
                    filter(usuarioV -> usuarioV.getNombre().equalsIgnoreCase(nombre)).toList().isEmpty();

            controlIdentificacion = usuarios.stream().
                    filter(usuarioV -> usuarioV.getIdentificacion().equalsIgnoreCase(identificacion)).toList().isEmpty();

            if (controlIdentificacion) {
                usuario = new Usuario(nombre, correo, identificacion, celular, ACIERTO);
                usuarios.add(usuario);
                generarApuesta(usuario);
            } else {
                if (!controlNombre) {
                    usuario = usuarios.stream().
                            filter(usuarioV -> usuarioV.getIdentificacion().equalsIgnoreCase(identificacion)).toList().getFirst();
                    generarApuesta(usuario);
                } else {
                    //TO DO : Error
                }
            }
        }else{
            System.out.println("La hora de apostar ya a terminado");
            //Se realizan la verificacion de los resultados y la obtencion del numero ganador
            //TO DO : ADMIN
            manejoResultado();
        }
    }

    //Se generan las apuestas de los usuarios
    public void generarApuesta(Usuario usuario){

        System.out.print("Ingresa tu el numero de cifras a las que las vas a apostar: ");
        int cifra = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingresa el numero a apostar: ");
        String numeroApostado = scanner.nextLine();
        System.out.print("Ingresa el dinero a apostar: ");
        long saldo = scanner.nextInt();
        scanner.nextLine();

        Apuesta apuesta = new Apuesta(numeroApostado, saldo);
        usuario.agregarApuesta(apuesta);

    }

    //Se agregan las apuestas y se determina el resultado de esta
    public void manejoResultado(){
        String numeroGanador  = "1546";
        List<String> numeroLista;

        //Se recorre la lista de usuarios y sus respectivas apuestas
        for(Usuario  usuario: usuarios ) {
            List<Apuesta> apuestas = usuario.getApuestas();
            for (Apuesta usuarioA : apuestas) {
                String identificacion = usuario.getIdentificacion();
                long saldo = usuarioA.getMonto();

                //Se crean listas que van a ser transformadas de String a listas
                numeroLista = transformacionALista(usuarioA.getNumeroApostado());
                List<String> numeroGanadorLista = transformacionALista(numeroGanador);
                int largoDelNumero = numeroLista.size();

                System.out.println("Apostaste por " + numeroLista.size() + " cifras por $" + usuarioA.getMonto()
                        + " al número: " + usuarioA.getNumeroApostado());

                // TO DO : No olvide quitar esto
                System.out.println(numeroGanador);
                System.out.println(numeroLista);

                System.out.println("Para el número ganador fue: " + numeroGanador);

                //Manejo de las diferentes situaciones dependiendo del numero de cifras del usuario
                switch (largoDelNumero) {
                    case 1 ->
                            manejarCaso(largoDelNumero, "Para una cifra tu número fue: " + numeroLista,
                                    10, numeroLista, numeroGanadorLista, saldo, identificacion);
                    case 2 ->
                            manejarCaso(largoDelNumero, "Para dos cifras tu número fue: " + numeroLista,
                                    15, numeroLista, numeroGanadorLista, saldo, identificacion);
                    case 3 ->
                            manejarCaso(largoDelNumero, "Para tres cifras tu número fue: " + numeroLista,
                                    50, numeroLista, numeroGanadorLista, saldo, identificacion);
                    case 4 -> {
                        manejarCaso(largoDelNumero, "Para cuatro cifra tu número fue: " + numeroLista,
                                1000, numeroLista, numeroGanadorLista, saldo, identificacion);
                    }
                    default -> {
                        System.out.println("Número de cifras no válido");
                    }
                }
            }
        }
    }

    //Manejo de los casos dependiendo del numero de cifras ingresados
    private boolean manejarCaso(int numeroDeCifras, String mensaje, int porcetajeDeGanancia, List<String> numeroLista, List<String> numeroGanadorLista, long saldo, String identificacion) {
        List<String> listaAComparar = numeroLista;

        if(numeroDeCifras != LONGITUD_NUMERO_GANADOR) {
            listaAComparar = numeroGanadorLista.subList(LONGITUD_NUMERO_GANADOR - numeroDeCifras, LONGITUD_NUMERO_GANADOR);
        }

        // TO DO : Eliminar
        System.out.println(listaAComparar);

        if (listaAComparar.equals(numeroLista)){
            System.out.println(mensaje);
            System.out.println("Haz acertado");
            System.out.println("Saldo original: " + saldo);
            System.out.println("Saldo actualizado: " + (saldo * porcetajeDeGanancia));
            usuarios.stream().filter(usuario -> usuario.getIdentificacion().equals(identificacion))
                    .forEach(Usuario::setAcierto);
            return true;
        }else
            System.out.println("Fallaste");
        return false;
    }

    public void historial(){
        usuarios.forEach(usuario -> System.out.println(usuario.getAcierto()));
    }
    // Convierte el número seleccionado en una lista
    public List<String> transformacionALista(String numero){
        List<String> listaNumeros = new ArrayList<>();
        for (char digito : numero.toCharArray()) {
            listaNumeros.add(String.valueOf(digito));
        }
        return listaNumeros;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}
