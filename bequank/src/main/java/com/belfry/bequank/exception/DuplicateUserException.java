package com.belfry.bequank.exception;

import com.belfry.bequank.util.Message;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE, reason = "email already used!")
public class DuplicateUserException extends RuntimeException {
}
