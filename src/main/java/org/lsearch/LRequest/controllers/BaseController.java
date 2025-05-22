package org.lsearch.LRequest.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.validation.Errors;
import java.util.Arrays;

public class BaseController {
    public void processErrors(Errors errors) throws BadRequestException {
        if (errors.hasErrors()) {
            var output = errors.getAllErrors().stream().map(item -> item.getDefaultMessage()).toArray();
            throw new BadRequestException("There are some errors: " + Arrays.toString(output));
        }
    }
}
