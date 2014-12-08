package de.axxg.validierungsfehler;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KontaktFragment extends Fragment {
	
	private EditText nameET;
	private EditText mailET;
	private EditText messageET;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// View holen
		View v = inflater.inflate(R.layout.kontakt_fragment, container, false);

		nameET = (EditText) v.findViewById(R.id.kontakt_name);
		mailET = (EditText) v.findViewById(R.id.kontakt_mail);
		messageET = (EditText) v.findViewById(R.id.kontakt_message);
		
		// Button
		Button sendBtn = (Button) v.findViewById(R.id.kontakt_btn);
		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMail();
			}
		});

		return v;
	}

	private void sendMail() {
		// validieren
		boolean isValid = true; 
		
		if(nameET.getText().length() < 3){
			nameET.setError(getActivity().getText(R.string.kontakt_err_name));
			isValid = false;
		}
		
		if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mailET.getText()).matches()){
			mailET.setError(getActivity().getText(R.string.kontakt_err_mail));
			isValid = false;
		}
		
		if(messageET.getText().length() < 1){
			messageET.setError(getActivity().getText(R.string.kontakt_err_message));
			isValid = false;
		}
		
		if(!isValid){
			Toast.makeText(getActivity(), getActivity().getText(R.string.kontakt_err_send), Toast.LENGTH_LONG).show();
			return;
		}
		
		// alles ist valid!!

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { "developer@axxg.de" });
		i.putExtra(Intent.EXTRA_SUBJECT, getActivity().getText(R.string.app_name) + " von " + nameET.getText() + "(" + mailET.getText() + ")");
		i.putExtra(Intent.EXTRA_TEXT, messageET.getText().toString());
		try {
			getActivity().startActivity(Intent.createChooser(i, "Mail-Apps:"));
			
			nameET.setText("");
			mailET.setText("");
			messageET.setText("");
			Toast.makeText(getActivity(), getActivity().getText(R.string.kontakt_info_send), Toast.LENGTH_LONG).show();
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(getActivity(), getActivity().getText(R.string.kontakt_err_send), Toast.LENGTH_LONG).show();
		}
	
		

	}

}