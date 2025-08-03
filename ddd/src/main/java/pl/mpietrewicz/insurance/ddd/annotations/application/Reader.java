
package pl.mpietrewicz.insurance.ddd.annotations.application;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Reader {

}