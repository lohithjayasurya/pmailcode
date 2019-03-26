package com.example.mailapplication;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class composemailTest {

    @Test
    public void emptycheck() {
        String[] inputs={"","aq"};
        boolean[] expected={true,false};
        boolean[] outputs=new boolean[inputs.length];
        composemail c=new composemail();
        for (int i=0;i<inputs.length;i++)
        {
            outputs[i]=c.emptycheck(inputs[i]);
        }
        Assert.assertArrayEquals(expected,outputs);
    }
}