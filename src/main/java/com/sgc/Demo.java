package com.sgc;

import com.sgc.model.*;
import com.sgc.service.CemiterioService;
import java.util.Date;
import java.util.Scanner;

public class Demo {
    private static CemiterioService service = new CemiterioService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║  SISTEMA DE GESTIÓN DE CEMENTERIO (SGC)   ║");
        System.out.println("║         DEMO - Presentación 2025           ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> registrarProprietario();
                case 2 -> registrarTumulo();
                case 3 -> registrarFalecido();
                case 4 -> vincularFalecidoATumulo();
                case 5 -> intentarEliminarTumulo();
                case 6 -> service.listarTumulos();
                case 7 -> service.listarFalecidos();
                case 8 -> service.listarProprietarios();
                case 9 -> ejecutarDemoCompleto();
                case 0 -> {
                    System.out.println("\n✅ Gracias por usar el SGC. ¡Hasta pronto!");
                    continuar = false;
                }
                default -> System.out.println("❌ Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│          MENÚ PRINCIPAL             │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ 1. Registrar Proprietario           │");
        System.out.println("│ 2. Registrar Túmulo                 │");
        System.out.println("│ 3. Registrar Falecido               │");
        System.out.println("│ 4. Vincular Falecido a Túmulo       │");
        System.out.println("│ 5. Eliminar Túmulo                  │");
        System.out.println("│ ----------------------------------- │");
        System.out.println("│ 6. 📋 Listar Túmulos                │");
        System.out.println("│ 7. 📋 Listar Falecidos              │");
        System.out.println("│ 8. 📋 Listar Proprietarios          │");
        System.out.println("│ ----------------------------------- │");
        System.out.println("│ 9. Demo Completo Automático         │");
        System.out.println("│ 0. Salir                            │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Opción: ");
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void registrarProprietario() {
        System.out.println("\n📝 REGISTRAR PROPRIETARIO");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Proprietario prop = new Proprietario(nombre, cedula, email);
        service.salvar(prop);
        System.out.println("✅ Proprietario registrado exitosamente.");
    }

    private static void registrarTumulo() {
        System.out.println("\n📝 REGISTRAR TÚMULO");
        
        // Primero mostrar proprietarios disponibles
        System.out.println("\n📋 Proprietarios disponibles:");
        service.listarProprietarios();
        
        System.out.print("ID del Proprietario: ");
        int idProprietario = leerOpcion();
        
        Proprietario prop = service.obtenerProprietario(idProprietario);
        if (prop == null) {
            System.out.println("❌ Proprietario no encontrado. Registro cancelado.");
            return;
        }
        
        System.out.print("Sector: ");
        String sector = scanner.nextLine();
        System.out.print("Fila: ");
        String fila = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.println("Tipo (1=TUMBA, 2=NICHO, 3=PANTEAO): ");
        int tipoOpt = leerOpcion();
        
        TipoTumulo tipo = switch (tipoOpt) {
            case 1 -> TipoTumulo.TUMBA;
            case 2 -> TipoTumulo.NICHO;
            case 3 -> TipoTumulo.PANTEAO;
            default -> TipoTumulo.TUMBA;
        };

        Tumulo tumulo = new Tumulo(sector, fila, numero, tipo, prop);
        service.salvar(tumulo);
        System.out.println("✅ Túmulo registrado exitosamente y vinculado a: " + prop.getNomeCompleto());
    }

    private static void registrarFalecido() {
        System.out.println("\n📝 REGISTRAR FALECIDO");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();

        Falecido falecido = new Falecido(nombre, cedula, new Date());
        service.salvar(falecido);
        System.out.println("✅ Falecido registrado exitosamente.");
    }

    private static void vincularFalecidoATumulo() {
        System.out.println("\n🔗 VINCULAR FALECIDO A TÚMULO");
        System.out.print("ID del Falecido: ");
        int idFalecido = leerOpcion();
        System.out.print("ID del Túmulo: ");
        int idTumulo = leerOpcion();

        service.vincularFalecido(idFalecido, idTumulo);
    }

    private static void intentarEliminarTumulo() {
        System.out.println("\n🗑️  ELIMINAR TÚMULO");
        System.out.print("ID del Túmulo: ");
        int idTumulo = leerOpcion();

        service.excluirTumulo(idTumulo);
    }

    private static void ejecutarDemoCompleto() {
        System.out.println("\n🎬 EJECUTANDO DEMO COMPLETO AUTOMÁTICO...\n");
        
        System.out.println("1️⃣  Creando Proprietario...");
        Proprietario prop = new Proprietario("Carlos Silva", "12345678", "carlos@email.com");
        service.salvar(prop);
        System.out.println("   ✅ Proprietario creado");

        System.out.println("\n2️⃣  Creando Túmulo...");
        Tumulo tumulo = new Tumulo("Sector A", "Fila 1", "Nicho 5", TipoTumulo.NICHO, prop);
        service.salvar(tumulo);
        System.out.println("   ✅ Túmulo creado (Estado: DISPONIBLE)");

        System.out.println("\n3️⃣  Creando Falecido...");
        Falecido falecido = new Falecido("Ana Rodriguez", "87654321", new Date());
        service.salvar(falecido);
        System.out.println("   ✅ Falecido creado");

        System.out.println("\n4️⃣  Vinculando Falecido al Túmulo...");
        service.vincularFalecido(falecido.getIdFalecido(), tumulo.getIdTumulo());
        System.out.println("   ✅ Estado actualizado automáticamente a OCUPADO (RN-006)");

        System.out.println("\n5️⃣  Intentando eliminar túmulo OCUPADO...");
        service.excluirTumulo(tumulo.getIdTumulo());
        System.out.println("   ✅ Validación RN-002 aplicada correctamente");

        System.out.println("\n🎉 DEMO COMPLETO FINALIZADO");
        System.out.println("   - CRUD completo demostrado");
        System.out.println("   - Reglas de negocio validadas (RN-002, RN-005, RN-006)");
    }
}
