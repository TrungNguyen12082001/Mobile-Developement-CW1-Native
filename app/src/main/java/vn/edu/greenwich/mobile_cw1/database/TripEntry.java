package vn.edu.greenwich.mobile_cw1.database;

public class TripEntry {
    public static final String TABLE_NAME = "trip";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_START_DATE = "start_date";
    public static final String COL_DEPARTURE = "departure";
    public static final String COL_DESTINATION = "destination";
    public static final String COL_RISK_ASSESSMENT = "risk_assessment";
    public static final String COL_DESC = "description";
    public static final String COL_PARTICIPANTS = "participants";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY," +
                    COL_NAME + " TEXT NOT NULL," +
                    COL_START_DATE + " TEXT NOT NULL," +
                    COL_DEPARTURE + " TEXT NOT NULL," +
                    COL_DESTINATION + " TEXT NOT NULL," +
                    COL_RISK_ASSESSMENT + " TEXT NOT NULL," +
                    COL_DESC + " TEXT NOT NULL," +
                    COL_PARTICIPANTS + " TEXT NOT NULL)";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}