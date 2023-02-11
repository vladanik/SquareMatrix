import java.util.*;

public class SquareMatrix {
    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random();
    private static double[][] buffer;

    double matrix[][];
    //double buffer[][];
    int size;
    int start, last;

    SquareMatrix(double[][] matrix, int size, int start, int last) {
        this.matrix = matrix;
        this.size = size;
        this.start = start;
        this.last = last;
    }

    SquareMatrix(String s) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '[' || s.charAt(i) == ']') {
                continue;
            } else {
                str.append(s.charAt(i));
            }
        }
        String n = String.valueOf(str);
        String[] nn = n.split(" |\\;");
        //System.out.println(nn.length);
        size = (int) Math.sqrt(nn.length);
        matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Double.parseDouble(String.valueOf(nn[i * size + j]));
            }
        }
    }

    public static double[][] subArray(double[][] matrix, int size, int j1) {
        buffer = new double[size - 1][];
        for (int i = 0; i < (size - 1); i++) {
            buffer[i] = new double[size - 1];
        }
        for (int i = 1; i < size; i++) {
            int j2 = 0;
            for (int j = 0; j < size; j++) {
                if (j == j1) {
                    continue;
                }
                buffer[i-1][j2] = matrix[i][j];
                j2++;
            }
        }
        return buffer;
    }

    public static double[][] Losowa(double[][] matrix, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Math.round((1 + 8 * rand.nextDouble()) * 100.0) / 100.0;
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        SquareMatrix a = new SquareMatrix("[1 2 3;4 5 6;7 8 9.5]");
        System.out.println("Macierz wypelniona:");
        for (int i = 0; i < a.size; i++) {
            for (int j = 0; j < a.size; j++) {
                System.out.print(a.matrix[i][j] + " \t");
            }
            System.out.println();
        }
        System.out.print("Postac matlabowa: ");
        toString(a.matrix, a.size);
        System.out.println("Rozmiar: " + getDim(a.size));
        if (a.size == 1 || a.size == 2) {
            System.out.println("Wyznacznik: " + det12(a.matrix, a.size));
        } else {
            System.out.println("Wyznacznik metoda Sarrusa: " + getSarrus(a.matrix, a.size));
            System.out.println("Wyznacznik metoda Laplace'a: " + getDetL(a.matrix, a.size));
            System.out.println("Wyznacznik z definicji permutacyjnej: " + getDetD(a.matrix, a.size));
        }
        System.out.println();
        System.out.println("Macierz losowa:");
        Losowa(a.matrix, a.size);
        for (int i = 0; i < a.size; i++) {
            for (int j = 0; j < a.size; j++) {
                System.out.print(a.matrix[i][j] + " \t");
            }
            System.out.println();
        }
        System.out.print("Postac matlabowa: ");
        toString(a.matrix, a.size);
        System.out.println("Rozmiar: " + getDim(a.size));
        if (a.size == 1 || a.size == 2) {
            System.out.println("Wyznacznik: " + det12(a.matrix, a.size));
        } else {
            System.out.println("Wyznacznik metoda Sarrusa: " + getSarrus(a.matrix, a.size));
            System.out.println("Wyznacznik metoda Laplace'a: " + getDetL(a.matrix, a.size));
            System.out.println("Wyznacznik z definicji permutacyjnej: " + getDetD(a.matrix, a.size));
        }
    }

    public static void toString(double[][] matrix, int size) {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j + 1 == size) {
                    if (i + 1 == size) {
                        System.out.print(matrix[i][j]);
                    } else {
                        System.out.print(matrix[i][j] + ";");
                    }
                } else {
                    System.out.print(matrix[i][j] + " ");
                }
            }
        }
        System.out.println("]");
    }

    public static String getDim(int size) {
        return size + " x " + size;
    }

    static double det12(double[][] matrix, int size) {
        double result = 0;
        if (size == 1) {
            result = matrix[0][0];
        } else if (size == 2) {
            result = matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
        }
        return result;
    }

    static double getSarrus(double[][] matrix, int size) {
        double[][] exMatrix = new double[2 * size - 1][size];
        double result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; ++j) {
                exMatrix[i][j] = matrix[i][j];
                if (i < size - 1) {
                    exMatrix[size - i][j] = matrix[i][j];
                }
            }
        }
        for (int i = 0; i < size; ++i) {
            double m1 = 1, m2 = 1;
            for (int j = 0; j < size; ++j) {
                m1 *= exMatrix[i + j][j];
                m2 *= exMatrix[(size - 1) + i - j][j];
            }
            result += m1 - m2;
        }
        return result;
    }

    static double getDetL(double[][] matrix, int size) {
        double result = 0;
        for (int j1 = 0; j1 < size; j1++) {
            buffer = subArray(matrix, size, j1);
            result = Math.pow(-1.0, j1) * matrix[0][j1] * getDetL(buffer, size - 1);
        }
        return result;
    }

    //nie dziala
    static double getDetD(double[][] matrix, int size) {
        double result = 0;
        return result;
    }
}