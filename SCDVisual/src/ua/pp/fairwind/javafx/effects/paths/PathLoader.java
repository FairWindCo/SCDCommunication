/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.fairwind.javafx.effects.paths;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author msi
 */
public class PathLoader {
 
    public String getPath(String filename) throws IOException{
        StringBuilder buf = new StringBuilder();
        
        InputStream is = this.getClass().getResourceAsStream(filename);
        int read;
        while((read=is.read())!= -1)
        {
            buf.append((char)read);
        }
              
        
        return buf.toString();
        
    }
    public String getPath(int i) throws IOException{
        return getPath("path"+i);
    }
    
}
