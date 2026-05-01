package net.neoforged.fml.common;
import java.lang.annotation.*;
import net.neoforged.api.distmarker.Dist;
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface EventBusSubscriber {
    String modid() default "";
    Bus bus() default Bus.GAME;
    Dist[] value() default {};
    enum Bus { MOD, GAME }
}
