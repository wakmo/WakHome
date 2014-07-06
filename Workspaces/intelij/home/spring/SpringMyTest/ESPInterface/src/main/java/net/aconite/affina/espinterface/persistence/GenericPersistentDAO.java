/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.persistence;

import com.platform7.standardinfrastructure.database.*;
import java.util.*;
import net.aconite.affina.espinterface.constants.*;
import net.aconite.affina.espinterface.factory.*;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.eclipse.persistence.queries.ReportQuery;
import org.eclipse.persistence.sessions.UnitOfWork;

/**
 * NOTE: This class is Spring framework dependent in obtaining data sources.
 * <p/>
 * This class has two constructors one that uses a default class with id sessionManager_pma with a database connection.
 * <p/>
 * The second constructor allows the caller to specify its own db related details.
 * <p/>
 * <p/>
 * This is a generic Data Access Object class for convenience that encapsulates lot of the work that would otherwise
 * have to be repeated.
 * <p/>
 * Also this class provides a method (doTransactionAndCommit()) to do work in single transaction while managing its
 * Units Of Work in doTransactionAndCommit()
 * <p/>
 * Further a method (getRegisteredObject())is introduced so that caller can create and retrieve an object which would be
 * registered with the persistence cache at the same time.
 * <p/>
 * @author thushara.pethiyagoda
 */
public class GenericPersistentDAO implements Persistent
{

    private Workable workable;    
    /**
     *
     */
    private final AffinaTOPLinkSessionManager sm;
    /**
     *
     */
    private boolean inTrans = false;
    /**
     *
     */
    private UnitOfWork transUOW;
    
    private final static Map<String, String> dbConnectionTypeMap = new HashMap<String, String>();
    
    static
    {
        dbConnectionTypeMap.put("JNDI", "sessionManager_pma");
        dbConnectionTypeMap.put("NONE_JNDI", "local_sessionManager_pma");
    }
    /**
     * Default connection to PMA that uses JNDI based connection.
     */
    protected GenericPersistentDAO()
    {      
        this(dbConnectionTypeMap.get("sessionManager_pma"));              
    }
    
    /**
     * 
     */
    protected GenericPersistentDAO(DBConnectionType dnConType)
    {      
        this(dbConnectionTypeMap.get(dnConType.name()));              
    }

    /**
     * Constructs a DAO using pre configured bean with a database connection related properties. To use this constructor
     * a Spring Bean with id 'sessionManager_pma' that has a DB connection should exists.
     * <p/>
     * @param dbContext Application context obtained via spring.
     */
    protected GenericPersistentDAO(String dbManagerBeanName)
    {        
        sm = PersistentContextFactory.getSessionManager(dbManagerBeanName);
    }    
    /**
     * 
     * @return 
     */
    public static Persistent getPersistent()
    {
        String conType = System.getProperty("DBConnectionType", "JNDI");        
        return new GenericPersistentDAO(DBConnectionType.valueOf(conType));
    }    

    /**
     * Implement this method if a set of operations is required to be executed a within a transaction via the
     * implementation class.
     * <p/>
     * The by calling doTransactionAndCommit() above will do the work for you.
     */
    private <T> void doTransactionalWork(T arg)
    {
        doWork(arg);
    }
    
    /**
     * Will perform the transactional work as defined by the implementation class.
     */
    private <T> void doWork(T arg)
    {        
        workable.doWork(arg);
    }
    /**
     * 
     * @param workable 
     */
    @Override
    public void addTransactionalWorker(Workable workable)
    {
        this.workable = workable;
    }    

    /**
     * Performs Transactional work.
     */
    @Override
    public synchronized <T> void doTransactionalWorkAndCommit(T arg)
    {
        try
        {
            transUOW = getUnitOfWork();
            inTrans = true;
            sm.getSession().beginTransaction();
            doTransactionalWork(arg);            
            sm.getSession().commitTransaction();
            transUOW.commit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            sm.release();
        }
        finally
        {
            inTrans = false;
        }
    }

    /**
     *
     * @param <T>
     * @param obj
     */
    @Override
    public synchronized <T> void delete(T obj)
    {
        try
        {
            UnitOfWork uow = getUnitOfWork();
            uow.registerObject(obj);
            uow.deleteObject(obj);
            if (!inTrans)
            {
                uow.commit();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param <T>
     * @param obj
     */
    @Override
    public synchronized void deleteAll(Collection obj)
    {
        UnitOfWork uow = getUnitOfWork();
        uow.registerAllObjects(obj);
        uow.deleteAllObjects(obj);
        if (!inTrans)
        {
            uow.commit();
        }
    }

    /**
     *
     * @param <T>
     * @param obj
     */
    @Override
    public synchronized <T> void save(T obj)
    {
        UnitOfWork uow = getUnitOfWork();
        uow.registerObject(obj);
        if (!inTrans)
        {
            uow.commit();
        }
    }

    /**
     *
     * @return
     */
    private UnitOfWork getUnitOfWork()
    {
        if (inTrans)
        {
            return transUOW;
        }
        return sm.getSession().acquireUnitOfWork();
    }

    /**
     * This will create a new object and registers it with the current persistence manager cache so that the persistent
     * manager knows that it is something it should take care of when it comes to executing CRUD commands.
     * <p/>
     * @param <R> The generic type of the object represented by the passed in class.
     * @param cls the Class of the object being passed.
     * <p/>
     * @return Returns the object after registering with the persistence cache.
     */
    @Override
    public <R> R getRegisteredObject(Class<R> cls)
    {
        Object v = null;
        try
        {
            v = cls.newInstance();
            v = getUnitOfWork().registerNewObject(v);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return cls.cast(v);
    }
    /**
     * 
     * @param <R>
     * @param cls
     * @return 
     */
    @Override
    public <R> Vector getRegisteredAllObjects(Collection<R> cls)
    {
        return getUnitOfWork().registerAllObjects(cls);
    }
    /**
     * 
     * @param cls
     * @return 
     */
    @Override
    public Object getRegisteredExistingObject(Object cls)
    {
        Object v = null;
        try
        {            
            v = getUnitOfWork().registerExistingObject(cls);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return v;
    }
    /**
     * Retrieves a DB object that matches the given ID.
     * <p/>
     * @param <T>     Generic type of the object.
     * @param id      ID of the database object.
     * @param cls
     * @param idField The name of the field that represents the ID. This is taken as a parameter as there may be no
     *                consistent naming mechanism for representing the id, so the caller can pass in the correct one.
     * <p/>
     * @return The object that matches th ID.
     */
    @Override
    public <T> T getObjectById(long id, Class<T> cls, String idField)
    {
        Object retValue = null;
        try
        {
            ExpressionBuilder builder = new ExpressionBuilder();
            Expression exp = builder.get(idField).equal(id);
            retValue = getUnitOfWork().readObject(cls, exp);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return cls.cast(retValue);
    }
    /**
     * Returns an object by a foreign key object.
     * @param <T> Generic type parameter.
     * @param obj Foreign key object value.
     * @param cls Class of the object to be fetched.
     * @param objField The foreign key as defined in the object to be fetched.
     * @return The object defined by the cls parameter..
     */
    @Override
    public <T> T getObjectByReferencingObject(Object obj, Class<T> cls, String objField)
    {
        Object retValue = null;
        try
        {
            ExpressionBuilder builder = new ExpressionBuilder();
            Expression exp = builder.get(objField).equal(obj);
            retValue = getUnitOfWork().readObject(cls, exp);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return cls.cast(retValue);
    }
     /**
     * Returns an object by a foreign key object.
     * @param <T> Generic type parameter.
     * @param obj Foreign key object value.
     * @param cls Class of the object to be fetched.
     * @param objField The foreign key as defined in the object to be fetched.
     * @return The object defined by the cls parameter..
     */
    @Override
    public <T> Vector getAllObjectsByReferencingObject(Object obj, Class<T> cls, String objField)
    {
        Vector retValue = null;
        try
        {
            ExpressionBuilder builder = new ExpressionBuilder();
            Expression exp = builder.get(objField).equal(obj);
            retValue = getUnitOfWork().readAllObjects(cls, exp);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return retValue;
    } 
    /**
     * Fetches an object from DB by its name.
     * <p/>
     * @param <T>
     * @param name    Name of the item to bee looked up.
     * @param cls     Class of the object.
     * @param idField The field name of the column to be looked up, as mapped within the domain object
     * <p/>
     * @return
     */
    @Override
    public <T> Vector getObjectByName(String value, Class<T> cls, String nameField)
    {
        Vector retValue = new Vector();
        try
        {
            ExpressionBuilder builder = new ExpressionBuilder();
            Expression exp = builder.get(nameField).equal(value);
            retValue = getUnitOfWork().readAllObjects(cls, exp);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
        }
        return retValue;
    }

    /**
     * Executes update, delete queries.
     * <p/>
     * @param sql
     * <p/>
     * @return
     */
    @Override
    public boolean executeUpdateQuery(String sql)
    {
        try
        {
            UnitOfWork uow = getUnitOfWork();
            uow.executeNonSelectingSQL(sql);
            if (!inTrans)
            {
                uow.commit();
            }
        }
        catch (Exception ex)
        {
            return false;
        }
        return true;
    }

    /**
     * Executes a results bearing select query.
     * <p/>
     * @param sql
     * <p/>
     * @return
     */
    @Override
    public Vector executeSelectQuery(String sql)
    {
        Vector res = new Vector();
        try
        {
            UnitOfWork uow = getUnitOfWork();
            res = uow.executeSQL(sql);
        }
        catch (Exception ex)
        {
            res = null;
        }
        return res;
    }

    /**
     * removes all rows from the given table.
     * <p/>
     * @param table
     */
    @Override
    public void emptyTable(String table)
    {
        String sql = "delete from " + table;
        executeUpdateQuery(sql);
    }

    /**
     * Reads the whole table represented by the parameter cls
     * <p/>
     * @param cls
     * <p/>
     * @return
     */
    @Override
    public Vector readTable(Class cls)
    {
        UnitOfWork uow = getUnitOfWork();
        return uow.readAllObjects(cls);
    }
    /**
     * 
     * @param selectionCriteria
     * @param cls
     * @param ordering
     * @param partialAttributes
     * @return 
     */
    @Override
    public Vector executeReadQuery(Expression selectionCriteria, Class cls, Expression ordering, String... partialAttributes)
    {
        ReadAllQuery q = new ReadAllQuery();
        q.setReferenceClass(cls);
        q.setSelectionCriteria(selectionCriteria);
        if(partialAttributes != null)
        {
            for(String attr : partialAttributes)
            {
                q.addPartialAttribute(attr);
            }
        }
        if(ordering != null)
        {
            q.addOrdering(ordering);
        }
        return (Vector) getUnitOfWork().executeQuery(q);
    }
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public Vector executeQuery(ReadAllQuery query)
    {        
        return (Vector) getUnitOfWork().executeQuery(query);
    }
    /**
     * 
     * @param query
     * @return 
     */
    @Override
    public Object executeReportQuery(ReportQuery query)
    {        
        return (Vector) getUnitOfWork().executeQuery(query);
    }
    
//        ExpressionBuilder builder = new ExpressionBuilder();
//        ReadAllQuery icquery = new ReadAllQuery();
//        Expression typeTest1 = null;
//        typeTest1 = builder.get("consignmentdata").get("processing_start_time").between(start, stop);
//
//        icquery.setSelectionCriteria(typeTest1);
//        icquery.addPartialAttribute("completed");
//        icquery.addPartialAttribute("primaryKey");
//        icquery.addPartialAttribute("sourceFilename");
//        icquery.addPartialAttribute("terminalError");
//        icquery.addPartialAttribute("consignmentdata");
//        icquery.addPartialAttribute("providedRequestsCount");
//        icquery.addPartialAttribute("context");
//        icquery.addPartialAttribute("workflowContext");
//        icquery.addOrdering(builder.get("consignmentdata").get("processing_start_time").ascending());
//
//        icquery.dontMaintainCache();
}
