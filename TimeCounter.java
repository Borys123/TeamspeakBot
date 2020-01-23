/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.teamspeakbot;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author borys
 */
public class TimeCounter
{

    private final Timer t = new Timer();
    private int interval = 1800000;

    TimeCounter()
    {
        t.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                Main.setPlayerOwner("");
            }
        }, 1000, this.interval);
    }

    TimeCounter(int interval)
    {
        t.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                Main.setPlayerOwner("");
            }
        }, 100, this.interval);
    }

    public void setInterval(int interval)
    {
        this.interval = interval;
    }

    public int getInterval()
    {
        return this.interval;
    }
}
