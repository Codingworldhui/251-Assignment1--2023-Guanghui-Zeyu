package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SimpleNotepad extends JFrame {

    private JTextArea textArea;
    private JFileChooser fileChooser;

    public SimpleNotepad() {
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem  = new JMenuItem("new");
        JMenuItem openMenuItem = new JMenuItem("open");
        JMenuItem saveMenuItem = new JMenuItem("save");
        JMenuItem exitMenuItem = new JMenuItem("quit");
        JMenu SearchMenu=new JMenu("Search");
        JMenuItem searchMenuItem=new JMenu("single word search");
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
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleNotepad notepad = new SimpleNotepad();
                notepad.setVisible(true);
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
//        searchMenuItem.addActionListener(new ActionListener() {
//            @Override
////            public void actionPerformed(ActionEvent e) {
//////                 弹出对话框来获取用户要搜索的文本
////                String searchText = JOptionPane.showInputDialog("Enter text to search:");
////
////                if (searchText != null && !searchText.isEmpty()) {
////                    // 获取文本编辑器的内容
////                    String editorText = textArea.getText();
////
////                    // 在文本中搜索匹配项
////                    int startIndex = editorText.indexOf(searchText);
////                    if (startIndex != -1) {
////                        // 如果找到匹配项，将光标移到匹配项位置
////                        textArea.setCaretPosition(startIndex);
////                        // 选择匹配项
////                        textArea.select(startIndex, startIndex + searchText.length());
////                    } else {
////                        JOptionPane.showMessageDialog(null, "Text not found.");
////                    }
////                }
//
//
//                public void actionPerformed(ActionEvent e) {
//                    // 弹出对话框
//
//                }
////            }
//        });


        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        fileMenu.add(newMenuItem);
        SearchMenu.add(searchMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(SearchMenu);
        menuBar.add(ViewMenu);
        menuBar.add(ManageMenu);
        menuBar.add(HelpMenu);
        setJMenuBar(menuBar);
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
