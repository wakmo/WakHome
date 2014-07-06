/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.persistence;

import java.util.*;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.queries.*;

/**
 *
 * @author thushara.pethiyagoda
 */
public interface Persistent
{
    public <T>void doTransactionalWorkAndCommit(T arg);
    public <T>void delete(T obj);
    public void deleteAll(Collection obj);    
    public <T>void save(T obj);
    public <T>T getObjectById(long id, Class<T> cls, String idField);
    public <T> T getObjectByReferencingObject(Object obj, Class<T> cls, String objField);
    public <T> Vector getAllObjectsByReferencingObject(Object obj, Class<T> cls, String objField);
    public <T> Vector getObjectByName(String value, Class<T> cls, String idField);
    public boolean executeUpdateQuery(String sql);
    public Vector executeSelectQuery(String sql);
    public void emptyTable(String table);
    public Vector readTable(Class cls);
    public Vector executeReadQuery(Expression selectionCriteria, Class cls, Expression ordering, String... partialAttributes);
    public Vector executeQuery(ReadAllQuery query);
    public Object executeReportQuery(ReportQuery query);
    public <R> R getRegisteredObject(Class<R> cls);
    public void addTransactionalWorker(Workable workable);
    public <R> Vector getRegisteredAllObjects(Collection<R> cls);
    public Object getRegisteredExistingObject(Object cls);
}
