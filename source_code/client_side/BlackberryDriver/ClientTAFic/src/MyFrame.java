import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Main frame for test BB driver application. 
 */
public class MyFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    
    private NativeDriver driver = new BlackberryNativeDriver();
    private ScriptExecutor scriptExecutor = new ScriptExecutor(driver);
    
    private JButton startSimulatorCommandButton = new JButton("Start Simulator");
    private JButton startApplicationCommandButton = new JButton("Start Application");
    private JButton getScreenShotCommandButton = new JButton("Screenshot");
    private JButton waitCommandButton = new JButton("Wait");
    private JButton getCurrentScreenCommandButton = new JButton("Current Screen");
    private JButton trackUpCommandButton = new JButton("Track Up");
    private JButton trackDownCommandButton = new JButton("Track Down");
    private JButton trackClickCommandButton = new JButton("Track Click");
    private JButton waitForScreenCommandButton = new JButton("Wait Screen");    
    private JButton exitCommandButton = new JButton("Exit");    
    private JButton touchCommandButton = new JButton("Touch");
    private JButton inputCommandButton = new JButton("Input");
    private JButton escapeCommandButton = new JButton("Escape");
    private JButton menuCommandButton = new JButton("Menu");
    //No, its not Call of Duty button.
    private JButton loadCodCommandButton = new JButton("Load COD");
    
    private JButton getCurrentScreenNameButton = new JButton("Get Current Screen Name");
    private JButton getTreeButton = new JButton("Get Tree");        
    private JButton parseButton = new JButton("Parse");
    
    private JTextField textField = new JTextField();
    private JTextPane scriptArea = new JTextPane();
    
         
    private JLabel outputLabel = new JLabel();

    public MyFrame() {
        super("BBTest");
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 2,10,10));
                
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));        
                       
        startSimulatorCommandButton.addActionListener(this);
        startApplicationCommandButton.addActionListener(this);
        getScreenShotCommandButton.addActionListener(this);
        waitCommandButton.addActionListener(this);
        getCurrentScreenCommandButton.addActionListener(this);
        trackClickCommandButton.addActionListener(this);
        trackUpCommandButton.addActionListener(this);
        trackDownCommandButton.addActionListener(this);
        waitForScreenCommandButton.addActionListener(this);
        exitCommandButton.addActionListener(this);
        getTreeButton.addActionListener(this);
        parseButton.addActionListener(this);        
        touchCommandButton.addActionListener(this);
        inputCommandButton.addActionListener(this);
        escapeCommandButton.addActionListener(this);
        getCurrentScreenNameButton.addActionListener(this);
        menuCommandButton.addActionListener(this);
        loadCodCommandButton.addActionListener(this);
        
        buttonPanel.add(startSimulatorCommandButton);
        buttonPanel.add(startApplicationCommandButton);
        buttonPanel.add(getScreenShotCommandButton);        
        buttonPanel.add(waitCommandButton);        
        buttonPanel.add(getCurrentScreenCommandButton);
        buttonPanel.add(trackUpCommandButton);
        buttonPanel.add(trackDownCommandButton);
        buttonPanel.add(trackClickCommandButton);
        buttonPanel.add(waitForScreenCommandButton);
        buttonPanel.add(exitCommandButton);
        buttonPanel.add(touchCommandButton);
        buttonPanel.add(inputCommandButton);        
        buttonPanel.add(escapeCommandButton);
        buttonPanel.add(menuCommandButton);
        buttonPanel.add(loadCodCommandButton);
        buttonPanel.add(getTreeButton);      
        buttonPanel.add(getCurrentScreenNameButton);
        buttonPanel.add(parseButton);
        
        //scriptArea.setPreferredSize(new Dimension(58, 50));
        inputPanel.setPreferredSize(new Dimension(600, 300));
        buttonPanel.setPreferredSize(new Dimension(300, 300));
        
        inputPanel.add(new JLabel("Output tree"));
        inputPanel.add(textField);
        
        inputPanel.add(outputLabel);
        inputPanel.add(new JLabel("Script area:"));
                
        inputPanel.add(scriptArea);
                                      
        add(buttonPanel, BorderLayout.EAST);
        add(inputPanel, BorderLayout.WEST);
       
        
        setSize(950, 600);
        setResizable(false);
        setVisible(true);
    }
    
    public static String loadScript(String filename) {
        String result = "";
        try {
            Scanner input = new Scanner(new File(filename));
            StringBuffer buffer = new StringBuffer();
            while (input.hasNextLine()) {
                buffer.append(input.nextLine());
                buffer.append("\n");
            }
            input.close();
            result = buffer.toString();
        } catch (FileNotFoundException e) {            
            e.printStackTrace();
        }
       
        return result;
    }
    
    public static void main(String[] args) {
        if (args.length == 0) {
            new MyFrame();
        } else {
            int current = 0;
            String path = "", script = "";
            
            while (current < args.length) {
                String attrib = args[current];
                current++;
                if (attrib.toUpperCase().equals("-SCRIPT")) {
                    script = args[current];                    
                    current++;
                } else if (attrib.toUpperCase().equals("-PATH")) {
                    path = args[current];
                    current++;                    
                }
            }
                        
            BlackberryNativeDriver driver = new BlackberryNativeDriver();
            driver.setFledgePath(path);
            ScriptExecutor scriptExecutor = new ScriptExecutor(driver);
            scriptExecutor.execute(loadScript(script));            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startSimulatorCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("startsimulator");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());            
        }
        if (e.getSource() == getScreenShotCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("screenshot PATH");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());    
        } else if (e.getSource() == startApplicationCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("startapplication APPNAME");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());  
        } else if (e.getSource() == waitCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("wait TIME");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());  
        } else if (e.getSource() == getCurrentScreenCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("currentscreen SCREENNAME");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());  
        } else if (e.getSource() == trackUpCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("trackup COUNT");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());  
        } else if (e.getSource() == trackDownCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("trackdown COUNT");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());  
        } else if (e.getSource() == trackClickCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("trackclick");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());  
        } else if (e.getSource() == waitForScreenCommandButton) {            
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("waitscreen SCREENNAME");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());              
        } else if (e.getSource() == exitCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("exit");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());  
        } else if (e.getSource() == touchCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("touch FIELDPATH");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());
        } else if (e.getSource() == inputCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("input FIELDPATH TEXT");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());
        } else if (e.getSource() == escapeCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("escape");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());
        } else if (e.getSource() == menuCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("menu");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());
        } else if (e.getSource() == getTreeButton) {                        
            textField.setText(driver.getTree());
        } else if (e.getSource() == getCurrentScreenNameButton) {
            try {
                String result = driver.getCurrentScreenName();
                textField.setText(result);
            } catch (Exception e1) {
                textField.setText("Failed");
            }
        } else if (e.getSource() == parseButton) {           
            boolean result = scriptExecutor.execute(scriptArea.getText());
            if (result) {
                outputLabel.setText("Test Completed Successfull");                
            } else {
                outputLabel.setText("Failed!");
            }
        } else if (e.getSource() == loadCodCommandButton) {
            StringBuffer buff = new StringBuffer();
            buff.append(scriptArea.getText());
            buff.append("loadcod CODPATH");
            buff.append("\r\n");
            scriptArea.setText(buff.toString());
        }
        
    }
}
