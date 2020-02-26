package com.kd.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
* @ClassName: SerializationUtil 
* @Description: TODO 序列化工具类
* @author Giles 
* @date 2016年7月19日 下午1:19:59 
*
 */
public class SerializationUtil {
	 /**
     * 序列化
     * 
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {

        }
        return null;
    }

}
