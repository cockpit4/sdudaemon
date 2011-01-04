/*
Copyright (c) 2010 cockpit4, Kevin Krüger

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

package de.cockpit4.sdudaemon.installer;

import de.cockpit4.sdudaemon.installer.eventhandling.ChangeEvent;
import de.cockpit4.sdudaemon.installer.eventhandling.ChangeListener;
import de.cockpit4.sdudaemon.installer.eventhandling.FinishedListener;
import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**Simple SDUDaemon installer, downloads all required libraries and builds the initial application nest.
 * @author Kevin Krüger
 */
public class InstallerWindow extends javax.swing.JFrame implements ChangeListener, FinishedListener{
    boolean[] steps = new boolean[5];
    public final String baseURL = "http://waslos.in/~kneo/sdudaemon/packages.properties";
    public String path;
    JCheckBox[] stepbox = new JCheckBox[5];

    /** Creates new form InstallerWindow */
    public InstallerWindow() {
        initComponents();
        for(int i = 0; i<5;i++ ){
            steps[i]= false;
        }
        lblMessage.setVisible(false);
        
        path = System.getProperty("user.dir");
        txtInstallPath.setText(System.getProperty("user.dir"));
        checkPath();

        stepbox[0] = chkStep0;
        stepbox[1] = chkStep1;
        stepbox[2] = chkStep2;
        stepbox[3] = chkStep3;
        stepbox[4] = chkStep4;
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
        jLabel1 = new javax.swing.JLabel();
        txtInstallPath = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        btnInstall = new javax.swing.JButton();
        chkStep0 = new javax.swing.JCheckBox();
        chkStep2 = new javax.swing.JCheckBox();
        chkStep3 = new javax.swing.JCheckBox();
        chkStep4 = new javax.swing.JCheckBox();
        pbStatus = new javax.swing.JProgressBar();
        btnFinish = new javax.swing.JButton();
        chkStep1 = new javax.swing.JCheckBox();
        lblMessage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Install/Update sdudaemon");
        setName(""); // NOI18N
        setResizable(false);

        jPanel1.setDoubleBuffered(true);

        jLabel1.setText("Install sdudaemon including libraries into:");
        jLabel1.setDoubleBuffered(true);

        txtInstallPath.setDoubleBuffered(true);
        txtInstallPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtInstallPathFocusLost(evt);
            }
        });

        btnBrowse.setText("...");
        btnBrowse.setDoubleBuffered(true);
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        btnInstall.setText("Install/Update");
        btnInstall.setDoubleBuffered(true);
        btnInstall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInstallActionPerformed(evt);
            }
        });

        chkStep0.setText("Creating directory structure");
        chkStep0.setToolTipText("Create default directory structure. ");
        chkStep0.setDoubleBuffered(true);
        chkStep0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkStep0ActionPerformed(evt);
            }
        });

        chkStep2.setText("downloading required library");
        chkStep2.setToolTipText("retrieving latest sdudaemon package");
        chkStep2.setDoubleBuffered(true);
        chkStep2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkStep0ActionPerformed(evt);
            }
        });

        chkStep3.setText("downloading sdudaemon binary");
        chkStep3.setToolTipText("Downloading necessary library archives");
        chkStep3.setDoubleBuffered(true);
        chkStep3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkStep0ActionPerformed(evt);
            }
        });

        chkStep4.setText("Finishing installation");
        chkStep4.setToolTipText("Computer Creates initial application configuration");
        chkStep4.setDoubleBuffered(true);
        chkStep4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkStep0ActionPerformed(evt);
            }
        });

        pbStatus.setDoubleBuffered(true);
        pbStatus.setStringPainted(true);

        btnFinish.setText("Finish");
        btnFinish.setDoubleBuffered(true);
        btnFinish.setEnabled(false);
        btnFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinishActionPerformed(evt);
            }
        });

        chkStep1.setText("Getting latest package information");
        chkStep1.setDoubleBuffered(true);
        chkStep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkStep0ActionPerformed(evt);
            }
        });

        lblMessage.setBackground(new java.awt.Color(202, 112, 110));
        lblMessage.setText(" ");
        lblMessage.setDoubleBuffered(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(txtInstallPath, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnBrowse))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnInstall, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(chkStep0)
                        .addComponent(chkStep1)
                        .addComponent(chkStep2)
                        .addComponent(chkStep3)
                        .addComponent(chkStep4)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(pbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnFinish)))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtInstallPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBrowse))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnInstall)
                                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(56, 56, 56))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(chkStep0)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(chkStep1)
                            .addGap(5, 5, 5)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(chkStep2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(chkStep3)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(chkStep4)
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFinish))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //function checks if the install paths are valid. Displays a notify message saying whats the story.
    private void checkPath(){
        File folder = new File(path);

        if(installPathWritable(folder)){
            if(folder.exists()){
                setNotificationMessage("Path writable, installer overwrites!", 1);
            }
            else{
                setNotificationMessage("Path writable, click install to start", 0);
            }
            btnInstall.setEnabled(true);
        }
        else{
            setNotificationMessage("Path not writable choose another install path!", 2);
            btnInstall.setEnabled(false);
        }
    }
    //print a notification message
    private void setNotificationMessage(String text,int type){
        Color color = Color.GRAY;
        switch(type){
            case 0: // normal text message
                color = Color.GRAY;
                break;

            case 1: // warning message
                color = Color.ORANGE;
                break;

            case 2: // error message
                color = Color.RED;
                break;
        }
        lblMessage.setBackground(color);
        lblMessage.setOpaque(true);
        lblMessage.setText(text);
        lblMessage.setVisible(true);
    }

    private boolean installPathWritable(File path){ //check a path if a parent directory is writable
        File parent = path;
        do{
            if(parent.exists()){
                if(parent.canWrite()){
                    return true;
                }
            }
            parent = parent.getParentFile();
        }while(parent!=null); //stop if the root appears
        return false;
    }

    
    private void chkStep0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkStep0ActionPerformed
        int i = 0;
        for(JCheckBox c : stepbox){
            c.setSelected(steps[i]);
            i++;
        }
    }//GEN-LAST:event_chkStep0ActionPerformed

    private void btnInstallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInstallActionPerformed
        btnBrowse.setEnabled(false);
        btnInstall.setEnabled(false);
        txtInstallPath.setEnabled(false);
        lblMessage.setVisible(false);
        for( int i = 0 ; i < 5; i++){
            try {
                stepbox[i].setOpaque(true);
                stepbox[i].setBackground(Color.ORANGE);
                step(i);
                steps[i]=true;
                stepbox[i].setOpaque(false);
                stepbox[i].setSelected(steps[i]);
                update(this.getGraphics());
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fatal Error!", JOptionPane.ERROR_MESSAGE);
                btnBrowse.setEnabled(true);
                btnInstall.setEnabled(true);
                txtInstallPath.setEnabled(true);
                stepbox[i].setOpaque(true);
                stepbox[i].setBackground(Color.RED);
                return;
            }
        }

        btnFinish.setEnabled(true);
    }//GEN-LAST:event_btnInstallActionPerformed
    
    private void step(int step) throws IOException{
        switch(step){
            case 0:
                step0();
            break;

            case 1:
                step1();
            break;

            case 2:
                step2();
            break;

            case 3:
                step3();
            break;

            case 4:
                step4();
            break;
        }
    }


    private void btnFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishActionPerformed
        this.setVisible(false);
        System.exit(0);
    }//GEN-LAST:event_btnFinishActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        JFileChooser folder = new JFileChooser();
        folder.setDialogTitle("Select Installation Directory");
        folder.setDialogType(JFileChooser.DIRECTORIES_ONLY);
        folder.setMultiSelectionEnabled(false);
        folder.setCurrentDirectory(new File(path));

        if(folder.showDialog(this, "install") == JFileChooser.APPROVE_OPTION){
            try {
                txtInstallPath.setText(folder.getSelectedFile().getCanonicalPath());
                path = folder.getSelectedFile().getCanonicalPath();
            } catch (IOException ex) {
                Logger.getLogger(InstallerWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            checkPath();
        }
        else{
            checkPath();
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void txtInstallPathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInstallPathFocusLost
        path = txtInstallPath.getText();

        checkPath();
    }//GEN-LAST:event_txtInstallPathFocusLost

    private void step0() throws IOException{ //create directory tree
        File install = new File(path);
        if(install.exists()){
            if(install.canWrite()){
                new File(path+File.separator+"bin").mkdirs(); // make the binary and script directory
                new File(path+File.separator+"lib").mkdirs(); // make the library directory
                new File(path+File.separator+"tmp").mkdirs(); // make a possible temporary directory used for installation
                new File(path+File.separator+"etc").mkdirs(); // make the configuration directory
                new File(path+File.separator+"log").mkdirs(); // make the logger directory
                new File(path+File.separator+"projects").mkdirs(); // make the projects directory
            }
            else{
                throw new IOException("can not write directory");
            }
        }
        else{
            install.mkdirs();
            new File(path+File.separator+"bin").mkdirs(); // make the binary and script directory
            new File(path+File.separator+"lib").mkdirs(); // make the library directory
            new File(path+File.separator+"tmp").mkdirs(); // make a possible temporary directory used for installation
            new File(path+File.separator+"etc").mkdirs(); // make the configuration directory
            new File(path+File.separator+"log").mkdirs(); // make the logger directory
            new File(path+File.separator+"projects").mkdirs(); // make the projects directory
        }
    }

    private void step1() throws IOException{ //download package information
        try {
            Download definition = new Download(new URL(baseURL), path+File.separator+"tmp"+File.separator+"package.properties");
            pbStatus.setMaximum(definition.getSize());
            pbStatus.setString("Package Definition");
            definition.addChangeListener(this);
            definition.start();
            System.getProperties().load(new FileReader(definition.getTarget()));
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(InstallerWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void step2() throws IOException{ //download libraries
        try {
            Download jaxen = new Download(new URL(System.getProperty("installer.jaxen.url")), path + File.separator + "lib"+File.separator+"jaxen.jar");
            pbStatus.setMaximum(jaxen.getSize());
            pbStatus.setString("jaxen Framework");
            jaxen.addChangeListener(this);
            jaxen.start();

            Download jdom = new Download(new URL(System.getProperty("installer.jdom.url")), path + File.separator + "lib"+File.separator+"jdom.jar");
            pbStatus.setMaximum(jdom.getSize());
            pbStatus.setString("jdom Framework");
            jdom.addChangeListener(this);
            jdom.start();

            Download log4j = new Download(new URL(System.getProperty("installer.log4j.url")), path + File.separator + "lib"+File.separator+"log4j.jar");
            pbStatus.setMaximum(log4j.getSize());
            pbStatus.setString("Logger log4j");
            log4j.addChangeListener(this);
            log4j.start();

            Download webharvest = new Download(new URL(System.getProperty("installer.webharvest.url")), path + File.separator + "lib"+File.separator+"webharvest.jar");
            pbStatus.setMaximum(webharvest.getSize());
            pbStatus.setString("webharvest Framework");
            webharvest.addChangeListener(this);
            webharvest.start();


            Download bsh = new Download(new URL(System.getProperty("installer.bsh.url")), path +File.separator + "lib"+ File.separator + "bsh.jar");
            pbStatus.setMaximum(bsh.getSize());
            pbStatus.setString("beanshell Interpreter");
            bsh.addChangeListener(this);
            bsh.start();


            Download postgres = new Download(new URL(System.getProperty("installer.postgres.url")), path + File.separator +"lib"+File.separator + "postgres.jar");
            pbStatus.setMaximum(postgres.getSize());
            pbStatus.setString("postgresql Driver");
            postgres.addChangeListener(this);
            postgres.start();

        } catch (MalformedURLException ex) {
            Logger.getLogger(InstallerWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void step3() throws IOException{ //download sdudaemon binary
            Download sdudaemon = new Download(new URL(System.getProperty("installer.binary.url")), path + File.separator + "lib"+File.separator+"sdudaemon.jar");
            pbStatus.setMaximum(sdudaemon.getSize());
            pbStatus.setString("sdudaemon Binary");
            sdudaemon.addChangeListener(this);
            sdudaemon.start();
    }

    public void step4() throws IOException{ //generate start scripts
        File confBat = new File(path+File.separator+"bin"+File.separator+"configurator.bat");
        File confSH = new File(path+File.separator+"bin"+File.separator+"configurator.sh");

        String cmd;
        cmd = "java -cp \""; //TODO:(MAJOR) make Windows Compatible
        File libdir = new File(path+File.separator+"lib");
        for(File f : libdir.listFiles()){
            cmd += f.getCanonicalPath()+File.pathSeparator;
        }
        cmd +=".\" de.cockpit4.sdudaemon.configuration.gui.sduConfigurator\n";

        String bat = "@echo off\n";
        bat += cmd;

        String sh;
        sh = "#/bin/sh\n";
        sh += cmd;

        System.out.println("sh : "+sh);
        System.out.println("bat : "+bat);
        try {
            FileWriter fw1 = new FileWriter(confBat);

            fw1.write(bat);
            fw1.close();

            FileWriter fw2 = new FileWriter(confSH);
            fw2.write(sh);
            fw2.close();
        } catch (IOException ex) {
            Logger.getLogger(InstallerWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        pbStatus.setString("Installation done!");
    }


    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) throws Exception {
        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InstallerWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnFinish;
    private javax.swing.JButton btnInstall;
    private javax.swing.JCheckBox chkStep0;
    private javax.swing.JCheckBox chkStep1;
    private javax.swing.JCheckBox chkStep2;
    private javax.swing.JCheckBox chkStep3;
    private javax.swing.JCheckBox chkStep4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JProgressBar pbStatus;
    private javax.swing.JTextField txtInstallPath;
    // End of variables declaration//GEN-END:variables

    public void onChange(ChangeEvent evt) {
        pbStatus.setValue(evt.getValue());
        update(this.getGraphics());
    }

    public void onFinish() {
        
    }
}
