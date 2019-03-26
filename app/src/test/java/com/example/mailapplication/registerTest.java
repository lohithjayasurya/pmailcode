package com.example.mailapplication;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class registerTest {

    @Test
    public void lenCheck() {
        String[] inputs={"","aq","hhhjhjxhjjxjhhhx"};


        boolean[] expected={false,true,false};
        boolean[] outputs=new boolean[inputs.length];
        register c=new register();
        for (int i=0;i<inputs.length;i++)
        {
            outputs[i]=inputs[i].length()>0 && inputs[i].length()<10;
        }
        Assert.assertArrayEquals(expected,outputs);


    }

    @Test
    public void lenCheck1() {
        String[] inputs={"","aq","hhhjhjxhjjxjhhhx"};


        boolean[] expected={false,false,false};
        boolean[] outputs=new boolean[inputs.length];
        register c=new register();
        for (int i=0;i<inputs.length;i++)
        {
            outputs[i]=inputs[i].length()>7 && inputs[i].length()<15;
        }
        Assert.assertArrayEquals(expected,outputs);


    }
    }
