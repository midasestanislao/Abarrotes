/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Hibernate.HibernateUtil;
import Mapeos.Cliente;
import Mapeos.Empleado;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Search
 */
public class ClienteDAO {
    private Session sesion;
    private Transaction tx;

    public int guardarCliente(Mapeos.Cliente cliente) throws HibernateException {
        int id = -1;
        try {
            iniciaOperacion();
            id = (Integer) sesion.save(cliente);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
        return id;
    }

    public void eliminaCliente(int ID_Cliente) {
        try {
            iniciaOperacion();
            Cliente cliente = sesion.get(Cliente.class, ID_Cliente);
            if (cliente != null) {
                sesion.delete(cliente);
                tx.commit();
            }
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    public Cliente obtenCliente(int ID_Cliente) throws HibernateException {
        Cliente cliente = null;
        try {
            iniciaOperacion();
            cliente = sesion.get(Cliente.class, ID_Cliente);
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
        return cliente;
    }

    public List<Cliente> obtenListaCliente() throws HibernateException {
        List<Cliente> listaClientes = null;
        try {
            iniciaOperacion();
            Query<Cliente> query = sesion.createQuery("from Cliente", Cliente.class);
            listaClientes = query.getResultList();
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
        return listaClientes;
    }

    public int actualizaCliente(Mapeos.Cliente cliente) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.update(cliente);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
        return 0;
    }

    private void iniciaOperacion() {
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            tx = sesion.beginTransaction();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        if (tx != null) {
            tx.rollback();
        }
        throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he);
    }
}

