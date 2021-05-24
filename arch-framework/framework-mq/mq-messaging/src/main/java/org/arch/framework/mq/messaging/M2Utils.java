package org.arch.framework.mq.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class M2Utils {
    private static final Logger log = LoggerFactory.getLogger(M2Utils.class);

    public M2Utils() {
    }

    public static MqMessage unserialize(byte[] bytes) {
        if (bytes != null && bytes.length != 0) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                return (MqMessage) ois.readObject();
            } catch (Exception var2) {
                log.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static byte[] serialize(Object object) {
        if (object == null) {
            throw new NullPointerException("cannot serialize null object");
        } else {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Throwable var2 = null;

                Object var5;
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    Throwable var4 = null;

                    try {
                        oos.writeObject(object);
                        var5 = baos.toByteArray();
                    } catch (Throwable var30) {
                        var5 = var30;
                        var4 = var30;
                        throw var30;
                    } finally {
                        if (oos != null) {
                            if (var4 != null) {
                                try {
                                    oos.close();
                                } catch (Throwable var29) {
                                    var4.addSuppressed(var29);
                                }
                            } else {
                                oos.close();
                            }
                        }

                    }
                } catch (Throwable var32) {
                    var2 = var32;
                    throw var32;
                } finally {
                    if (baos != null) {
                        if (var2 != null) {
                            try {
                                baos.close();
                            } catch (Throwable var28) {
                                var2.addSuppressed(var28);
                            }
                        } else {
                            baos.close();
                        }
                    }

                }

                return (byte[]) var5;
            } catch (Exception var34) {
                throw new RuntimeException(var34.getMessage(), var34);
            }
        }
    }
}
