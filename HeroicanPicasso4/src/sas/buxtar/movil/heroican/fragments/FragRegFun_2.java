package sas.buxtar.movil.heroican.fragments;


import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.TerminosActivity;
import sas.buxtar.movil.heroican.clases.ClsFundacion;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class FragRegFun_2 extends Fragment implements OnClickListener{

	private EditText txtRepresentante, txtCcRepresentante, txtEmail, txtPassword, txtReplay, txtRazon, txtDescripcion;
	private ClsFundacion fun;
	private String repre, ccrepre, email, password, replay, razon, descripcion;
	private CheckBox checkTerminos;
	
	public FragRegFun_2() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_regfun2, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		txtRazon=(EditText)view.findViewById(R.id.regFun_txtRazon);
		txtDescripcion=(EditText)view.findViewById(R.id.regFun_txtdescripcion);
		txtRepresentante=(EditText)view.findViewById(R.id.regFun_txtrepresentante);
		txtEmail=(EditText)view.findViewById(R.id.regFun_txtemail);
		txtPassword=(EditText)view.findViewById(R.id.regFun_txtPassword);
		txtCcRepresentante=(EditText)view.findViewById(R.id.regFun_txtCcRepresentante);
		txtReplay=(EditText)view.findViewById(R.id.regFun_txtReplayPassword);
		checkTerminos=(CheckBox) view.findViewById(R.id.regFun_checkTerminos);
		txtRepresentante.requestFocus();
		((Button) view.findViewById(R.id.regFun_btnTerminos)).setOnClickListener(this);
	
		txtDescripcion.setText(fun.getFun_descripcion());
		txtRazon.setText(fun.getFun_razonsocial());
		txtCcRepresentante.setText(fun.getFun_cedularepresentante());
		txtRepresentante.setText(fun.getFun_representante());
		txtEmail.setText(fun.getFun_email());
		txtPassword.setText("");
		txtReplay.setText("");
		
		clearErrors();
	}
	
	private void clearErrors() {
		txtDescripcion.setError(null);
		txtRazon.setError(null);
		txtRepresentante.setError(null);
		txtCcRepresentante.setError(null);
		txtEmail.setError(null);
		txtPassword.setError(null);
		txtReplay.setError(null);
		checkTerminos.setError(null);
	}
	
	public void setErrorEmail(String x) {
		if(txtEmail==null){
			txtEmail=(EditText)getView().findViewById(R.id.regFun_txtemail);
		}
		txtEmail.setError(x);
	}
	
	public static FragRegFun_2 newInstace(ClsFundacion fun) {
		FragRegFun_2 frag=new FragRegFun_2();
		frag.setFundation(fun);
		frag.setRetainInstance(true);
		return frag;
	}
	
	public static void restoreInstace(FragRegFun_2 frag, ClsFundacion fun) {
		frag.setFundation(fun);
		frag.setRetainInstance(true);
	}
	
	public boolean validate() {
		clearErrors();
		
		if(fun!=null){
			descripcion=txtDescripcion.getText().toString().toLowerCase();
			razon=txtRazon.getText().toString().toLowerCase();
			repre=txtRepresentante.getText().toString().toLowerCase();
			ccrepre=txtCcRepresentante.getText().toString().toLowerCase();
			email=txtEmail.getText().toString().toLowerCase();
			password=txtPassword.getText().toString().toLowerCase();
			replay=txtReplay.getText().toString().toLowerCase();
			if(razon.length()>0){
				if(razon.length()<getResources().getInteger(R.integer.chars_descripcion)){
					fun.setFun_razonsocial(razon);
					if(descripcion.length()>0){
						if(descripcion.length()<getResources().getInteger(R.integer.chars_descripcion)){
							fun.setFun_descripcion(descripcion);
							if(repre.length()>0){
								fun.setFun_representante(repre);
								if(ccrepre.length()>0){
									if(ccrepre.length()<getResources().getInteger(R.integer.chars_ids)){
										fun.setFun_cedularepresentante(ccrepre);
										if(email.length()>0){
											if(email.contains("@")){
												fun.setFun_email(email);
												if(password.length()>0){
													if(password.length()<getResources().getInteger(R.integer.chars_60)){
														if(password.equals(replay)){
															fun.setFun_password(password);
							    							if(checkTerminos.isChecked()){
							    								return true;
							    							}else{
							    								checkTerminos.setError(getString(R.string.error_terminos));	
							    							}
														}else{
															txtReplay.setError(getResources().getString(R.string.campo_nocoincide));
														}	
													}else{
														txtPassword.setError(getResources().getString(R.string.error_chars));	
													}
												}else{
													txtPassword.setError(getResources().getString(R.string.campo_requerido));
												}
											}else{
												txtEmail.setError(getString(R.string.email_incorrecto));
											}
										}else{
											txtEmail.setError(getResources().getString(R.string.campo_requerido));
										}	
									}else{
										txtCcRepresentante.setError(getResources().getString(R.string.error_chars));	
									}
								}else{
									txtCcRepresentante.setError(getResources().getString(R.string.campo_requerido));
								}
							}else{
								txtRepresentante.setError(getResources().getString(R.string.campo_requerido));
							}	
						}else{
							txtDescripcion.setError(getResources().getString(R.string.error_chars));	
						}
					}else{
						txtDescripcion.setError(getResources().getString(R.string.campo_requerido));
					}	
				}else{
					txtRazon.setError(getResources().getString(R.string.error_chars));	
				}
			}else{
				txtRazon.setError(getResources().getString(R.string.campo_requerido));
			}
		}
		return false;
	}
	
	private void setFundation(ClsFundacion fundation) {
		this.fun=fundation;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		startActivity(new Intent(getActivity(), TerminosActivity.class));
	}
}
