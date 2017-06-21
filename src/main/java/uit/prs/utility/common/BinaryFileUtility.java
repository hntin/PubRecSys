/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.prs.utility.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author THNghiep
 */
public class BinaryFileUtility {

    // Prevent instantiation.
    private BinaryFileUtility() {}

    /**
     * Serialize.
     */
    public static void saveObjectToFile(Object o, String fileName) throws Exception {
        try (
                FileOutputStream fileOut = new FileOutputStream(fileName); 
                ObjectOutputStream out = new ObjectOutputStream(fileOut)
                ) {
            out.writeObject(o);
        }
    }
    
    /**
     * Deserialize.
     */
    public static Object loadObjectFromFile(String fileName) throws Exception {
        Object o = null;
        try (
                FileInputStream fileIn = new FileInputStream(fileName); 
                ObjectInputStream in = new ObjectInputStream(fileIn)
                ) {
            o = in.readObject();
        }
        return o;
    }
}
