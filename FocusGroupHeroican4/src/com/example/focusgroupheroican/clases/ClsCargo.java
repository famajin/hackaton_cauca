package com.example.focusgroupheroican.clases;

public class ClsCargo {

	public int carg_id;
	public String carg_description;
	
	public ClsCargo(int carg_id, String carg_description) {
		this.carg_id = carg_id;
		this.carg_description = carg_description;
	}
	
	public String getSQL() {
		return "INSERT INTO tbl_cargo VALUES("+carg_id+", '"+carg_description+"');";
	}
	
}
