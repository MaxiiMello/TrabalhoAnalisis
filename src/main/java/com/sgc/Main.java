package com.sgc;

import java.util.Date;

import com.sgc.model.Falecido;
import com.sgc.model.Proprietario;
import com.sgc.model.TipoTumulo;
import com.sgc.model.Tumulo;
import com.sgc.service.CemiterioService;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBA DE CRUD COMPLETO (SGC) ===");
        CemiterioService service = new CemiterioService();

        System.out.println("\n[1] CREATE: Creando registros...");
        Proprietario prop = new Proprietario("Juan Perez", "12345678", "juan@email.com");
        service.salvar(prop);
        
        Tumulo tumulo = new Tumulo("Setor A", "Fila 1", "Nicho 10", TipoTumulo.NICHO, prop);
        service.salvar(tumulo);
        
        Falecido falecido = new Falecido("Maria Lopez", "87654321", new Date());
        service.salvar(falecido);
        System.out.println(" -> Datos creados correctamente.");

        System.out.println("\n[2] UPDATE: Vinculando Falecido a Túmulo...");
        service.vincularFalecido(falecido.getIdFalecido(), tumulo.getIdTumulo());
        System.out.println(" -> Vínculo realizado. Estado del túmulo actualizado a OCUPADO.");

        System.out.println("\n[3] READ: Consultando datos (SELECT)...");
        service.listarProprietarios();
        service.listarTumulos();
        service.listarFalecidos();

        System.out.println("\n[4] DELETE: Intentando borrar túmulo ocupado...");
        service.excluirTumulo(tumulo.getIdTumulo());

        System.out.println("\n=== FIN DE LA PRUEBA ===");
    }
}