/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.exception;

/**
 * Exception class that is called when ingredients quantity is negative
 * @author Wiktoria Krajcer
 * version 2.0
 */
public class WrongAmountException extends Exception {
/**
 * Constructor with argument
 * @param message message to be printed
 */
    public WrongAmountException(String message) {
        super(message);
    }
}
