package vn.edu.greenwich.mobile_cw1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import vn.edu.greenwich.mobile_cw1.models.Expense;
import vn.edu.greenwich.mobile_cw1.models.Trip;

public class ResimaDAO {
    protected ResimaDbHelper resimaDbHelper;
    protected SQLiteDatabase dbWrite, dbRead;

    public ResimaDAO(Context context) {
        resimaDbHelper = new ResimaDbHelper(context);

        dbRead = resimaDbHelper.getReadableDatabase();
        dbWrite = resimaDbHelper.getWritableDatabase();
    }

    public void close() {
        dbRead.close();
        dbWrite.close();
    }

    public void reset() {
        resimaDbHelper.onUpgrade(dbWrite, 0, 0);
    }

    // Trip

    public long insertTrip(Trip trip) {
        ContentValues values = getTripValues(trip);

        return dbWrite.insert(TripEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Trip> getTripList(Trip trip, String orderByColumn, boolean isDesc) {
        String orderBy = getOrderByString(orderByColumn, isDesc);

        String selection = null;
        String[] selectionArgs = null;

        if (trip != null) {
            selection = "";
            ArrayList<String> conditionList = new ArrayList<String>();

            if (trip.getName() != null && !trip.getName().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_NAME + " LIKE ?";
                conditionList.add("%" + trip.getName() + "%");
            }

            if (trip.getStartDate() != null && !trip.getStartDate().trim().isEmpty()) {
                selection += " AND " + TripEntry.COL_START_DATE + " = ?";
                conditionList.add(trip.getStartDate());
            }

            if (trip.getDeparture() != null && !trip.getDeparture().trim().isEmpty()) {
                selection += " AND" + TripEntry.COL_DEPARTURE + " LIKE ?";
                conditionList.add("%" + trip.getDeparture() + "%");
            }

            if (trip.getDestination() != null && !trip.getDestination().trim().isEmpty()) {
                selection += " AND" + TripEntry.COL_DESTINATION + " LIKE ?";
                conditionList.add("%" + trip.getDestination() + "%");
            }

            if (trip.getRiskAssessment() != null && !trip.getRiskAssessment().trim().isEmpty()) {
                selection += " AND" + TripEntry.COL_RISK_ASSESSMENT + " LIKE ?";
                conditionList.add("%" + trip.getRiskAssessment() + "%");
            }

            if (trip.getDesc() != null && !trip.getDesc().trim().isEmpty()) {
                selection += " AND" + TripEntry.COL_DESC + " LIKE ?";
                conditionList.add("%" + trip.getDesc() + "%");
            }

            if (trip.getParticipants() != null && !trip.getParticipants().trim().isEmpty()) {
                selection += " AND" + TripEntry.COL_PARTICIPANTS + " LIKE ?";
                conditionList.add("%" + trip.getParticipants() + "%");
            }

//            if (resident.getOwner() != -1) {
//                selection += " AND " + ResidentEntry.COL_OWNER + " = ?";
//                conditionList.add(String.valueOf(resident.getOwner()));
//            }

            if (!selection.trim().isEmpty()) {
                selection = selection.substring(5);
            }

            selectionArgs = conditionList.toArray(new String[conditionList.size()]);
        }

        return getTripFromDB(null, selection, selectionArgs, null, null, orderBy);
    }

    public Trip getTripById(long id) {
        String selection = TripEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return getTripFromDB(null, selection, selectionArgs, null, null, null).get(0);
    }

    public long updateTrip(Trip resident) {
        ContentValues values = getTripValues(resident);

        String selection = TripEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(resident.getId())};

        return dbWrite.update(TripEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public long deleteTrip(long id) {
        String selection = TripEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return dbWrite.delete(TripEntry.TABLE_NAME, selection, selectionArgs);
    }

    protected String getOrderByString(String orderByColumn, boolean isDesc) {
        if (orderByColumn == null || orderByColumn.trim().isEmpty())
            return null;

        if (isDesc)
            return orderByColumn.trim() + " DESC";

        return orderByColumn.trim();
    }

    protected ContentValues getTripValues(Trip resident) {
        ContentValues values = new ContentValues();

        values.put(TripEntry.COL_NAME, resident.getName());
        values.put(TripEntry.COL_START_DATE, resident.getStartDate());
        values.put(TripEntry.COL_DEPARTURE, resident.getDeparture());
        values.put(TripEntry.COL_DESTINATION, resident.getDestination());
        values.put(TripEntry.COL_RISK_ASSESSMENT, resident.getRiskAssessment());
        values.put(TripEntry.COL_DESC, resident.getDesc());
        values.put(TripEntry.COL_PARTICIPANTS, resident.getParticipants());

        return values;
    }

    protected ArrayList<Trip> getTripFromDB(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Trip> list = new ArrayList<>();

        Cursor cursor = dbRead.query(TripEntry.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        while (cursor.moveToNext()) {
            Trip residentItem = new Trip();

            residentItem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(TripEntry.COL_ID)));
            residentItem.setName(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_NAME)));
//            residentItem.setOwner(cursor.getInt(cursor.getColumnIndexOrThrow(ResidentEntry.COL_OWNER)));
            residentItem.setStartDate(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_START_DATE)));
            residentItem.setDeparture(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_DEPARTURE)));
            residentItem.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_DESTINATION)));
            residentItem.setRiskAssessment(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_RISK_ASSESSMENT)));
            residentItem.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_DESC)));
            residentItem.setParticipants(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_PARTICIPANTS)));

            list.add(residentItem);
        }

        cursor.close();

        return list;
    }

    // Expense.

    public long insertExpense(Expense expense) {
        ContentValues values = getExpenseValues(expense);

        return dbWrite.insert(ExpenseEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Expense> getExpenseList(Expense expense, String orderByColumn, boolean isDesc) {
        String orderBy = getOrderByString(orderByColumn, isDesc);

        String selection = null;
        String[] selectionArgs = null;

        if (expense != null) {
            selection = "";
            ArrayList<String> conditionList = new ArrayList<String>();

            if (expense.getAmount() != null && !expense.getAmount().trim().isEmpty()) {
                selection += " AND " + ExpenseEntry.COL_AMOUNT + " LIKE ?";
                conditionList.add("%" + expense.getAmount() + "%");
            }

            if (expense.getContent() != null && !expense.getContent().trim().isEmpty()) {
                selection += " AND " + ExpenseEntry.COL_TYPE_AMOUNT + " LIKE ?";
                conditionList.add("%" + expense.getContent() + "%");
            }

            if (expense.getDate() != null && !expense.getDate().trim().isEmpty()) {
                selection += " AND " + ExpenseEntry.COL_DATE + " = ?";
                conditionList.add(expense.getDate());
            }

            if (expense.getTime() != null && !expense.getTime().trim().isEmpty()) {
                selection += " AND " + ExpenseEntry.COL_TIME + " = ?";
                conditionList.add(expense.getTime());
            }

            if (expense.getType() != null && !expense.getType().trim().isEmpty()) {
                selection += " AND " + ExpenseEntry.COL_TYPE + " = ?";
                conditionList.add(expense.getType());
            }

            if (expense.getTripId() != -1) {
                selection += " AND " + ExpenseEntry.COL_TRIP_ID + " = ?";
                conditionList.add(String.valueOf(expense.getTripId()));
            }

            if (!selection.trim().isEmpty()) {
                selection = selection.substring(5);
            }

            selectionArgs = conditionList.toArray(new String[conditionList.size()]);
        }

        return getRequestFromDB(null, selection, selectionArgs, null, null, orderBy);
    }

    public Expense getExpenseById(long id) {
        String selection = ExpenseEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return getRequestFromDB(null, selection, selectionArgs, null, null, null).get(0);
    }

    public long updateExpense(Expense request) {
        ContentValues values = getExpenseValues(request);

        String selection = ExpenseEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(request.getId())};

        return dbWrite.update(ExpenseEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public long deleteExpense(long id) {
        String selection = ExpenseEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return dbWrite.delete(ExpenseEntry.TABLE_NAME, selection, selectionArgs);
    }

    protected ContentValues getExpenseValues(Expense request) {
        ContentValues values = new ContentValues();

        values.put(ExpenseEntry.COL_AMOUNT, request.getAmount());
        values.put(ExpenseEntry.COL_TYPE_AMOUNT, request.getContent());
        values.put(ExpenseEntry.COL_DATE, request.getDate());
        values.put(ExpenseEntry.COL_TIME, request.getTime());
        values.put(ExpenseEntry.COL_TYPE, request.getType());
        values.put(ExpenseEntry.COL_TRIP_ID, request.getTripId());

        return values;
    }

    protected ArrayList<Expense> getRequestFromDB(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        ArrayList<Expense> list = new ArrayList<>();

        Cursor cursor = dbRead.query(ExpenseEntry.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        while (cursor.moveToNext()) {
            Expense requestItem = new Expense();

            requestItem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_ID)));
            requestItem.setAmount(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_AMOUNT)));
            requestItem.setContent(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_TYPE_AMOUNT)));
            requestItem.setDate(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_DATE)));
            requestItem.setTime(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_TIME)));
            requestItem.setType(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_TYPE)));
            requestItem.setTripId(cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_TRIP_ID)));

            list.add(requestItem);
        }

        cursor.close();

        return list;
    }
}