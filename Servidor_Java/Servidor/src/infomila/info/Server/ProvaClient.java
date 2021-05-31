/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infomila.info.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author averd
 */
public class ProvaClient {
    
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 9876);
        } catch (IOException ex) {
            System.out.println(ex.getMessage()); 
        }
    }
}
