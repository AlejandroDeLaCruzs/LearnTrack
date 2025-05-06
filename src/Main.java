import Modelo.basededatos.BaseDatos;

public class Main {
    public static void main(String[] args) {
        new Vista.LoginView().mostrar();
        BaseDatos bd = new BaseDatos();

    }
} 