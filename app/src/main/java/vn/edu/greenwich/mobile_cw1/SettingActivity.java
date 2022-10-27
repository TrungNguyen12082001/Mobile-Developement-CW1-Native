package vn.edu.greenwich.mobile_cw1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Date;
import vn.edu.greenwich.mobile_cw1.database.BackupEntry;
import vn.edu.greenwich.mobile_cw1.database.ResimaDAO;
import vn.edu.greenwich.mobile_cw1.models.Backup;
import vn.edu.greenwich.mobile_cw1.models.Expense;
import vn.edu.greenwich.mobile_cw1.models.Trip;

public class SettingActivity extends AppCompatActivity {
    protected ResimaDAO _db;
    protected Button settingBackup, settingResetDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle(R.string.label_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _db = new ResimaDAO(this);

        settingBackup = findViewById(R.id.settingBackup);
        settingResetDB = findViewById(R.id.settingResetDB);

        settingBackup.setOnClickListener(v -> backup());
        settingResetDB.setOnClickListener(v -> resetDatabase());
    }

    protected void backup() {
        ArrayList<Trip> trips = _db.getTripList(null, null, false);
        ArrayList<Expense> expenses = _db.getExpenseList(null, null, false);

        if (null != trips && 0 < trips.size() && null != expenses && 0 < expenses.size()) {
            String deviceName = Build.MANUFACTURER
                    + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                    + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();

            Backup backup = new Backup(new Date(), deviceName, trips, expenses);

            FirebaseFirestore.getInstance().collection(BackupEntry.TABLE_NAME)
                    .add(backup)
                    .addOnSuccessListener(document -> {
                        Toast.makeText(this, R.string.notification_backup_success, Toast.LENGTH_SHORT).show();
                        Log.d(getResources().getString(R.string.label_backup_firestore), document.getId());
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, R.string.notification_backup_fail, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
        } else {
            Toast.makeText(this, R.string.error_empty_list, Toast.LENGTH_SHORT).show();
        }
    }

    protected void resetDatabase() {
        _db.reset();

        Toast.makeText(this, R.string.label_reset_db, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}