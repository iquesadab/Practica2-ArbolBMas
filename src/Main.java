import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        ArbolBMas arbol = new ArbolBMas(4);

        boolean continuar = true;
        int opcion;
        int llave;
        String dato;
        int cantidad;

        // Ciclo principal del programa
        while (continuar) {
            System.out.println("***** MENÚ *****\n" +
                    "1. Insertar elemento\n" +
                    "2. Buscar llave\n" +
                    "3. Buscar dato por llave\n" +
                    "4. Eliminar elemento\n" +
                    "5. Recorrer rango\n" +
                    "6. Imprimir árbol\n" +
                    "0. Salir\n");
            System.out.print("Seleccione una opción: \n");
            opcion = entrada.nextInt();
            entrada.nextLine(); //Borrar el buffer

            switch (opcion) {

                // Insertar elemento
                case 1:
                    System.out.print("Digite la llave: ");
                    llave = entrada.nextInt();
                    entrada.nextLine();

                    System.out.print("Digite el dato asociado: ");
                    dato = entrada.nextLine();

                    arbol.insertar(llave, dato);
                    System.out.println("Elemento insertado.");
                    break;

                // Buscar llave
                case 2:
                    System.out.print("Digite la llave a buscar: ");
                    llave = entrada.nextInt();

                    if (arbol.buscar(llave)) {
                        System.out.println("La llave existe en el árbol.");
                    } else {
                        System.out.println("La llave no existe en el árbol.");
                    }
                    break;

                // Buscar dato por llave
                case 3:
                    System.out.print("Digite la llave: ");
                    llave = entrada.nextInt();
                    entrada.nextLine();

                    dato = arbol.buscarDato(llave);

                    if (dato != null) {
                        System.out.println("Dato asociado: " + dato);
                    } else {
                        System.out.println("No se encontró la llave.");
                    }
                    break;

                // Eliminar elemento
                case 4:
                    System.out.print("Digite la llave a eliminar: ");
                    llave = entrada.nextInt();

                    if (arbol.eliminar(llave)) {
                        System.out.println("Elemento eliminado.");
                    } else {
                        System.out.println("La llave no existe.");
                    }
                    break;

                // Recorrer rango
                case 5:
                    System.out.print("Digite la llave inicial: ");
                    llave = entrada.nextInt();

                    System.out.print("Digite la cantidad de elementos a mostrar: ");
                    cantidad = entrada.nextInt();

                    arbol.recorrerRango(llave, cantidad);
                    break;

                // Imprimir árbol
                case 6:
                    System.out.println("\nEstructura del árbol:");
                    arbol.imprimirArbol();
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}