package com.pack.mine.trade;

import com.google.api.client.auth.oauth2.draft10.AccessProtectedResource;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.googleapis.extensions.android2.auth.GoogleAccountManager;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.tasks.Tasks;

import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	Button login;
	int DIALOG_ACCOUNTS = 0;
	AccountManager accountManager;
	String AUTH_TOKEN_TYPE = "Manage your tasks";
	String account_name, user_name;
	SampleDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		login = (Button) findViewById(R.id.button1);
		
		db = new SampleDatabase(this);
		accountManager = AccountManager.get(Login.this);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Account[] accounts = accountManager.getAccounts();
				if(accounts.length!=0){
					showDialog(0);
				}				
			}
		});
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Select a Google account");
			final Account[] accounts = accountManager.getAccountsByType("com.google");
			final int size = accounts.length;
			String[] names = new String[size];
			for (int i = 0; i < size; i++) {
				names[i] = accounts[i].name;
			}
			builder.setItems(names, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					gotAccount(accounts[which]);
				}
			});
			return builder.create();
		}
		return null;
	}
	private void gotAccount(Account account) {
		Log.d("Account Grabed","is"+account.name+account.toString());
		account_name = account.name;
		accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, this, new AccountManagerCallback<Bundle>() {
			@Override
			public void run(AccountManagerFuture<Bundle> future) {
				try {
					String token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
					useTasksAPI(token);
				} catch (OperationCanceledException e) {
				} catch (Exception e) {
				}
			}			
		}, null);

	}

	private void useTasksAPI(String token) {
		HttpTransport transport = AndroidHttp.newCompatibleTransport();
		AccessProtectedResource accessProtectedResource = new GoogleAccessProtectedResource(token);
		Tasks service = new Tasks(transport, accessProtectedResource, new JacksonFactory());
		service.apiKey = "AIzaSyAVcZ4cz-AN1YFNnNct1ZStiJ-qTmMHX8A";
		service.setApplicationName("Google-Login/1.0");
		startActivity(new Intent(Login.this, ListMainComponent.class));
		this.finish();

	}
	
}
