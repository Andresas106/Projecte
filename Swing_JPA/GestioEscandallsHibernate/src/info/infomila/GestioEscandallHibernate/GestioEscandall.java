/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.GestioEscandallHibernate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author averd
 */
public class GestioEscandall {

    
    public static void main(String[] args) {
        String url;
        String user;
        String password;
        String up;
        try {
            InputStream input = new FileInputStream("MySql.properties");
            
            Properties prop = new Properties();
        
            prop.load(input); 
            
             url = prop.getProperty("url");
             user = prop.getProperty("user");
             password = prop.getProperty("password");
             up = prop.getProperty("up");
        } catch (FileNotFoundException ex) {
            throw new GestorEscandallException("Error en trobar fitxer de propietats");
        } catch (IOException ex) {
            throw new GestorEscandallException("Error en obrir fitxer de propietats");
        }
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            em = null;
            emf = null;
            System.out.println("Intent amb " + up);
            HashMap<String,String> propietats = new HashMap();
            propietats.put("javax.persistence.jdbc.url", url);
            propietats.put("javax.persistence.jdbc.user", user);
            propietats.put("javax.persistence.jdbc.password", password);
            emf = Persistence.createEntityManagerFactory(up, propietats);
            System.out.println("EntityManagerFactory creada");
            em = emf.createEntityManager();
            System.out.println();
            System.out.println("EntityManager creat");
            
            
            JFrame frame = new JFrame("Gestio d'escandalls");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 800);
            
            JPanel nort = new JPanel();
            
            frame.setVisible(true); 
            
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.print(ex.getCause() != null ? "Caused by:" + ex.getCause().getMessage() + "\n" : "");
            System.out.println("Traça:");
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
                System.out.println("EntityManager tancat");
            }
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory tancada");
            }
        }
    }
    
}
