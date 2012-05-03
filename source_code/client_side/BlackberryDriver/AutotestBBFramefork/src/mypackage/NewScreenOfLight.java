package mypackage;

import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.container.MainScreen;

/**
 * EPAM Systems
 * @author German_Lomakin
 *
 * 03/05/2012
 * 
 * Second test screen
 */

public class NewScreenOfLight extends MainScreen {

    private ButtonField b1;
    
    public NewScreenOfLight() {
        b1 = new ButtonField("Good Button");
        add(b1);
    }

}
