package com.sgc;

import com.sgc.model.*;
import com.sgc.service.CemiterioService;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBA DE CRUD COMPLETO (SGC) ===");
        CemiterioService service = new CemiterioService();

        // 1. CREATE (Crear datos)
        System.out.println("\n[1] CREATE: Creando registros...");
        Proprietario prop = new Proprietario("Juan Perez", "12345678", "juan@email.com");
        service.salvar(prop);
        
        Tumulo tumulo = new Tumulo("Setor A", "Fila 1", "Nicho 10", TipoTumulo.NICHO, prop);
        service.salvar(tumulo);
        
        Falecido falecido = new Falecido("Maria Lopez", "87654321", new Date());
        service.salvar(falecido);
        System.out.println(" -> Datos creados correctamente.");

        // 2. UPDATE (Actualizar/Vincular - Regla de Negocio)
        System.out.println("\n[2] UPDATE: Vinculando Falecido a Túmulo...");
        service.vincularFalecido(falecido.getIdFalecido(), tumulo.getIdTumulo());
        System.out.println(" -> Vínculo realizado. Estado del túmulo actualizado a OCUPADO.");

        // 3. READ (Leer/Listar datos con SELECT)
        System.out.println("\n[3] READ: Consultando datos (SELECT)...");
        service.listarProprietarios();
        service.listarTumulos();
        service.listarFalecidos();

        // 4. DELETE (Borrar - Probando Regla de Negocio RN-002)
        System.out.println("\n[4] DELETE: Intentando borrar túmulo ocupado...");
        service.excluirTumulo(tumulo.getIdTumulo()); // Debería fallar o avisar que no se puede

        System.out.println("\n=== FIN DE LA PRUEBA ===");
    }
}