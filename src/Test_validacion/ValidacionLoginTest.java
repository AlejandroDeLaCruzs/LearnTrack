package Test_validacion;

import Controlador.ValidacionUsuarios.ValidacionLogin;
import Modelo.Ficheros.GestorUsuariosCSV;
import Modelo.Usuarios.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ValidacionLoginTest {

    @BeforeEach
    public void setUp() {
        List<Usuario> lista = new ArrayList<>();
        lista.add(new Test_validacion.UsuarioPrueba("1", "Ana", "ana@email.com", "1234", "Admin"));
        lista.add(new Test_validacion.UsuarioPrueba("2", "Luis", "luis@email.com", "abcd", "Usuario"));
        GestorUsuariosCSV.setUsuariosPrueba(lista);
    }

    @Test
    public void testCamposVacios() {
        assertEquals("Rellene los datos", ValidacionLogin.validar("", ""));
        assertEquals("Rellene el correo", ValidacionLogin.validar("", "1234"));
        assertEquals("Rellene la contraseña", ValidacionLogin.validar("ana@email.com", ""));
    }

    @Test
    public void testCredencialesCorrectas() {
        assertEquals("Admin", ValidacionLogin.validar("ana@email.com", "1234"));
        assertEquals("Usuario", ValidacionLogin.validar("luis@email.com", "abcd"));
    }

    @Test
    public void testCorreoIncorrecto() {
        assertEquals("El correo no existe", ValidacionLogin.validar("noexiste@email.com", "1234"));
    }

    @Test
    public void testContrasenaIncorrecta() {
        assertEquals("Contraseña incorrecta", ValidacionLogin.validar("ana@email.com", "mala"));
    }
}
