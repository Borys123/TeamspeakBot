/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.teamspeakbot;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author doma3
 */
public class Util
{

    public static final Util CONSTANT_UTIL = new Util();

    private Util()
    {
        if (CONSTANT_UTIL != null)
        {
            throw new IllegalStateException("Instancja Util już istnieje!");
        }
    }

    public static String stringDeleteFirst(String input)
    {
        String output = "";
        String[] words = input.split(" ");
        for (int i = 1; i < words.length; i++)
        {
            output += words[i];
            if (words.length > 2 && i < words.length - 1)
            {
                output += " ";
            }
        }
        return output;
    }

    public static String stringSelectSecond(String input)
    {
        String[] words = input.split(" ");
        if (words.length > 1)
        {
            return words[1];
        } else
        {
            return input;
        }

    }

    public static String[] stringSelectSecondThird(String input)
    {
        String[] words = input.split(" ");
        if (words.length > 2)
        {
            String[] result =
            {
                words[1], words[2]
            };
            return result;
        } else if (words.length > 1)
        {
            String[] result =
            {
                words[1]
            };
            return result;
        } else
        {
            String[] result =
            {
                words[0]
            };
            return result;
        }
    }

    public static int[] stringArrayToIntArray(String[] input)
    {
        try
        {
            if (input.length == 1)
            {
                return new int[]
                {
                    Integer.parseInt(input[0].trim())
                };
            } else
            {
                return new int[]
                {
                    Integer.parseInt(input[0].trim()), Integer.parseInt(input[1].trim())
                };
            }
        } catch (NumberFormatException e)
        {
            return new int[]
            {
                Integer.MIN_VALUE
            };
        }
    }

    public static String stringFilename(String input)
    {
        String separator = "\\";
        String[] words = input.replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");
        return words[words.length - 1];
    }

    public static int stringToInt(String input)
    {
        try
        {
            int i = Integer.parseInt(input.trim());
            return i;
        } catch (NumberFormatException e)
        {
            return -1;
        }
    }

    public static int stringToIntArray(String input)
    {
        try
        {
            int i = Integer.parseInt(input.trim());
            return i - 1;
        } catch (NumberFormatException e)
        {
            return -1;
        }
    }

    public static List<String> stringComma(String input)
    {
        List<String> words;
        words = Arrays.asList(input.split(","));
        return words;
    }

    public static void clearFolder(File folder)
    {
        File[] files = folder.listFiles();
        if (files != null)
        {
            for (File f : files)
            {
                f.delete();
            }
        }
    }
}
