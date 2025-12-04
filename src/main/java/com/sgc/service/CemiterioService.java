package com.sgc.service;

import com.sgc.model.*;
import com.sgc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CemiterioService {

    // Método para guardar datos genéricos (Create)
    public void salvar(Object objeto) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(objeto);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // Lógica principal: Vincular Falecido a Túmulo (Update con reglas)
    public void vincularFalecido(int idFalecido, int idTumulo) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Falecido falecido = session.get(Falecido.class, idFalecido);
            Tumulo tumulo = session.get(Tumulo.class, idTumulo);

            if (falecido != null && tumulo != null) {
                // RN-005: Validar estado
                if (tumulo.getEstado() == EstadoTumulo.OCUPADO) {
                    System.out.println("ERROR: El túmulo ya está ocupado.");
                    return;
                }

                // Realizar vínculo
                falecido.setTumulo(tumulo);
                tumulo.setFalecido(falecido);

                // RN-006: Actualizar estado automáticamente
                tumulo.setEstado(EstadoTumulo.OCUPADO);

                session.merge(falecido);
                session.merge(tumulo);
                System.out.println("VÍNCULO EXITOSO: Falecido asignado y túmulo marcado como OCUPADO.");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // READ: Listar todos los Túmulos
    public void listarTumulos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            var tumulos = session.createQuery("FROM Tumulo", Tumulo.class).list();
            
            if (tumulos.isEmpty()) {
                System.out.println("No hay túmulos registrados.");
            } else {
                System.out.println("\n═══════════════════════════════════════════════════════");
                System.out.println("                  LISTA DE TÚMULOS");
                System.out.println("═══════════════════════════════════════════════════════");
                for (Tumulo t : tumulos) {
                    System.out.printf("ID: %d | %s-%s-%s | Tipo: %s | Estado: %s | Proprietario: %s%n",
                        t.getIdTumulo(), t.getSetor(), t.getFila(), t.getNumero(),
                        t.getTipo(), t.getEstado(), t.getProprietario().getNomeCompleto());
                }
                System.out.println("═══════════════════════════════════════════════════════\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ: Listar todos los Falecidos
    public void listarFalecidos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            var falecidos = session.createQuery("FROM Falecido", Falecido.class).list();
            
            if (falecidos.isEmpty()) {
                System.out.println("No hay falecidos registrados.");
            } else {
                System.out.println("\n═══════════════════════════════════════════════════════");
                System.out.println("                 LISTA DE FALECIDOS");
                System.out.println("═══════════════════════════════════════════════════════");
                for (Falecido f : falecidos) {
                    String tumulo = f.getTumulo() != null ? 
                        "Túmulo ID: " + f.getTumulo().getIdTumulo() : "Sin asignar";
                    System.out.printf("ID: %d | %s | Cédula: %s | %s%n",
                        f.getIdFalecido(), f.getNomeCompleto(), f.getCedula(), tumulo);
                }
                System.out.println("═══════════════════════════════════════════════════════\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ: Listar todos los Proprietarios
    public void listarProprietarios() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            var proprietarios = session.createQuery("FROM Proprietario", Proprietario.class).list();
            
            if (proprietarios.isEmpty()) {
                System.out.println("No hay proprietarios registrados.");
            } else {
                System.out.println("\n═══════════════════════════════════════════════════════");
                System.out.println("               LISTA DE PROPRIETARIOS");
                System.out.println("═══════════════════════════════════════════════════════");
                for (Proprietario p : proprietarios) {
                    System.out.printf("ID: %d | %s | Cédula: %s | Email: %s%n",
                        p.getIdProprietario(), p.getNomeCompleto(), p.getCedula(), p.getEmail());
                }
                System.out.println("═══════════════════════════════════════════════════════\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ: Obtener Proprietario por ID
    public Proprietario obtenerProprietario(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Proprietario.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método que faltaba: Excluir Túmulo (Delete con reglas)
    // Implementa RN-002: Una parcela con estado Ocupado no puede ser excluida
    public void excluirTumulo(int idTumulo) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Tumulo t = session.get(Tumulo.class, idTumulo);
            
            if (t != null) {
                // Validación RN-002
                if (t.getEstado() == EstadoTumulo.OCUPADO) {
                    System.out.println("⚠️ ACCIÓN DENEGADA (RN-002): No se puede borrar una parcela OCUPADA.");
                } else {
                    session.remove(t);
                    System.out.println("✅ Túmulo eliminado correctamente.");
                }
            } else {
                System.out.println("El túmulo no existe.");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}