package cucumber_blueprint.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Used to mark a field on a Page Object to indicate an alternative mechanism for locating the
 * element or a list of elements. Used in conjunction with {@link AnnotationProcessor}
 * this allows users to quickly and easily create PageObjects.</p>
 *
 * <p>Example for locating WebElement using annotation:</p>
 *
 * <pre class="code">
 * &#64;FindByDataTestId("foobar")
 * WebElement foobar;
 * </pre>
 *
 * <p>and its same for list of elements:</p>
 *
 * <pre class="code">
 * &#64;FindByDataTestId("product")
 * List&lt;WebElement&gt; products;
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FindByDataTestId {

  String value();
}
