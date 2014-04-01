package adhoc_app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Utilities {
        public static byte[] serializa(Object o) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
	ObjectOutputStream oos = new ObjectOutputStream(baos);
	oos.writeObject(o);

	return baos.toByteArray();
    }
    
    public static Object desSerializa(byte[] ab) throws IOException, ClassNotFoundException{
	ByteArrayInputStream bais = new ByteArrayInputStream(ab);

	ObjectInputStream ois = new ObjectInputStream(bais);

	return ois.readObject();
    }
}
