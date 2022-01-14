package com.mycompany.app;


import com.alibaba.excel.EasyExcel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GUIDemo extends JFrame {
    public static final Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main(String[] args) throws Exception {
        final JFrame jf = new JFrame("测试窗口");
        jf.setSize(400, 250);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        // 创建文本区域, 用于显示相关信息
        final JTextArea msgTextArea = new JTextArea(10, 30);
        msgTextArea.setBounds(ss.width / 4, ss.height / 5, 100, 100);
        msgTextArea.setWrapStyleWord(true);
        msgTextArea.setLineWrap(true);
        panel.add(msgTextArea);
        final JButton openBtn = new JButton("打开");
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<DemoData> objects = showFileOpenDialog(jf, msgTextArea);
                for (int i = 0; i < objects.size(); i++) {
                    JPanel panel1 = new JPanel();

                    // 创建文本区域, 用于显示相关信息
                    JTextArea msgTextArea1 = new JTextArea(10, 30);
                    msgTextArea1.setBounds(ss.width / 4, ss.height / 5, 100, 100);
                    msgTextArea1.setWrapStyleWord(true);
                    msgTextArea1.setLineWrap(true);
                    System.out.println("读取行" + i);
                    DemoData o = objects.get(i);
                    if (o.getX() == null && o.getY() == null) {
                        break;
                    }
                    //msgTextArea.setText("");
                    //msgTextArea.paintImmediately(msgTextArea.getX(), msgTextArea.getY(), msgTextArea.getWidth(), msgTextArea.getHeight());
                    //msgTextArea.paintImmediately(msgTextArea.getBounds());
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    msgTextArea1.append(o.getX() + o.getY());
                    msgTextArea1.invalidate();
                    msgTextArea1.repaint();
                    //msgTextArea.paintImmediately(msgTextArea.getBounds());
                    msgTextArea1.paintImmediately(msgTextArea1.getBounds());
                    msgTextArea1.paintImmediately(msgTextArea1.getX(), msgTextArea1.getY(), msgTextArea1.getWidth(), msgTextArea1.getHeight());

                    //parent.invalidate();
                    //parent.setP(panel);
                    //parent.setVisible(true);
                    panel1.add(msgTextArea1);
                    panel1.add(openBtn);
                    jf.setContentPane(panel1);
                    jf.invalidate();
                    jf.revalidate();
                    jf.repaint();
                    jf.setVisible(true);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    //parent.update(msgTextArea);
                    //msgTextArea.invalidate();
                    //msgTextArea.repaint();

                }
            }
        });
        panel.add(openBtn);

        jf.setContentPane(panel);
        jf.setVisible(true);

    }

    /*
     * 打开文件
     */
    private static List<DemoData> showFileOpenDialog(Component parent, JTextArea msgTextArea) {
        List<DemoData> objects = new ArrayList<>();
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("excel(*.xlsx, *.xls)", "xlsx", "xls"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("excel(*.xlsx, *.xls)", "xlsx", "xls"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();
            //msgTextArea.append("打开文件: " + file.getAbsolutePath() + "\n\n");
            objects = EasyExcel.read(file, DemoData.class, new DemoDataListener()).sheet().doReadSync();


            //EasyExcel.write("res", DemoData.class).sheet("结果").doWrite(objects);
        }
        return objects;
    }

    /*
     * 选择文件保存路径
     */
    private static void showFileSaveDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File("测试文件.zip"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            msgTextArea.append("保存到文件: " + file.getAbsolutePath() + "\n\n");
        }
    }

}

