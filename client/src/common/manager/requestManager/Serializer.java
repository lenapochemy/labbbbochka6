package common.manager.requestManager;

import common.commands.*;
import common.data.FormOfEducation;
import common.data.StudyGroup;
import common.manager.requestManager.Parser;
import common.manager.requestManager.Response;

import java.io.*;
import java.nio.ByteBuffer;

public class Serializer {


    public static Object deserialize(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object o = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return o;
    }

    public static ByteBuffer serialize(Object o) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(o);
        objectOutputStream.flush();
        objectOutputStream.close();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer;
    }


}

