/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd.Server;

/**
 *
 * @author Rui_vieira
 */
public class NoAuthorizationException extends Exception {
    NoAuthorizationException(String message) {
		super(message);
	}
}