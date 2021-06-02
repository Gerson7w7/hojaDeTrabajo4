package criptografía;
//Gerson Quiroa carné 202000166

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.time.LocalDateTime;

public class CRIPTOGRAFÍA {

    public static Scanner leer = new Scanner(System.in);

    public static int[][] matrizM = null, matrizA = null, matrizB = null, matrizC = null, matrizRes = null, matrizDscr = null;
    public static double[][] matrizInv = null;
    public static String intrMensaje = "";
    public static int num = 0;
    public static boolean matriz1 = false, matriz2 = false, matriz3 = false, matriz4 = false;

    public static void main(String[] args) {
        int menu = 0;
        //MENÚ PRINCIPAL
        while (menu < 5) {
            System.out.println("MENÚ");
            System.out.println("\t 1. Encriptar");
            System.out.println("\t 2. Desencriptar");
            System.out.println("\t 3. Ataque con texto claro");
            System.out.println("\t 4. Generar reportes");
            System.out.println("\t 5. Salir");
            System.out.println("Eliga una opción pulsando un número (1-5).");
            try {
                menu = Integer.valueOf(leer.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, intente únicamente con números.");
                menu = 0;
            }

            switch (menu) {
                //ENCRIPTAR
                case 1:
                    encriptar();
                    break;

                //DESENCRIPTAR
                case 2:
                    desencriptar();
                    break;

                //ATAQUE CON TEXTO CLARO
                case 3:
                    System.out.println("Lo siento, no puedo hacer esta opción :(");
                    break;

                //GENERAR REPORTES 
                case 4:
                    reporte();
                    break;
            }
        }
    }

    public static void encriptar() {
        int menEncrip = 0;
        //MENÚ ENCRIPTAR
        while (menEncrip < 5) {
            System.out.println("MENÚ ENCRIPTAR");
            System.out.println("\t 1. Ingresar mensaje");
            System.out.println("\t 2. Ingresar matriz clave A");
            System.out.println("\t 3. Ingresar matriz clave B");
            System.out.println("\t 4. Encriptar");
            System.out.println("\t 5. Menú principal");
            System.out.println("Eliga una opción pulsando un número (1-5).");

            try {
                menEncrip = Integer.valueOf(leer.nextLine());
                switch (menEncrip) {
                    case 1: //MENSAJE A ENCRIPTAR
                        matrizM = mensaje();
                        break;

                    case 2: //MATRIZ CLAVE A
                        matrizA = matrizClave();
                        break;

                    case 3: //MATRIZ CLAVE B
                        matrizB = matrizClave();
                        break;

                    case 4: //ENCRIPTACIÓN DEL MENSAJE 
                        System.out.println("El mensaje cifrado es: ");
                        matrizC = encriptacion(matrizM, matrizA, matrizB, matrizC);
                        System.out.println("\n");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, intente únicamente con números.");
                menEncrip = 0;
            }

        }
    }

    public static int[][] mensaje() {
        System.out.println("INGRESE MENSAJE A ENCRIPTAR");
        intrMensaje = "";
        intrMensaje = leer.nextLine();
        intrMensaje = intrMensaje.toUpperCase();
        char matrizz[] = intrMensaje.toCharArray();
        int matrizR[][] = new int[3][(int) Math.ceil(matrizz.length / 3.0)];
        for (int i = 0; i < matrizz.length; i++) {
            if (!((matrizz[i] >= 65 && matrizz[i] <= 90) || matrizz[i] == 32 || matrizz[i] == 65533 || matrizz[i] == 164)) {
                System.out.println("Por favor, escribir solo con letras de la A-Z incluyendo el espacio.");
                System.out.println("Intente de nuevo.");
                return null;
            } else if (matrizz[i] == 32) {
                matrizR[i % 3][(int) Math.floor(i / 3.0)] = 27;
            } else if (matrizz[i] == 65533 || matrizz[i] == 164) {
                matrizR[i % 3][(int) Math.floor(i / 3.0)] = 14;
            } else if (matrizz[i] < 79) {
                matrizR[i % 3][(int) Math.floor(i / 3.0)] = matrizz[i] - 65;
            } else if (matrizz[i] >= 79) {
                matrizR[i % 3][(int) Math.floor(i / 3.0)] = matrizz[i] - 64;
            }
        }
        if (matrizz.length % 3 == 1) {
            matrizR[2][matrizR[0].length - 1] = 27;
            matrizR[1][matrizR[0].length - 1] = 27;
        } else if (matrizz.length % 3 == 2) {
            matrizR[2][matrizR[0].length - 1] = 27;
        }

        for (int i = 0; i < matrizR.length; i++) {
            for (int j = 0; j < matrizR[0].length; j++) {
                System.out.print("|" + matrizR[i][j] + "|");
            }
            System.out.println("");
        }
        matriz1 = true;
        return matrizR;
    }

    public static int[][] matrizClave() {
        System.out.println("Escribe el nombre del archivo: ");
        File archivo = new File(leer.nextLine());
        try {
            FileReader arch = new FileReader(archivo);
            BufferedReader miBuf = new BufferedReader(arch);
            String numL = miBuf.readLine();
            int matrizR[][] = new int[3][];
            for (int i = 0; numL != null && i < 3; i++) {
                String matrizz[] = numL.split(",");
                matrizR[i] = new int[matrizz.length];
                for (int j = 0; j < matrizz.length; j++) {
                    matrizR[i][j] = Integer.valueOf(matrizz[j]);
                }
                numL = miBuf.readLine();
            }
            for (int i = 0; i < matrizR.length; i++) {
                for (int j = 0; j < matrizR[0].length; j++) {
                    System.out.print("|" + matrizR[i][j] + "|");
                }
                System.out.println("");
            }
            matriz2 = true;
            return matrizR;
        } catch (Exception e) {
            System.out.println("No encuentro el archivo :(");
            System.out.println("Intente de nuevo. \n");
            return null;
        }
    }

    public static int[][] encriptacion(int[][] matrizM, int[][] matrizA, int[][] matrizB, int[][] matrizC) {
        try {
            int matrizOp[][] = new int[matrizA.length][matrizM[0].length];
            //MUTIPLICACIÓN DE A*M
            for (int i = 0; i < matrizOp.length; i++) {
                for (int j = 0; j < matrizOp[0].length; j++) {
                    int rest = 0;
                    for (int k = 0; k < matrizA.length; k++) {
                        rest += (matrizA[i][k] * matrizM[k][j]);
                    }
                    matrizOp[i][j] = rest;
                }
            }
            //SUMA DE A*M + C
            for (int i = 0; i < matrizOp.length; i++) {
                for (int j = 0; j < matrizOp[0].length; j++) {
                    matrizOp[i][j] += matrizB[i][j];
                }
            }
            for (int i = 0; i < matrizOp.length; i++) {
                for (int j = 0; j < matrizOp[0].length; j++) {
                    System.out.print(matrizOp[i][j] + ",");
                }
            }
            System.out.println("\n");
            matriz3 = true;
            return matrizOp;
        } catch (Exception e) {
            System.out.println("Algo ha salido mal. Verifique que haya ingresado los archivos correctos. \n");
            return null;
        }

    }

    public static void desencriptar() {
        try {
            System.out.println("MENÚ DESENCRIPTAR");
            System.out.println("El mensaje descifrado es: ");
            desencriptacion(matrizM, matrizA, matrizB, matrizC);
        } catch (Exception e) {
            System.out.println("Algo ha salido mal. Verifique que haya ingresado los archivos correctos. \n");
        }
    }

    public static void desencriptacion(int[][] matrizM, int[][] matrizA, int[][] matrizB, int[][] matrizC) {
        matrizRes = new int[matrizM.length][matrizM[0].length];

        //RESTA DE C - B
        for (int i = 0; i < matrizRes.length; i++) {
            for (int j = 0; j < matrizRes[0].length; j++) {
                int rest = 0;
                rest += (matrizC[i][j] - matrizB[i][j]);
                matrizRes[i][j] = rest;
            }
        }

        //INVERSA DE A
        //Determinante 
        int detP = 0, detN = 0, det = 0;
        detP = (matrizA[0][0] * matrizA[1][1] * matrizA[2][2] + matrizA[0][1] * matrizA[1][2] * matrizA[2][0] + matrizA[0][2] * matrizA[1][0] * matrizA[2][1]);
        detN = (matrizA[0][2] * matrizA[1][1] * matrizA[2][0] + matrizA[0][0] * matrizA[1][2] * matrizA[2][1] + matrizA[0][1] * matrizA[1][0] * matrizA[2][2]);
        det = detP - detN;

        //Transpuesta de A       
        for (int i = 0; i < 3; i++) {
            int aux = 0;
            for (int j = 0; j < i; j++) {
                aux = matrizA[i][j];
                matrizA[i][j] = matrizA[j][i];
                matrizA[j][i] = aux;
            }
        }

        //Adjunta de A^t
        matrizInv = new double[3][3];
        matrizInv[0][0] = (matrizA[1][1] * matrizA[2][2] - matrizA[1][2] * matrizA[2][1]);
        matrizInv[0][1] = -(matrizA[1][0] * matrizA[2][2] - matrizA[1][2] * matrizA[2][0]);
        matrizInv[0][2] = (matrizA[1][0] * matrizA[2][1] - matrizA[1][1] * matrizA[2][0]);
        matrizInv[1][0] = -(matrizA[0][1] * matrizA[2][2] - matrizA[0][2] * matrizA[2][1]);
        matrizInv[1][1] = (matrizA[0][0] * matrizA[2][2] - matrizA[0][2] * matrizA[2][0]);
        matrizInv[1][2] = -(matrizA[0][0] * matrizA[2][1] - matrizA[0][1] * matrizA[2][0]);
        matrizInv[2][0] = (matrizA[0][1] * matrizA[1][2] - matrizA[0][2] * matrizA[1][1]);
        matrizInv[2][1] = -(matrizA[0][0] * matrizA[1][2] - matrizA[0][2] * matrizA[1][0]);
        matrizInv[2][2] = (matrizA[0][0] * matrizA[1][1] - matrizA[0][1] * matrizA[1][0]);

        //Inversa de A
        for (int i = 0; i < matrizInv.length; i++) {
            for (int j = 0; j < matrizInv[0].length; j++) {
                matrizInv[i][j] = ((matrizInv[i][j]) / (1.0 * det));
            }
        }

        //A^-1(C-B)
        matrizDscr = new int[matrizM.length][matrizM[0].length];
        for (int i = 0; i < matrizDscr.length; i++) {
            for (int j = 0; j < matrizDscr[0].length; j++) {
                double num = 0;
                for (int k = 0; k < matrizDscr.length; k++) {
                    num += ((1.0 * matrizInv[i][k]) * (1.0 * matrizRes[k][j]));
                }
                matrizDscr[i][j] = (int) Math.round(num);
            }
        }
        for (int i = 0; i < matrizDscr.length; i++) {
            for (int j = 0; j < matrizDscr[0].length; j++) {
                System.out.print("|" + matrizDscr[i][j] + "|");
            }
            System.out.println("");
        }

        //ASCII A CARACTERES        
        for (int i = 0; i < matrizDscr[0].length; i++) {
            for (int j = 0; j < matrizDscr.length; j++) {
                num = matrizDscr[j][i];
                switch (num) {
                    case 0:
                        System.out.print("a");
                        break;
                    case 1:
                        System.out.print("b");
                        break;
                    case 2:
                        System.out.print("c");
                        break;
                    case 3:
                        System.out.print("d");
                        break;
                    case 4:
                        System.out.print("e");
                        break;
                    case 5:
                        System.out.print("f");
                        break;
                    case 6:
                        System.out.print("g");
                        break;
                    case 7:
                        System.out.print("h");
                        break;
                    case 8:
                        System.out.print("i");
                        break;
                    case 9:
                        System.out.print("j");
                        break;
                    case 10:
                        System.out.print("k");
                        break;
                    case 11:
                        System.out.print("l");
                        break;
                    case 12:
                        System.out.print("m");
                        break;
                    case 13:
                        System.out.print("n");
                        break;
                    case 14:
                        System.out.print("ñ");
                        break;
                    case 15:
                        System.out.print("o");
                        break;
                    case 16:
                        System.out.print("p");
                        break;
                    case 17:
                        System.out.print("q");
                        break;
                    case 18:
                        System.out.print("r");
                        break;
                    case 19:
                        System.out.print("s");
                        break;
                    case 20:
                        System.out.print("t");
                        break;
                    case 21:
                        System.out.print("u");
                        break;
                    case 22:
                        System.out.print("v");
                        break;
                    case 23:
                        System.out.print("w");
                        break;
                    case 24:
                        System.out.print("x");
                        break;
                    case 25:
                        System.out.print("y");
                        break;
                    case 26:
                        System.out.print("z");
                        break;
                    case 27:
                        System.out.print(" ");
                        break;
                }
            }
        }
        System.out.println("\n");
        matriz4 = true;
    }

    //REPORTES
    public static void reporte() {
        int reporte = 0;
        while (reporte < 3) {
            System.out.println("GENERAR REPORTES");
            System.out.println("\t 1. Reporte encriptar");
            System.out.println("\t 2. Reporte descencriptar");
            System.out.println("\t 3. Regresar al menú principal");

            try {
                reporte = Integer.valueOf(leer.nextLine());
                switch (reporte) {
                case 1: //REPORTE ENCPRITACIÓN
                    repEncriptacion();
                    break;
                case 2: //REPORTE DESCENCRIPTAR
                    repDesencriptacion();
                    break;
            }
            } catch (NumberFormatException e) {
                System.out.println("Por favor introduce un número (1-3).");
            }
            
        }
    }

    //REPORTE ENCRIPTACION  
    public static void repEncriptacion() {
        if (matriz1 == true && matriz2 == true && matriz3 == true) {
            LocalDateTime fechaHoraActuales = LocalDateTime.now();
            FileWriter fichero = null;
            PrintWriter pw = null;
            try {
                fichero = new FileWriter("Reporte Encriptación.html");
                pw = new PrintWriter(fichero);

                pw.println("<!DOCTYPE html>\n"
                        + "<html lang=\"en\">\n"
                        + "<head>\n"
                        + "    <meta charset=iso-utf8>\n"
                        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    <title>Document</title>\n"
                        + "</head>\n"
                        + "<body>\n");
                pw.println("Hora de generación:" + fechaHoraActuales + "<br><br>\n");
                pw.println("<center>"
                        + "REPORTE ENCRIPTACIÓN"
                        + "</center>");
                pw.println("PASO 1: El programa le pide al usuario que ingrese el mensaje a encriptar. El cual fue el siguiente: <br>\n");
                pw.println(intrMensaje);

                pw.println("<table class=\"default\">\n"
                        + "\n"
                        + "  <colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(128, 255, 0, 0.3); border: 1px solid rgba(100, 200, 0, 0.3);\"></colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(255, 128, 0, 0.3); border: 1px solid rgba(200, 100, 0, 0.3);\"></colgroup>");
                for (int i = 0; i < matrizM.length; i++) {
                    pw.println(" <tr>");
                    for (int j = 0; j < matrizM[0].length; j++) {
                        pw.println("<td>" + matrizM[i][j] + "</td>");
                    }
                    pw.println("</tr>");
                }
                pw.println("</table><br><br>");

                pw.println(" PASO 2: El programa pide al usuario que ingrese la ruta de la matriz clave A. <br>\n");

                pw.println("<table class=\"default\">\n"
                        + "\n"
                        + "  <colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(128, 255, 0, 0.3); border: 1px solid rgba(100, 200, 0, 0.3);\"></colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(255, 128, 0, 0.3); border: 1px solid rgba(200, 100, 0, 0.3);\"></colgroup>");
                for (int i = 0; i < matrizA.length; i++) {
                    pw.println(" <tr>");
                    for (int j = 0; j < matrizA[0].length; j++) {
                        pw.println("<td>" + matrizA[i][j] + "</td>");
                    }
                    pw.println("</tr>");
                }
                pw.println("</table><br><br>");

                pw.println(" PASO 3: El programa pide al usuario que ingrese la ruta de la matriz clave B. <br>\n");
                pw.println("<table class=\"default\">\n"
                        + "\n"
                        + "  <colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(128, 255, 0, 0.3); border: 1px solid rgba(100, 200, 0, 0.3);\"></colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(255, 128, 0, 0.3); border: 1px solid rgba(200, 100, 0, 0.3);\"></colgroup>");
                for (int i = 0; i < matrizB.length; i++) {
                    pw.println(" <tr>");
                    for (int j = 0; j < matrizB[0].length; j++) {
                        pw.println("<td>" + matrizB[i][j] + "</td>");
                    }
                    pw.println("</tr>");
                }
                pw.println("</table><br><br>");

                pw.println(" PASO 4: El programa devuelve una serie de números, los cuales son el mensaje encriptado. <br>\n"
                        + "El mensaje encriptado es el siguiente: <br><br>\n");
                pw.println("<table class=\"default\">\n"
                        + "\n"
                        + "  <colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(128, 255, 0, 0.3); border: 1px solid rgba(100, 200, 0, 0.3);\"></colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(255, 128, 0, 0.3); border: 1px solid rgba(200, 100, 0, 0.3);\"></colgroup>");
                for (int i = 0; i < matrizC.length; i++) {
                    pw.println(" <tr>");
                    for (int j = 0; j < matrizC[0].length; j++) {
                        pw.println(matrizC[i][j] + ",");
                    }
                    pw.println("</tr>");
                }
                pw.println("</table><br><br>");

                pw.println(" "
                        + "    \n"
                        + "</body>\n"
                        + "</html>");

                fichero.close();
                System.out.println("El reporte se ha generado correctamente :D \n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Necesito más información para generar este reporte.");
        }
    }

    //REPORTE DESENCRIPTACION 
    public static void repDesencriptacion() {
        if (matriz1 == true && matriz2 == true && matriz3 == true && matriz4 == true) {
            LocalDateTime fechaHoraActuales = LocalDateTime.now();
            FileWriter fichero = null;
            PrintWriter pw = null;
            try {
                fichero = new FileWriter("Reporte Desencriptación.html");
                pw = new PrintWriter(fichero);

                pw.println("<!DOCTYPE html>\n"
                        + "<html lang=\"en\">\n"
                        + "<head>\n"
                        + "    <meta charset=iso-utf8>\n"
                        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    <title>Document</title>\n"
                        + "</head>\n"
                        + "<body>\n");
                pw.println("Hora de generación:" + fechaHoraActuales + "<br><br>\n");
                pw.println("<center>"
                        + "REPORTE DESENCRIPTACIÓN"
                        + "</center>");
                pw.println("PASO 1: El programa realiza una operación matricial (la resta) entre el mensaje encriptado y la matriz clave B. El cual fue el siguiente: <br>\n");
                pw.println("<table class=\"default\">\n"
                        + "\n"
                        + "  <colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(128, 255, 0, 0.3); border: 1px solid rgba(100, 200, 0, 0.3);\"></colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(255, 128, 0, 0.3); border: 1px solid rgba(200, 100, 0, 0.3);\"></colgroup>");
                for (int i = 0; i < matrizRes.length; i++) {
                    pw.println(" <tr>");
                    for (int j = 0; j < matrizRes[0].length; j++) {
                        pw.println("<td>" + matrizRes[i][j] + "</td>");
                    }
                    pw.println("</tr>");
                }
                pw.println("</table><br><br>");

                pw.println(" PASO 2: Luego, el programa procede a calcular la inversa de la matriz clave A. El cual fue el siguiente: <br>\n");

                pw.println("<table class=\"default\">\n"
                        + "\n"
                        + "  <colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(128, 255, 0, 0.3); border: 1px solid rgba(100, 200, 0, 0.3);\"></colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(255, 128, 0, 0.3); border: 1px solid rgba(200, 100, 0, 0.3);\"></colgroup>");
                for (int i = 0; i < matrizInv.length; i++) {
                    pw.println(" <tr>");
                    for (int j = 0; j < matrizInv[0].length; j++) {
                        pw.println("<td>" + matrizInv[i][j] + "</td>");
                    }
                    pw.println("</tr>");
                }
                pw.println("</table><br><br>");

                pw.println(" PASO 3: El programa ahora, procede a realizar la última operación que será la multiplicación de la matrices calculadas en los pasos enteriores. <br>\n");
                pw.println("<table class=\"default\">\n"
                        + "\n"
                        + "  <colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(128, 255, 0, 0.3); border: 1px solid rgba(100, 200, 0, 0.3);\"></colgroup>\n"
                        + "\n"
                        + "  <colgroup span=\"2\" style=\"background: rgba(255, 128, 0, 0.3); border: 1px solid rgba(200, 100, 0, 0.3);\"></colgroup>");
                for (int i = 0; i < matrizDscr.length; i++) {
                    pw.println(" <tr>");
                    for (int j = 0; j < matrizDscr[0].length; j++) {
                        pw.println("<td>" + matrizDscr[i][j] + "</td>");
                    }
                    pw.println("</tr>");
                }
                pw.println("</table><br>");

                pw.println(" PASO 4: Por último, el programa regresará el mensaje que el usuario introdujo en la sección de Encriptación. <br>\n");
                pw.println("El mensaje desencriptado es el siguiente: <br> \n");
                for (int i = 0; i < matrizDscr[0].length; i++) {
                    for (int j = 0; j < matrizDscr.length; j++) {
                        num = matrizDscr[j][i];
                        switch (num) {
                            case 0:
                                pw.println("a");
                                break;
                            case 1:
                                pw.println("b");
                                break;
                            case 2:
                                pw.println("c");
                                break;
                            case 3:
                                pw.println("d");
                                break;
                            case 4:
                                pw.println("e");
                                break;
                            case 5:
                                pw.println("f");
                                break;
                            case 6:
                                pw.println("g");
                                break;
                            case 7:
                                pw.println("h");
                                break;
                            case 8:
                                pw.println("i");
                                break;
                            case 9:
                                pw.println("j");
                                break;
                            case 10:
                                pw.println("k");
                                break;
                            case 11:
                                pw.println("l");
                                break;
                            case 12:
                                pw.println("m");
                                break;
                            case 13:
                                pw.println("n");
                                break;
                            case 14:
                                pw.println("ñ");
                                break;
                            case 15:
                                pw.println("o");
                                break;
                            case 16:
                                pw.println("p");
                                break;
                            case 17:
                                pw.println("q");
                                break;
                            case 18:
                                pw.println("r");
                                break;
                            case 19:
                                pw.println("s");
                                break;
                            case 20:
                                pw.println("t");
                                break;
                            case 21:
                                pw.println("u");
                                break;
                            case 22:
                                pw.println("v");
                                break;
                            case 23:
                                pw.println("w");
                                break;
                            case 24:
                                pw.println("x");
                                break;
                            case 25:
                                pw.println("y");
                                break;
                            case 26:
                                pw.println("z");
                                break;
                            case 27:
                                pw.println(" ");
                                break;
                        }
                    }
                }
                pw.println(" "
                        + "    \n"
                        + "</body>\n"
                        + "</html>");

                fichero.close();
                System.out.println("El reporte se ha generado correctamente :D \n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Necesito más información para generar este reporte.");
        }
    }
}
