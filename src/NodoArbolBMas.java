import java.util.ArrayList;
import java.util.List;

public class NodoArbolBMas {

    // Atributos.
    private boolean esHoja; // Indica si el nodo es una hoja o un nodo interno.
    private List<Integer> llaves; // Lista de llaves del nodo.
    private List<NodoArbolBMas> hijos; // Vacía si es una hoja.

    // Lista de datos asociados a las llaves.
    // Solo se usa en nodos hoja.
    // En nodos internos se deja en null.
    private List<String> datos;

    // Referencia a la siguiente hoja del árbol.
    // Solo se usa en hojas.
    // En nodos internos se deja en null.
    private NodoArbolBMas siguiente;


    // Métodos.
    // Constructor.
    public NodoArbolBMas(boolean esHoja) {
        this.esHoja = esHoja;
        this.llaves = new ArrayList<>();
        this.hijos = new ArrayList<>();

        // Si es hoja, sí necesita almacenar datos.
        if (esHoja) {
            this.datos = new ArrayList<>();
            this.siguiente = null;
        } else {
            // Si es nodo interno, no guarda datos ni apunta a otra hoja.
            this.datos = null;
            this.siguiente = null;
        }
    }

    // Getters.
    public boolean esHoja() {
        return esHoja;
    }

    public List<Integer> getLlaves() {
        return llaves;
    }

    public List<NodoArbolBMas> getHijos() {
        return hijos;
    }

    public List<String> getDatos() { return datos; }

    public NodoArbolBMas getSiguiente() { return siguiente; }

    // Setters.
    public void setEsHoja(boolean esHoja) { this.esHoja = esHoja; }

    public void setLlaves(List<Integer> llaves) {
        this.llaves = llaves;
    }

    public void setHijos(List<NodoArbolBMas> hijos) {
        this.hijos = hijos;
    }

    public void setDatos(List<String> datos) { this.datos = datos; }

    public void setSiguiente(NodoArbolBMas siguiente) { this.siguiente = siguiente; }
}