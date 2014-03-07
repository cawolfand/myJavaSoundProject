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
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import my.audioplayer.AudioPlayer;

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
    private AudioPlayer audioPlayer;
    private int currentSelection;   //indexed by songTable
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
        audioPlayer = new AudioPlayer();
        // This timer calls the tick( ) method 10 times a second to keep 
        // our slider in sync with the music.
        timer = new javax.swing.Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
//         jGainSlider.setValue(Math.round( 100*audioPlayer.getGain()));
//        jProgressSlider.addChangeListener(new ChangeListener() {
//            public void stateChanged(ChangeEvent e) {
//                int value = jProgressBar1.getValue();
//                // Update the time label
//                jNowPlayingLabel.setText(value / 1000 + "."
//                        + (value % 1000) / 100);
//                // If we're not already there, skip there.
//                // if (value != audioPosition) skip(value);
//                if (!jProgressSlider.getValueIsAdjusting()) {
//                    int val = (int) jProgressSlider.getValue();
//
//                    int sval = (int) (player.getLength() * val / 100);
//                    System.out.print("Slider sval = " + sval);
//                    player.seekTo(val);
//                    player.resume();
//                } else {
//                    player.pause();
//                }
//            }
//        });

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


        directoryTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        ImageIcon img = new ImageIcon("C:\\Users\\Carol\\Documents\\NetBeansProjects\\myJavaSoundProject3\\myJavaSoundProject\\images\\2ts Icon2.png");
        setIconImage(img.getImage());
        //jLabel2.setIcon(img);

        fillTable2();
    }

    private void initMyComponents() {
        Object[][] rowData = {};
        Object[] columnNames = {"Artist", "Album", "TrackNum", "Title", "Length", "Genre"};

        myPlayButton = new MyButton("images\\playChrome.png");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        songTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPlayButton = new javax.swing.JButton();
        jStopButton = new javax.swing.JButton();
        jPauseButton = new javax.swing.JButton();
        jNextButton = new javax.swing.JButton();
        jPrevButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jNowPlayingLabel1 = new javax.swing.JLabel();
        jGainSlider = new javax.swing.JSlider();
        jPanSlider = new javax.swing.JSlider();
        jLabelVolMin = new javax.swing.JLabel();
        jLabelVolMax = new javax.swing.JLabel();
        jLabelVolVal = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jNowPlayingLabel = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jProgressSlider = new javax.swing.JSlider();
        jLabelTime = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
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

        songTable.setBackground(new java.awt.Color(51, 0, 102));
        songTable.setFont(new java.awt.Font("Tekton Pro", 0, 14)); // NOI18N
        songTable.setForeground(new java.awt.Color(255, 255, 153));
        songTable.setModel(myDefaultTableModel);
        jScrollPane1.setViewportView(songTable);
        songTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPlayButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Carol\\Documents\\NetBeansProjects\\myJavaSoundProject3\\myJavaSoundProject\\images\\play3.png")); // NOI18N
        jPlayButton.setToolTipText("Play ");
        jPlayButton.setBorder(null);
        jPlayButton.setBorderPainted(false);
        jPlayButton.setContentAreaFilled(false);
        jPlayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPlayButtonActionPerformed(evt);
            }
        });

        jStopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myjavasoundproject/stop3.png"))); // NOI18N
        jStopButton.setToolTipText("Stop");
        jStopButton.setBorder(null);
        jStopButton.setBorderPainted(false);
        jStopButton.setContentAreaFilled(false);
        jStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStopButtonActionPerformed(evt);
            }
        });

        jPauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pause3.png"))); // NOI18N
        jPauseButton.setToolTipText("Pause");
        jPauseButton.setBorder(null);
        jPauseButton.setBorderPainted(false);
        jPauseButton.setContentAreaFilled(false);
        jPauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPauseButtonActionPerformed(evt);
            }
        });

        jNextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myjavasoundproject/next.png"))); // NOI18N
        jNextButton.setBorder(null);
        jNextButton.setBorderPainted(false);
        jNextButton.setContentAreaFilled(false);
        jNextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNextButtonActionPerformed(evt);
            }
        });

        jPrevButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myjavasoundproject/prev.png"))); // NOI18N
        jPrevButton.setBorder(null);
        jPrevButton.setBorderPainted(false);
        jPrevButton.setContentAreaFilled(false);
        jPrevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPrevButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPrevButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPlayButton)
                .addGap(2, 2, 2)
                .addComponent(jPauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jStopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jNextButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jNextButton)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jStopButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPauseButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPlayButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPrevButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jNowPlayingLabel1.setText("Volume");

        jGainSlider.setMajorTickSpacing(10);
        jGainSlider.setPaintLabels(true);
        jGainSlider.setPaintTicks(true);
        jGainSlider.setValue(0);
        jGainSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jGainSlider.setName(""); // NOI18N
        jGainSlider.setValueIsAdjusting(true);
        jGainSlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jGainSliderMouseDragged(evt);
            }
        });

        jPanSlider.setMaximum(1);
        jPanSlider.setMinimum(-1);
        jPanSlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanSliderMouseDragged(evt);
            }
        });

        jLabelVolMin.setText("jLabel1");

        jLabelVolMax.setText("jLabel1");

        jLabelVolVal.setText("jLabel1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jGainSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(104, 104, 104)
                    .addComponent(jNowPlayingLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelVolVal)))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelVolMin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelVolMax)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jNowPlayingLabel1)
                    .addComponent(jLabelVolVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jGainSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVolMin)
                    .addComponent(jLabelVolMax))
                .addGap(4, 4, 4)
                .addComponent(jPanSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jNowPlayingLabel.setText("label");

        jProgressSlider.setToolTipText("");
        jProgressSlider.setValue(0);
        jProgressSlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jProgressSliderMouseDragged(evt);
            }
        });

        jLabelTime.setText("jLabel1");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jNowPlayingLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelTime))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jProgressSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jNowPlayingLabel)
                    .addComponent(jLabelTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jProgressSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myjavasoundproject/ts Icon3.png"))); // NOI18N

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jButton1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 11, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(246, 246, 246)))
                .addComponent(jButton1)
                .addGap(266, 266, 266))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            for (Track track : addedTracks) {
                Object[] rowData = {track.getAlbum(), track.getArtist(), track.getTrackNum(), track.getTitle(), track.getLength(), track.getGenre()};
                myDefaultTableModel.addRow(rowData);
            }
            fillTable2();


        } else {
            System.out.println("File access cancelled by user.");

        }
    }//GEN-LAST:event_jOpenNewActionPerformed

    private void jPrevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPrevButtonActionPerformed
        // TODO add your handling code here:
           int  i, temp;
        String selectedTitle;
        File selectedFile;
        Track t;

        if (timer.isRunning()) {
            timer.stop();
        }
        if(audioPlayer.isPlaying()){
            audioPlayer.stop();
        }
        if (currentSelection>0){
             currentSelection =  songTable.getSelectedRow()-1;
        }
        int realColumnIndex = songTable.convertColumnIndexToModel(3);
        songTable.changeSelection(currentSelection, realColumnIndex,false, false);
        selectedTitle = (String) songTable.getValueAt(currentSelection, realColumnIndex);
        i = organizer.getTrack(selectedTitle);
        t = organizer.getTrack(i);

        audioPlayer.startPlaying(t.getFilename());
      
        temp = player.getLength();
        jProgressBar1.setMaximum(audioPlayer.getLength());
        jProgressSlider.setMaximum(audioPlayer.getLength());
        jNowPlayingLabel.setText(selectedTitle);
          timer.start();
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
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();

        if (node == null) {
            return;
        }


        Object nodeInfo = node.getUserObject();
        setListOrdering((String) nodeInfo);


    }//GEN-LAST:event_directoryTreeValueChanged

    private void jNextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNextButtonActionPerformed
        // TODO add your handling code here:
        int  i;
        String selectedTitle;
        Track t;

        if (timer.isRunning()) {
            timer.stop();
        }
        if(audioPlayer.isPlaying()){
            audioPlayer.stop();
        }
        if (currentSelection < organizer.getNumberOfTracks()- 1 ){
            currentSelection = 1 + songTable.getSelectedRow();
        }
   
        int realColumnIndex = songTable.convertColumnIndexToModel(3);
        songTable.changeSelection(currentSelection, realColumnIndex,false, false);
        selectedTitle = (String) songTable.getValueAt(currentSelection, realColumnIndex);
        i = organizer.getTrack(selectedTitle);
        t = organizer.getTrack(i);

        audioPlayer.startPlaying(t.getFilename());
      
        
        jProgressBar1.setMaximum(player.getLength());
        jProgressSlider.setMaximum(player.getLength());
        jNowPlayingLabel.setText(selectedTitle);
          timer.start();
    }//GEN-LAST:event_jNextButtonActionPerformed

    private void jPauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPauseButtonActionPerformed
        // TODO add your handling code here:

        if (audioPlayer.isPlaying()) {
            audioPlayer.pause();
        } else {
            audioPlayer.resume();
        }
    }//GEN-LAST:event_jPauseButtonActionPerformed

    private void jStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStopButtonActionPerformed
        // TODO add your handling code here:
        audioPlayer.stop();
        timer.stop();
        if (!jPlayButton.isEnabled())
            jPlayButton.setEnabled(true);
    }//GEN-LAST:event_jStopButtonActionPerformed

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
        int realColumnIndex = songTable.convertColumnIndexToModel(3);

        selectedTitle = (String) songTable.getValueAt(row, realColumnIndex);
        i = organizer.getTrack(selectedTitle);
        t = organizer.getTrack(i);

        audioPlayer.startPlaying(t.getFilename());
        if (audioPlayer.isPlaying())
            jPlayButton.setEnabled(false);
        temp = player.getLength();
        jProgressBar1.setMaximum(audioPlayer.getLength());
        jProgressSlider.setMaximum(audioPlayer.getLength());
        jGainSlider.setMinimum(audioPlayer.getGainMin());
        jLabelVolMin.setText(Integer.toString(audioPlayer.getGainMin()));
        jGainSlider.setMaximum(audioPlayer.getGainMax());
        jLabelVolMax.setText(Integer.toString(audioPlayer.getGainMax()));
        jGainSlider.setValue(Math.round( audioPlayer.getGain()));
        jLabelVolVal.setText(Integer.toString(Math.round(audioPlayer.getGain())));

        jNowPlayingLabel.setText(selectedTitle);
          timer.start();
          // jProgressBar1.setMaximum(player.getLength());
    }//GEN-LAST:event_jPlayButtonActionPerformed

    private void jProgressSliderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProgressSliderMouseDragged
        // TODO add your handling code here:
        
       boolean state;
        JSlider source = (JSlider)evt.getSource();
        state = source.getValueIsAdjusting();
        //int val = source.getValue();
        timer.stop();
        audioPlayer.pause();
        int val = (int) source.getValue();
        jLabelTime.setText(Integer.toString(val));
        audioPlayer.seekTo(val);
        audioPlayer.resume();
        timer.start();
//    if (!source.getValueIsAdjusting()) {
//        
//                 
//               int val = (int) source.getValue();
//jLabelTime.setText(Integer.toString(val));
//                    int sval = (int) (player.getLength() * val / 100);
//                    System.out.print("Slider sval = " + sval);
//                    player.seekTo(val);
//                    player.resume();
//                    timer.start();
//        }
//        else{
//            timer.stop();
//            player.pause();
//        }
        
    }//GEN-LAST:event_jProgressSliderMouseDragged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
               int row, i, temp;
        String selectedTitle;
        File selectedFile;
        Track t;

      

        row = songTable.getSelectedRow();
        int realColumnIndex = songTable.convertColumnIndexToModel(3);

        selectedTitle = (String) songTable.getValueAt(row, realColumnIndex);
        i = organizer.getTrack(selectedTitle);
        t = organizer.getTrack(i);

        audioPlayer.startPlaying(t.getFilename());
      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jGainSliderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGainSliderMouseDragged
        // TODO add your handling code here:
           JSlider source = (JSlider)evt.getSource();
           
           float val;
        val =  source.getValue();
           
           audioPlayer.setGain(val);
           jLabelVolVal.setText(Integer.toString(Math.round(audioPlayer.getGain())));
        
    }//GEN-LAST:event_jGainSliderMouseDragged

    private void jPanSliderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanSliderMouseDragged
        // TODO add your handling code here:
          JSlider source = (JSlider)evt.getSource();
           
           float val;
           val =  source.getValue();
           
           audioPlayer.setPan(val);
        
    }//GEN-LAST:event_jPanSliderMouseDragged

    private void initTable() {
        int n = organizer.getNumberOfTracks();
        Track t;

        TableColumn trackColumn = songTable.getColumnModel().getColumn(2);
        trackColumn.setPreferredWidth(5);


        for (int i = 1; i < n; i++) {
            t = organizer.getTrack(i);
           
            myDefaultTableModel.addRow(new Object[]{t.getAlbum(),
                t.getArtist(),
                t.getTitle(),
                t.getTrackNum(),
                t.getLength(),
                t.getGenre()}
                );

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
                if (i == 0) {
                    lastAlbum = t.getAlbum();
                    songTable.setValueAt(t.getAlbum(), i, 0);
                }
                if (!lastAlbum.equals(t.getAlbum())) {
                    songTable.setValueAt(t.getAlbum(), i, 0);
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

        if (m != songTable.getRowCount()) {
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

            songTable.setValueAt(t.getAlbum(), i, 0);
            songTable.setValueAt(t.getArtist(), i, 1);
            songTable.setValueAt(t.getTrackNum(), i, 2);
            songTable.setValueAt(t.getTitle(), i, 3);
            songTable.setValueAt(t.getLength(), i, 4);
            songTable.setValueAt(t.getGenre(),i,5);


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

        audioPosition = (int) (audioPlayer.getFrame());

   //     jProgressBar1.setValue(audioPosition);
        jProgressSlider.setValue(audioPosition);
   jLabelTime.setText(Integer.toString(audioPosition));
  //      int i = jProgressBar1.getValue();
        

        // else reset( );  
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            //    int progress = (Integer) evt.getNewValue();
            int progress = 100 * (int) audioPlayer.getFrame() / audioPlayer.getLength();
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
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(myJavaSoundProjectUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(myJavaSoundProjectUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
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
    private javax.swing.JButton jButton1;
    private javax.swing.JMenuItem jCreateDBItem;
    private javax.swing.JMenuItem jDeleteMenu;
    private javax.swing.JMenuItem jExitMenu;
    private javax.swing.JSlider jGainSlider;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelVolMax;
    private javax.swing.JLabel jLabelVolMin;
    private javax.swing.JLabel jLabelVolVal;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JButton jNextButton;
    private javax.swing.JLabel jNowPlayingLabel;
    private javax.swing.JLabel jNowPlayingLabel1;
    private javax.swing.JMenuItem jOpenMenu;
    private javax.swing.JMenuItem jOpenNew;
    private javax.swing.JSlider jPanSlider;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JButton jPauseButton;
    private javax.swing.JButton jPlayButton;
    private javax.swing.JButton jPrevButton;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JSlider jProgressSlider;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jStopButton;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable songTable;
    // End of variables declaration//GEN-END:variables

    public class MyDefaultTableModel extends DefaultTableModel {

        public MyDefaultTableModel(Object[] colNames, int r) {
            super(colNames, r);
        }
        Class[] types = new Class[]{
            java.lang.String.class, java.lang.String.class, java.lang.String.class,
            java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean[]{
            false, false, false, false, false, false
        };

        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
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
