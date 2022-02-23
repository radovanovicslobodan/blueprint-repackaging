package cucumber_blueprint.common.helpers.ui.annotations;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Custom element locator, which will lazily locate an element or an element list on a page. This
 * class is designed for use with the {@link AnnotationProcessor}
 * and understands the annotations {@link FindByDataTestId}.
 */
public class MyElementLocator implements ElementLocator {

  private final SearchContext searchContext;
  private final By by;

  /**
   * Creates a new element locator.
   *
   * @param searchContext The context to use when finding the element
   * @param field         The field on the Page Object that will hold the located value
   */
  public MyElementLocator(SearchContext searchContext, Field field) {
    this.searchContext = searchContext;

    FindByDataTestId annotation = field.getAnnotation(FindByDataTestId.class);
    String dataTestId = annotation.value();
    String css = String.format("[data-testid='%s']", dataTestId);

    this.by = By.cssSelector(css);
  }

  /**
   * Find the element.
   */
  @Override
  public WebElement findElement() {
    return searchContext.findElement(by);
  }

  /**
   * Find the element list.
   */
  @Override
  public List<WebElement> findElements() {
    return searchContext.findElements(by);
  }
}
