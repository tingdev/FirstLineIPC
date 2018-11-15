package kevin.com.ipc;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver bc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bc = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("kevin.com.action.ADD_FRUIT")) {
                    Toast.makeText(MainActivity.this, "I received the boradcast!!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        /*
        IntentFilter inf = new IntentFilter("kevin.com.action.ADD_FRUIT");
        // <uses-permission android:name="com.kevin.permission.recv_broadcast"/> declared in Manifest is enough
        // arg broadcastPermission is not required to be set.
        registerReceiver(new MyReceiver(), inf,null,null);
        */

        Button btnQuery = findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://kevin.com.firstline.provider/fruit");
                Cursor c = getContentResolver().query(uri, null, null, null, null);
                if (c != null) {
                    while(c.moveToNext()) {
                        String name = c.getString(c.getColumnIndex("name"));
                        String detail = c.getString(c.getColumnIndex("detail"));
                        double price = c.getDouble(c.getColumnIndex("price"));

                        Log.i("SS", name + "\n" + detail + "\n" + price + "\n----------------");
                    }
                    c.close();
                }

                uri = Uri.parse("content://kevin.com.firstline.provider/fruit/apple");
                c = getContentResolver().query(uri, null, null, null, null);
                if (c != null) {
                    while(c.moveToNext()) {
                        String name = c.getString(c.getColumnIndex("name"));
                        String detail = c.getString(c.getColumnIndex("detail"));
                        double price = c.getDouble(c.getColumnIndex("price"));

                        Log.i("SS", "specified name: " + name + "\n" + detail + "\n" + price + "\n----------------");
                    }
                    c.close();
                }

                //NOTE: actually this will never hit if /fruit/* is matched!
                uri = Uri.parse("content://kevin.com.firstline.provider/fruit/1");
                c = getContentResolver().query(uri, null, null, null, null);
                if (c != null) {
                    while(c.moveToNext()) {
                        String name = c.getString(c.getColumnIndex("name"));
                        String detail = c.getString(c.getColumnIndex("detail"));
                        double price = c.getDouble(c.getColumnIndex("price"));

                        Log.i("SS", "specified id: " + name + "\n" + detail + "\n" + price + "\n----------------");
                    }
                    c.close();
                }
            }
        });

        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://kevin.com.firstline.provider/fruit");

                ContentValues cv = new ContentValues();
                cv.put("price", 12.34);
                Log.i("SS", "update returns " + getContentResolver().update(uri, cv, "name=?", new String[]{"apple"}));
            }
        });
        Log.i("Broadcast", "my task id " + getTaskId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bc);
    }
}
