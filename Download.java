/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadmanager;

/**
 *
 * @author USER
 */
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Observable;
import java.net.URL;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

class Download extends Observable implements Runnable {

    private static final int MAX_BUFFER_SIZE=1024;
    
    public static final String[] STATUSES={"Downloading","paused","Complete","Cancelled","Error"};
    
    public static final int DOWNLOADING=0;
    public static final int PAUSED=1;
    public static final int COMPLETE=2;
    public static final int CANCELLED=3;
    public static final int ERROR=4;
    
    private URL url;
    private int size;//size of download in byte;
    private int downloaded;//no. of bytes downloaded
    private int status;
    
    public Download(URL url){
        this.url=url;
        size=-1;
        downloaded=0;
        status=DOWNLOADING;
        
        
    }
    
    public String getUrl(){
        return url.toString();
    }
    public int getSize(){
        return this.size;
    }
    public float getProgress(){
        return ((float)this.downloaded/(float)this.size)*100 ;
    }
    public int getStatus(){
        return status;
    }
    public void resume(){status=DOWNLOADING;}
    public void cancel(){status=CANCELLED;}
    public void pause(){status=PAUSED;}
    public void error(){status=ERROR;}
    
    public void download(){
        new Thread(this).start();
    }
    

    @Override
    public void run() {
        RandomAccessFile file=null;
        InputStream stream=null;
    
        try {
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            
            connection.setRequestProperty("Range", "bytes="+downloaded+"-");
            connection.connect();
            if(connection.getResponseCode()/100!=2){
                error();
            }
        } catch (IOException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
   
    
    
    
    
    
}
