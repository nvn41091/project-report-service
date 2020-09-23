package com.nvn41091.rest.errors;

public class LoginAlreadyUsedException extends com.nvn41091.rest.errors.BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Login name already used!", "userManagement", "userexists");
    }
}
