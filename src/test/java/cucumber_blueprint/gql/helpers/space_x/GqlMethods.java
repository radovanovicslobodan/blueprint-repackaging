package cucumber_blueprint.gql.helpers.space_x;

public enum GqlMethods {
    MISSIONS("missions"),
    SHIPS("ships"),
    ROCKETS("rockets");

    private String methodName;

    GqlMethods(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}
