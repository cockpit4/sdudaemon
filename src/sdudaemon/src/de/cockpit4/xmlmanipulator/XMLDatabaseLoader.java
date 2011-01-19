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
package de.cockpit4.xmlmanipulator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;

/**
 * Database Loader loads data of an XMLDataTable into a PostGRES Database;
 *
 * @author cockpit4 Gmbh, Kevin Krüger (kkruege@cockpit4.de)
 * @version 1.0
 *
 *  Copyright (c) 2010 cockpit4 GmbH
 *  sdudeamon is released under the MIT license
 */
public class XMLDatabaseLoader {

    private String host;
    private String port;
    private String user;
    private String password;
    private String databaseRepository;
    private String database;
    private XMLDatabaseManager dbman;
    private Connection dbConn = null;

    /**Constructor creating a new database loader
     * @param host Database host
     * @param port Database port
     * @param user privileged user to perform database transactions
     * @param password password of the user
     * @param data data to load
     */
    public XMLDatabaseLoader(String host, String port, String user, String password, String database, String databaseRepository) throws IOException, JDOMException {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
        this.databaseRepository = databaseRepository;
        Logger.getLogger("SystemLogger").log(Level.INFO, "Inizializing Database loader...");
        dbman = new XMLDatabaseManager(databaseRepository);

        Logger.getLogger("SystemLogger").log(Level.INFO, "XML DatabaseManager created!");
    }

    /**connects to the database
     */
    private boolean connect() {
        try {
            Logger.getLogger("SystemLogger").log(Level.INFO, "Aquiring database driver...");
            Class.forName("org.postgresql.Driver");
            dbConn = null;
            Logger.getLogger("SystemLogger").log(Level.INFO, "Connecting ...");
            dbConn = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);
            Logger.getLogger("SystemLogger").log(Level.INFO, "Connection established!");
        } catch (SQLException ex) {
            Logger.getLogger("SystemLogger").log(Level.SEVERE, "Database connection failed : {0}", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger("SystemLogger").log(Level.SEVERE, "Database connection failed : {0}", ex);
        }

        return (dbConn != null);
    }

    /**Closes the database
     */
    private boolean close() throws SQLException {
        if (!dbConn.isClosed()) {
            dbConn.close();
        }
        return dbConn.isClosed();
    }

    /**Loads all data determined in the XMLDatabaseTable into the specified Database
     */
    public void load() throws JDOMException, SQLException {
        if (connect()) {
            for (String name : dbman.listTables()) {
                XMLDatabaseTable data = dbman.aquireTable(name);
                XMLDataRow[] rows = data.getData();           //data of the XML file
                XMLDataColumn[] cols = data.getXMLDataColumns(); // columns of the data table this also contains the data types of each column

                Statement sql; //Statement
                ResultSet res; //result of each SQL Statement


                sql = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                res = sql.executeQuery("SELECT COUNT(*) FROM pg_tables WHERE tablename=\'" + data.getTableName() + "\';");
                res.next();
                int result = res.getInt(1);
                System.out.println("res : " + result);
                res.close();
                if (result == 0) { //table does not exist
                    String tableCreate = "CREATE TABLE " + data.getDatabaseName() + "." + data.getTableName() + " (\nid BIGINT,\nmd5 VARCHAR(32),\n";
                    for (XMLDataColumn col : cols) {
                        tableCreate += col.name + " " + col.type + ",\n";
                    }
                    tableCreate += " PRIMARY KEY(id));";
                    System.out.println("Query : " + tableCreate);
                    sql.execute(tableCreate);
                }

                for(XMLDataRow t : rows){
                    String selectRow = "SELECT COUNT(*) FROM "+data.getDatabaseName()+"."+data.getTableName()+" WHERE md5=\'"+t.checksum+"\';";
                    System.out.println("Select Data Set : "+selectRow);
                    res = sql.executeQuery(selectRow);
                    res.next();
                    result = res.getInt(1);
                    res.close();
                    if(result == 0){ // dataset not avaliable create it
                        String columns = "id, md5, ";
                        String values  = t.id+", \'"+t.checksum+"\', ";

                        for(int i = 0; i< t.columnsNames.length; i++){
                            columns += t.columnsNames[i].name+(i<(t.columnsNames.length -1) ? ", " : "");
                            values  += "\'"+t.values[i].replaceAll("\'", "")+"\'"+(i<(t.columnsNames.length -1) ? ", " : "");
                        }
                        String query = "INSERT INTO "+data.getDatabaseName()+"."+data.getTableName()+" ("+columns+") VALUES ("+values+");";

                        System.out.println("INSERT Query : "+query);
                        sql.execute(query);
                    }
                    else{ // dataset present for now do nothing here ... later do updating
                        
                    }
                }
            }
        }
        close();
        /*
        Statement sql; //Statement
        ResultSet res; //result of each SQL Statement

        if(connect()){
        //System.out.println("Connected!");
        sql = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        //check if the table exists
        res = sql.executeQuery("SELECT COUNT(*) FROM pg_tables WHERE tablename=\'"+data.getTableName()+"\';");
        res.last();

        if(res.getRow()<1){ //no table so create it.
        //System.out.println("Table does not exist!");

        String query = "CREATE TABLE "+data.getDatabaseName()+"."+data.getTableName()+" (id int,";
        //System.out.println("Creating Table..."+query);
        for(XMLDataColumn col : cols){
        //System.out.println(" QUERY: "+query);

        query += col.name+" "+col.type+", ";
        }
        query += "PRIMARY KEY(id));";

        //System.out.println(" QUERY : "+query);
        sql.execute(query);
        }
        else{ //Table drop it
        //System.out.println("Table exists dropping it ...");
        //TODO: (Blocker) make ID a serial stop dropping tables and check for existing values
        sql.execute("DROP TABLE "+data.getDatabaseName()+"."+data.getTableName()+";");

        String query = "CREATE TABLE "+data.getDatabaseName()+"."+data.getTableName()+" (id int,";
        for(XMLDataColumn col : cols){
        query += col.name+" "+col.type+", ";
        }
        query += "PRIMARY KEY(id));";

        //System.out.println(" QUERY : "+query);

        sql.execute(query);
        }

        for(XMLDataRow dat : data.getData()){ //insert all data
        String columns = "id, ";
        String values  = dat.id+", ";

        for(int i = 0; i< dat.columnsNames.length; i++){
        columns += dat.columnsNames[i].name+(i<(dat.columnsNames.length -1) ? ", " : "");
        values  += "\'"+dat.values[i].replaceAll("\'", "")+"\'"+(i<(dat.columnsNames.length -1) ? ", " : "");
        }

        String query = "INSERT INTO "+data.getDatabaseName()+"."+data.getTableName()+" ("+columns+") VALUES ("+values+");";
        //System.out.println("QUERY : "+query);
        sql.execute(query);
        }

        close();
        }*/
    }

    public static void main(String[] argV) throws JDOMException, IOException, SQLException {
        XMLDatabaseLoader xl = new XMLDatabaseLoader("localhost", "5432", "kneo", "praktikum", "pmw", "/home/kneo/Documents/cockpit4/Projects/Praktikum/XML-Tables");


        xl.connect();
        xl.load();
    }
}
