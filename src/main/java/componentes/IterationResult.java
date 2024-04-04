package componentes;

public class IterationResult {
    private int iteration;
    private double xiMinus1;
    private double xi;
    private double fxiMinus1;
    private double fxi;
    private double xiPlus1;
    private double errorPercentage;

    public IterationResult(int iteration, double xiMinus1, double xi, double fxiMinus1, double fxi, double xiPlus1, double errorPercentage) {
        this.iteration = iteration;
        this.xiMinus1 = xiMinus1;
        this.xi = xi;
        this.fxiMinus1 = fxiMinus1;
        this.fxi = fxi;
        this.xiPlus1 = xiPlus1;
        this.errorPercentage = errorPercentage;
    }

    public int getIteration() {
        return iteration;
    }

    public double getXiMinus1() {
        return xiMinus1;
    }

    public double getXi() {
        return xi;
    }

    public double getFxiMinus1() {
        return fxiMinus1;
    }

    public double getFxi() {
        return fxi;
    }

    public double getXiPlus1() {
        return xiPlus1;
    }

    public double getErrorPercentage() {
        return errorPercentage;
    }
}
