package ru.gb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Client extends JFrame{

    // константы, определяющие размеры и позицию окна
    private static final int WINDOW_HEIGHT = 500;
    private static final int WINDOW_WIDTH = 500;
    private static final int WIN_POS_X = 800;
    private static final int WIN_POS_Y = 200;

//элементы интерфейса: кнопки "Login" и "Send",
// текстовые поля для ввода сообщений, адреса сервера, логина и пароля,
// а также метка и панели для расположения элементов
    JButton btnLog = new JButton("Login");
    JButton btnSend = new JButton("Send");

    JTextField msg = new JTextField();

    JTextField txtId = new JTextField("some ID");
    JTextField txtMask = new JTextField("some Mask");
    JLabel labTemp = new JLabel();
    JTextField txtLogin = new JTextField("Valery");
    JPasswordField txtPass = new JPasswordField("74522");

//конструктор
    public Client(Server chatServ) {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WIN_POS_X, WIN_POS_Y);
        setTitle("ChatClient");
        setResizable(false);
        JPanel panelReg1 = new JPanel(new GridLayout(2, 3));
        panelReg1.add(txtId);
        panelReg1.add(txtMask);
        panelReg1.add(labTemp);
        panelReg1.add(txtLogin);
        panelReg1.add(txtPass);
        panelReg1.add(btnLog);
        add(panelReg1, BorderLayout.NORTH);
        JPanel panelReg2 = new JPanel();
        panelReg2.setLayout(new BoxLayout(panelReg2, BoxLayout.X_AXIS));
        panelReg2.add(msg);
        panelReg2.add(btnSend);
        add(panelReg2, BorderLayout.SOUTH);

        msg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chatServ.isStopped){
                    try {
                        addMessage(chatServ.panelMessage);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else System.out.println("Сервер не отвечает");
            }
        });

        //если флаг stopConst объекта chatServ равен true,
        // вызывается метод addMessage с передачей ему объекта panelMessage.
        // Если флаг равен false, выводится сообщение "Сервер не отвечает"
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chatServ.isStopped){
                    try {
                        addMessage(chatServ.panelMessage);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else System.out.println("Сервер не отвечает");
            }
        });

        setVisible(true);
    }

    public void addMessage (JPanel jPanel) throws Exception{
        String str = msg.getText();
        if(str.length() == 0) throw new Exception("Пустая строка");
        else{
            JLabel chatMessage = new JLabel();
            jPanel.add(chatMessage);
            chatMessage.setText(txtLogin.getText() + ": " + str);
            addTextFile(txtLogin.getText() + ": " + str);
            jPanel.add(new JLabel(""));
            msg.setText("");
        }
    }

    public static void addTextFile(String str){
        try(FileWriter writer = new FileWriter("chatServ.txt", true))
        {
            writer.write(str);
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
