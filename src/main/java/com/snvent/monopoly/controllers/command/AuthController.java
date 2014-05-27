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
import com.snvent.monopoly.services.frontend.UserStorage;

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
            this.frontendService.removeUserSession(user);

            UserStorage userStorage = this.frontendService.getUserStorage();
            if (userStorage.has(user.getId())) {
                userStorage.remove(user);
            }

            session = this.frontendService.createUserSession(user);
            userStorage.add(user);
            return new SessionCreated(session.getId());
        } else {
            throw new InvocationCommandException("Login failed", GameErrorCode.LOGIN_FAILED);
        }
    }

}
