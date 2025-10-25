public class EjemploUsoPilas {

    public static void main(String[] args) {

        System.out.println("--- EJEMPLO 1: Historial de Navegación (HU-01, HU-02, HU-03) ---");
        // Usamos la clase genérica con <String> para las URLs
        UndoRedoManager<String> historial = new UndoRedoManager<>();

        // El usuario navega
        historial.perform("google.com");
        historial.perform("github.com");
        historial.perform("stackoverflow.com");
        System.out.println("¿Puede ir 'Atrás'? " + historial.canUndo()); // true
        System.out.println("¿Puede ir 'Adelante'? " + historial.canRedo()); // false

        try {
            // --- Prueba HU-01: Regresar con Atrás ---
            System.out.println("\nProbando HU-01 (Atrás):");
            historial.undo(); // Deshace "stackoverflow.com". Ahora estamos en "github.com"
            System.out.println("¿Puede ir 'Adelante'? " + historial.canRedo()); // true

            // --- Prueba HU-02: Avanzar con Adelante ---
            System.out.println("\nProbando HU-02 (Adelante):");
            historial.redo(); // Rehace "stackoverflow.com". Ahora estamos en "stackoverflow.com"
            System.out.println("¿Puede ir 'Adelante'? " + historial.canRedo()); // false

            // Volvemos a ir para atrás para probar HU-03
            historial.undo(); // De nuevo en "github.com"
            System.out.println("\nPreparando para HU-03. Pila 'redo' NO está vacía.");

            // --- Prueba HU-03: Visita URL nueva tras retroceder ---
            System.out.println("Probando HU-03 (Nueva URL limpia 'Adelante'):");
            historial.perform("openai.com"); // Esto debe limpiar la pila 'redo'

            // Verificación HU-03:
            System.out.println("¿Puede ir 'Adelante'? " + historial.canRedo()); // Debe ser false

        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n\n--- EJEMPLO 2: Editor de Texto (HU-04, HU-05, HU-06) ---");
        // Usamos la MISMA clase genérica, ahora para "Comandos" de texto
        UndoRedoManager<String> editor = new UndoRedoManager<>();

        // El usuario edita
        editor.perform("Escribir 'Hola'");
        editor.perform("Escribir 'Mundo'");

        try {
            // --- Prueba HU-04: Revertir con Deshacer (Ctrl+Z) ---
            System.out.println("\nProbando HU-04 (Deshacer):");
            editor.undo(); // Deshace "Escribir 'Mundo'"
            System.out.println("¿Se puede 'Rehacer'? " + editor.canRedo()); // true

            // --- Prueba HU-05: Recuperar con Rehacer (Ctrl+Y) ---
            System.out.println("\nProbando HU-05 (Rehacer):");
            editor.redo(); // Rehace "Escribir 'Mundo'"
            System.out.println("¿Se puede 'Rehacer'? " + editor.canRedo()); // false

            // Preparamos para HU-06
            editor.undo(); // Deshace "Escribir 'Mundo'"
            System.out.println("\nPreparando para HU-06. Pila 'redo' NO está vacía.");

            // --- Prueba HU-06: Acción nueva después de deshacer ---
            System.out.println("Probando HU-06 (Nueva acción limpia 'Rehacer'):");
            editor.perform("Borrar todo"); // Esto debe limpiar la pila 'redo'

            // Verificación HU-06:
            System.out.println("¿Se puede 'Rehacer'? " + editor.canRedo()); // Debe ser false

            // --- Prueba de Manejo de Bordes (Rubro 40%) ---
            System.out.println("\nProbando Manejo de Bordes (Vaciar pilas):");
            editor.undo(); // Deshace "Borrar todo"
            editor.undo(); // Deshace "Escribir 'Hola'"
            System.out.println("¿Se puede 'Deshacer'? " + editor.canUndo()); // false

            // Intentar deshacer en pila vacía
            System.out.println("Intentando 'Deshacer' en pila vacía:");
            editor.undo(); // Esto debe lanzar una excepción

        } catch (IllegalStateException e) {
            System.err.println("ÉXITO (Manejo de bordes): " + e.getMessage());
        }
    }
}