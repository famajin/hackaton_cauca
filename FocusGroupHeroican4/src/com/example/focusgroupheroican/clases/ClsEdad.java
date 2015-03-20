package com.example.focusgroupheroican.clases;

public class ClsEdad {

	public int edad_id;
	public String edad_description;
	
	public ClsEdad(int edad_id, String edad_description) {
		this.edad_id = edad_id;
		this.edad_description = edad_description;
	}
	
	public String getSQL() {
		return "INSERT INTO tbl_edad VALUES("+edad_id+", '"+edad_description+"');";
	}
	
}
