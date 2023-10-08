package org.example;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.*;
import java.awt.datatransfer.*;
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
import java.util.Iterator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.text.Paragraph;

public class SimpleNotepad extends JFrame {
    public static void convertTextToPdf(String textContent) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String outputPath = selectedFile.getAbsolutePath() + ".pdf";

            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            contentStream.beginText();
            contentStream.newLineAtOffset(20, 750); // 设置文本起始坐标
            contentStream.showText(textContent);
            contentStream.endText();
            contentStream.close();

            document.save(outputPath);
            document.close();

            JOptionPane.showMessageDialog(null, "Text successfully converted to PDF.",
                    "Conversion Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
   // private JTextArea textArea;//文本区域
   private RSyntaxTextArea textArea;
    private JFileChooser fileChooser;
    static JMenuItem convertPDF=new JMenuItem("convertPDF");
    static File fileAddress;
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
    private String originalText = "";

    public SimpleNotepad() {
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

     //   textArea = new JTextArea();
        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA); // 设置默认语法为Java
        textArea.setCodeFoldingEnabled(true); // 启用代码折叠功能
        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
      //  JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        //Zeyu
        JMenuItem aboutMenuItem = new JMenuItem("About");
        JMenuItem TimeAndData = new JMenuItem("Time And Data");
        JMenuItem PrintMenuItem = new JMenuItem("Print");
        JMenuItem SelectMenuItem = new JMenuItem("Select");
        JMenuItem CopyMenuItem = new JMenuItem("Copy");
        JMenuItem PasteMenuItem = new JMenuItem("Paste");
        JMenuItem CutMenuItem = new JMenuItem("Cut");

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


        convertPDF.addActionListener(e -> {
            try {
                String content= textArea.getText();
                convertTextToPdf(content);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Error occurred during conversion: " + e1.getMessage(),
                        "Conversion Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
//                        BufferedReader br = new BufferedReader(new FileReader(file));
//                        textArea.setText("");
//                        String line;
//                        while ((line = br.readLine()) != null) {
//                            textArea.append(line + "\n");
//                        }
//                        br.close();
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                }
                        String fileName = file.getName();
                        String directory = file.getPath();
                        String[] parts = fileName.split("\\."); // 以点号 "." 分割文件名
                        String postfix = null;
                        if (parts.length > 1) {
                            postfix = parts[parts.length - 1]; // 获取最后一个部分作为后缀
                            // 打印后缀
                            System.out.println("File postfix: " + postfix);
                        } else {
                            System.out.println("File has no postfix.");
                        }
                        String Style;
                        switch (postfix) {
                            case "java":
                                Style = SyntaxConstants.SYNTAX_STYLE_JAVA;
                                break;
                            case "c":
                                Style = SyntaxConstants.SYNTAX_STYLE_C;
                                break;
                            case "cpp":
                                Style = SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS;
                                break;
                            case "py":
                                Style = SyntaxConstants.SYNTAX_STYLE_PYTHON;
                                break;
                            default:
                                Style = SyntaxConstants.SYNTAX_STYLE_NONE; // default no rule
                        }
                        textArea.setSyntaxEditingStyle(Style);
                        if (fileName == null || directory == null)
                            return;
                        textArea.setText("");
                        fileAddress = new File(directory);

                        if (fileName.endsWith(".rtf")) {
                            String result = null;
                            try {
                                DefaultStyledDocument styledDoc = new DefaultStyledDocument();
                                InputStream streamReader = new FileInputStream(fileAddress);
                                new RTFEditorKit().read(streamReader, styledDoc, 0);
                                //Gets byte[] in the encoded form of ISO-8859-1 and generates the string in the encoded form of GBK
                                result = new String(styledDoc.getText(0, styledDoc.getLength()).getBytes("ISO8859-1"), "GBK");
                                textArea.setText(result);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (BadLocationException ex) {
                                ex.printStackTrace();
                            }
                        } else if (fileName.endsWith(".odt")) {
                            File openedFile = new File(directory);

                            try {
                                TextDocument textDocument = TextDocument.loadDocument(openedFile);

                                textArea.setText("");

                                // Iterate through paragraphs and append to the text area
                                for (Iterator<Paragraph> it = textDocument.getParagraphIterator(); it.hasNext(); ) {
                                    Paragraph paragraph = it.next();
                                    textArea.append(paragraph.getTextContent() + "\n");
                                }
                            } catch (Exception exception) {
                                throw new RuntimeException("Failed to open the file");
                            }
                        } else {
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
                    } finally {
                        return;
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
        SelectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.selectAll();//全选文本内容
            }
        });
        CopyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedText = textArea.getSelectedText();
                if (selectedText != null && !selectedText.isEmpty()) {
                    StringSelection selection = new StringSelection(selectedText);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, null);
                }
            }
        });
        PasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable contents = clipboard.getContents(null);
                if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        String pastedText = (String) contents.getTransferData(DataFlavor.stringFlavor);
                        textArea.append(pastedText); // 将粘贴的文本追加到文本区域中
                    } catch (UnsupportedFlavorException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        CutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedText = textArea.getSelectedText();
                if (selectedText != null && !selectedText.isEmpty()) {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection selection = new StringSelection(selectedText);
                    clipboard.setContents(selection, null);
                    textArea.replaceSelection(""); // 从文本区域中删除选定的文本
                }

            }
        });


        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);

        //Zeyu
        ViewMenu.add(SelectMenuItem);
        ViewMenu.add(CopyMenuItem);
        ViewMenu.add(PasteMenuItem);
        ViewMenu.add(CutMenuItem);

        HelpMenu.add(aboutMenuItem);

        ManageMenu.add(TimeAndData);
        ManageMenu.add(PrintMenuItem);
        ManageMenu.add(convertPDF);
        fileMenu.add(newMenuItem);
        SearchMenu.add(searchMenuItem);
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
