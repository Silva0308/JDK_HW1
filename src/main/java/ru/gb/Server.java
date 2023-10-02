package ru.gb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Server extends JFrame{

    // константы, определяющие размеры и позицию окна
    private static final int WINDOW_HEIGHT = 500;
    private static final int WINDOW_WIDTH = 500;
    private static final int WIN_POS_X = 200;
    private static final int WIN_POS_Y = 200;
    boolean isStarted = true;
    boolean isStopped = true;
    JPanel panelMessage = new JPanel();

    File file = new File("chatServ.txt"); // файл логов

    JButton btnStart = new JButton("Start");
    JButton btnStop = new JButton("Stop");

    public Server() {
        super("Server");
        createGUI();
    }

    public void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WIN_POS_X, WIN_POS_Y);
        setResizable(false);
        JPanel panelBtn = new JPanel(new GridLayout(1, 2));
        panelBtn.add(btnStart);
        panelBtn.add(btnStop);
        add(panelBtn, BorderLayout.SOUTH);
        panelMessage.setLayout(new BoxLayout(panelMessage, BoxLayout.Y_AXIS));
        add(panelMessage, BorderLayout.NORTH);
        ActionListener actionListener1 = new btnStart();
        btnStart.addActionListener(actionListener1);
        ActionListener actionListener2 = new btnStop();
        btnStop.addActionListener(actionListener2);
        isStopped = false;
        setVisible(true);
    }

    //запуск
    public class btnStop implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(isStopped){
                panelMessage.removeAll();
                panelMessage.repaint();
                panelMessage.revalidate();
                isStarted = true;
                isStopped = false;
            }
            else {
                System.out.println("Сервер уже остановлен");
            }
        }
    }

    //остановка
    public class btnStart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (isStarted) {
                if(file.exists()){
                    try {
                        chatArchive(file, panelMessage);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                isStarted = false;
                isStopped = true;
            }
            else {
                System.out.println("Сервер уже запущен");
            }
        }
    }
//метод, который отвечает за загрузку архива чата из файла и отображение сообщений на панели.
    public static void chatArchive (File file, JPanel jPanel) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            JLabel gg = new JLabel();
            jPanel.add(gg);
            gg.setText(st);
        }
    }
}

