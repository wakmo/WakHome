/*
 * Component.java
 *
 * Created on 10 October 2001, 12:28
 */

package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyComponents;

/** Enumeration class for component.
 * @author Richard Izzard
 * @version 1.0*/
public class Component implements java.io.Serializable
{
    private static final String [] description = {"Complete",
                                                  "Component 1",
                                                  "Component 2",
                                                  "Component 3"};
    /** Complete component.
     */
    public static final Component Complete = new Component(SCIKeyComponents.COMPONENT_COMPLETE);
    /** Part one component.
     */
    public static final Component One      = new Component(SCIKeyComponents.COMPONENT_1);
    /** Part two component.
     */
    public static final Component Two      = new Component(SCIKeyComponents.COMPONENT_2);
    /** Part three component.
     */
    public static final Component Three    = new Component(SCIKeyComponents.COMPONENT_3);

    private int comp;

    /** Creates new Component */
    private Component(int comp) {
      this.comp = comp;
    }

    int value() {
      return comp;
    }

    /** Generate a string representaation of the component type.
     * @return Component string.
     */
    public String toString() {
      return description[comp];
    }

    public static Component getComponent (String component) throws SecureCryptoException
    {
      int index = -1;
      for (int f=0; f<description.length && index == -1; f++) {
        if (description[f].equals(component)) {
          index = f;
        }
      }
      return getComponent(index);
    }

    public static Component getComponent(int index) throws SecureCryptoException
    {
      Component comp = null;
      switch (index) {
        case SCIKeyComponents.COMPONENT_COMPLETE:
          comp = Complete;
        break;
        case SCIKeyComponents.COMPONENT_1:
          comp = One;
        break;
        case SCIKeyComponents.COMPONENT_2:
          comp = Two;
        break;
        case SCIKeyComponents.COMPONENT_3:
          comp = Three;
        break;
        default:
          throw new SecureCryptoException("Invalid component index specified: " + index);
      }
      return comp;
    }

    public int toInt()
    {
      return comp;
    }
}
