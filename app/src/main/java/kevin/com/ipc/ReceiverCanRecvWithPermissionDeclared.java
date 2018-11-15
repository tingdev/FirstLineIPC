package kevin.com.ipc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReceiverCanRecvWithPermissionDeclared extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("kevin.com.action.ADD_FRUIT")) {
            Toast.makeText(context, "I received the boradcast in ReceiverCanRecvWithPermissionDeclared", Toast.LENGTH_SHORT).show();
        }
    }
}
