package com.example.mailapplication;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class passwordupdationTest {

    @Test
    public void equalret() {
        String[] inputs1={"hello","aq"};
        String[] inputs2={"hii","aq"};

        boolean[] expected={false,true};
        boolean[] outputs=new boolean[inputs1.length];
        passwordupdation c=new passwordupdation();
        for (int i=0;i<inputs1.length;i++)
        {
            outputs[i]=inputs1[i].equals(inputs2[i]);
        }
        Assert.assertArrayEquals(expected,outputs);
    }
}