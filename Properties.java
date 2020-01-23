/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.teamspeakbot;

/**
 *
 * @author doma3
 */
public class Properties
{

    private String host = "localhost";
    private String login = "serveradmin";
    private String password = "5NZ2Pxrv";
    private String nickname = "TeamspeakBot";

    public Properties()
    {
    }

    public Properties(String host, String login, String password, String nickname)
    {
        this.host = host;
        this.login = login;
        this.password = password;
        this.nickname = nickname;
    }

    public String getHost()
    {
        return host;
    }

    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public void setlogin(String login)
    {
        this.login = login;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }
}
