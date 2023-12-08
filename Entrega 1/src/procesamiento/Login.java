package procesamiento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * La clase Login se encarga de manejar la autenticación de usuarios y sus roles en la aplicación.
 * 
 * Contiene los siguientes métodos:
 * - getrol: devuelve el rol del usuario autenticado y llama al método correspondiente en LoaderFerreteria.
 * - getUsuarios: devuelve un mapa con los usuarios registrados.
 * - UsuarioExist: verifica si un usuario existe en el mapa de usuarios.
 * - cargarUsuarios: carga los usuarios registrados desde un archivo de texto.
 */
public class Login {

    private static Map<String, String[]> usuarios = new HashMap<>();

    private static final String CARPETA_USUARIOS = "usuarios";

    private static final String ARCHIVO_USUARIOS = CARPETA_USUARIOS + "/usuarios.txt";

    /**
     * Devuelve el rol del usuario autenticado y llama al método correspondiente en LoaderFerreteria.
     * 
     * @param correo el correo electrónico del usuario autenticado.
     * @param loaderFerreteria una instancia de LoaderFerreteria para llamar al método correspondiente.
     * @return el rol del usuario autenticado.
     */
    public String getrol(String correo, LoaderFerreteria loaderFerreteria) {
        String rol = usuarios.get(correo)[1];
        if (rol.equals("adminlocal")) {
            loaderFerreteria.loginAdminLoc(correo);
        } else if (rol.equals("empleado")) {
            loaderFerreteria.loginEmpleado(correo);
        } else if (rol.equals("cliente")) {
            loaderFerreteria.loginCliente(correo);
        }
        return rol;
    }

    /**
     * Devuelve un mapa con los usuarios registrados.
     * 
     * @return un mapa con los usuarios registrados.
     */
    public Map<String, String[]> getUsuarios() {
        return usuarios;
    }

    /**
     * Verifica si un usuario existe en el mapa de usuarios.
     * 
     * @param correo el correo electrónico del usuario a verificar.
     * @param contrasena la contraseña del usuario a verificar.
     * @return true si el usuario existe y la contraseña es correcta, false de lo contrario.
     */
    public boolean UsuarioExist(String correo, String contrasena) {
        return usuarios.containsKey(correo) && usuarios.get(correo)[0].equals(contrasena);
    }

    /**
     * Carga los usuarios registrados desde un archivo de texto.
     */
    public void cargarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    usuarios.put(partes[0], new String[] { partes[1], partes[2] });
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
    }
}
