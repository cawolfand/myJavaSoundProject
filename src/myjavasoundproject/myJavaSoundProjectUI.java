/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjavasoundproject;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import my.musicorganizer.MusicOrganizer;
import my.musicplayer.MusicPlayer;
import my.track.Track;
import my.trackreader.TrackReader;
import customComponents.MyButton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
/**
 *
 * @author Carol
 */


class MyCustomFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".txt" extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(".mp3") || file.getAbsolutePath().endsWith(".wav");
    }

    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "Text documents (*.txt)";
    }
}

public class myJavaSoundProjectUI extends javax.swing.JFrame
        implements ChangeListener, ActionListener, KeyListener {

    private ArrayList<Track> tracks;
    private TrackReader reader;
    private MusicOrganizer organizer;
    private MusicPlayer player;
    private boolean done = true;
    private Timer timer;
    private int audioPosition;
    private int audioLength;
    private int pageIndex;
    private String sortMode;
    private MyDefaultTableModel myDefaultTableModel;

    /**
     * Creates new form myJavaSoundProjectUI
     */
    public myJavaSoundProjectUI() {
       initMyComponents();
        initComponents();
        //instance table model   TableColumn trackColumn = songTable.getColumnModel().getColumn(2);
      
              
//        songTable.addKeyListener(new KeyListener() {
//            public void actionPerformed(KeyEvent e){
//                JOptionPane.showMessageDialog(songTable, "Don't do that");
//            }
//
//            @Override
//            public void keyTyped(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        });
//        
        organizer = new MusicOrganizer();
        player = new MusicPlayer();
        // This timer calls the tick( ) method 10 times a second to keep 
        // our slider in sync with the music.
        timer = new javax.swing.Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        jProgressBar1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = jProgressBar1.getValue();
                // Update the time label
                time.setText(value / 1000 + "."
                        + (value % 1000) / 100);
                // If we're not already there, skip there.
                // if (value != audioPosition) skip(value);
            }
        });
        String[] ordering = Track.FIELDS;
        //   for (int i=0; i<ordering.length; i++){
        //       jSortByCombo.addItem(ordering[i]);
        //   }
        sortMode = ordering[0];
        try {
            organizer.initLibraryDB();
            initTable();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
               
        directoryTree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
 
      
 
        fillTable2();
    }
   
    private void initMyComponents(){
        Object[][] rowData = {};
        Object[] columnNames = {"Artist", "Album","TrackNum","Title","Length","Genre"};
        
        myPlayButton =new MyButton("images\\playChrome.png");
        myPlayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPlayButtonActionPerformed(evt);
            }
        });
        this.add(myPlayButton);
        
        
        myDefaultTableModel = new MyDefaultTableModel(columnNames, 1);  
        
            
        
           
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        FileChooser = new javax.swing.JFileChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        directoryTree = new javax.swing.JTree();
        jNextButton = new javax.swing.JButton();
        jPrevButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        songTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPlayButton = new javax.swing.JButton();
        jStopButton = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        time = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPauseButton = new javax.swing.JButton();
        jSortByCombo = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jOpenMenu = new javax.swing.JMenuItem();
        jCreateDBItem = new javax.swing.JMenuItem();
        jExitMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jOpenNew = new javax.swing.JMenuItem();
        jDeleteMenu = new javax.swing.JMenuItem();

        FileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\Carol\\Music"));
        FileChooser.setDialogTitle("My Open Dialog");
        FileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        FileChooser.setMultiSelectionEnabled(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Album", "Title 2", "Title 3", "Title 4"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Music");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Album");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Artist");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Genre");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Playlist");
        treeNode1.add(treeNode2);
        directoryTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        directoryTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                directoryTreeValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(directoryTree);

        jNextButton.setText("Next");
        jNextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNextButtonActionPerformed(evt);
            }
        });

        jPrevButton.setText("Prev");
        jPrevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPrevButtonActionPerformed(evt);
            }
        });

        songTable.setBackground(new java.awt.Color(51, 0, 102));
        songTable.setFont(new java.awt.Font("Tekton Pro", 0, 14)); // NOI18N
        songTable.setForeground(new java.awt.Color(255, 255, 153));
        songTable.setModel(myDefaultTableModel);
        jScrollPane1.setViewportView(songTable);
        songTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPlayButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Carol\\Documents\\NetBeansProjects\\myJavaSoundProject2\\images\\play3.png")); // NOI18N
        jPlayButton.setToolTipText("Play ");
        jPlayButton.setBorder(null);
        jPlayButton.setBorderPainted(false);
        jPlayButton.setContentAreaFilled(false);
        jPlayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPlayButtonActionPerformed(evt);
            }
        });

        jStopButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Carol\\Documents\\NetBeansProjects\\myJavaSoundProject2\\images\\stop3.png")); // NOI18N
        jStopButton.setToolTipText("Stop");
        jStopButton.setBorder(null);
        jStopButton.setBorderPainted(false);
        jStopButton.setContentAreaFilled(false);
        jStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStopButtonActionPerformed(evt);
            }
        });

        time.setText("jLabel1");

        jLabel1.setText("Sort By");

        jPauseButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Carol\\Documents\\NetBeansProjects\\myJavaSoundProject2\\images\\pause3.png")); // NOI18N
        jPauseButton.setToolTipText("Pause");
        jPauseButton.setBorder(null);
        jPauseButton.setBorderPainted(false);
        jPauseButton.setContentAreaFilled(false);
        jPauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPauseButtonActionPerformed(evt);
            }
        });

        jSortByCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Artist", "Album", "Title" }));
        jSortByCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSortByComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPlayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPauseButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jStopButton)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(time)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSortByCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time)
                    .addComponent(jLabel1)
                    .addComponent(jSortByCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(jPlayButton, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
            .addComponent(jStopButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPauseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jMenu1.setText("File");

        jOpenMenu.setText("Open");
        jOpenMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOpenMenuActionPerformed(evt);
            }
        });
        jMenu1.add(jOpenMenu);

        jCreateDBItem.setText("CreateDB");
        jCreateDBItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreateDBItemActionPerformed(evt);
            }
        });
        jMenu1.add(jCreateDBItem);

        jExitMenu.setText("Exit");
        jExitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExitMenuActionPerformed(evt);
            }
        });
        jMenu1.add(jExitMenu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jOpenNew.setText("Add Folder");
        jOpenNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOpenNewActionPerformed(evt);
            }
        });
        jMenu2.add(jOpenNew);

        jDeleteMenu.setText("Delete");
        jDeleteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteMenuActionPerformed(evt);
            }
        });
        jMenu2.add(jDeleteMenu);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPrevButton)
                                .addGap(29, 29, 29)
                                .addComponent(jNextButton)
                                .addGap(90, 90, 90))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 681, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(747, 747, 747)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jNextButton)
                                    .addComponent(jPrevButton)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }//GEN-END:initComponents

    private void jExitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExitMenuActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jExitMenuActionPerformed

    private void jOpenMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOpenMenuActionPerformed
        // TODO add your handling code here:
        int returnVal = FileChooser.showOpenDialog(this);
        tracks = new ArrayList<Track>();
        reader = new TrackReader();

        int i = 0, rowCount = 0;
        String txtString = new String();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] file = FileChooser.getSelectedFiles();



            for (File f : file) {
                AudioFileFormat baseFileFormat = null;
                AudioFormat baseFormat = null;
                try {
                    baseFileFormat = AudioSystem.getAudioFileFormat(f);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(myJavaSoundProjectUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(myJavaSoundProjectUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println(">" + f.getName() + ",\r\n");
                txtString = txtString + f.getName() + ",\r\n";
                songTable.setValueAt(f.getName(), i++, 3);
            }

//        try {
//          // What to do with the file, e.g. display it in a TextArea
//        
//          jTextArea1.read( new FileReader( file[0].getAbsolutePath() ), null );
//        } catch (IOException ex) {
//          System.out.println("problem accessing file"+file[0].getAbsolutePath());
//        }
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_jOpenMenuActionPerformed

    private void jOpenNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOpenNewActionPerformed
        // TODO add your handling code here:
        int returnVal = FileChooser.showOpenDialog(this);
        int i = 0;

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            ArrayList<Track> addedTracks = organizer.readLibrary(file.getPath());
            for (Track track : addedTracks){
                Object[] rowData = {track.getAlbum(), track.getArtist(), track.getTrackNum(), track.getTitle(),track.getLength()};
                myDefaultTableModel.addRow(rowData);
            }
            fillTable2();


        } else {
            System.out.println("File access cancelled by user.");

        }
    }//GEN-LAST:event_jOpenNewActionPerformed

    private void jPlayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPlayButtonActionPerformed
        // TODO add your handling code here:
        int row, i, temp;
        String selectedTitle;
        File selectedFile;
        Track t;



        if (timer.isRunning()) {
            timer.stop();
        }

        row = songTable.getSelectedRow();
        selectedTitle = (String) songTable.getValueAt(row, 3);
        i = organizer.getTrack(selectedTitle);
        t = organizer.getTrack(i);

        player.startPlaying(t.getFilename());
        timer.start();
        temp = player.getLength();
        jProgressBar1.setMaximum(player.getLength());
        // jProgressBar1.setMaximum(player.getLength());        
    }//GEN-LAST:event_jPlayButtonActionPerformed

    private void jPauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPauseButtonActionPerformed
        // TODO add your handling code here:

        if (player.isPlaying()) {
            player.pause();
        } else {
            player.resume();
        }
    }//GEN-LAST:event_jPauseButtonActionPerformed

    private void jStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStopButtonActionPerformed
        // TODO add your handling code here:
        player.stop();
        timer.stop();
    }//GEN-LAST:event_jStopButtonActionPerformed

    private void jSortByComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSortByComboActionPerformed
        // TODO add your handling code here:
        JComboBox cb = (JComboBox) evt.getSource();
        String ordering = (String) cb.getSelectedItem();
        sortMode = ordering;
        if (ordering != null) {
            setListOrdering(ordering);
        }
    }//GEN-LAST:event_jSortByComboActionPerformed

    private void jNextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNextButtonActionPerformed
        // TODO add your handling code here:
        int i;
        Track t;
        int k, m;

        if (pageIndex + 1 == (int) organizer.getNumberOfTracks() / 20) {
            pageIndex++;
            jNextButton.setVisible(false);
            i = pageIndex;

            fillTable2();
        }
    }//GEN-LAST:event_jNextButtonActionPerformed

    private void jPrevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPrevButtonActionPerformed
        // TODO add your handling code here:
        if (pageIndex - 1 >= 0) {
            pageIndex--;
            jNextButton.setVisible(true);

            fillTable2();
        }
    }//GEN-LAST:event_jPrevButtonActionPerformed

    private void jCreateDBItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreateDBItemActionPerformed
        // TODO add your handling code here:
        //organizer.createLibraryDB();
    }//GEN-LAST:event_jCreateDBItemActionPerformed

    private void jDeleteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteMenuActionPerformed
        // TODO add your handling code here:
        int row = songTable.getSelectedRow();
        String selectedTitle = (String) songTable.getValueAt(row, 3);
        int i = organizer.getTrack(selectedTitle);
        organizer.removeTrack(i);
        fillTable2();
    }//GEN-LAST:event_jDeleteMenuActionPerformed

    private void directoryTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_directoryTreeValueChanged
        // TODO add your handling code here:
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           directoryTree.getLastSelectedPathComponent();
 
        if (node == null) return;
        
 
        Object nodeInfo = node.getUserObject();
        setListOrdering((String) nodeInfo);
            
               
    }//GEN-LAST:event_directoryTreeValueChanged

    
    private  void initTable(){
              int n = organizer.getNumberOfTracks();
        Track t;
       
        TableColumn trackColumn = songTable.getColumnModel().getColumn(2);
        trackColumn.setPreferredWidth(5);
      
       
       for(int i = 1; i < n ; i++){
           t = organizer.getTrack(i);
           String rowString = "Quiz #" + i;
           myDefaultTableModel.addRow(new Object[]{t.getAlbum(),
                                                    t.getArtist(),
                                                    t.getTitle(),
                                                    t.getTrackNum(),
                                                    t.getLength(),
                                                    ""});
           
       }
        
     

       
    }
    
    private void fillTable() {
        int i;
        Track t;
        int k, m;
        String lastAlbum = new String();
        List<Track> trackList = organizer.getAllTracks();
        m = organizer.getNumberOfTracks();

        if (pageIndex * 20 + 19 < m) {
            k = 20;
        } else {
            k = m % 20;
        }
        lastAlbum = "";
        for (i = 0; i < 20; i++) {
            if (i < k) {
                t = trackList.get(i + pageIndex * 20);
                if (i == 0){
                    lastAlbum = t.getAlbum();
                    songTable.setValueAt(t.getAlbum(), i, 0);
                }
                if ( !lastAlbum.equals(t.getAlbum())){
                     songTable.setValueAt(t.getAlbum(),i,0);
                     lastAlbum = t.getAlbum();
                   
                }
                
                
                songTable.setValueAt(t.getArtist(), i, 1);
                songTable.setValueAt(t.getTrackNum(), i, 2);
                songTable.setValueAt(t.getTitle(), i, 3);
                songTable.setValueAt(t.getLength(), i, 4);
            } else {
                songTable.setValueAt("", i, 0);
                songTable.setValueAt("", i, 1);
                songTable.setValueAt("", i, 2);
                songTable.setValueAt("", i, 3);
                songTable.setValueAt("", i, 4);
            }
        }

    }
private void fillTable2() {
        int i;
        Track t;
        int k, m;
        String lastAlbum = new String();
        List<Track> trackList = organizer.getAllTracks();
        m = organizer.getNumberOfTracks();
        
        if ( m != songTable.getRowCount()){
            //songs must have been added
            
        }

        if (pageIndex * 20 + 19 < m) {
            k = 20;
        } else {
            k = m % 20;
        }
        lastAlbum = "";
        for (i = 0; i < m; i++) {
                t = trackList.get(i + pageIndex * 20);
                           
                songTable.setValueAt(t.getAlbum(),i,0);
                songTable.setValueAt(t.getArtist(), i, 1);
                songTable.setValueAt(t.getTrackNum(), i, 2);
                songTable.setValueAt(t.getTitle(), i, 3);
                songTable.setValueAt(t.getLength(), i, 4);
           
              
        }

    }
    /**
     * Set the ordering of the track list.
     *
     * @param ordering The ordering to use.
     */
    private void setListOrdering(String ordering) {
        int i = 0, m, k;
        Track t;
        int startI;

        List<Track> trackList = organizer.sortByField(ordering);
        m = organizer.getNumberOfTracks();


        if (pageIndex * 20 + 19 < m) {
            k = 20;
        } else {
            k = m % 20;
        }


        for (i = 0; i < k; i++) {
            t = trackList.get(i + pageIndex * 20);
            songTable.setValueAt(t.getAlbum(), i, 0);
            songTable.setValueAt(t.getArtist(), i, 1);
            songTable.setValueAt(t.getTrackNum(), i, 2);
            songTable.setValueAt(t.getTitle(), i, 3);
            songTable.setValueAt(t.getLength(), i, 4);
            i++;
        }
    }

    // An internal method that updates the progress bar.
    // The Timer object calls it 10 times a second.
    // If the sound has finished, it resets to the beginning
    void tick() {

        audioPosition = (int) (player.getFrame());

        jProgressBar1.setValue(audioPosition);

        int i = jProgressBar1.getValue();

        // else reset( );  
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            //    int progress = (Integer) evt.getNewValue();
            int progress = 100 * (int) player.getFrame() / player.getLength();
            jProgressBar1.setValue(progress);

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                

}
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(myJavaSoundProjectUI.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(myJavaSoundProjectUI.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(myJavaSoundProjectUI.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(myJavaSoundProjectUI.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new myJavaSoundProjectUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser FileChooser;
    private javax.swing.JTree directoryTree;
    private javax.swing.JMenuItem jCreateDBItem;
    private javax.swing.JMenuItem jDeleteMenu;
    private javax.swing.JMenuItem jExitMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JButton jNextButton;
    private javax.swing.JMenuItem jOpenMenu;
    private javax.swing.JMenuItem jOpenNew;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jPauseButton;
    private javax.swing.JButton jPlayButton;
    private javax.swing.JButton jPrevButton;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox jSortByCombo;
    private javax.swing.JButton jStopButton;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable songTable;
    private javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables
   public class MyDefaultTableModel extends DefaultTableModel{
       public MyDefaultTableModel(Object[] colNames,int r){
           super(colNames,r);
       }
       Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
       
   }
    private MyButton myPlayButton;
    
    @Override
        public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
        public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
