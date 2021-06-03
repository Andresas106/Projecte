/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infomila.info.Server;

import infomila.info.model.Cambrer;
import infomila.info.model.Categoria;
import infomila.info.model.LiniaComanda;
import infomila.info.model.Plat;
import infomila.info.model.Taula;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author averd
 */
public class Server extends Thread {
    
    Socket s = null;
    public static Map<Long, Cambrer> sessions_ids;
    public static long sesID;
    /**
     * @param args the command line arguments
     */
    
    public Server(Socket s)
    {
       this.s = s; 
    }
    
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(15000);
            sessions_ids = new HashMap<Long, Cambrer>();
            while(true)
            {
                System.out.println("Estic escoltant clients");
                Socket s = server.accept(); 
                Server th = new Server(s);
                sesID = (long) (Math.random()*(1000000-100000+1)+100000);
                
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
        Statement st1 = null;
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
                        Login(dis, st, rs, con);
                    break;
                    case 2:
                        GetTaules(dis, st1, rs, con);
                    break;
                    case 3:
                        GetCarta(dis, st, rs, con);
                    break;
                    case 4:
                        GetComanda(dis, st, rs, con);
                    break;
                    case 5:

                    break;
                    case 6:
                        BuidarTaula(dis, st, rs, con);
                    break;
                    default:
                        throw new RuntimeException("Opcio no controlada:" + opcio);

                }
            }
        } catch (SQLException | IOException | InterruptedException  ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void Login(DataInputStream dis, PreparedStatement st, ResultSet rs, Connection con) throws IOException, SQLException, InterruptedException {
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
                            
                            Thread.sleep(150); 
                            
                            //Enviem la session id del cambrer
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeLong(sesID);
                            
                            Thread.sleep(150);
                            //Cambrer c = new Cambrer(codi, nom, cognom1, cognom2, user, passw) enviarem un cambrer 
                            //Codi cambrer
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeLong(codi);
                            
                            //nom
                            Thread.sleep(150);
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(nom);
                            
                            //cognom1
                            Thread.sleep(150);
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(cognom1);
                            
                            //cognom2
                            Thread.sleep(150);
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(cognom2);
                            
                            //user
                            Thread.sleep(150);
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(usuariSQL);
                            
                            //passw
                            Thread.sleep(150);
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(passwSQL); 
                            
                            Cambrer cam = new Cambrer(codi, nom, cognom1, cognom2, usuariSQL, passw);
                            sessions_ids.put(sesID, cam);
                        }
    }

    private void GetTaules(DataInputStream dis, Statement st, ResultSet rs, Connection con) throws SQLException, IOException, InterruptedException {
        int numero = -1;
        long codi = -1;
        String nom = "";
        int PlatsTotals = 0;
        int PlatsPreparats = 0;
        int PlatsPendents = 0;
        String consulta = "select t.numero, ifnull(c.codi, -1) as codi, ifnull(cam.nom, '') as nom, \n" +
"(select count(*) from liniacomanda lc where lc.comanda=c.codi) as PlatsTotals,\n" +
"(select count(*) from liniacomanda lc where lc.comanda = c.codi and lc.preparat=true) as PlatsPreparats,\n" +
" (select count(*) from liniacomanda lc where lc.comanda = c.codi and lc.preparat=false) as PlatsPendents\n" +
" from taula t left join comanda c\n" +
"on t.numero = c.taula left join cambrer cam on cam.codi = c.cambrer";
        Cambrer cam = null;
        
        dis = new DataInputStream(s.getInputStream());
        long sesion_id = dis.readLong();
        if(sessions_ids.containsKey(sesion_id))
        {
            cam = sessions_ids.get(sesion_id);
            
        }
        
        st = con.createStatement();
        rs = st.executeQuery(consulta);
        ArrayList<Taula> taules = new ArrayList<Taula>();
        while(rs.next())
        {
           numero = rs.getInt("numero");
           codi = rs.getLong("codi");
           nom = rs.getString("nom");
           PlatsTotals = rs.getInt("PlatsTotals");
           PlatsPreparats = rs.getInt("PlatsTotals");
           PlatsPendents = rs.getInt("PlatsPendents");
           Taula t = null;
           if(nom.equals(cam.getNom()))
           {
               t = new Taula(numero, codi, nom, PlatsTotals, PlatsPreparats, PlatsPendents, true);
           }
           else
           {
               t = new Taula(numero, codi, nom, PlatsTotals, PlatsPreparats, PlatsPendents, false);
           }
           
           taules.add(t);
        }   
           //ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
           //oos.writeObject(taules);
           
           int numTaules = taules.size();
           
           DataOutputStream dos = new DataOutputStream(s.getOutputStream());
           dos.writeInt(numTaules); 
           int rebut = 0;
           Thread.sleep(100); 
           for(int i=0;i<numTaules;i++)
           {
               //enviem numero de Taula
               dos = new DataOutputStream(s.getOutputStream());
               dos.writeInt(taules.get(i).getNumero()); 
               dis = new DataInputStream(s.getInputStream());
               rebut = dis.readInt();
               
               //enviem codi
               dos = new DataOutputStream(s.getOutputStream());
               dos.writeLong(taules.get(i).getCodi()); 
               
               dis = new DataInputStream(s.getInputStream());
               rebut = dis.readInt();
               //enviem nom
               dos = new DataOutputStream(s.getOutputStream());
               dos.writeUTF(taules.get(i).getNomCambrer());
               
               dis = new DataInputStream(s.getInputStream());
               rebut = dis.readInt();
               
               //enviem numPlats
               dos = new DataOutputStream(s.getOutputStream());
               dos.writeInt(taules.get(i).getNumPlats());
               
               dis = new DataInputStream(s.getInputStream());
               rebut = dis.readInt();
               
               //enviem plats preparats
               dos = new DataOutputStream(s.getOutputStream());
               dos.writeInt(taules.get(i).getPlatsPreparats());
               
               dis = new DataInputStream(s.getInputStream());
               rebut = dis.readInt();
               
               //enviem plats pendents
               dos = new DataOutputStream(s.getOutputStream());
               dos.writeInt(taules.get(i).getPlatsPendents());
               
               dis = new DataInputStream(s.getInputStream());
               rebut = dis.readInt();
               
               //enviem es meva
               dos = new DataOutputStream(s.getOutputStream());
               dos.writeBoolean(taules.get(i).isEs_meva());
               
           }
        
    }
 
    private void BuidarTaula(DataInputStream dis , PreparedStatement st, ResultSet rs, Connection con) throws IOException, SQLException
    {
        String consulta1 = "delete from comanda where taula=?";
        String consulta2 = "delete from liniacomanda where comanda=?";
        dis = new DataInputStream(s.getInputStream());
        long sesion_id = dis.readLong();
        if(sessions_ids.containsKey(sesion_id))
        {
            //El usuari te acces
            dis = new DataInputStream(s.getInputStream());
            int numeroTaula = dis.readInt();
            
            dis = new DataInputStream(s.getInputStream());
            long codiComanda = dis.readLong();
            st = con.prepareStatement(consulta2);
            
            st.setLong(1, codiComanda);
            
            st.executeUpdate();
            
            st = con.prepareStatement(consulta1);
            
            st.setInt(1, numeroTaula); 
            
            int delete = st.executeUpdate();
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());            
            if(delete == 1)
            {
                dos.writeInt(0);
            }
            else
            {
                dos.writeInt(-1); 
            }
        }
    }
    
    private void GetCarta(DataInputStream dis, Statement st, ResultSet rs, Connection con) throws IOException, SQLException
    {
        String consulta1 = "select c.codi, c.nom from categoria c";
        String consulta2 = "select p.codi, p.nom, p.preu, octet_length(p.foto) as midaFoto, p.foto, p.categoria from plat p";
        long codiCategoria;
        String nomCategoria;
        dis = new DataInputStream(s.getInputStream());
        long sesion_id = dis.readLong();
        if(sessions_ids.containsKey(sesion_id))
        {
            st = con.createStatement();
            rs = st.executeQuery(consulta1);
            ArrayList<Categoria> categories = new ArrayList<Categoria>();
            
            while(rs.next())
            {
                codiCategoria = rs.getLong("codi");
                nomCategoria = rs.getString("nom");
                
                Categoria cat = new Categoria(codiCategoria, nomCategoria);
                
                categories.add(cat);
            }
            
            int numCategories = categories.size();
            
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(numCategories);
            
            dis.readInt();
            
            for(int i=0;i<numCategories;i++)
            {
               dos = new DataOutputStream(s.getOutputStream());
               dos.writeLong(categories.get(i).getCodi());
               
               dis.readInt();
               
               dos = new DataOutputStream(s.getOutputStream());
               dos.writeUTF(categories.get(i).getNom());
              
               dis.readInt();
            }
            
            st = con.createStatement();
            rs =st.executeQuery(consulta2);
            long codiPlat;
            String nomPlat;
            float preu;
            int midaBytes;
            byte[] streamfoto;
            int categoria;
            ArrayList<Plat> plats = new ArrayList<>();
            while(rs.next())
            {
                codiPlat = rs.getLong("codi");
                nomPlat = rs.getString("nom");
                preu = rs.getFloat("preu"); 
                midaBytes = rs.getInt("midaFoto");
                streamfoto = rs.getBytes("foto");
                categoria = rs.getInt("categoria");
                Plat p = new Plat(codiPlat, nomPlat, preu, midaBytes, streamfoto, categoria);
                
                plats.add(p);
            }
            
            int numPlats = plats.size();
            
            dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(numPlats);
            
            dis.readInt();
            
            for(int i=0;i< numPlats;i++)
            {
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeLong(plats.get(i).getCodi());
                
                dis.readInt();
                
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(plats.get(i).getNom());
                
                dis.readInt();
                
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeFloat(plats.get(i).getPreu());
                
                dis.readInt();
                
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(plats.get(i).getMidaBytesFoto());
                
                dis.readInt();
                
                dos = new DataOutputStream(s.getOutputStream());
                dos.write(plats.get(i).getStreamFoto());
                
                dis.readInt();
                
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(plats.get(i).getCategoria());
                
                dis.readInt();
            }
        }
    }
    
    private void GetComanda(DataInputStream dis, PreparedStatement st, ResultSet rs, Connection con) throws IOException, SQLException
    {
        String consulta = "select lc.num, lc.quantitat, lc.item, p.nom, p.preu from liniacomanda lc"
                + " join plat p on lc.item=p.codi where lc.comanda=?";
        
         dis = new DataInputStream(s.getInputStream());
        long sesion_id = dis.readLong();
        if(sessions_ids.containsKey(sesion_id))
        {
            dis = new DataInputStream(s.getInputStream());
            long codiComanda = dis.readLong();
            
            st = con.prepareStatement(consulta);
            
            st.setLong(1, codiComanda);
            
            rs = st.executeQuery();
            
            List<LiniaComanda> linies = new ArrayList<>();
            while(rs.next())
            {
                int numero = rs.getInt("num");
                int quantitat = rs.getInt("quantitat");
                String nom = rs.getString("nom");
                int plat = rs.getInt("item");
                float preu = rs.getFloat("preu");
                LiniaComanda lc = new LiniaComanda(numero, quantitat, codiComanda, nom, preu);
                
                linies.add(lc);
            }
            
            int numLinies = linies.size();
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(numLinies);
            
            dis = new DataInputStream(s.getInputStream());
            dis.readInt();
            
            for(int i=0;i<numLinies;i++)
            {
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(linies.get(i).getNumero());
                
                dis = new DataInputStream(s.getInputStream());
                dis.readInt();
                
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(linies.get(i).getQuantitat());
                
                dis = new DataInputStream(s.getInputStream());
                dis.readInt();
                
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeLong(linies.get(i).getCodiPlat());
                
                dis = new DataInputStream(s.getInputStream());
                dis.readInt();
                
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(linies.get(i).getNomPlat());
                
                dis = new DataInputStream(s.getInputStream());
                dis.readInt();
                
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeFloat(linies.get(i).getPreu());
                
                dis = new DataInputStream(s.getInputStream());
                dis.readInt();
            }
        }
    }
    
    private void CreateComanda()
    {
        
    }
}
