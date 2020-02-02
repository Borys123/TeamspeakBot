Projekt z przedmiotu Zaawansowane programowanie w języku Java


Temat projektu:

Bot do serwera TeamSpeak 3 w języku Java

https://github.com/Borys123/TeamspeakBot 



Wykonali:
Imię i nazwisko: Borys Kuncewicz, Dominika Rodziewicz
Nr Indeksu: 73278, 73297
Grupa: L3
Prowadzący: dr hab. Artur Korniłowicz

Wstęp

Tematem projektu jest stworzenie w języku Java bota współpracującego z serwerem TeamSpeak 3. TeamSpeak to komunikator internetowy wykorzystujący technologię VoIP do komunikacji głosowej i tekstowej między wieloma użytkownikami danego serwera. Komunikator jest wykorzystywany głównie przez graczy do szybkiej wymiany informacji w grach wieloosobowych. Serwery tworzone są głównie przez społeczność skupioną wokół komunikatora na własnych serwerach. 

Opis projektu

Program stworzony w ramach projektu łączy się z serwerem (również przez sieć) i potrafi nim zarządzać. Wymagane jest jedynie podanie w programie danych logowania administratora. Z botem można rozmawiać, może on reagować na podstawowe słowa i różne komendy. Potrafi on między innymi sprawdzać pogodę w dowolnym miejscu na świecie, łącząc się z serwisem OpenWeatherMap. Może również losować liczby w zakresie podanym przez rozmówcę. Wykonuje wiele zadań związanych z zarządzaniem serwerem – potrafi dodawać i usuwać użytkownikom ikony, o które poproszą (widoczne przy nazwie użytkownika), może też stworzyć użytkownikowi jego własny kanał do rozmów, o ile już takiego nie ma i nadać mu uprawnienia do zarządzania nim.

Jeśli na komputerze, na którym uruchamiany jest bot, uruchomimy również klienta TeamSpeak, możemy połączyć go z botem. Istotne jest nadanie klientowi nazwy „Głośnik” lub zmiana tej nazwy na inną w kodzie bota. Korzystając z klienta, bot może wydawać dźwięk (nie jest to możliwe bez klienta, wtedy można jedynie zarządzać serwerem i rozmawiać poprzez komunikaty tekstowe). Wtedy, po otrzymaniu odpowiedniej wiadomości, bot może odtworzyć dowolny dźwięk ze swojej biblioteki. Dostępny jest odtwarzacz muzyczny z komendami play, pause, stop, replay itp. Można również przesłać mu link do serwisu YouTube, a bot samodzielnie pobierze film i wyodrębni z niego muzykę, którą doda do biblioteki. Robi to poprzez użycie biblioteki „java-youtube-downloader” do pobrania pliku i późniejszą konwersję pliku .mp4 do .wav przy użyciu zewnętrznego programu „ffmpeg”, który musi znajdować się w katalogu głównym bota. Pliki video z YouTube pobierane są asynchronicznie w tle, dzięki czemu nie blokują działania bota.
Drzewo folderów aplikacji




Wykorzystane oprogramowanie i biblioteki

NetBeans IDE – zintegrowane środowisko programistyczne dla języka Java

Apache Maven - narzędzie automatyzujące budowę oprogramowania na platformę Java

teamspeak3-api v1.2.0 – com.github.theholywaffle – biblioteka do komunikacji z serwerem TeamSpeak 3

owm-japis v2.5.3.0 – net.aksingh – api OpenWeatherMap

ffmpeg v0.6.2 – net.bramp.ffmpeg – biblioteka umożliwiająca korzystanie z ffmpeg w kodzie Java

java-youtube-downloader v2.0.1 – com.github.sealedtx – biblioteka do pobierania filmów z YouTube

Instrukcja uruchomienia

Do uruchomienia potrzeba:
    1. Serwer TeamSpeak 3, https://teamspeak.com/en/downloads/#server
    2. Klient TeamSpeak 3, https://teamspeak.com/en/downloads/
    3. Konwerter plików video Ffmpeg, https://www.ffmpeg.org/
FFmpeg powinien być umieszczony w katalogu głównym programu, w folderze "ffmpeg". Pliki dźwiękowe w formacie .wav powinny być umieszczone w katalogu głównym programu, w folderze "audio". Dane logowania do serwera umieszcza się w Properties.java.
Biblioteki wymagane do kompilacji umieszczone są w pom.xml.
Jak uruchomić program:
    •  uruchamiamy serwer TeamSpeak 3, dane logowania podajemy w Properties.java,
    •  uruchamiamy klienta TeamSpeak 3 i łączymy się z serwerem jako użytkownik o nicku "Głośnik",
    •  uruchamiamy program, bot powinien pojawić się na kanale głównym i napisać wiadomość.


Perspektywy dalszego rozwoju aplikacji

W przyszłości możliwa jest rozbudowa aplikacji o kolejne funkcjonalności. Najprostsze będzie dodanie większej ilości dostępnych komend, a nawet sprawienie, że bot będzie rozmawiał podobnie jak człowiek. Można również rozszerzyć jego funkcjonalność zarządzania serwerem – na chwilę obecną istnieje wiele niewykorzystanych możliwości takich, jak zarządzanie uprawnieniami użytkowników, przenoszenie ich, zbieranie statystyk o serwerze itp. 

Rozbudować można również funkcjonalność odtwarzania dźwięku – dodać obsługę większej ilości formatów audio, możliwość pobierania z innych serwisów niż YouTube oraz możliwość wgrywania własnych plików przez użytkowników (obecnie jedynie przez administratora, bezpośrednio do folderu aplikacji).

Istnieje jeszcze możliwość budowy GUI dla aplikacji. Obecnie nie jest to koniecznie lecz być może niektóre dodane funkcjonalności na tym skorzystają, jak na przykład wspomniane wcześniej zbieranie statystyk dotyczących serwera.
