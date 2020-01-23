# TeamspeakBot
Bot do serwera TeamSpeak 3, napisany w języku Java.

Do uruchomienia potrzeba:
1. Serwer TeamSpeak 3, https://teamspeak.com/en/downloads/#server
2. Klient TeamSpeak 3, https://teamspeak.com/en/downloads/
3. Konwerter plików video FFmpeg, https://www.ffmpeg.org/

FFmpeg powinien być umieszczony w katalogu głównym programu, w folderze "ffmpeg".
Pliki dźwiękowe w formacie .wav powinny być umieszczone w katalogu głównym programu, w folderze "audio".
Dane logowania do serwera umieszcza się w Properties.java.

Bibioteki wymagane do kompilacji umieszczone są w pom.xml.

Jak uruchomić program:
- uruchamiamy serwer TeamSpeak 3, dane logowania podajemy w Properties.java,
- uruchamiamy klienta TeamSpeak 3 i łączymy się z serwerem jako użytkownik o nicku "Głośnik",
- uruchamiamy program, bot powinien pojawić się na kanale głównym i napisać wiadomość.
