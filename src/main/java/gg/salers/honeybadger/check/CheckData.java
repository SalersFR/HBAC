package gg.salers.honeybadger.check;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CheckData {

    public String name();
    public boolean experimental();
}
