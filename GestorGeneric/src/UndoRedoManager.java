import java.util.Deque;
import java.util.LinkedList;

/**
 * Gestor genérico para manejar la lógica de Deshacer (Undo) y Rehacer (Redo)
 * utilizando dos pilas dinámicas.
 * Esta clase es genérica y puede usarse para el historial de navegación (con T=String)
 * o para un editor de texto (con T=Comando o T=String).
 *
 * @param <T> El tipo de elemento o comando a gestionar.
 */
public class UndoRedoManager<T> {

    // Pila para "Atrás" o "Deshacer"
    private Deque<T> undoStack = new LinkedList<>();

    // Pila para "Adelante" o "Rehacer"
    private Deque<T> redoStack = new LinkedList<>();

    /**
     * Ejecuta una nueva acción o guarda un nuevo estado (ej. visitar una URL).
     * Esto limpia la pila de rehacer (Cumple HU-03 y HU-06).
     *
     * @param action El elemento o acción a registrar en la pila 'undo'.
     */
    public void perform(T action) {
        undoStack.push(action);
        // Al realizar una nueva acción, el historial "adelante" se invalida.
        if (!redoStack.isEmpty()) {
            redoStack.clear(); // Cumple HU-03 y HU-06
            System.out.println("INFO: Pila 'redo' limpiada.");
        }
        System.out.println("Acción realizada: " + action);
    }

    /**
     * Deshace la última acción (Atrás / Ctrl+Z).
     * Mueve la acción de la pila 'undo' a la pila 'redo'.
     *
     * @return El elemento/acción que se deshizo.
     * @throws IllegalStateException si no hay nada que deshacer (Manejo de bordes).
     */
    public T undo() {
        if (!canUndo()) {
            // Manejo de bordes (Rubro 40%)
            throw new IllegalStateException("No se puede deshacer. Pila 'undo' vacía.");
        }
        T undoneAction = undoStack.pop(); // Saca de 'undo' (HU-01, HU-04)
        redoStack.push(undoneAction);     // Pone en 'redo'
        System.out.println("Deshaciendo: " + undoneAction);
        return undoneAction; // Devuelve la acción deshecha
    }

    /**
     * Rehace la última acción deshecha (Adelante / Ctrl+Y).
     * Mueve la acción de la pila 'redo' a la pila 'undo'.
     *
     * @return El elemento/acción que se rehízo.
     * @throws IllegalStateException si no hay nada que rehacer (Manejo de bordes).
     */
    public T redo() {
        if (!canRedo()) {
            // Manejo de bordes (Rubro 40%)
            throw new IllegalStateException("No se puede rehacer. Pila 'redo' vacía.");
        }
        T redoneAction = redoStack.pop(); // Saca de 'redo' (HU-02, HU-05)
        undoStack.push(redoneAction);     // Pone en 'undo'
        System.out.println("Rehaciendo: " + redoneAction);
        return redoneAction; // Devuelve la acción rehecha
    }

    /**
     * Verifica si es posible deshacer una acción.
     *
     * @return true si la pila 'undo' no está vacía.
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * Verifica si es posible rehacer una acción.
     *
     * @return true si la pila 'redo' no está vacía.
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    /**
     * Limpia ambas pilas.
     */
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}