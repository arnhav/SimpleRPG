package com.tyrriel.simplerpg.util;

import java.util.ArrayList;

public class MySQLShortcuts {

    enum Compare {
        EQUAL, NOTEQUAL, GREATERTHAN, LESSTHAN
    }

    ///////////////////////////
    // SQL Opening selectors //
    ///////////////////////////

    public static String selectFrom(String table) {
        return "SELECT * FROM " + table + " ";
    }

    public static String update(String table) {
        return "UPDATE "+table + " ";
    }

    public static String insertInto(String table) {

        return "INSERT INTO " + table + " ";
    }

    public static String replaceInto(String table) {

        return "REPLACE INTO " + table + " ";
    }

    public static String deleteFrom(String table) {

        return "DELETE FROM " + table + " ";
    }




    ////////////////////////
    // SQL Fields setters //
    ////////////////////////

    public static String withFields(ArrayList<String> fields) {
        String data = "";
        for(String field: fields) {
            data = data + "," + field;
        }
        data = data.substring(data.indexOf(',')+1, data.length());
        return "(" + data + ")" + " ";
    }

    public static String withValues(ArrayList<Object> fields) {
        String data = "";
        for(Object field: fields) {
            data = data + "," + "'" + field + "'";
        }
        data = data.substring(data.indexOf(',')+1, data.length());
        return "VALUES (" + data + ")" + " ";
    }

    public static String withMultiValues(ArrayList<ArrayList<Object>> valuesList) {
        String data = "";
        for(ArrayList<Object> values: valuesList) {
            String data2 = "";
            for(Object field: values) {
                data2 = data2 + "," + "'" + field + "'";
            }
            data2 = "(" + data2.substring(data2.indexOf(',')+1, data2.length()) + ")";
            data = data + "," + data2;
        }

        data = data.substring(data.indexOf(',')+1, data.length());
        return "VALUES " + data  + " ";
    }

    public static String set(String field, Object var) {
        return "SET " + field + " = " + "'" + var + "'"  + " ";
    }

    //////////////////////
    // SQL Conditionals //
    //////////////////////

    public static String where(String field, Object var) {
        return "WHERE " + field + " = " + "'" + var + "'"  + " ";
    }

    public static String where(String field, Object var, Compare compare) {
        return "WHERE " + field + " " + getCompareString(compare) + " " + "'" + var + "'"  + " ";
    }

    public static String whereNULL(String field) {
        return "WHERE " + field + " IS NULL" + " ";
    }

    public static String whereNOTNULL(String field) {
        return "WHERE " + field + " IS NOT NULL" + " ";
    }

    public static String and(String field, Object var) {
        if(field instanceof String) {
            return "AND " + field + " = " + "'" + var + "'"  + " ";
        } else {
            return "AND " + field + " = " + var + " ";
        }
    }

    public static String or(String field, Object var) {
        if(field instanceof String) {
            return "OR " + field + " = " + "'" + var + "'"  + " ";
        } else {
            return "OR " + field + " = " + var + " ";
        }
    }

    ///////////////////////////
    // SQL EXTENSION PHRASES //
    ///////////////////////////

    public static String onDuplicateKeyUpdate(String field, Object var) {
        if(field instanceof String) {
            return "ON DUPLICATE KEY UPDATE " + field + " = " + "'" + var + "'"  + " ";
        } else {
            return "ON DUPLICATE KEY UPDATE " + field + " = " + var + " ";
        }

    }

    public static String onDuplicateKeysUpdate(String field) {
        if(field instanceof String) {
            return "ON DUPLICATE KEY UPDATE " + field + " = " + "VALUES" + "(" + /*"'" +*/ field /*+ "'"*/  + ") ";
        } else {
            return "ON DUPLICATE KEY UPDATE " + field + " = " + "VALUES" + "(" + field + ") ";
        }

    }


    /////////////////
    // SQL Closers //
    /////////////////

    public static String end() {
        return ";";
    }

    ///////////////////////
    // Private Functions //
    ///////////////////////

    private static String getCompareString(Compare compare) {
        switch(compare) {
            case EQUAL:
                return "=";
            case NOTEQUAL:
                return "!=";
            case GREATERTHAN:
                return ">";
            case LESSTHAN:
                return "<";
        }
        return "ERROR";
    }


}
