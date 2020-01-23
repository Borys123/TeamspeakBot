/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.teamspeakbot;

import java.util.Random;

/**
 *
 * @author borys
 */
public class Rnd
{

    private static final Random CONSTANT_RANDOM = new Random();

    public int generate(int... numbers) throws Exception
    {
        int rnd;
        if (numbers.length == 1)
        {
            if (numbers[0] == Integer.MIN_VALUE)
            {
                throw new Exception("Niepoprawna liczba!");
            }
            rnd = CONSTANT_RANDOM.nextInt(numbers[0] + 1);
        } else
        {
            Integer a = numbers[0];
            Integer b = numbers[1];
            int comp = a.compareTo(b);
            if (comp == 0)
            {
                rnd = numbers[0];
            } else if (comp < 0)
            {
                rnd = CONSTANT_RANDOM.nextInt(numbers[1] - numbers[0] + 1) + numbers[0];
            } else
            {
                rnd = CONSTANT_RANDOM.nextInt(numbers[0] - numbers[1] + 1) + numbers[1];
            }
        }
        return rnd;
    }
}
