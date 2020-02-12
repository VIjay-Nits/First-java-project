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
    public void resume(){
        status=DOWNLOADING;
    }
    public void cancel(){
        status=CANCELLED;
    }
    public void pause(){
        status=PAUSED;
    }
    public void error(){
        status=ERROR;
    }
    
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
            if(connection.getContentLength()<1){
                error();
            }
            if(size==-1){
                size=connection.getContentLength();
            }
            file=new RandomAccessFile(getFileName(url), "rw");
            file.seek(downloaded);
            
            stream=connection.getInputStream();
            while(status==DOWNLOADING){
                byte buffer[];
                if(size-downloaded>MAX_BUFFER_SIZE){
                    buffer=new byte[MAX_BUFFER_SIZE];
                }
                else{
                    buffer=new byte[size-downloaded];
                }
                
                int read =stream.read(buffer);
                if(read==-1){
                    break;
                }
                
                file.write(buffer, 0, read);
                downloaded=downloaded+read;
            }
            //downloading has finished
            if(status==DOWNLOADING){
                status=COMPLETE;
            }
            
        } catch (Exception e) {
            error();
        }
        finally{
            //close file
            if(file!=null){
                try{
                    file.close();
                }
                catch(Exception e){}
            }
        }
        
    
    }

    private String getFileName(URL url) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    
    
    
    
}
