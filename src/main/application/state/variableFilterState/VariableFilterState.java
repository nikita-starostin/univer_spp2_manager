package main.application.state.variableFilterState;

import java.util.ArrayList;
import java.util.List;

public class VariableFilterState {
    private String filter;
    private List<IVariableFilterStateChangeHandler> filterStateChangeHandlers = new ArrayList<>();

    public void setFilter(String filter) {
        this.filter = filter;
        for (var handler : filterStateChangeHandlers) {
            handler.onChange(filter);
        }
    }

    public String getFilter() {
        return filter;
    }

    public void addHandler(IVariableFilterStateChangeHandler handler) {
        filterStateChangeHandlers.add(handler);
    }
}
