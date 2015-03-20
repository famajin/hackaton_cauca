package sas.buxtar.movil.heroican;

import sas.buxtar.movil.heroican.util.GeneralData;

import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

public class ImageActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		try {
			String image=getIntent().getExtras().getString("url");
	        Picasso.with(this)
			.load(image)
			.into((ImageView)findViewById(R.id.image_img));	
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
