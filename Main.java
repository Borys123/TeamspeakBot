/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.teamspeakbot;

import com.github.theholywaffle.teamspeak3.*;
import com.github.theholywaffle.teamspeak3.api.*;
import com.github.theholywaffle.teamspeak3.api.event.*;
//import com.github.theholywaffle.teamspeak3.api.exception.*;
import com.github.theholywaffle.teamspeak3.api.reconnect.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.*;
//import com.github.theholywaffle.teamspeak3.commands.*;
//import com.github.theholywaffle.teamspeak3.commands.parameter.*;
//import com.github.theholywaffle.teamspeak3.commands.response.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author doma3
 */
public class Main
{

    private static final Weather CONSTANT_WEATHER = new Weather();
    private static final Player CONSTANT_PLAYER = new Player();
    private static final Rnd CONSTANT_RND = new Rnd();
    private static final Properties CONSTANT_PROPERTIES = new Properties();
    private static final Download CONSTANT_DOWNLOAD = new Download();
    private static final String CONSTANT_HELP = "\nJeśli chcesz edytować swoje grupy, wpisz \"grupy\""
            + "\nJeśli chcesz mieć własny kanał, wpisz \"kanał\""
            + "\nJeśli chcesz uzyskać informacje o pogodzie, wpisz \"pogoda nazwa miasta\""
            + "\nJeśli chcesz wygenerować liczbę losową od 0 do X, wpisz \"losuj liczba1\""
            + "\nJeśli chcesz wygenerować liczbę losową od X do Y, wpisz \"losuj liczba1 liczba2\""
            + "\nJeśli chcesz odtwarzać muzykę, skorzystaj z komend: play, pause, stop oraz list - lista utworów."
            + "\nŻeby przywołać do siebie głośnik, wpisz \"glosnik\""
            + "\nŻeby przejąć odtwarzacz na własność na 30 minut, wplisz \"przejmij\", a żeby go zwolnić wpisz \"zwolnij\""
            + "\nJeśli chcesz dodać dźwięk z filmu YouTube do listy odtwarzania, wpisz \"youtube id-filmu (id po znaku =)\"";
    private static final String CONSTANT_HELPGROUPS = "\nWpisz \"nadaj numer-grupy\" lub \"zabierz numer-grupy\"\n"
            + "Lista grup:\n10. Lubię Linuxa\n11. Lubię Windowsa\n12. Gram w gry";
    private static final List<Integer> CONSTANT_GROUPS = Arrays.asList(10, 11, 12);
    private static volatile int ownId;
    private static String player_owner = "";
    private static String serverName = "";

    public static void main(String[] args)
    {
        final TS3Config config = new TS3Config();
        config.setHost(CONSTANT_PROPERTIES.getHost());
        config.setEnableCommunicationsLogging(true);

        config.setReconnectStrategy(ReconnectStrategy.exponentialBackoff());

        config.setConnectionHandler(new ConnectionHandler()
        {
            @Override
            public void onConnect(TS3Query ts3Query)
            {
                everyTime(ts3Query.getApi());
            }

            @Override
            public void onDisconnect(TS3Query ts3Query)
            {
            }
        });

        final TS3Query query = new TS3Query(config);
        query.connect();
        once(query.getApi());
        async(query.getAsyncApi());
    }

    private static void everyTime(TS3Api api)
    {
        api.login(CONSTANT_PROPERTIES.getLogin(), CONSTANT_PROPERTIES.getPassword());
        api.selectVirtualServerById(1);
        api.setNickname(CONSTANT_PROPERTIES.getNickname());
        api.registerAllEvents();
        ownId = api.whoAmI().getId();
    }

    private static void once(final TS3Api api)
    {
        serverName = api.getServerInfo().getName();
        api.sendChannelMessage("Witam Was! Wpisz \"!priv\", żebym do Ciebie napisał.");
        TimeCounter t = new TimeCounter(1800000);
        api.addTS3Listeners(new TS3EventAdapter()
        {
            @Override
            public void onTextMessage(TextMessageEvent e)
            {
                if (e.getTargetMode() == TextMessageTargetMode.CHANNEL && e.getInvokerId() != ownId)
                {
                    String message = e.getMessage().toLowerCase();

                    if (message.equals("!priv"))
                    {
                        api.sendPrivateMessage(e.getInvokerId(), "Napisz \"help\", żeby zobaczyć listę komend.");
                    }

                }
                if (e.getTargetMode() == TextMessageTargetMode.CLIENT && e.getInvokerId() != ownId)
                {
                    String message = e.getMessage().toLowerCase();
                    if (message.equals("help"))
                    {
                        api.sendPrivateMessage(e.getInvokerId(), CONSTANT_HELP);
                    } else if (message.equals("grupy"))
                    {
                        api.sendPrivateMessage(e.getInvokerId(), CONSTANT_HELPGROUPS);
                    } else if (message.startsWith("cześć") || message.startsWith("czesc") || message.startsWith("witaj")
                            || message.startsWith("witam") || message.startsWith("hej"))
                    {
                        api.sendPrivateMessage(e.getInvokerId(), "Cześć, " + e.getInvokerName() + "!");
                    } else if (message.startsWith("dziekuje") || message.startsWith("dziękuję") || message.startsWith("dzieki") || message.startsWith("dzięki"))
                    {
                        api.sendPrivateMessage(e.getInvokerId(), "Proszę!");
                    } else if (message.startsWith("nadaj"))
                    {
                        int groupNumber = Util.stringToInt(Util.stringSelectSecond(message));
                        if (groupNumber != -1 && CONSTANT_GROUPS.contains(groupNumber))
                        {
                            try
                            {
                                api.addClientToServerGroup(groupNumber, api.getDatabaseClientByUId(e.getInvokerUniqueId()).getDatabaseId());
                                api.sendPrivateMessage(e.getInvokerId(), "Nadano grupę: " + groupNumber);
                            } catch (Exception ex)
                            {
                                api.sendPrivateMessage(e.getInvokerId(), "Nie ma takiej grupy lub już ją masz!");
                            }
                        } else
                        {
                            api.sendPrivateMessage(e.getInvokerId(), "Nie ma takiej grupy!");
                        }
                    } else if (message.startsWith("zabierz"))
                    {
                        int groupNumber = Util.stringToInt(Util.stringSelectSecond(message));
                        if (groupNumber != -1 && CONSTANT_GROUPS.contains(groupNumber))
                        {
                            try
                            {
                                api.removeClientFromServerGroup(groupNumber, api.getDatabaseClientByUId(e.getInvokerUniqueId()).getDatabaseId());
                                api.sendPrivateMessage(e.getInvokerId(), "Usunięto grupę: " + groupNumber);
                            } catch (Exception ex)
                            {
                                api.sendPrivateMessage(e.getInvokerId(), "Nie ma takiej grupy lub jej nie masz!");
                            }
                        } else
                        {
                            api.sendPrivateMessage(e.getInvokerId(), "Nie ma takiej grupy!");
                        }
                    } else if (message.startsWith("pogoda"))
                    {
                        api.sendPrivateMessage(e.getInvokerId(), CONSTANT_WEATHER.getWeather(Util.stringDeleteFirst(message)));
                    } else if (message.startsWith("losuj"))
                    {
                        try
                        {
                            api.sendPrivateMessage(e.getInvokerId(), Integer.toString(CONSTANT_RND.generate(Util.stringArrayToIntArray(Util.stringSelectSecondThird(message)))));

                        } catch (Exception ex)
                        {
                            api.sendPrivateMessage(e.getInvokerId(), "Błędna liczba!");
                        }
                    } else if (message.equals("play") || message.equals("pause") || message.equals("stop") || message.equals("restart")
                            || message.equals("list") || message.startsWith("start") || message.equals("glosnik") || message.equals("głośnik")
                            || message.equals("przejmij") || message.equals("zwolnij"))
                    {
                        if (getPlayerOwner().equals("") || getPlayerOwner().equals(e.getInvokerName()))
                        {
                            if (message.equals("play"))
                            {
                                CONSTANT_PLAYER.controls("play");
                            } else if (message.equals("pause"))
                            {
                                CONSTANT_PLAYER.controls("pause");
                            } else if (message.equals("stop"))
                            {
                                CONSTANT_PLAYER.controls("stop");
                            } else if (message.equals("restart"))
                            {
                                CONSTANT_PLAYER.controls("restart");
                            } else if (message.equals("list"))
                            {
                                api.sendPrivateMessage(e.getInvokerId(), "\n" + CONSTANT_PLAYER.list());
                                api.sendPrivateMessage(e.getInvokerId(), "Wpisz start i cyfrę!");
                            } else if (message.startsWith("start"))
                            {
                                api.sendPrivateMessage(e.getInvokerId(), CONSTANT_PLAYER.selectFile(Util.stringToIntArray(Util.stringSelectSecond(message))));
                            } else if (message.equals("glosnik") || message.equals("głośnik"))
                            {
                                api.moveClient(api.getClientByNameExact("Głośnik", true).getId(), api.getClientByNameExact(e.getInvokerName(), true).getChannelId());

                            } else if (message.equals("przejmij"))
                            {
                                setPlayerOwner(e.getInvokerName());
                                api.sendPrivateMessage(e.getInvokerId(), "Przejąłeś odtwarzacz!");
                            } else if (message.equals("zwolnij"))
                            {
                                setPlayerOwner("");
                                api.sendPrivateMessage(e.getInvokerId(), "Zwolniłeś odtwarzacz!");
                            }
                        } else
                        {
                            api.sendPrivateMessage(e.getInvokerId(), "Odtwarzacz jest zajęty!");
                        }
                    }

                }
            }

            @Override
            public void onClientJoin(ClientJoinEvent e)
            {
                api.sendPrivateMessage(e.getClientId(), "Cześć, " + e.getClientNickname() + ". Witam na serwerze " + serverName + ".\n"
                        + "Wpisz help, aby uzyskać listę dostępnych komend.\nNapisz na kanale głównym !priv, jeśli chcesz żebym znowu do Ciebie napisał.");
                List<String> groups = Util.stringComma(e.getClientServerGroups());
                if (!groups.contains("9"))
                {
                    api.addClientToServerGroup(9, e.getClientDatabaseId());
                    api.sendPrivateMessage(e.getClientId(), "Zostałeś zarejestronwany na serwerze.");

                }
            }
        });
    }

    private static void async(TS3ApiAsync api)
    {
        api.addTS3Listeners(new TS3EventAdapter()
        {
            @Override
            public void onTextMessage(TextMessageEvent e)
            {
                if (e.getTargetMode() == TextMessageTargetMode.CLIENT && e.getInvokerId() != ownId)
                {

                    String message = e.getMessage().toLowerCase();
                    if (message.startsWith("kanal") || message.startsWith("kanał"))
                    {
                        final Map<ChannelProperty, String> properties = new HashMap<>();
                        properties.put(ChannelProperty.CHANNEL_FLAG_PERMANENT, "1");
                        try
                        {
                            if (api.getChannelByNameExact("Kanał " + e.getInvokerName(), true).get() == null)
                            {
                                api.createChannel("Kanał " + e.getInvokerName(), properties);
                                Channel channel = api.getChannelByNameExact("Kanał " + e.getInvokerName(), true).get();
                                api.moveClient(e.getInvokerId(), channel.getId());
                                api.setClientChannelGroup(5, channel.getId(), api.getDatabaseClientByUId(e.getInvokerUniqueId()).get().getDatabaseId());
                                api.sendPrivateMessage(e.getInvokerId(), "Proszę bardzo :)");
                                api.whoAmI().getUninterruptibly();
                            } else
                            {
                                api.sendPrivateMessage(e.getInvokerId(), "Masz już kanał!");
                            }

                        } catch (InterruptedException ex)
                        {

                        }
                    } else if (message.startsWith("youtube"))
                    {
                        try
                        {
                            api.sendPrivateMessage(e.getInvokerId(), CONSTANT_DOWNLOAD.Download(Util.stringSelectSecond(e.getMessage())));
                        } catch (Exception ex)
                        {
                            api.sendPrivateMessage(e.getInvokerId(), "Coś poszło nie tak, prawdopodobnie filmu nie da się pobrać.");
                        }
                    }
                }
            }
        });
    }

    public static void setPlayerOwner(String name)
    {
        player_owner = name;
    }

    public static String getPlayerOwner()
    {
        return player_owner;
    }
}
