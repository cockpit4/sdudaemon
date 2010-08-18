/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kkevin.xmlmanipulator;

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
 * @category Webscraper
 * @package org.kkevin.xmlmanipulator
 * @subpackage org.kkevin.xmlmanipulator
 * @author kneo
 * @copyright 2010 cockpit4 rights reserved.
 * @version SVN $Id$
 */
public class XMLDatabaseLoader {
	private String host;
	private String port;
	private String user;
	private String password;
	private XMLDatabaseTable data;

	private Connection dbConn = null;

	/**Constructor creating a new database loader
	 * @param host Database host
	 * @param port Database port
	 * @param user privileged user to perform database transactions
	 * @param password password of the user
	 * @param data data to load
	 */
	public XMLDatabaseLoader(String host,String port, String user, String password, XMLDatabaseTable data){
		this.host     = host;
		this.port     = port;
		this.user     = user;
		this.password = password;
		this.data     = data;
	}
	/**connects to the database
	 */
	private boolean connect(){
		try {
			Class.forName("org.postgresql.Driver");
			dbConn = null;
			dbConn = DriverManager.getConnection("jdbc:postgresql://"+host+":"+port+"/"+data.getDatabaseName(), user, password);
		}
		catch (SQLException ex) {
			Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
		}
		catch (ClassNotFoundException ex) {
			Logger.getLogger("SystemLogger").log(Level.SEVERE, null, ex);
		}

		return (dbConn != null);
	}
	/**Closes the database
	 */
	private boolean close() throws SQLException{
		if(!dbConn.isClosed()){
			dbConn.close();
		}
		return dbConn.isClosed();
	}
	/**Loads all data determined in the XMLDatabaseTable into the specified Database
	 */
	public void load() throws JDOMException, SQLException{
		XMLDataRow[]    rows = data.getData(); //data of the XML file
		XMLDataColumn[] cols = data.getXMLDataColumns();// columns of the data table this also contains the data types of each column
		
		Statement sql; //Statement
		ResultSet res; //result of each SQL Statement

		if(connect()){
			//System.out.println("Connected!");
			sql = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			//check if the table exists
			res = sql.executeQuery("SELECT * FROM pg_tables WHERE tablename=\'"+data.getTableName()+"\';");
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
		}
	}
}
