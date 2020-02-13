/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadmanager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;

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
    
    private DownloadsTableModel tableModel1;//download table's data model
    private Download selectedDownload;
    
    private boolean clearing;//table selection is being cleared or not
    private void actionExit(){System.exit(0);}
    public DownloadManager() {
        setTitle("Downlaod Manager");
        setSize(640,480);
        addWindowListener(new WindowAdapter(){@Override
        public void windowClosing(WindowEvent e){actionExit();}});
    }
    
    
    
    
    
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
}
