/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadmanager;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Vijay
 */


public class DownloadManager extends JFrame implements Observer  {

    
    public static void main(String[] args) {
    
    }
    
    private JTextField addTextField;
    private JTable table;
    private JButton pauseButton,resumeButton,canelButton,clearButton;
    
    private DownloadsTableModel tableModel;//download table's data model
    private Download selectedDownload;
    
    private boolean clearing;//table selection is being cleared or not
    private void actionExit(){System.exit(0);}
    
    public DownloadManager() {
        setTitle("Downlaod Manager");
        setSize(640,480);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){actionExit();}});
        
        JMenuBar menuBar=new JMenuBar();
        JMenu fileMenu=new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem fileExitMenuItem=new JMenuItem("Exit",KeyEvent.VK_X);
        fileExitMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                actionExit();
            }
        });
        fileMenu.add(fileExitMenuItem);
        menuBar.add(menuBar);
        
        JPanel addPanel=new JPanel();
        addTextField=new JTextField(30);
        addPanel.add(addTextField);
        JButton addButton =new JButton("Add Download");
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                actionAdd();
            }
        });
        
        addPanel.add(addButton);
        tableModel=new DownloadsTableModel();
        table=new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                tableSelectionChanged();
            }
        });
        //one row at a time
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        ProgressRenderer renderer=new ProgressRenderer(0,100);
        renderer.setStringPainted(true);
        table.setDefaultRenderer(JProgressBar.class, renderer);
        
        table.setRowHeight((int)renderer.getPreferredSize().getHeight());
        
        JPanel downloadsPanel=new JPanel();
        downloadsPanel.setBorder(
                BorderFactory.createTitledBorder("Downloads"));
        downloadsPanel.setLayout(new BorderLayout());
        downloadsPanel.add(new JScrollPane(table),BorderLayout.CENTER);
        
        JPanel buttonsPanel=new JPanel();
        pauseButton=new JButton("Pause");
        pauseButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                actionPause();
            }
        });
        pauseButton.setEnabled(false);
        buttonsPanel.add(pauseButton);
        resumeButton=new JButton("Resume");
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionResume();
            }
        });
        resumeButton.setEnabled(false);
        buttonsPanel.add(resumeButton);
        canelButton=new JButton("Cancel");
        canelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionClear();
            }
        });
        clearButton.setEnabled(false);
        buttonsPanel.add(clearButton);
        
        
        
        
    
    
    }
    
    
    
    
    
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
}
