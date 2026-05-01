package net.neoforged.fml.common;
import java.lang.annotation.*;
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Mod { String value(); }
