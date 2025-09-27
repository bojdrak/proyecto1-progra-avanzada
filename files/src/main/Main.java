import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import gui.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}