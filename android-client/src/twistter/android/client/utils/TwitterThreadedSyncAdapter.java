package twistter.android.client.utils;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

public class TwitterThreadedSyncAdapter extends AbstractThreadedSyncAdapter {

	public TwitterThreadedSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onPerformSync(Account arg0, Bundle arg1, String arg2,
			ContentProviderClient arg3, SyncResult arg4) {
		// TODO Auto-generated method stub
		
	}

}
