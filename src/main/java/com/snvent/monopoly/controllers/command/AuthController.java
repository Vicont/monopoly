package com.snvent.monopoly.controllers.command;

import com.snvent.core.http.annotation.CommandAction;
import com.snvent.core.http.annotation.controller.CommandController;
import com.snvent.core.http.dispatcher.exception.InvocationCommandException;
import com.snvent.monopoly.GameErrorCode;
import com.snvent.monopoly.commands.incoming.Login;
import com.snvent.monopoly.commands.outgoing.SessionCreated;
import com.snvent.monopoly.controllers.MonopolyHttpCommandController;
import com.snvent.monopoly.models.User;
import com.snvent.monopoly.models.UserSession;

/**
 * Auth controller
 *
 * @author vicont
 */
@CommandController
public class AuthController extends MonopolyHttpCommandController {

    @CommandAction("Login")
    public SessionCreated login() {
        Login command = (Login) this.getRequestCommand();

        if (command.login == null || command.login.isEmpty() || command.password == null || command.password.isEmpty()) {
            throw new InvocationCommandException("Invalid parameters", GameErrorCode.INVALID_LOGIN_PARAMETERS);
        }

        UserSession session;
        User user = this.frontendService.getUserByLoginAndPassword(command.login, command.password);

        if (user != null) {
            session = this.frontendService.createUserSession(user);
            this.frontendService.getUserStorage().add(user);
            return new SessionCreated(session.getId());
        } else {
            throw new InvocationCommandException("Login failed", GameErrorCode.LOGIN_FAILED);
        }
    }

}
