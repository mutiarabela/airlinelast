package com.restapi.airlines.exception;

public class AirlineNotFoundException extends RuntimeException {
    public AirlineNotFoundException() { super("Airline ID not Found"); }
    public AirlineNotFoundException(String message) { super(message); }
}
