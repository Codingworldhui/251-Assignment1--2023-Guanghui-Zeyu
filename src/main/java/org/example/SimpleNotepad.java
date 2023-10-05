package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SimpleNotepad extends JFrame {

    private JTextArea textArea;
    private JFileChooser fileChooser;
    private String originalText = "";

    public SimpleNotepad() {
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
        JMenuItem searchMenuItem=new JMenuItem("single word search");
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
                if (textArea.getText().equals(originalText)) {
                    // 文本没有被修改过，直接退出
                    dispose();
                } else {
                    int option = JOptionPane.showConfirmDialog(
                            null,
                            "The file has been modified, whether to save it?",
                            "exit",
                            JOptionPane.YES_NO_CANCEL_OPTION
                    );
                    if (option == JOptionPane.YES_OPTION) {
                        // 保存文件
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
                        // 退出程序
                        dispose();
                    } else if (option == JOptionPane.NO_OPTION) {
                        // 不保存文件，直接退出程序
                        dispose();
                    }
                    // 如果用户选择取消，什么也不做，继续编辑
                }

                SimpleNotepad notepad = new SimpleNotepad();
                notepad.setVisible(true);

            }
        });
        searchMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = JOptionPane.showInputDialog(null, "Please enter the word you are searching for");

                if (searchText != null && !searchText.isEmpty()) {
                    String text = textArea.getText();
                    int index = text.indexOf(searchText);

                    if (index != -1) {
                        textArea.requestFocusInWindow();
                        textArea.select(index, index + searchText.length());
                    } else {
                        JOptionPane.showMessageDialog(null, "can not find this word");
                    }
                }
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textArea.getText().equals(originalText)) {
                    // 文本没有被修改过，直接退出
                    dispose();
                } else {
                    int option = JOptionPane.showConfirmDialog(
                            null,
                            "The file has been modified, whether to save it?",
                            "exit",
                            JOptionPane.YES_NO_CANCEL_OPTION
                    );
                    if (option == JOptionPane.YES_OPTION) {
                        // 保存文件
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
                        // 退出程序
                        dispose();
                    } else if (option == JOptionPane.NO_OPTION) {
                        // 不保存文件，直接退出程序
                        dispose();
                    }
                    // 如果用户选择取消，什么也不做，继续编辑
                }
            }
        });

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
