
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.net.*;
// import java.sql.SQLException;
//import javax.swing.event.*;
// import java.io.IOException;
// import java.io.InputStream;
import java.io.ObjectInputStream;
// import com.github.sarxos.webcam.*; // for geting webcam Videos 
// import java.sql.Connection;
// import java.sql.SQLException;

class ClientAudioStreamThread extends Thread {
    Socket audioSocket;
    ObjectInputStream ois;
    AudioFormat format;
    DataLine.Info info;
    SourceDataLine speakers;
    byte[] data;

    public void run() {
        try {
            audioSocket = new Socket(Client.IP_ADDRESS_STRING, Client.PORT + 2);
            Client.audioSocket = audioSocket;
            data = new byte[1024];
            format = new AudioFormat(48000.0f, 16, 2, true, false);
            info = new DataLine.Info(SourceDataLine.class, format);
            data = new byte[1024];

            speakers = (SourceDataLine) AudioSystem.getLine(info);
            speakers.open(format);
            speakers.start();
            ois = new ObjectInputStream(audioSocket.getInputStream());
            while (true) {
                int dsize = ois.read(data);
                if (dsize == 1024) {
                    speakers.write(data, 0, dsize);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

