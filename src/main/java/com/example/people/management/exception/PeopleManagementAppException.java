package com.example.people.management.exception;

public class PeopleManagementAppException extends Exception{

    private static final long serialVersionUID = 751591411121229260L;

    /**
     * Instantiates a new PeopleManagementAppException with a message.
     *
     * @param message the message
     */
    public PeopleManagementAppException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new PeopleManagementAppException with a caused by exception.
     *
     * @param exp the exception
     */
    public PeopleManagementAppException(final Exception exp) {
        super(exp);
    }

    /**
     * Instantiates a new PeopleManagementAppException with a message and a caused by exception.
     *
     * @param message the message
     * @param exp the exception
     */
    public PeopleManagementAppException(final String message, final Exception exp) {
        super(message, exp);
    }
}
