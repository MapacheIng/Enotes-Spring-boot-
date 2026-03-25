package com.mapache.Enotes_API_Service.util;

public class Constants {

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final String MOB_NUMBER_REGEX = "^[0-9]{1,10}$";

    public static final String ROLE_ADMIN = "hasRole('ADMIN')";
    public static final String ROLE_USER = "hasRole('USER')";
    public static final String ROLE_ADMIN_USER = "hasAnyRole('USER', 'ADMIN')";



}
