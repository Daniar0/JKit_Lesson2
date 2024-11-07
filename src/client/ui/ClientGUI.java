package client.ui;

import client.domain.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements ClientView {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private JTextArea log;
    private JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    private JPasswordField Password;
    private JButton btnLogin, btnSend;
    private JPanel headerPanel;

    private ClientController clientController;

    public ClientGUI() {
        setting();
        createPanel();
        setVisible(true);
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    private void setting() {
        setTitle("Chat Client");
        setSize(WIDTH,HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public void showMessage(String msg) {
        log.append(msg);
    }
    public void disconnectedFromServer(){
        hideHeaderPanel(true);
    }
    public void disconnectServer(){
        clientController.disconnectServer();

    }
    public void hideHeaderPanel(boolean visible) {
        headerPanel.setVisible(visible);
    }

    public void login(){
        if(clientController.connectToServer(tfLogin.getText())){
            headerPanel.setVisible(false);
        }
    }

    private void message(){
        clientController.message(tfMessage.getText());
        tfMessage.setText("");
    }

    private void createPanel() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }
    private Component createHeaderPanel() {
        headerPanel = new JPanel(new GridLayout(2, 3));
        tfIPAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8888");
        Password = new JPasswordField("123456");
        tfLogin = new JTextField("admin Adminovich");
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        headerPanel.add(tfIPAddress);
        headerPanel.add(tfPort);
        headerPanel.add(Password);
        headerPanel.add(tfLogin);
        headerPanel.add(btnLogin);
        headerPanel.add(new JPanel());

        return headerPanel;

    }
    private Component createLog() {
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }

    private Component createFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage=new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    message();
                }
            }
        });
        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                message();
            }
        });
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if(e.getID()==WindowEvent.WINDOW_CLOSING){
            disconnectServer();
        }
    }


}

