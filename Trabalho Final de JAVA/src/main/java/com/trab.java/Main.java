package com.trab.java;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClienteView view = new ClienteView();
            ClienteController controller = new ClienteController(view);
            controller.iniciar();
        });
    }
}
