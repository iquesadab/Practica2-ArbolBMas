import java.util.ArrayList;

public class ArbolBMas {

    // Atributos.
    private final int orden;
    private NodoArbolBMas raiz;

    // Métodos.
    // Constructor.
    public ArbolBMas(int orden) {
        this.orden = orden;
        raiz = new NodoArbolBMas(true);
    }

    // Inserción de una llave en el árbol B+.
    // Permite insertar una llave junto con su dato.
    public void insertar(int llave, String dato) {
        if(this.raiz.getLlaves().size() == orden - 1) {
            NodoArbolBMas nuevaRaiz = new NodoArbolBMas(false);
            nuevaRaiz.getHijos().add(this.raiz);
            dividirHijo(nuevaRaiz, 0);
            this.raiz = nuevaRaiz;
        }
        insertarNoLleno(this.raiz, llave, dato);
    }

    // Inserción privada de una llave en un nodo que no está lleno.
    // Si el nodo no es una hoja, la inserción de una nueva llave puede implicar la división de uno de sus hijos.
    private void insertarNoLleno(NodoArbolBMas nodo, int llave, String dato) {
        int i = nodo.getLlaves().size() - 1;

        if(nodo.esHoja()) {
            while(i >= 0 && llave < nodo.getLlaves().get(i)) {
                i--;
            }
            // Validación simple para no repetir llaves.
            if (i >= 0 && nodo.getLlaves().get(i) == llave) {
                System.out.println("La llave " + llave + " ya existe en el árbol.");
                return;
            }

            if (i + 1 < nodo.getLlaves().size() && nodo.getLlaves().get(i + 1) == llave) {
                System.out.println("La llave " + llave + " ya existe en el árbol.");
                return;
            }
            nodo.getLlaves().add(i + 1, llave);

            // El dato se inserta en la misma posición que la llave.
            nodo.getDatos().add(i + 1, dato);
        } else {
            while(i >= 0 && llave < nodo.getLlaves().get(i)) {
                i--;
            }
            i++;

            NodoArbolBMas hijo = nodo.getHijos().get(i);
            if(hijo.getLlaves().size() == orden - 1) {
                dividirHijo(nodo, i);
                if(llave > nodo.getLlaves().get(i)) {
                    i++;
                }
            }
            insertarNoLleno(nodo.getHijos().get(i), llave, dato);
        }
    }

    // Dividir un nodo que está lleno.
    private void dividirHijo(NodoArbolBMas padre, int indice) {
        NodoArbolBMas nodoLleno = padre.getHijos().get(indice);
        NodoArbolBMas nuevoNodo = new NodoArbolBMas(nodoLleno.esHoja());
        int mitad = orden / 2;

        if (nodoLleno.esHoja()) {
            // En nodos hoja, se mantiene la llave media en ambos lados.
            nuevoNodo.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(mitad, nodoLleno.getLlaves().size())));
            nodoLleno.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(0, mitad)));

            // También se dividen los datos para que sigan coincidiendo
            // con las llaves de cada hoja.
            nuevoNodo.setDatos(new ArrayList<>(nodoLleno.getDatos().subList(mitad, nodoLleno.getDatos().size())));
            nodoLleno.setDatos(new ArrayList<>(nodoLleno.getDatos().subList(0, mitad)));

            // Se enlazan las hojas para permitir recorridos por rango.
            nuevoNodo.setSiguiente(nodoLleno.getSiguiente());
            nodoLleno.setSiguiente(nuevoNodo);

            // El padre recibe como separador la primera llave del nuevo nodo.
            int llaveMedia = nuevoNodo.getLlaves().get(0);
            padre.getLlaves().add(indice, llaveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
        else {
            // En nodos internos, la llave media sube y se elimina del hijo.
            int claveMedia = nodoLleno.getLlaves().get(mitad);
            nuevoNodo.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(mitad + 1, nodoLleno.getLlaves().size())));
            nodoLleno.setLlaves(new ArrayList<>(nodoLleno.getLlaves().subList(0, mitad)));

            // Mover los hijos del nodo dividido.
            nuevoNodo.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(mitad + 1, nodoLleno.getHijos().size())));
            nodoLleno.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(0, mitad + 1)));

            padre.getLlaves().add(indice, claveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
    }

    // Impresión del árbol como parte de la interfaz pública del árbol.
    public void imprimirArbol() {
        imprimirNodo(raiz, "", true);
    }

    // Impresión recursiva privada de los nodos a partir de uno inicial.
    private void imprimirNodo(NodoArbolBMas nodo, String indentacion, boolean esUltimo) {
        System.out.println(indentacion + "+- " + (nodo.esHoja() ? "Hoja" : "Interno") + " Nodo: " + nodo.getLlaves());
        indentacion += esUltimo ? "   " : "|  ";
        for(int i = 0; i < nodo.getHijos().size(); i++) {
            imprimirNodo(nodo.getHijos().get(i), indentacion, i == (nodo.getHijos().size() - 1));
        }
    }

    // Los métodos de búsqueda pueden retornar el dato en caso de que la búsqueda sea exitosa.
    // Búsqueda de un nodo como parte de la interfaz pública del árbol.
    public boolean buscar(int llave) {
        return buscarNodo(raiz, llave);
    }

    // Búsqueda recursiva privada de un nodo a partir de uno inicial.
    private boolean buscarNodo(NodoArbolBMas nodo, int llave) {
        int i = 0;
        while(i < nodo.getLlaves().size() && llave > nodo.getLlaves().get(i)) {
            i++;
        }
        if(i < nodo.getLlaves().size() && llave == nodo.getLlaves().get(i)) {
            return true;
        }
        if(nodo.esHoja()) {
            return false;
        } else {
            return buscarNodo(nodo.getHijos().get(i), llave);
        }
    }

    // Este metodo devuelve el dato asociado a la llave.
    public String buscarDato(int llave) {
        return buscarDatoNodo(raiz, llave);
    }

    // Búsqueda recursiva privada que retorna el dato asociado a una llave.
    // Recorre el árbol hasta llegar a una hoja y, si encuentra la llave,
    // devuelve el dato almacenado en la misma posición.
    private String buscarDatoNodo(NodoArbolBMas nodo, int llave) {
        int i = 0;

        while (i < nodo.getLlaves().size() && llave > nodo.getLlaves().get(i)) {
            i++;
        }

        if (nodo.esHoja()) {
            if (i < nodo.getLlaves().size() && llave == nodo.getLlaves().get(i)) {
                return nodo.getDatos().get(i);
            }
            return null;
        } else {
            return buscarDatoNodo(nodo.getHijos().get(i), llave);
        }
    }

    // Eliminación sencilla.

    // Baja hasta la hoja y elimina la llave si la encuentra.
    public boolean eliminar(int llave) {
        return eliminarNodo(raiz, llave);
    }

    // Eliminación recursiva privada de una llave a partir de un nodo.
    // Si la llave se encuentra en una hoja, se elimina junto con su dato.
    // Si el nodo no es hoja, la búsqueda continúa en el hijo correspondiente.
    private boolean eliminarNodo(NodoArbolBMas nodo, int llave) {
        int i = 0;

        while (i < nodo.getLlaves().size() && llave > nodo.getLlaves().get(i)) {
            i++;
        }

        if (nodo.esHoja()) {
            if (i < nodo.getLlaves().size() && llave == nodo.getLlaves().get(i)) {
                nodo.getLlaves().remove(i);

                // También se elimina el dato que está en la misma posición.
                if (nodo.getDatos() != null && i < nodo.getDatos().size()) {
                    nodo.getDatos().remove(i);
                }

                return true;
            }
            return false;
        } else {
            return eliminarNodo(nodo.getHijos().get(i), llave);
        }
    }

    // Recorrido por rango. Muestra los datos correspondientes al rango recuperado.

    // Busca una llave inicial e imprime los siguientes n elementos.
    public void recorrerRango(int llaveInicial, int n) {
        NodoArbolBMas hoja = buscarHoja(raiz, llaveInicial);

        if (hoja == null) {
            System.out.println("No se encontró una hoja válida.");
            return;
        }

        int contador = 0;
        boolean inicioEncontrado = false;

        while (hoja != null && contador < n) {
            for (int i = 0; i < hoja.getLlaves().size() && contador < n; i++) {

                // Empieza a imprimir cuando encuentra la llave inicial
                // o una mayor que ella.
                if (!inicioEncontrado) {
                    if (hoja.getLlaves().get(i) >= llaveInicial) {
                        inicioEncontrado = true;
                    } else {
                        continue;
                    }
                }

                System.out.println("Llave: " + hoja.getLlaves().get(i)
                        + " | Dato: " + hoja.getDatos().get(i));
                contador++;
            }

            // Avanza a la siguiente hoja enlazada.
            hoja = hoja.getSiguiente();
        }

        if (contador == 0) {
            System.out.println("No se encontraron elementos a partir de la llave " + llaveInicial);
        }
    }

    // Busca la hoja donde debería estar una llave.
    private NodoArbolBMas buscarHoja(NodoArbolBMas nodo, int llave) {
        if (nodo.esHoja()) {
            return nodo;
        }

        int i = 0;
        while (i < nodo.getLlaves().size() && llave >= nodo.getLlaves().get(i)) {
            i++;
        }

        return buscarHoja(nodo.getHijos().get(i), llave);
    }
}