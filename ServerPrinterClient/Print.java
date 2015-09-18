import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import java.net.*;
import java.util.*;
import java.io.*;

public class Print {
    static JTextField dataText;
    static JFormattedTextField hostText;
    static JButton sendButton;
    public final static String DEFAULT_HOST = "localhost";
    public final static int DEFAULT_PORT = 8732;
    public final static String BASE_URI = "/Design_Time_Addresses/ServerPrinter/DoPrintGet/";
    public static void main(String[] args) {
        JFrame frame = new JFrame("ServerPrinter : Client");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel hostLabel = new JLabel("host");
        hostLabel.setBounds(10, 10, 80, 25);
        frame.add(hostLabel);

        try {
            MaskFormatter mf = new MaskFormatter("###.###.###.###");
            JFormattedTextField f = new JFormattedTextField(mf);
            hostText = new JFormattedTextField(mf);
            hostText.setBounds(100, 10, 100, 25);
            frame.add(hostText);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel dataLabel = new JLabel("data");
        dataLabel.setBounds(10, 40, 80, 25);
        frame.add(dataLabel);

        dataText = new JTextField(20);
        dataText.setBounds(100, 40, 160, 25);
        frame.add(dataText);

        sendButton = new JButton("send");
        sendButton.setBounds(10, 80, 120, 25);
        frame.add(sendButton);

        ActionListener sendButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(dataText.getText().equals("")){
                        JOptionPane.showMessageDialog(null,"Data Empty!");
                        return;
                    }
                    sendButton.setEnabled(false);
                    sendButton.setText("Sending...");
                    String host=DEFAULT_HOST;
                    if(!hostText.getText().equals("   .   .   .   ")){
                        host = hostText.getText();
                    }
                    final URI uri = new URI(
                        "http", 
                        null,
                        host,
                        DEFAULT_PORT,
                        BASE_URI+dataText.getText(),
                        null,
                        null);
                    new Thread(new Runnable() {
                        public void run() {
                            try{
                                URL u = uri.toURL();
                                u = uri.toURL();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));
                        
                                String line = "";
                                String result = "";
                                
                                while ((line = reader.readLine()) != null)
                                {
                                    result += line;
                                }
                                JOptionPane.showMessageDialog(null,result);
                                sendButton.setEnabled(true);
                                sendButton.setText("Send");
                            }catch(Exception exc)
                            {
                                 exc.printStackTrace(System.out);
                            }
                        }
                    }).start();
                    
                    /*
                     * 
                    String rawData = "data="+dataText.getText();
                    String type = "application/x-www-form-urlencoded";
                    String encodedData = URLEncoder.encode( rawData ); 
                    URL u = new URL(url+URI);
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty( "Content-Type", type );
                    conn.setRequestProperty( "Content-Length", String.valueOf(encodedData.length()));
                    OutputStream os = conn.getOutputStream();
                    os.write(encodedData.getBytes());
                    java.io.BufferedReader rd = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
                    String line;
                    String result="";
                    while ((line = rd.readLine()) != null) { result+=line;}
                    */
                }
                catch (Exception ex) {
                    ex.printStackTrace(System.out);
                }
            }
        };
        sendButton.addActionListener(sendButtonListener);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}