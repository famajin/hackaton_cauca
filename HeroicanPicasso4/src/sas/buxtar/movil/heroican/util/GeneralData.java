package sas.buxtar.movil.heroican.util;

public class GeneralData {
	
	public static final boolean SEND_ANALYTICS=true;
	
	private static final String SERVER="http://heroican2.azurewebsites.net/HeroiCan/";
	
	public static final String SERVERCONTROLLER=SERVER+"index.php/site/";
	public static final String SERVER_IMAGES=SERVER+"images/";
	
	public static final int ALL=444; 
	 
	//CATEGORIAS REGISTRO
	public static final int USUARIO=0;
	public static final int MASCOTA=1;
	public static final int EVENTO=2;
	public static final int TIP=3;
	public static final int TESTIMONIO=4;
	public static final int SEARCH=5;
	public static final int DEVICE=-7;
	
	//FOR THE DIALOG MAP
	public static final int MAP=23;
	
	//CATEGORIA USUARIO
	public static final int FUNDACION=6;
	public static final int PERSONA=7;
	
	//CATEGORIAS DE MASCOTAS Y FILTRO
	public static final int MAS_ADOPTAR=8, MAS_PERDIDA=9, MAS_ENCONTRADA=10, MAS_AGREGADA=11;
	
	//ESTADOS DE USUARIO
	public static final int CONNECTED=1, DESCONNECTED=0;
	
	//ESTADO ESCUADRON
	public static final int ACTIVE=1, INACTIVE=0;
	
	//PREFERENCES NAMES
	public static final String PREFERENCES_NAME="HeroicanPreferences";
	public static final String PREF_EMAIL="email", PREF_USUID="usu_id", PREF_ESCUADRON="usu_Escuadron", PREF_STATE="usu_state", PREF_DEVICEID="dev_id", PREF_DEVICETAG="dev_tag", PREF_USUCIUDAD="usu_ciudad", PREF_TUTORIAL="tuto", PREF_TUTORIAL_CAMARA="tuto_cam", PREF_LOGIN_TIPE="login_tipe";
	
	//VARIABLES DE MANEJO
	public static String USU_ID="";
	public static String USU_CIUDAD="";
	public static String DEV_ID="";
	public static String IS_SEARCH_ACTIVE="";
	public static int TIME_WAITMAP=1500;
	public static int LOGIN_TIPE=-1;
	public static double COL_LATITUD=2.449299, COL_LONGITUD=-76.607265;
	public static String WORK="true";
	public static String NOT_EXIST="exists";
	public static int SHOWMAX_TUTORIAL=2;
	
	//MARGIN DIALOGS
	public static int marginX=0, marginY=0;
	public static int marginX_tablet=15, marginY_tablet=11;
	
	public static final String ESCUADRON_PRODUCT_ID = "com.djap.billing.papas";
	public static final String MERCHANT_KEY = "11075195007404342149"; // PUT YOUR MERCHANT KEY HERE;
	
	public static final int LOGIN_APP=0, LOGIN_FACE=1, LOGIN_PLUS=2;
}