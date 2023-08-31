package matrixMultiplication;

public interface MatrixMultiplication {
    public void multiply(int[][] matrixA, int[][] matrixB, int startColumn, int endColumn);
    public boolean checkAuthenticity(int[][] matrixA, int[][] matrixB);
}
