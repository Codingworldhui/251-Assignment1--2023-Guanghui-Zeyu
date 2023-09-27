package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
//Zeyu
import java.awt.print.PrinterJob;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class SimpleNotepad extends JFrame {

    private JTextArea textArea;//文本区域
    private JFileChooser fileChooser;
    public static void Print (String content){
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.defaultPage();
        Printable printable = new Printable() {
            public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
                if (page > 0) {
                    return NO_SUCH_PAGE;
                }
                Graphics2D g2d = (Graphics2D) g;
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
                g2d.drawString(content, 100, 100);
                return PAGE_EXISTS;
            }
        };

        printerJob.setPrintable(printable, pageFormat);

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SimpleNotepad() {
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);//?
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("打开");
        JMenuItem saveMenuItem = new JMenuItem("保存");
        JMenuItem exitMenuItem = new JMenuItem("退出");
        //Zeyu
        JMenuItem aboutMenuItem = new JMenuItem("About");
        JMenuItem TimeAndData = new JMenuItem("Time And Data");
        JMenuItem PrintMenuItem = new JMenuItem("Print");

        JMenu SearchMenu=new JMenu("Search");
        JMenu ViewMenu=new JMenu("View");
        JMenu ManageMenu=new JMenu("Manage");
        JMenu HelpMenu=new JMenu("Help");
        fileChooser = new JFileChooser();

        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        textArea.setText("");
                        String line;
                        while ((line = br.readLine()) != null) {
                            textArea.append(line + "\n");
                        }
                        br.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                        bw.write(textArea.getText());
                        bw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Name of member: ZeYu Yang ,GuangHui Pang ,You can contact us at: 111222333");
                //null表示在屏幕中间显示
            }
        });
        TimeAndData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentText = textArea.getText();//当前的文本
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String TAD = dateFormat.format(new Date());
                textArea.setText(TAD + currentText);
            }
        });
        PrintMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentText = textArea.getText();
                Print(currentText);
            }
        });


        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);

        //Zeyu
        HelpMenu.add(aboutMenuItem);

        ManageMenu.add(TimeAndData);
        ManageMenu.add(PrintMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(SearchMenu);
        menuBar.add(ViewMenu);
        menuBar.add(ManageMenu);
        menuBar.add(HelpMenu);
        setJMenuBar(menuBar);
        //Zeyu
        this.setLocationRelativeTo(null);//居中显示
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SimpleNotepad notepad = new SimpleNotepad();
                notepad.setVisible(true);
            }
        });
    }
}