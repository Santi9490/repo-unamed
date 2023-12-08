package Interaz;

import javax.swing.*;

import procesamiento.LoaderFerreteria;

import java.awt.*;
import java.awt.event.*;

public class RegistrationEmpleado extends JPanel {
    private JTextField correoField, contraseniaField, nombreField, datosContactoField, fechaNacimientoField,
            cedulaField, sedeField;
    private JButton submitButton;
    private LoaderFerreteria loaderFerreteria = LoaderFerreteria.newFerreteria();

    public RegistrationEmpleado() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(227, 72, 45));

        // Agregar etiquetas y campos de texto al panel
        add(createLabelAndTextField("Ingrese el correo:", correoField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese una contraseña:", contraseniaField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese el nombre del empleado:", nombreField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese algún dato de contacto:", datosContactoField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese la fecha de nacimiento:", fechaNacimientoField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese la cédula:", cedulaField = new JTextField(20)));
        add(createLabelAndTextField("Ingrese la sede en la que estará ubicado:", sedeField = new JTextField(20)));

        submitButton = new JButton("Registrar Empleado");
        styleButton(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerEmpleado();
            }
        });
        add(submitButton);
    }

    private JPanel createLabelAndTextField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(labelText);
        panel.setBackground(new Color(227, 72, 45));
        label.setForeground(Color.WHITE); // Establecer el color del texto a blanco
        label.setFont(new Font("Palatino", Font.BOLD, 12)); // Establecer la fuente y el estilo del texto

        panel.add(label);
        panel.add(textField);
        return panel;
    }

    private void registerEmpleado() {
        try {
            String correo = correoField.getText();
            String contrasenia = contraseniaField.getText();
            String nombre = nombreField.getText();
            String datosContacto = datosContactoField.getText();
            String fechaNacimiento = fechaNacimientoField.getText();
            int cedula = Integer.parseInt(cedulaField.getText());
            String sede = sedeField.getText();

            loaderFerreteria.crearEmpleado(correo, contrasenia, nombre, datosContacto, fechaNacimiento, cedula, sede);

            JOptionPane.showMessageDialog(this, "Empleado " + nombre + " registrado exitosamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de la cédula.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar el empleado: " + e.getMessage(), "Error",
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
