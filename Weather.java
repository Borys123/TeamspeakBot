/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.teamspeakbot;

import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.CurrentWeather;
import java.text.*;

/**
 *
 * @author doma3
 */
public class Weather
{

    private static final OWM CONSTANT_OWM = new OWM("b5826b73ce1f0d2ab1df89fff111d0cb");

    private static String trim(double num)
    {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(num);
    }

    private static String kToC(double tempK)
    {
        return trim(tempK - 273.0);
    }

    public String getWeather(String city)
    {
        String text = "";
        try
        {
            CurrentWeather cwd = CONSTANT_OWM.currentWeatherByCityName(city);
            if (cwd.hasRespCode() && cwd.getRespCode() == 200)
            {
                if (cwd.hasCityName() && cwd.hasMainData())
                {
                    text = "Pogoda dla " + cwd.getCityName() + ":\nTemperatura: " + kToC(cwd.getMainData().getTemp()) + " *C\n"
                            + "Wilgotność: " + cwd.getMainData().getHumidity() + "%\nTemperatura maksymalna: " + kToC(cwd.getMainData().getTempMax()) + " *C\n"
                            + "Temperatura minimalna: " + kToC(cwd.getMainData().getTempMin()) + " *C";
                }
            }
        } catch (APIException e)
        {
            text = "Błędna nazwa miasta lub błąd serwera pogody";
        }
        return text;
    }
}
