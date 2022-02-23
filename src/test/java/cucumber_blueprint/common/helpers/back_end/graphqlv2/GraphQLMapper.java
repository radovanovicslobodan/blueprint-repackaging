package cucumber_blueprint.common.helpers.back_end.graphqlv2;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Class for mapping model class to GraphQL request body and GraphQL response to model class
 */
@Slf4j
public class GraphQLMapper {

  public static RequestBuilder requestBuilder(RequestType requestType,
      String methodName) {
    return new RequestBuilder(requestType, methodName);
  }

  /**
   * Method will return string representation of GraphQL query request for provided model class.<br>
   * If model class contain recursion, method will not allow recursion.<br> For allowing recursion
   * see {@link GraphQLMapper#mapObjectToQuery(String, Class, int)}<br>
   *
   * @param methodName  Name of method on GraphQL
   * @param entityClass Class for mapping response fields
   * @return String representation of GraphQL request body
   */
  public static String mapObjectToQuery(String methodName, Class<?> entityClass) {
    return mapObjectToQuery(methodName, entityClass, 1);
  }

  /**
   * Method will return string representation of GraphQL query request for provided model class.
   * Recursive classes are allowed to provided level of depth.
   *
   * @param methodName     Name of method on GraphQL
   * @param entityClass    Class for mapping response fields
   * @param recursiveDepth Level of recursive depth
   * @return String representation of GraphQL request body
   */
  public static String mapObjectToQuery(String methodName, Class<?> entityClass,
      int recursiveDepth) {
    Objects.requireNonNull(methodName, "Method name can't be null");
    Objects.requireNonNull(entityClass, "EntityClass name can't be null");

    log.info(
        "Creating GraphQL query for getting " + entityClass.getSimpleName() + " from " + methodName
            + " with recursive depth " + recursiveDepth + "...");

    try {
      StringBuilder stringBuilder = new StringBuilder();

      stringBuilder.append("{ \"query\" : \"{ ");
      stringBuilder.append(methodName);
      Map<Class, Integer> recursiveClassMap = new HashMap<>();
      appendAllFields(stringBuilder, entityClass, recursiveClassMap, recursiveDepth);
      stringBuilder.append("}\" }");

      log.info("GraphQL query successfully created : " + stringBuilder.toString());
      return stringBuilder.toString();
    } catch (Exception e) {
      log.info("There is an error in creating query for class " + entityClass.getSimpleName());
      e.printStackTrace();

      return "";
    }
  }

  private static void appendAllFields(StringBuilder stringBuilder, Class<?> entityClass,
      Map<Class, Integer> recursiveClassMap, int recursiveDepth) {
    appendAllFields(stringBuilder, entityClass, recursiveClassMap, recursiveDepth, true);
  }

  private static void appendAllFields(StringBuilder stringBuilder, Class<?> entityClass,
      Map<Class, Integer> recursiveClassMap, int recursiveDepth, boolean closeObject) {
    stringBuilder.append("{");

    if (entityClass.isAnnotationPresent(JsonSubTypes.class)) {
      JsonSubTypes annotation = entityClass.getAnnotation(JsonSubTypes.class);
      JsonSubTypes.Type[] value = annotation.value();

      for (JsonSubTypes.Type type : value) {
        Class<?> extensionClass = type.value();
        stringBuilder.append(" ... on ");
        appendObjectField(extensionClass.getSimpleName(), stringBuilder, extensionClass,
            recursiveClassMap, recursiveDepth, false);
        appendClassFields(stringBuilder, entityClass, recursiveClassMap, recursiveDepth);
        stringBuilder.append("} ");
      }
    } else {
      appendClassFields(stringBuilder, entityClass, recursiveClassMap, recursiveDepth);
    }

    if (closeObject) {
      stringBuilder.append("} ");
    }
  }

  private static void appendClassFields(StringBuilder stringBuilder, Class<?> entityClass,
      Map<Class, Integer> recursiveClassMap, int recursiveDepth) {
    for (Field field : entityClass.getDeclaredFields()) {
      if (isSimpleType(field.getType())) {
        stringBuilder.append(field.getName());
        stringBuilder.append(" ");
      } else if (field.getType().equals(List.class)) {

        Class<?> fieldClass = getClassOfListElements(field);

        if (isSimpleType(fieldClass)) {
          stringBuilder.append(field.getName());
          stringBuilder.append(" ");
        } else {
          appendObjectField(field.getName(), stringBuilder, fieldClass, recursiveClassMap,
              recursiveDepth, true);
        }
      } else {
        appendObjectField(field.getName(), stringBuilder, field.getType(), recursiveClassMap,
            recursiveDepth, true);
      }
    }
  }

  private static Class<?> getClassOfListElements(Object list) {
    ParameterizedType stringListType = (ParameterizedType) list;
    Class<?> fieldClass = (Class<?>) stringListType.getActualTypeArguments()[0];
    return fieldClass;
  }

  private static Class<?> getClassOfListElements(Field field) {
    ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
    Class<?> fieldClass = (Class<?>) stringListType.getActualTypeArguments()[0];
    return fieldClass;
  }

  private static void appendObjectField(String fieldName, StringBuilder stringBuilder,
      Class<?> fieldClass, Map<Class, Integer> recursiveClassMap, int recursiveDepth,
      boolean closeObject) {
    Integer integer = recursiveClassMap.getOrDefault(fieldClass, 0);
    if (integer < recursiveDepth) {
      recursiveClassMap.put(fieldClass, ++integer);
      stringBuilder.append(fieldName);
      appendAllFields(stringBuilder, fieldClass, recursiveClassMap, recursiveDepth, closeObject);
      recursiveClassMap.put(fieldClass, --integer);
    }
  }

  private static boolean isSimpleType(Class<?> clazz) {
    return clazz.equals(String.class)
        || clazz.equals(Integer.class)
        || clazz.equals(Boolean.class)
        || clazz.equals(Double.class)
        || clazz.isEnum();
  }

  /**
   * Method will return string representation of GraphQL mutation request with provided parameter
   * and for provided model class. Parameter for mutation will be treated as Object<br> If model
   * class contain recursion, method will not allow recursion.<br> For treating parameter like list
   * of  parameter see {@link GraphQLMapper#mapObjectToMutation(String, Object, boolean, Class)}<br>
   * For allowing recursion see {@link GraphQLMapper#mapObjectToMutation(String, Object, Class,
   * int)}<br>
   *
   * @param methodName  Name of mutation method on GraphQL
   * @param parameter   Parameter for mutation
   * @param entityClass Class for mapping response fields
   * @return String representation of GraphQL mutation request body
   */
  public static String mapObjectToMutation(String methodName, Object parameter,
      Class<?> entityClass) {
    return mapObjectToMutation(methodName, parameter, true, entityClass, 1);
  }

  /**
   * Method will return string representation of GraphQL mutation request with provided parameter
   * and for provided model class. Parameter for mutation will be treated as Object<br> Recursive
   * classes are allowed to provided level of depth.<br> For treating parameter like List of
   * parameter see {@link GraphQLMapper#mapObjectToMutation(String, Object, boolean, Class,
   * int)}<br>
   *
   * @param methodName     Name of mutation method on GraphQL
   * @param parameter      Parameter for mutation
   * @param entityClass    Class for mapping response fields
   * @param recursiveDepth Level of recursive depth
   * @return String representation of GraphQL mutation request body
   */
  public static String mapObjectToMutation(String methodName, Object parameter,
      Class<?> entityClass, int recursiveDepth) {
    return mapObjectToMutation(methodName, parameter, true, entityClass, recursiveDepth);
  }

  /**
   * Method will return string representation of GraphQL mutation request with provided parameter
   * and for provided model class.<br> If model class contain recursion, method will not allow
   * recursion.<br> For treating parameter like list of  parameter see {@link
   * GraphQLMapper#mapObjectToMutation(String, Object, boolean, Class, int)}<br>
   *
   * @param methodName   Name of mutation method on GraphQL
   * @param parameter    Parameter for mutation
   * @param sendAsObject Send provided parameter as object
   * @param entityClass  Class for mapping response fields
   * @return String representation of GraphQL mutation request body
   */
  public static String mapObjectToMutation(String methodName, Object parameter,
      boolean sendAsObject, Class<?> entityClass) {
    return mapObjectToMutation(methodName, parameter, sendAsObject, entityClass, 1);
  }

  /**
   * Method will return string representation of GraphQL mutation request with provided parameter
   * and for provided model class.<br>
   *
   * @param methodName     Name of mutation method on GraphQL
   * @param parameter      Parameter for mutation
   * @param sendAsObject   Send provided parameter as object
   * @param entityClass    Class for mapping response fields
   * @param recursiveDepth Level of recursive depth
   * @return String representation of GraphQL mutation request body
   */
  public static String mapObjectToMutation(String methodName, Object parameter,
      boolean sendAsObject, Class<?> entityClass, int recursiveDepth) {
    Objects.requireNonNull(methodName, "Method name can't be null");

    try {
      StringBuilder stringBuilder = new StringBuilder();

      stringBuilder.append("{\"query\" : \"mutation { ");
      stringBuilder.append(methodName);
      stringBuilder.append("(");
      if (parameter != null) {
        appendParametersToMutation(stringBuilder, parameter, sendAsObject);
      }
      stringBuilder.append(")");

      if (entityClass != null) {
        Map<Class, Integer> recursiveClassMap = new HashMap<>();
        appendAllFields(stringBuilder, entityClass, recursiveClassMap, recursiveDepth);
      } else {
        stringBuilder.append("{ }");
      }

      stringBuilder.append("}\"}");

      if (parameter == null) {
        String result = stringBuilder.toString().replace("()", "");
        result = result.replace("{}", "");
        return result;
      }

      log.info("GraphQL mutation successfully created : " + stringBuilder.toString());
      return stringBuilder.toString();
    } catch (Exception e) {
      log.info(
          "There is an error in creating mutation query for class " + entityClass.getSimpleName());
      e.printStackTrace();

      return "";
    }
  }

  @SneakyThrows
  private static void appendParametersToMutation(StringBuilder stringBuilder, Object parameter,
      boolean sendAsObject) {

    Class<?> entityClass = parameter.getClass();

    if (parameter instanceof Map) {

      ((Map<?, ?>) parameter).forEach((k, l) -> {
        stringBuilder.append(k.toString()).append(":");
        addSimpleType(stringBuilder, l.getClass(), l);
      });
    } else {

      if (sendAsObject) {
        String paramName = parameter.getClass().getSimpleName();
        stringBuilder.append(paramName.toLowerCase().charAt(0)).append(paramName.substring(1));
        stringBuilder.append(":{");
      }

      for (Field field : entityClass.getDeclaredFields()) {

        String fieldName = field.getName();
        field.setAccessible(true);
        Object fieldValueObject = field.get(parameter);
        if (fieldValueObject == null) {
          continue;
        }
        String fieldValue = fieldValueObject.toString();

        if (isSimpleType(field.getType())) {
          stringBuilder.append(fieldName).append(":");
          addSimpleType(stringBuilder, field.getType(), fieldValue);
        } else if (field.getType() == List.class) {
          Class<?> elementsClass = getClassOfListElements(field);
          List<?> fieldValueList = (List<?>) fieldValueObject;

          stringBuilder.append(fieldName).append(":[");

          fieldValueList.forEach(listElement -> {
            if (isSimpleType(elementsClass)) {
              if (elementsClass == String.class) {
                stringBuilder.append("\"");
              }
              stringBuilder.append(listElement.toString());
              if (elementsClass == String.class) {
                stringBuilder.append("\"");
              }
            } else {
              appendParametersToMutation(stringBuilder, listElement, true);
            }
            if (fieldValueList.indexOf(listElement) + 1 < fieldValueList.size()) {
              stringBuilder.append(", ");
            }
          });

          stringBuilder.append("] ");
        } else {
          appendParametersToMutation(stringBuilder, field, true);
        }
      }

      if (sendAsObject) {
        stringBuilder.append("}");
      }
    }
  }

  private static void addSimpleType(StringBuilder stringBuilder, Class<?> clazz, Object value) {

    if (clazz == String.class) {
      stringBuilder.append("\\\"").append(value).append("\\\" ");
    } else {
      stringBuilder.append(value).append(" ");
    }
  }

  /**
   * Method for mapping data object from response to provided model
   *
   * @param response   Response from GraphQL
   * @param methodName Name of method on GraphQL
   * @param clazz<T>   Class for mapping response fields
   * @return Response model of provided class
   */
  public static <T> T parseDataObject(Response response, String methodName, Class<T> clazz) {
    log.info("Parse data response of method " + methodName + " to " + clazz.getSimpleName()
        + " class...");
    return parseObject(response.getBody().asString(), ResponseType.DATA, methodName.split("[(]")[0],
        clazz);
  }

  /**
   * Method for mapping data object from response to provided model
   *
   * @param response   String representation of response body
   * @param methodName Name of method on GraphQL
   * @param clazz<T>   Class for mapping response fields
   * @return Response model of provided class
   */
  public static <T> T parseDataObject(String response, String methodName, Class<T> clazz) {
    log.info("Parse data response of method " + methodName + " to " + clazz.getSimpleName()
        + " class...");
    return parseObject(response, ResponseType.DATA, methodName, clazz);
  }

  /**
   * Method for mapping error object from response to provided model
   *
   * @param response Response from GraphQL
   * @param clazz<T> Class for mapping error response fields
   * @return Response error model of provided class
   */
  public static <T> T parseErrorObject(Response response, Class<T> clazz) {
    log.info("Parse error response to " + clazz.getSimpleName() + " class...");
    return parseObject(response.getBody().asString(), ResponseType.ERRORS, "", clazz);
  }

  /**
   * Method for mapping error object from response to provided model
   *
   * @param response String representation of response body
   * @param clazz<T> Class for mapping error response fields
   * @return Response error model of provided class
   */
  public static <T> T parseErrorObject(String response, Class<T> clazz) {
    log.info("Parse error response to " + clazz.getSimpleName() + " class...");
    return parseObject(response, ResponseType.ERRORS, "", clazz);
  }


  private static <T> T parseObject(String response, ResponseType responseType, String methodName,
      Class<T> clazz) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      JsonNode responseNode = objectMapper.readTree(response);
      JsonNode objectNode = responseNode.path(responseType.getValue());

      if (responseType == ResponseType.DATA) {
        objectNode = objectNode.get(methodName);
      }

      JsonParser parser = objectNode.traverse();

      T returnValue = objectMapper.readValue(parser, clazz);

      log.info(
          "Successfully parsed " + responseType.getValue() + " object to " + clazz.getSimpleName()
              + " class");
      return returnValue;
    } catch (IOException e) {
      log.info("There is problem with parsing " + responseType.getValue() + " from response to "
          + clazz.getSimpleName());
      e.printStackTrace();
      return null;
    }
  }

  private static String createRequest(RequestBuilder requestBuilder) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("{ \"query\" : \"");

    addRequestType(stringBuilder, requestBuilder.getRequestType());

    stringBuilder.append(requestBuilder.getMethodName());

    addRequestParameters(stringBuilder, requestBuilder.getParameter());

    addResponseClass(stringBuilder, requestBuilder.getResponseClass(),
        requestBuilder.getRecursionDepth());

    stringBuilder.append("}\" }");

    return stringBuilder.toString();
  }

  private static void addRequestType(StringBuilder stringBuilder,
      RequestType requestType) {

    if (requestType == RequestType.MUTATION) {
      stringBuilder.append("mutation ");
    }

    stringBuilder.append("{ ");
  }

  private static void addRequestParameters(StringBuilder stringBuilder, Object parameter) {
    if (parameter != null) {
      stringBuilder.append("(");
      appendParameters(stringBuilder, parameter);
      stringBuilder.append(")");
    }
  }

  @SneakyThrows
  private static void appendParameters(StringBuilder stringBuilder, Object parameter) {

    if (parameter instanceof Map) {

      ((Map<?, ?>) parameter).forEach((key, value) -> {

        stringBuilder.append(key.toString())
            .append(":");

        Class<?> valueClass = value.getClass();
        if (isSimpleType(valueClass)) {
          if (valueClass == String.class) {
            stringBuilder.append("\\\"")
                .append(value)
                .append("\\\"");
          } else {
            stringBuilder.append(value);
          }
          stringBuilder.append(" ");
        } else if (value instanceof Collection) {
          List listValue = (List) value;
          if (listValue.isEmpty()) {
            stringBuilder.append("[]");
          } else {
            appendList(stringBuilder, value, listValue.get(0).getClass());
          }
        } else {
          appendObject(stringBuilder, value);
        }
      });
    } else {
      appendObjectStructure(stringBuilder, parameter);
    }
  }

  private static void appendObjectStructure(StringBuilder stringBuilder, Object parameter) {
    String paramName = parameter.getClass().getSimpleName();
    stringBuilder.append(paramName.toLowerCase().charAt(0)).append(paramName.substring(1));
    stringBuilder.append(":");
    appendObject(stringBuilder, parameter);
  }


  @SneakyThrows
  private static void appendObject(StringBuilder stringBuilder, Object parameter) {

    if (parameter == null) {
      return;
    }
    stringBuilder.append("{");
    appendObjectFields(stringBuilder, parameter, parameter.getClass());
    stringBuilder.append("} ");
  }

  private static void appendObjectFields(StringBuilder stringBuilder, Object parameter,
      Class<?> entityClass) throws IllegalAccessException {
    for (Field field : entityClass.getDeclaredFields()) {

      String fieldName = field.getName();
      field.setAccessible(true);
      Object fieldValueObject = field.get(parameter);
      if (fieldValueObject == null) {
        continue;
      }
      String fieldValue = fieldValueObject.toString();
      stringBuilder.append(fieldName).append(":");

      if (isSimpleType(field.getType())) {
        addSimpleType(stringBuilder, field.getType(), fieldValue);
      } else if (field.getType() == List.class) {
        Class<?> elementsClass = getClassOfListElements(field);
        appendList(stringBuilder, fieldValueObject, elementsClass);
      } else {
        appendObject(stringBuilder, fieldValueObject);
      }
    }
  }

  private static void appendList(StringBuilder stringBuilder, Object fieldValueObject,
      Class<?> elementsClass) {
    List<?> fieldValueList = (List<?>) fieldValueObject;

    stringBuilder.append("[");
    fieldValueList.forEach(listElement -> {
      if (isSimpleType(elementsClass)) {
        if (elementsClass == String.class) {
          stringBuilder.append("\"");
        }
        stringBuilder.append(listElement.toString());
        if (elementsClass == String.class) {
          stringBuilder.append("\"");
        }
      } else {
        appendObject(stringBuilder, listElement);
      }
      if (fieldValueList.indexOf(listElement) + 1 < fieldValueList.size()) {
        stringBuilder.append(", ");
      }
    });
    stringBuilder.append("] ");
  }

  private static void addResponseClass(StringBuilder stringBuilder, Class<?> responseClass,
      Integer recursionDepth) {

    if (responseClass != null) {
      Map<Class, Integer> recursiveClassMap = new HashMap<>();
      appendAllFields(stringBuilder, responseClass, recursiveClassMap, recursionDepth);
    }
  }

  /**
   * Builder class for creating GraphQL request
   */
  @Getter(AccessLevel.PROTECTED)
  public static class RequestBuilder {

    private RequestType requestType;
    private String methodName;
    private Class<?> responseClass;
    private Object parameter;
    private Integer recursionDepth = 1;

    public RequestBuilder(RequestType requestType, String methodName) {
      Objects.requireNonNull(requestType);
      Objects.requireNonNull(methodName);

      this.requestType = requestType;
      this.methodName = methodName;
    }

    public RequestBuilder responseClass(Class<?> responseClass) {
      Objects.requireNonNull(responseClass);
      this.responseClass = responseClass;
      return this;
    }

    public RequestBuilder parameter(Object parameter) {
      Objects.requireNonNull(parameter);
      this.parameter = parameter;
      return this;
    }

    public RequestBuilder recursionDepth(Integer recursionDepth) {
      Objects.requireNonNull(recursionDepth);
      this.recursionDepth = recursionDepth;
      return this;
    }

    public String build() {
      return createRequest(this);
    }
  }
}
