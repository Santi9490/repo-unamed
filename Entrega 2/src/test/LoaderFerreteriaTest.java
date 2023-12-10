package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inventario.Vehiculo;
import procesamiento.LoaderFerreteria;
import reserva_alquiler.Alquiler;
import reserva_alquiler.Reserva;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoaderFerreteriaTest {

    private LoaderFerreteria loader;

    @BeforeEach
    void setUp() {
        loader = LoaderFerreteria.newFerreteria();
    }

    @Test
    void testCargarDatos() {
        loader.cargarDatos();

        // Verificar que las categorías se han cargado correctamente
        assertFalse(loader.getCategorias().isEmpty(), "La lista de categorías debería tener elementos");

        // Verificar que las sedes se han cargado correctamente
        assertFalse(loader.getSedes().isEmpty(), "La lista de sedes debería tener elementos");

        // Verificar que los seguros se han cargado correctamente
        assertFalse(loader.getSeguros().isEmpty(), "La lista de seguros debería tener elementos");

        // Verificar que los vehículos se han cargado correctamente
        assertFalse(loader.getVehiculos().isEmpty(), "La lista de vehículos debería tener elementos");

        // Verificar que los administradores locales se han cargado correctamente
        assertFalse(loader.getAdminsLocales().isEmpty(), "La lista de administradores locales debería tener elementos");

        // Verificar que los empleados se han cargado correctamente
        assertFalse(loader.getEmpleados().isEmpty(), "La lista de empleados debería tener elementos");

        // Verificar que los clientes se han cargado correctamente
        assertFalse(loader.getClientes().isEmpty(), "La lista de clientes debería tener elementos");

        // Verificar que los alquileres se han cargado correctamente
        assertFalse(loader.getAlquileres().isEmpty(), "La lista de alquileres debería tener elementos");

        // Verificar que las reservas se han cargado correctamente
        assertFalse(loader.getReservas().isEmpty(), "La lista de reservas debería tener elementos");

        // Verificar que los mantenimientos se han cargado correctamente
        assertFalse(loader.getMantenimientos().isEmpty(), "La lista de mantenimientos debería tener elementos");

    }
	

    @Test
    void testCrearArchivosDatos() {

        // Verifica que los archivos se hayan creado correctamente
        assertTrue(new File("datos/categorias.txt").exists(), "El archivo categorias.txt debería existir");
        assertTrue(new File("datos/sedes.txt").exists(), "El archivo sedes.txt debería existir");
        assertTrue(new File("datos/seguros.txt").exists(), "El archivo seguros.txt debería existir");
        assertTrue(new File("datos/vehiculos.txt").exists(), "El archivo vehiculos.txt debería existir");
        assertTrue(new File("datos/alquileres.txt").exists(), "El archivo alquileres.txt debería existir");
        assertTrue(new File("datos/reservas.txt").exists(), "El archivo reservas.txt debería existir");
        assertTrue(new File("datos/mantenimientos.txt").exists(), "El archivo mantenimientos.txt debería existir");

        // Verifica que los archivos de usuarios también se hayan creado
        assertTrue(new File("usuarios/usuarios.txt").exists(), "El archivo usuarios.txt debería existir");
        assertTrue(new File("usuarios/superAdministradores.txt").exists(), "El archivo superAdministradores.txt debería existir");
        assertTrue(new File("usuarios/administradoresLocales.txt").exists(), "El archivo administradoresLocales.txt debería existir");
        assertTrue(new File("usuarios/empleados.txt").exists(), "El archivo empleados.txt debería existir");
        assertTrue(new File("usuarios/clientes.txt").exists(), "El archivo clientes.txt debería existir");

    }


    @Test
    void testGuardarEliminarDatos() {

        // Configura los datos necesarios para guardar
    	
    	String seguro= "NuevaSeguro";
    	String adminLoc= "aaaaaaaaaaaaa";
    	String vehiculo = "222AAA";
    	
    	loader.crearAdministradorLocal(adminLoc, adminLoc, adminLoc, adminLoc, adminLoc, 0, "AutoRápido Alquileres");
    	loader.crearEmpleado(adminLoc, adminLoc, adminLoc, adminLoc, adminLoc, 0, "AutoRápido Alquileres");
    	short pasajeros= 0;
    	loader.crearVehiculo(vehiculo, vehiculo, vehiculo, vehiculo, pasajeros, vehiculo, "Compacto", "AutoRápido Alquileres", vehiculo, 0);
        
        loader.crearSeguro("NuevaSeguro", "Info", 10000);

        // Guardar los datos
        loader.guardadatos();
        

        // Verificar que los archivos se han actualizado correctamente
        assertTrue(contieneSeguro("datos/seguros.txt", seguro),
                   "El archivo de seguros debe contener el nuevo seguro");
        assertTrue(contieneAdminloc("datos/administradoresLocales.txt", adminLoc),
                "El archivo de administradores locales debe contener el nuevo administrador");
        assertTrue(contieneEmpleado("datos/empleados.txt", adminLoc),
                "El archivo de empleados debe contener el nuevo empleado");
        assertTrue(contieneVehiculo("datos/vehiculos.txt", vehiculo),
                "El archivo de vehiculos debe contener el nuevo vehiculo");
        
        
        // eliminar
        loader.eliminarSeguro(seguro);
        loader.eliminarAdminLocal(0);
        loader.eliminarEmpleado(0);
        loader.eliminarVehiculo(vehiculo);
     // Guardar los datos después de la eliminación
        loader.guardar_eliminado();
        assertFalse(contieneSeguro("datos/categorias.txt", seguro),
                "El archivo de seguros no debe contener el seguro eliminado");
        assertTrue(contieneAdminloc("datos/administradoresLocales.txt", adminLoc),
                "El archivo de administradores locales no debe contener el administrador");
        assertTrue(contieneEmpleado("datos/empleados.txt", adminLoc),
                "El archivo de empleados no debe contener el empleado");
        assertTrue(contieneVehiculo("datos/vehiculos.txt", vehiculo),
                "El archivo de vehiculos no debe contener el vehiculo");
    }

    private boolean contieneSeguro(String archivo, String seguro) {
        return contieneEnArchivo(archivo, seguro);
    }
    
    private boolean contieneAdminloc(String archivo, String admin) {
        return contieneEnArchivo(archivo, admin);
    }
    
    private boolean contieneEmpleado(String archivo, String empleado) {
        return contieneEnArchivo(archivo, empleado);
    }
    
    private boolean contieneVehiculo(String archivo, String vehiculo) {
        return contieneEnArchivo(archivo, vehiculo);
    }

    private boolean contieneEnArchivo(String archivo, String busqueda) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(busqueda)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    //Reservas alquileres
    
    @Test
    void testCrearReserva() {
        // Preparar datos de prueba
        loader.cargarDatos();
        String categoria = "Económico";
        String sedeRecogida = "Sede Central";
        String sedeEntrega = "Sede Norte";
        int cedulaCliente = 12345678;
        String fechaInicio = "2023-01-01";
        String fechaFin = "2023-01-05";
        String horaInicio = "10:00";
        String horaFin = "16:00";

        // Realizar reserva
        int precio = loader.registrarReserva(fechaInicio, fechaFin, horaInicio, horaFin, sedeRecogida, sedeEntrega, cedulaCliente, categoria, "12:00");

        // Verificar resultados
        assertFalse(loader.getReservas().isEmpty(), "Debería haber al menos una reserva");
        List<Reserva> reservas = new ArrayList<>();
        reservas = loader.getReservas();
        Reserva reservaCreada = reservas.get(reservas.size()-1);
        assertEquals("12:00", reservaCreada.getHoraLlegada(), "La horas de llegada de la reserva deben coincidir");
    }
    
    @Test
    void testCrearAlquiler() {
        // Preparar datos de prueba
        loader.cargarDatos();
        String placaVehiculo = "ABC123";
        int cedulaCliente = 12345678;
        String fechaInicio = "2023-01-01";
        String fechaFin = "2023-01-07";
        String horaInicio = "09:00";
        String horaFin = "18:00";
        String sedeRecogida = "Sede Central";
        String sedeEntrega = "Sede Sur";

        // Realizar alquiler
        loader.registrarAlquiler(fechaInicio, fechaFin, horaInicio, horaFin, sedeRecogida, sedeEntrega, cedulaCliente, placaVehiculo, null);

        // Verificar resultados
        assertFalse(loader.getAlquileres().isEmpty(), "Debería haber al menos un alquiler");
        Alquiler alquilerCreado = loader.getAlquileres().get(0);
        Vehiculo vehiculo= (Vehiculo) alquilerCreado.getVehiculo();
        assertEquals(placaVehiculo, vehiculo.getPlaca(), "La placa del vehículo en el alquiler debe coincidir");
    }

}


