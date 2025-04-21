

package tienda.alyso;

import javax.swing.SwingUtilities;
import tienda.alyso.vista.MenuPrincipal;

/**
 *
 * @author pla571cos
 */
public class Alyso {

     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
