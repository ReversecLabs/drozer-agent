package com.reversec.jsolar.api.builders;

import com.reversec.jsolar.api.Protobuf.Message;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReflectionResponseFactoryTest {
    @Test
    public void serializesCharacterValuesInTheCharField() {
        Message.Argument result = ReflectionResponseFactory.primitive('\u03a9')
                .setSessionId("test").build().getResult();
        assertEquals(Message.Primitive.PrimitiveType.CHAR, result.getPrimitive().getType());
        assertEquals('\u03a9', result.getPrimitive().getChar());
    }

    @Test
    public void serializesCharacterArraysIncludingAnEmptyArray() {
        Message.Array result = ReflectionResponseFactory.primitiveArray(new char[] {'A', '\uffff'})
                .setSessionId("test").build().getResult().getArray();
        assertEquals(2, result.getElementCount());
        assertEquals('A', result.getElement(0).getPrimitive().getChar());
        assertEquals('\uffff', result.getElement(1).getPrimitive().getChar());
        assertEquals(0, ReflectionResponseFactory.primitiveArray(new char[0])
                .setSessionId("test").build().getResult().getArray().getElementCount());
    }
}
