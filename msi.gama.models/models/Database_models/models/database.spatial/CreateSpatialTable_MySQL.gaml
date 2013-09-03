/**
 *  CreateBuildingTableMySQL
 *  Author: thaitruongminh
 *  Description: 
 */

model CreateBuildingTableMySQL


global {
			map<string,string> PARAMS <- ['host'::'localhost','dbtype'::'MySQL','database'::'','port'::'8889','user'::'root','passwd'::'root'];
	init {
		create species: toto number: 1;
		ask toto {
			if (self testConnection[params::PARAMS]){
				
 			    do executeUpdate    params:PARAMS updateComm: "CREATE DATABASE spatial_DB_GAMA"; 
 			    write "spatial_BD_GAMA database was created ";
 			    
 			    remove "database" from: PARAMS;
				put "spatial_DB_GAMA" key:"database" in: PARAMS;
				
				do executeUpdate params: PARAMS 
								  updateComm : "CREATE TABLE bounds"+
								  "( "  +
				                    " geom GEOMETRY " + 
				                  ")";
				write "bounds table was created ";
				
				do executeUpdate params: PARAMS 
								  updateComm : "CREATE TABLE buildings "+
								  "( "  +
				                   	" name VARCHAR(255), " + 
				                    " type VARCHAR(255), " + 
				                    " geom GEOMETRY " + 
				                  ")";
                write "buildings table was created ";
 			}else {
 				write "Connection to MySQL can not be established ";
 			}	
		}
	}
}
entities {
	species toto skills: [ SQLSKILL ] {

	}
}      
experiment default_expr type: gui {

}