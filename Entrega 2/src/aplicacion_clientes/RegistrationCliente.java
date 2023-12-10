package aplicacion_clientes;

import javax.swing.*;

import procesamiento.LoaderFerreteria;

import java.awt.*;
import java.awt.event.*;

public class RegistrationCliente extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField correoField, contraseniaField, cedulaField, nombreField, datosContactoField,
            fechaNacimientoField, nacionalidadField, rutaImagenField,
            numeroLicenciaField, paisExpedicionField, fechaVencimientoField, rutaImagenLicenciaField, tipoTarjetaField,
            bancoField, numeroTarjetaField, cVCField;
    private JButton submitButton;

    private LoaderFerreteria loaderFerreteria = LoaderFerreteria.newFerreteria();

    public RegistrationCliente() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(227, 72, 45));

        // Agregar etiquetas y campos de texto al panel
        add(createLabelAndTextField("Ingrese su correo:", correoField = new JTextField(20)));
        add(createLabelAndTextField("Cree una contraseña:", contraseniaField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese su cédula:", cedulaField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese su nombre:", nombreField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese sus datos de contacto:", datosContactoField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese su fecha de nacimiento:", fechaNacimientoField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese su nacionalidad:", nacionalidadField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese la ruta de su imagen:", rutaImagenField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese su número de licencia:", numeroLicenciaField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese el país de expedición:", paisExpedicionField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese la fecha de vencimiento:", fechaVencimientoField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese la ruta de su imagen de licencia:",
                rutaImagenLicenciaField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese el tipo de tarjeta:", tipoTarjetaField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese el banco:", bancoField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese el número de tarjeta:", numeroTarjetaField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese el CVC:", cVCField = new JTextField(20)));

        submitButton = new JButton("Registrar");
        styleButton(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerClient();
            }
        });
        add(submitButton);
    }

    private JPanel createLabelAndTextField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(227, 72, 45));

        JLabel label = new JLabel(labelText);
        label.setBackground(new Color(227, 72, 45));
        label.setForeground(Color.WHITE); // Establecer el color del texto a blanco
        label.setFont(new Font("Palatino", Font.BOLD, 12)); // Establecer la fuente y el estilo del texto

        panel.add(label);
        panel.add(textField);
        return panel;
    }

    private void registerClient() {
        try {
            String correo = correoField.getText();
            String contrasenia = contraseniaField.getText();
            String cedulastr = cedulaField.getText();
            int cedula = Integer.parseInt(cedulastr);
            String nombre = nombreField.getText();
            String datosContato = datosContactoField.getText();
            String fechaNacimiento = fechaNacimientoField.getText();
            String nacionalidad = nacionalidadField.getText();
            String rutaImagen = rutaImagenField.getText();
            String numeroLicencia = numeroLicenciaField.getText();
            String paisExpedicion = paisExpedicionField.getText();
            String fechaVencimiento = fechaVencimientoField.getText();
            String rutaImagenLicencia = rutaImagenLicenciaField.getText();
            String tipoTarjeta = tipoTarjetaField.getText();
            String banco = bancoField.getText();
            String numeroTarjeta = numeroTarjetaField.getText();
            String cVCstr = cVCField.getText();
            short cVC = Short.parseShort(cVCstr);

            loaderFerreteria.crearCliente(correo, contrasenia, cedula, nombre, datosContato, fechaNacimiento,
                    nacionalidad, rutaImagen, numeroLicencia, paisExpedicion, fechaVencimiento, rutaImagenLicencia,
                    tipoTarjeta, banco, numeroTarjeta, cVC);

            JOptionPane.showMessageDialog(this, "Sr/a " + nombreField.getText() + ", quedo registrado exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrarse: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Palatino", Font.BOLD, 14)); // Usar Palatino
        button.setForeground(Color.WHITE); // Letra blanca
        button.setBackground(new Color(199, 13, 0)); // Fondo rojo oscuro
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false); // Quitar el borde pintado
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMargin(new Insets(10, 20, 10, 20)); // Margen dentro del botón

        // Usar un borde vacío para forzar el espacio, pero sin pintarlo
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        // Agregar un MouseListener para cambiar el aspecto del botón al pasar el mouse
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(true);
                btn.setContentAreaFilled(true);
                btn.setBorderPainted(false);
                btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
                btn.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(false);
                btn.setContentAreaFilled(false);
                btn.setBorderPainted(false);
                btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
                btn.repaint();
            }
        });
    }
}
