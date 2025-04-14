package org.lsearch.LRequest.interfaces;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface CurrentUser {
}
