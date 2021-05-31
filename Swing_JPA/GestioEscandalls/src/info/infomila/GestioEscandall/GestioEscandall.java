/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.GestioEscandallHibernate;

import info.infomila.model.Categoria;
import info.infomila.model.Ingredient;
import info.infomila.model.LiniaEscandall;
import info.infomila.model.Plat;
import info.infomila.model.Unitat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author averd
 */
public class GestioEscandall {

    JDialog dialog;
    Ingredient ingSelected = null;
    int rowSelected = -1;
    int numLinia;
    Unitat unitSelected = null;
    LiniaEscandall LiniaEscandallSelected = null;
    Integer eleccioRadioButton = -1;
    String eleccioCombo = "";
    DefaultListModel modelList = new DefaultListModel();
    JComboBox categoria;
    JFrame frame;
    JList plats;
    EntityManagerFactory emf = null;
    EntityManager em = null;
    public static void main(String[] args) {
        GestioEscandall ge = new GestioEscandall();
    }


    public Color convertirColorHexARGB(String colorStr)
    {
        return new Color(
            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ));
    }
    
    public GestioEscandall()
    {
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
            
            
            frame = new JFrame("Gestio d'escandalls");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setSize(450, 450);
            
            JMenuBar barra = new JMenuBar();
            JMenu menuOpcions = new JMenu("Opcions");
            JMenuItem opcioSortir = new JMenuItem("Exit");
            KeyStroke altF4 = KeyStroke.getKeyStroke("alt F4");
            opcioSortir.setAccelerator(altF4);
            opcioSortir.addActionListener(new GestioMenus());
            menuOpcions.add(opcioSortir);
            barra.add(menuOpcions);
            
            frame.setJMenuBar(barra);
            
                
            
            JPanel esquerra = new JPanel();
            esquerra.setLayout(new BoxLayout(esquerra,BoxLayout.Y_AXIS));
            
            plats = new JList(modelList);
            plats.setPreferredSize(new Dimension(1200, 200));
            
            plats.setCellRenderer(new DefaultListCellRenderer()
            
            {
                @Override
                     public Component getListCellRendererComponent(JList list, Object value, int index,
                               boolean isSelected, boolean cellHasFocus) {
                          Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                               Plat plat = (Plat) value;
                               String consulta = "select c from Categoria c join Plat p on p.categoria = c.codi"
                                       + " where p.codi=:codi";
                               Query q = em.createQuery(consulta);
                               q.setParameter("codi", plat.getCodi());
                               List<Categoria> colors = q.getResultList();
                               Color color = convertirColorHexARGB(colors.get(0).getColor().toString());
                               c.setBackground(color);
                          
                          return c;
                     }
            }); 
            
            plats.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        JList theList = (JList) e.getSource();
                        
                        Plat plat = (Plat) theList.getSelectedValue();
                        Rectangle r = theList.getCellBounds(0, theList.getLastVisibleIndex());
                        if(plat != null && r!= null && r.contains(e.getPoint()))
                        {
                            dialog = new JDialog(frame, "Editar escandall plat " + plat.getNom(), true);
                            dialog.setSize(new Dimension(700, 350));
                            
                            colocarObjectesJDialog(plat);
                            
                            dialog.setVisible(true);
                        }
                    }
    }
            });
           
            JScrollPane scroll = new JScrollPane(plats);
            frame.add(scroll);
            
            categoria = new JComboBox();
             
            String consulta = "select nom from Categoria";
            Query q = em.createQuery(consulta);
            List<String> categories = q.getResultList();
            
            categoria.addItem(""); 
            
            for(int i=0;i< categories.size();i++)
            {
                categoria.addItem(categories.get(i));
            }
            JLabel titolCategoria = new JLabel("Categoria");
            esquerra.add(titolCategoria);
            categoria.addActionListener(new GestioComboBox()); 
            esquerra.add(categoria);
            
            titolCategoria.setFont(titolCategoria.getFont().deriveFont(20f)); 
            
            JRadioButton radioTotes = new JRadioButton("Totes", true);
            JRadioButton radioSi = new JRadioButton("Si");
            JRadioButton radioNo = new JRadioButton("No");
            
            ButtonGroup grup = new ButtonGroup();
            grup.add(radioTotes);
            grup.add(radioSi);
            grup.add(radioNo);
            
            
            JLabel titolDisponibilitat = new JLabel("Disponibilitat");
            titolDisponibilitat.setFont(titolDisponibilitat.getFont().deriveFont(20f)); 
            esquerra.add(titolDisponibilitat);
            
            radioTotes.addActionListener(new GestioCheckBox());
            radioSi.addActionListener(new GestioCheckBox());
            radioNo.addActionListener(new GestioCheckBox());
            esquerra.add(radioTotes);
            esquerra.add(radioSi);
            esquerra.add(radioNo);
            
            JButton cercar = new JButton("Cercar");
            cercar.addActionListener(new GestioButton()); 
            esquerra.add(cercar);
            
            frame.add(esquerra, BorderLayout.WEST);
            
            
            
            frame.setVisible(true); 
            frame.pack();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.print(ex.getCause() != null ? "Caused by:" + ex.getCause().getMessage() + "\n" : "");
            System.out.println("Traça:");
            ex.printStackTrace();
        }
    }
    
    public void colocarObjectesJDialog(Plat plat)
    {
        JPanel esquerra = new JPanel();
        esquerra.setLayout(new BoxLayout(esquerra, BoxLayout.Y_AXIS));
        DefaultTableModel model = new DefaultTableModel()        
        {
            @Override
            public boolean isCellEditable(int row, int column) {
            //all cells false
                return false;
    }
        };
        JTable liniesEscandallTaula = new JTable(model);
        DefaultListModel modelIngredients = new DefaultListModel();
        DefaultListModel modelUnitats = new DefaultListModel();
        String[] columnes = {"NUM", "QUANTITAT", "UNITAT", "INGREDIENT"};
        
        for(int i=0;i<columnes.length;i++)
        {
            model.addColumn(columnes[i]); 
        }

        liniesEscandallTaula.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0){
                
                rowSelected = liniesEscandallTaula.getSelectedRow(); 
                if(rowSelected != -1){
                    numLinia = (int) model.getValueAt(rowSelected, 0);

                    String consulta = "select le from LiniaEscandall le where le.plat=:plat and le.num=:num";
                    Query q = em.createQuery(consulta);
                    q.setParameter("plat", plat);
                    q.setParameter("num", numLinia);

                    LiniaEscandallSelected = (LiniaEscandall) q.getSingleResult();
                }
            }
        }); 
        
        
        String consulta = "select le from LiniaEscandall le where le.plat=:plat";
        Query q = em.createQuery(consulta);
        q.setParameter("plat", plat);
        List<LiniaEscandall> liniaEscandallRes = q.getResultList();
        
        for(int i=0;i<liniaEscandallRes.size();i++)
        {
            Object[] object = new Object[]{liniaEscandallRes.get(i).getNum(),liniaEscandallRes.get(i).getQuantitat(),
                                           liniaEscandallRes.get(i).getUnitat().getCodi(),
                                           liniaEscandallRes.get(i).getIngredient().getCodi()};
            model.addRow(object);
        }
        
        
        JScrollPane scroll = new JScrollPane(liniesEscandallTaula,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        scroll.setPreferredSize(new Dimension(400,200));
        
        JPanel esquerraSud = new JPanel();
        esquerraSud.setLayout(new BoxLayout(esquerraSud, BoxLayout.X_AXIS)); 
        
        JButton afegir = new JButton("Afegir");
        
        afegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                String consulta = "select le from LiniaEscandall le where le.plat=:plat";
                Query q = em.createQuery(consulta);
                q.setParameter("plat", plat);
                List<LiniaEscandall> Linies = q.getResultList();
                int numLinies = Linies.size();
                
                if(ingSelected != null && unitSelected!=null){

                    String m = null;
                    do{
                     m = JOptionPane.showInputDialog("Inserint linia amb Ingredient "
                            + ingSelected.getCodi() + " i Unitat " + unitSelected.getCodi() + "\n" + "Indica quantitat");
                    }while( m!= null && !m.matches("^[1-9][0-9]*$"));
                    if(m != null)
                    {
                        em.getTransaction().begin();
                        int quantitat = Integer.parseInt(m);
                        LiniaEscandall le = new LiniaEscandall(plat.getCodi(), numLinies+1, quantitat, ingSelected, unitSelected);
                        em.persist(le);
                        Object[] o = new Object[]{numLinies+1, quantitat, unitSelected.getCodi(), ingSelected.getCodi()};
                        model.addRow(o);
                        em.getTransaction().commit();
                    }
                }
                
                
                
            }
        });
        
        JButton eliminar = new JButton("Eliminar");
        
        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(LiniaEscandallSelected != null && rowSelected != -1)
                {
                    String consulta = "select le from LiniaEscandall le where le.plat=:plat and le.num=:num";
                    Query q= em.createQuery(consulta);
                    q.setParameter("plat", plat);
                    q.setParameter("num", LiniaEscandallSelected.getNum());
                    List<LiniaEscandall> liniaEscandall = q.getResultList();
                    if(liniaEscandall.size() == 1)
                    {


                            int input = JOptionPane.showConfirmDialog(dialog, "Segur que vols eliminar la linia "
                                    + LiniaEscandallSelected.getNum(), "Eliminar Linia Escandall", JOptionPane.YES_NO_OPTION);
                        if(input == 0)
                        {
                            em.getTransaction().begin();
                            
                            em.remove(liniaEscandall.get(0));  
                            
                            model.removeRow(rowSelected);  
                            
                            em.getTransaction().commit();
                        }
                    }
                }
            }
        });
        
        esquerraSud.add(afegir);
        esquerraSud.add(eliminar);
        
        
        
        esquerra.add(scroll);
        esquerra.add(esquerraSud);
        dialog.add(esquerra, BorderLayout.WEST);
        
        JPanel dreta = new JPanel();
        dreta.setLayout(new BoxLayout(dreta, BoxLayout.Y_AXIS));
        
        JList ingredients = new JList(modelIngredients);
        JList unitats = new JList(modelUnitats);
        
        JLabel titleIngredients = new JLabel("Ingredients");
        dreta.add(titleIngredients);
        titleIngredients.setFont(titleIngredients.getFont().deriveFont(20f)); 
        scroll = new JScrollPane(ingredients,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dreta.add(scroll);
        JLabel titleUnitats = new JLabel("Unitats");
        dreta.add(titleUnitats);
        titleUnitats.setFont(titleUnitats.getFont().deriveFont(20f)); 
        scroll = new JScrollPane(unitats,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dreta.add(scroll);
        
        consulta = "select i from Ingredient i";
        q = em.createQuery(consulta);
        List<Ingredient> ingredientsres = q.getResultList();
        
        for(int i=0;i<ingredientsres.size();i++)
        {
            modelIngredients.addElement(ingredientsres.get(i)); 
        }
        
        consulta = "select u from Unitat u";
        
        
        q = em.createQuery(consulta);
        List<Unitat> unitatsres = q.getResultList();
        
        for(int i=0;i<unitatsres.size();i++)
        {
            modelUnitats.addElement(unitatsres.get(i)); 
        }
        
        ingredients.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                JList listIng= (JList) arg0.getSource();
                ingSelected = (Ingredient) listIng.getSelectedValue();
            }
        });
        
        unitats.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                JList listUnit = (JList) arg0.getSource();
                unitSelected = (Unitat) listUnit.getSelectedValue();
            }
        });
        
        dialog.add(dreta);
        
        
        
    }

    class GestioMenus implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            
                if (em != null) {
                em.close();
                System.out.println("EntityManager tancat");
            }
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory tancada");
            }
                frame.dispose();
        }

    }
    
    class GestioCheckBox implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String opcio = arg0.getActionCommand();
            if(opcio == "Totes")
            {
                eleccioRadioButton = -1;
            }else if(opcio=="Si")
            {
                eleccioRadioButton = 1;
            }else if(opcio=="No")
            {
                eleccioRadioButton = 0;
            }
        }
        
    }
    
    class GestioComboBox implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent arg0) {             
               eleccioCombo = categoria.getSelectedItem().toString();
        }
        
    }
    
    class GestioButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String opcio = arg0.getActionCommand();
            if(opcio == "Cercar"){
                modelList.clear();
                if(eleccioCombo!="" && eleccioRadioButton != -1)
                {
                    String consulta = "select p from Plat p join Categoria c on p.categoria=c.codi"
                            + " where c.nom=:name and disponible=:disponible";
                    Query q = em.createQuery(consulta);
                    q.setParameter("name", eleccioCombo);
                    q.setParameter("disponible", eleccioRadioButton); 
                    List<Plat> platsCerca = q.getResultList();
                    for(int i = 0;i< platsCerca.size();i++)
                    {
                        modelList.addElement(platsCerca.get(i));  
                    }
                    plats = new JList(modelList);
                }else if(eleccioCombo == "" && eleccioRadioButton != -1)
                {
                    String consulta = "select p from Plat p"
                        + " where  disponible=:disponible";
                    Query q = em.createQuery(consulta);
                    q.setParameter("disponible", eleccioRadioButton); 
                    List<Plat> platsCerca = q.getResultList();
                    for(int i = 0;i< platsCerca.size();i++)
                    {
                        modelList.addElement(platsCerca.get(i));  
                    }
                    plats = new JList(modelList);
                }
                else if(eleccioCombo != "" && eleccioRadioButton == -1)
                {
                    String consulta = "select p from Plat p join Categoria c on p.categoria=c.codi"
                            + " where c.nom=:name";
                    Query q = em.createQuery(consulta);
                    q.setParameter("name", eleccioCombo);
                    List<Plat> platsCerca = q.getResultList();
                    for(int i = 0;i< platsCerca.size();i++)
                    {
                        modelList.addElement(platsCerca.get(i));  
                    }
                    plats = new JList(modelList);
                }
                else{
                    String consulta = "select p from Plat p";
                    Query q = em.createQuery(consulta);
                    List<Plat> platsCerca = q.getResultList();
                    for(int i = 0;i< platsCerca.size();i++)
                    {
                        modelList.addElement(platsCerca.get(i));  
                    }
                    plats = new JList(modelList);
                }
            }
        }
    }        
}    


