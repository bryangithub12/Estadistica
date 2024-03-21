package probabilidad_bryan;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;

public class Probabilidad_bryan {
    public static void main(String[] args) {
        String nombreArchivo = "D:\\PROBABILIDAD\\numeros.txt"; // archivo aquí

        Scanner input = new Scanner(System.in);
        System.out.print("INGRESE EL NUMERO DE CLASES: ");
        int k=input.nextInt();
        
        /*String respuesta = input.nextLine();
        int clases=0;
        if (respuesta.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el número de clases: ");
            clases = input.nextInt();
        } else {
            // Número de clases calculado automáticamente
            clases = calcularNumeroClases(nombreArchivo);
        }*/

        ArrayList<Integer> numeros = leerNumeros(nombreArchivo);

        if (numeros != null) {
            calcularTablaDeFrecuencias(numeros, k);
        } else {
            System.out.println("No se pudo leer ningún número del archivo.");
        }
    }

    public static ArrayList<Integer> leerNumeros(String nombreArchivo) {
        ArrayList<Integer> numeros = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(nombreArchivo));
            while (scanner.hasNextInt()) {
                numeros.add(scanner.nextInt());
            }
            scanner.close();
            return numeros;
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no se encuentra.");
            return null;
        }
    }

    public static void calcularTablaDeFrecuencias(ArrayList<Integer> numeros, int k) {
        int minimo = Collections.min(numeros);
        int maximo = Collections.max(numeros);
        System.out.println(" EL NUMERO DE CLASES ES  : " + k);
        System.out.println(" MAXIMO: " + maximo);
        System.out.println(" MINIMO: " + minimo);
        double rango = (double) (maximo - minimo);
        System.out.println(" EL RANGO ES  : " + rango);
        double anchoClase = Math.ceil(rango / k);
        System.out.println(" EL TAMANO DEL INTERVALO (SIN REDONDEAR) : " + (rango / k));
        System.out.println(" ELTAMANO DEL INTERVALO ES  : " + anchoClase);

        System.out.println("Li-1 - Li\t\t Xi\t\t ni\t\t Ni\t\t hi (%)\t\t Hi (%)");

        int limiteInferior = minimo;
        int limiteSuperior;
        int frecuenciaAcumulada = 0;

        for (int i = 0; i < k; i++) {
            limiteSuperior = (int) (limiteInferior + anchoClase);

            double marcaClase = (limiteInferior + limiteSuperior) / 2.0;

            int frecuencia = contarFrecuencia(numeros, limiteInferior, limiteSuperior);
            frecuenciaAcumulada += frecuencia;

            double frecuenciaRelativa = (double) frecuencia * 100 / numeros.size();
            double frecuenciaRelativaAcumulada = (double) frecuenciaAcumulada * 100 / numeros.size();

            System.out.println("[" + limiteInferior + " ; " + limiteSuperior + ")\t  " + marcaClase + "\t\t" + frecuencia + "\t\t" + frecuenciaAcumulada + "\t\t" + frecuenciaRelativa + "\t\t" + frecuenciaRelativaAcumulada);

            limiteInferior = limiteSuperior;
        }

        // Imprimir histograma
        System.out.println("\nHistograma:");
        imprimirHistograma(numeros, k, minimo, anchoClase);
    }

    public static int contarFrecuencia(ArrayList<Integer> numeros, int limiteInferior, int limiteSuperior) {
        int frecuencia = 0;
        for (int numero : numeros) {
            if (numero >= limiteInferior && numero < limiteSuperior) {
                frecuencia++;
            }
        }
        return frecuencia;
    }

    public static void imprimirHistograma(ArrayList<Integer> numeros, int clases, int minimo, double anchoClase) {
        int limiteInferior = minimo;
        int limiteSuperior;

        for (int i = 0; i < clases; i++) {
            limiteSuperior = (int) (limiteInferior + anchoClase);
            System.out.print("[" + limiteInferior + "-" + limiteSuperior + "): ");
            for (int numero : numeros) {
                if (numero >= limiteInferior && numero < limiteSuperior) {
                    System.out.print("*");
                }
            }
            System.out.println();
            limiteInferior = limiteSuperior;
        }
    }

    public static int calcularNumeroClases(String nombreArchivo) {
        ArrayList<Integer> numeros = leerNumeros(nombreArchivo);
        if (numeros == null || numeros.isEmpty()) {
            return 0;
        }

        int n = numeros.size();
        return (int) Math.ceil(1 + 3.322 * Math.log10(n)); // Fórmula de Sturges para calcular el número de clases
    }
}