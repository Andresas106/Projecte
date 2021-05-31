/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infomila.info.Server;

import infomila.info.model.Cambrer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author averd
 */
public class Server extends Thread {
    
    Socket s = null;
    /**
     * @param args the command line arguments
     */
    
    public Server(Socket s)
    {
       this.s = s; 
    }
    
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(9876);
            
            while(true)
            {
                System.out.println("Estic escoltant clients");
                Socket s = server.accept(); 
                Server th = new Server(s);
                th.start();
                
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void run()
    {
        Properties p = new Properties();
        try {
            p.load(new FileReader("connexioMySQL.properties"));
        } catch (IOException ex) {
            System.out.println("Problemes en carregar el fitxer de configuració");
            System.out.println("Més info: " + ex.getMessage());
            System.exit(1);
        }
        // p conté les propietats necessàries per la connexió
        String url = p.getProperty("url");
        String usu = p.getProperty("usuari");
        String pwd = p.getProperty("contrasenya");
        if (url == null || usu == null || pwd == null) {
            System.out.println("Manca alguna de les propietats: url, usuari, contrasenya");
            System.exit(1);
        }
        // Ja tenim les 3 propietats
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        
        try {
            int opcio = -1;
            con = DriverManager.getConnection(url, usu, pwd);
            while(true)
            {
                DataInputStream dis = new DataInputStream(s.getInputStream());
                opcio = dis.readInt();

                switch(opcio)
                {
                    case 1:
                        boolean trobat = false;
                        dis = new DataInputStream(s.getInputStream());
                        String user = dis.readUTF();

                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        dos.writeInt(0);
                        
                        dis = new DataInputStream(s.getInputStream());
                        String passw = dis.readUTF();
 
                        
                        dos = new DataOutputStream(s.getOutputStream());
                        dos.writeInt(0);

                        
                        String consulta = "select c.codi, c.nom, c.cognom1, c.cognom2, c.usuari, c.passw from cambrer c"
                                + " where c.usuari=? and c.passw=?";
                        long codi = -1;
                        String nom = "";
                        String cognom1 = "";
                        String cognom2 = "";
                        String usuariSQL = "";
                        String passwSQL = "";
                        st = con.prepareStatement(consulta);
                        st.setString(1, user);
                        st.setString(2, passw);
                        rs = st.executeQuery();
                        while(rs.next())
                        {
                            trobat = true;
                            codi = rs.getLong("codi");
                            nom = rs.getString("nom");
                            cognom1 = rs.getString("cognom1");
                            cognom2 = rs.getString("cognom2");
                            usuariSQL = rs.getString("usuari");
                            passwSQL = rs.getString("passw");

                            
                        }

                        if(!trobat)
                        {
                            Thread.sleep(250); 
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeInt(0);
                            
                            Thread.sleep(250); 
                            //Enviem -1 perque el login no es correcte
                            
                            dos.writeInt(-1); 
                        }
                        else
                        {
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeInt(1);
                            
                            Thread.sleep(250); 
                            //Cambrer c = new Cambrer(codi, nom, cognom1, cognom2, user, passw) enviarem un cambrer 
                            //Codi cambrer
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeLong(codi);
                            
                            //nom
                            Thread.sleep(250);
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(nom);
                            
                            //cognom1
                            Thread.sleep(250);
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(cognom1);
                            
                            //cognom2
                            Thread.sleep(250);
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(cognom2);
                            
                            //user
                            Thread.sleep(250);
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(usuariSQL);
                            
                            //passw
                            Thread.sleep(250);
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(passwSQL);
                            
                            
                        }
                    break;
                    case 2:

                    break;
                    case 3:

                    break;
                    case 4:

                    break;
                    case 5:

                    break;
                    case 6:

                    break;
                    default:
                        throw new RuntimeException("Opcio no controlada:" + opcio);

                }
            }
        } catch (SQLException | IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
