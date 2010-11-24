/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConfigurationWindow.java
 *
 * Created on Oct 19, 2010, 8:44:26 AM
 */

package de.cockpit4.sdudaemon.configuration.gui.view;

import de.cockpit4.sdudaemon.configuration.gui.controller.ConfigurationController;
import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.ModelChangeListener;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author kneo
 */
public class ConfigurationWindow extends javax.swing.JFrame implements ModelChangeListener{
	private ConfigurationController configControll;
	private JFileChooser folderSelector;
    /** Creates new form ConfigurationWindow */
    public ConfigurationWindow(ConfigurationController m) {
        initComponents();

	if(m!= null){
		configControll = m;
		configControll.getModel().addListener(this);
		folderSelector = new JFileChooser();
		folderSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		projectTable.setModel(m.getModel().getProjects());
		
		projectTable.setColumnSelectionAllowed(false);
		projectTable.setRowSelectionAllowed(true);
		projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
        	txtLoggerPath.setEnabled(chkLogger.isSelected()); // de-/activate textfield depending on check box state
		btnBrowseLogger.setEnabled(chkLogger.isSelected());
		onChange();
	}
	else{
		throw new NullPointerException("(ConfigurationController) null pointer assigned");
	}
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        chkLogger = new javax.swing.JCheckBox();
        txtLoggerPath = new javax.swing.JTextField();
        btnBrowseLogger = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtStatePath = new javax.swing.JTextField();
        btnBrowseState = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtTempPath = new javax.swing.JTextField();
        btnBrowseTemp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        projectTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtLibPath = new javax.swing.JTextField();
        btnBrowseLib = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        chkJaxen = new javax.swing.JCheckBox();
        chkBSH = new javax.swing.JCheckBox();
        chkJdom = new javax.swing.JCheckBox();
        chkPost = new javax.swing.JCheckBox();
        chkWeb = new javax.swing.JCheckBox();
        lblAllClear = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnGenerateBatch = new javax.swing.JButton();
        btnGenerateShell = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("sdudaemon Configurator");
        setMinimumSize(new java.awt.Dimension(800, 500));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Daemon Settings"));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("General"));

        chkLogger.setText("Logger Path:");
        chkLogger.setToolTipText("decide if you want to disable or enable logging feature");
        chkLogger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLoggerActionPerformed(evt);
            }
        });
        chkLogger.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusGainedEvent(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusLostEvent(evt);
            }
        });

        txtLoggerPath.setToolTipText("determine where to store log files");
        txtLoggerPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusGainedEvent(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusLostEvent(evt);
            }
        });

        btnBrowseLogger.setText("...");
        btnBrowseLogger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseLoggerActionPerformed(evt);
            }
        });

        jLabel3.setText("State Files:");

        txtStatePath.setToolTipText("determine where to store project state files");
        txtStatePath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusGainedEvent(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusLostEvent(evt);
            }
        });

        btnBrowseState.setText("...");
        btnBrowseState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseStateActionPerformed(evt);
            }
        });

        jLabel4.setText("Temporary Directory:");

        txtTempPath.setToolTipText("determine where to store temporary data");
        txtTempPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusGainedEvent(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusLostEvent(evt);
            }
        });

        btnBrowseTemp.setText("...");
        btnBrowseTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseTempActionPerformed(evt);
            }
        });

        jLabel5.setText("<html><b>Note</b>: Paths are OS dependent. A Unix configuration will not run on a e.g. Windows OS unless you adapt all required paths to fit the founding environment!");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(chkLogger))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtStatePath, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                                    .addComponent(txtLoggerPath, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnBrowseLogger)
                                    .addComponent(btnBrowseState)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtTempPath, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBrowseTemp))))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkLogger)
                    .addComponent(txtLoggerPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseLogger))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtStatePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseState))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTempPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseTemp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Projects"));

        jButton1.setText("Add");

        jButton2.setText("Edit");

        jButton3.setText("Remove");

        projectTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(projectTable);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Library Path"));

        jLabel1.setLabelFor(btnBrowseLib);
        jLabel1.setText("Path");

        txtLibPath.setToolTipText("enter the path where the libraries on the right are located");
        txtLibPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusGainedEvent(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusLostEvent(evt);
            }
        });

        btnBrowseLib.setText("...");
        btnBrowseLib.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseLibActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLibPath, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBrowseLib)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtLibPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseLib))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Libraries"));

        jLabel2.setText("found Libraries:");

        chkJaxen.setText("jaxen");
        chkJaxen.setEnabled(false);

        chkBSH.setText("bsh");
        chkBSH.setEnabled(false);

        chkJdom.setText("jdom");
        chkJdom.setEnabled(false);

        chkPost.setText("postgres");
        chkPost.setEnabled(false);

        chkWeb.setText("webharvest");
        chkWeb.setEnabled(false);

        lblAllClear.setBackground(java.awt.Color.red);
        lblAllClear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAllClear.setText("not OK!");
        lblAllClear.setToolTipText("Indicates if your library path contains all jar libraries sdudeamon needs to operate properly.");
        lblAllClear.setOpaque(true);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAllClear, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(chkJaxen)
                    .addComponent(chkBSH)
                    .addComponent(chkJdom)
                    .addComponent(chkPost)
                    .addComponent(chkWeb))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkJaxen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBSH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkJdom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkPost)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkWeb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAllClear)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Start-Helper"));
        jPanel5.setToolTipText("Tip: use this buttons to generate a startscript running your scraper.\nYou can use it to schedule periodical tasks.");

        btnGenerateBatch.setText("Generate Batchfile");
        btnGenerateBatch.setToolTipText("generates a batchfile to perform scraping events easily");

        btnGenerateShell.setText("Generate Shellscript");
        btnGenerateShell.setToolTipText("generate a bash shell script for easy execution");
        btnGenerateShell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateShellActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGenerateShell, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
            .addComponent(btnGenerateBatch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(btnGenerateBatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGenerateShell)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(634, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBrowseLibActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseLibActionPerformed
		if(folderSelector.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			configControll.getModel().setLibraryPath(folderSelector.getSelectedFile().getPath());
		}
    }//GEN-LAST:event_btnBrowseLibActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
	    setVisible(false);
	    dispose();
	    System.exit(0);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void chkLoggerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLoggerActionPerformed
	txtLoggerPath.setEnabled(chkLogger.isSelected()); // de-/activate textfield depeding on check box state
	btnBrowseLogger.setEnabled(chkLogger.isSelected());
	configControll.getModel().setLoggerEnabled(chkLogger.isSelected());
    }//GEN-LAST:event_chkLoggerActionPerformed

    private void focusGainedEvent(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusGainedEvent
	updateValues();
    }//GEN-LAST:event_focusGainedEvent

    private void focusLostEvent(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusLostEvent
	updateValues();
    }//GEN-LAST:event_focusLostEvent

    private void btnBrowseLoggerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseLoggerActionPerformed
		if(folderSelector.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			configControll.getModel().setLoggerPath(folderSelector.getSelectedFile().getPath());
		}
    }//GEN-LAST:event_btnBrowseLoggerActionPerformed

    private void btnBrowseStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseStateActionPerformed
		if(folderSelector.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			configControll.getModel().setStatePath(folderSelector.getSelectedFile().getPath());
		}
    }//GEN-LAST:event_btnBrowseStateActionPerformed

    private void btnBrowseTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseTempActionPerformed
		if(folderSelector.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			configControll.getModel().setTempPath(folderSelector.getSelectedFile().getPath());
		}
    }//GEN-LAST:event_btnBrowseTempActionPerformed

    private void btnGenerateShellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateShellActionPerformed
	String config = JOptionPane.showInputDialog("Creating a new File Please Enter Desired Filename"); //create empty configuration if aborted
	
	File[] files = new File(configControll.getModel().getLibraryPath()).listFiles();
	
	
	int i = 0;
	String[] names = new String[files.length];
	
	for(File f : files){
			try {
				names[i] = f.getCanonicalPath();
			}
			catch (IOException ex) {
				Logger.getLogger(ConfigurationWindow.class.getName()).log(Level.SEVERE, null, ex);
			}
		i++;
	}
	
	
	String result = null;
	if(config!= null){
		result =	  "#!/bin/sh\n"
				+ "cd "+configControll.getModel().getLibraryPath()
				+ "\njava -classpath \"";

		for(String name : names){
		    result += name+":";
		}

		result += ".\" " + de.cockpit4.sdudaemon.Daemon.class.getCanonicalName();
		result += " "+configControll.getModel().getConfigPath();
	}
	try {
	    FileWriter fw = new FileWriter(config);
	    fw.write(result);
	    fw.close();
	} catch (IOException ex) {
	    Logger.getLogger(ConfigurationWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_btnGenerateShellActionPerformed

    private void updateValues(){
	    configControll.getModel().setLoggerEnabled(chkLogger.isSelected());
	    configControll.getModel().setLoggerPath(txtLoggerPath.getText());
	    configControll.getModel().setLibraryPath(txtLibPath.getText());
	    configControll.getModel().setStatePath(txtStatePath.getText());
	    configControll.getModel().setTempPath(txtTempPath.getText());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowseLib;
    private javax.swing.JButton btnBrowseLogger;
    private javax.swing.JButton btnBrowseState;
    private javax.swing.JButton btnBrowseTemp;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnGenerateBatch;
    private javax.swing.JButton btnGenerateShell;
    private javax.swing.JCheckBox chkBSH;
    private javax.swing.JCheckBox chkJaxen;
    private javax.swing.JCheckBox chkJdom;
    private javax.swing.JCheckBox chkLogger;
    private javax.swing.JCheckBox chkPost;
    private javax.swing.JCheckBox chkWeb;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAllClear;
    private javax.swing.JTable projectTable;
    private javax.swing.JTextField txtLibPath;
    private javax.swing.JTextField txtLoggerPath;
    private javax.swing.JTextField txtStatePath;
    private javax.swing.JTextField txtTempPath;
    // End of variables declaration//GEN-END:variables

	public void onChange() {
		//model Changed act here
		txtLibPath.setText(configControll.getModel().getLibraryPath());
		txtLibPath.setText(configControll.getModel().getLibraryPath());
		txtLoggerPath.setText(configControll.getModel().getLoggerPath());
		txtStatePath.setText(configControll.getModel().getStatePath());
		txtTempPath.setText(configControll.getModel().getTempPath());
		chkLogger.setSelected(configControll.getModel().isLoggerEnabled());
		
		chkJaxen.setSelected((configControll.getModel().getFoundLibraries()&1) == 1);
		chkJdom.setSelected((configControll.getModel().getFoundLibraries()&4) == 4);
		chkPost.setSelected((configControll.getModel().getFoundLibraries()&8) == 8);
		chkWeb.setSelected((configControll.getModel().getFoundLibraries()&16) == 16);
		chkBSH.setSelected((configControll.getModel().getFoundLibraries()&2) == 2);

		if((configControll.getModel().getFoundLibraries()&31) == 31){
			lblAllClear.setBackground(Color.GREEN);
			lblAllClear.setText("All OK!");
		}
		else{
			lblAllClear.setBackground(Color.RED);
			lblAllClear.setText("none OK!");
		}
	}

}

