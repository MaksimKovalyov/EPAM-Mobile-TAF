package mypackage;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Main test screen
 */
public final class MyScreen extends MainScreen implements FieldChangeListener {

    private ButtonField b1;
    private ButtonField b2;
    private ButtonField b3;

    /**
     * Creates a new MyScreen object
     */
    public MyScreen() {
        // Set the displayed title of the screen
        setTitle("MyTitle");
        b1 = new ButtonField("But 1");
        b1.setChangeListener(this);
        b2 = new ButtonField("But 2");
        b2.setChangeListener(this);
        b3 = new ButtonField("But 3");
        b3.setChangeListener(this);
        add(b1);
        add(b2);
        add(b3);
        add(new TextField());

        HorizontalFieldManager manager = new HorizontalFieldManager();
        ButtonField bu1 = new ButtonField("Cl1");
        ButtonField bu2 = new ButtonField("Cl2");
        ButtonField bu3 = new ButtonField("Cl3");
        bu1.setChangeListener(this);
        bu2.setChangeListener(this);
        bu3.setChangeListener(this);
        manager.add(bu1);
        manager.add(bu2);
        manager.add(bu3);
        add(manager);

    }

    public void fieldChanged(Field field, int context) {

        if (field.equals(b1)) {
                Dialog.alert("ba1");
        } else if (field.equals(b2)) {
            UiApplication.getUiApplication().pushScreen(new NewScreenOfLight());
        } else {                       
            Dialog.alert(((ButtonField) field).getLabel());
        }
    }
}
