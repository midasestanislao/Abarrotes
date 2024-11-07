/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Hibernate_conexion_prueba {
      public static void main(String[] args) {
        // Obtener la fábrica de sesiones desde HibernateUtil
        Session session = null;
        Transaction transaction = null;

        try {
            // Intentar abrir una sesión
            session = HibernateUtil.getSessionFactory().openSession();
            System.out.println("Conexión a la base de datos exitosa.");

            // Iniciar una transacción
            transaction = session.beginTransaction();

            // Aquí puedes realizar operaciones con la base de datos, por ejemplo:
            // Empleado empleado = session.get(Empleado.class, 1);
            // System.out.println(empleado);

            // Commit de la transacción (confirmar los cambios)
            transaction.commit();
            System.out.println("Operación realizada con éxito.");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Revertir los cambios si hay algún error
            }
            System.err.println("Error en la conexión o durante la transacción.");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();  // Cerrar la sesión
                System.out.println("Sesión cerrada correctamente.");
            }
        }

        // Cerrar la fábrica de sesiones
        HibernateUtil.shutdown();
    }
}
