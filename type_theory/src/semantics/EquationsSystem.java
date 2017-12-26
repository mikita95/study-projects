package semantics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EquationsSystem {
    private List<Equation> equations;

    public EquationsSystem(List<Equation> equations) {
        this.equations = equations;
    }

    private boolean isSolved() {
        Set<Variable> leftSet = new HashSet<>();
        for (Equation equation : equations)
            if (equation.getLeft() instanceof Variable) {
                Variable left = (Variable) equation.getLeft();
                if (leftSet.contains(left)) return false;
                leftSet.add(left);
            } else return false;
        for (Equation equation : equations) {
            Set<Variable> variables = equation.getRight().getVariables();
            for (Variable variable : leftSet) if (variables.contains(variable)) return false;
        }
        return true;
    }

    private boolean isInconsistent() {
        for (Equation equation : equations)
            if (equation.getRight() instanceof Function) {
                if (equation.getLeft() instanceof Variable) {
                    if (equation.getRight().getVariables().contains(equation.getLeft())) return true;
                } else if (!((Function) equation.getLeft()).getName().equals(((Function) equation.getRight()).getName())) return true;
            }
        return false;
    }

    private void deleteLeft(int first, int second) {
        Variable variable = (Variable) equations.get(first).getLeft();
        equations.set(second, equations.get(second).getLeft().equals(variable) && !(equations.get(first).getRight() instanceof Variable) ? new Equation(equations.get(first).getRight(), equations.get(second).getRight()) : new Equation(equations.get(second).getLeft().substitute(variable, equations.get(first).getRight()), equations.get(second).getRight().substitute(variable, equations.get(first).getRight())));
    }

    public boolean solve() {
        while (!isSolved()) {
            if (isInconsistent()) return false;
            for (int i = 0; i < equations.size(); i++) {
                if (equations.get(i).getLeft() instanceof Function) {
                    if (equations.get(i).getRight() instanceof Variable)
                        equations.set(i, new Equation(equations.get(i).getRight(), equations.get(i).getLeft()));
                    else {
                        Function function1 = (Function) equations.get(i).getLeft();
                        Function function2 = (Function) equations.get(i).getRight();
                        if (function1.getArguments().size() != function2.getArguments().size()) return false;
                        for (int j = 0; j < function1.getArguments().size(); j++)
                            equations.add(new Equation(function1.getArguments().get(j), function2.getArguments().get(j)));
                        equations.remove(i--);
                    }
                }
                if (i >= 0)
                    if (equations.get(i).getLeft().equals(equations.get(i).getRight())) equations.remove(i--);
            }
            for (int i = 0; i < equations.size(); i++)
                if (equations.get(i).getLeft() instanceof Variable)
                    for (int j = 0; j < equations.size(); j++) if (i != j) deleteLeft(i, j);
        }
        return true;
    }

    public List<Equation> getEquations() {
        return equations;
    }

    public void setEquations(List<Equation> equations) {
        this.equations = equations;
    }
}
