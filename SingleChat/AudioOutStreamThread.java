
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
// import java.net.*;
// import java.sql.SQLException;
//import javax.swing.event.*;
import java.io.ObjectOutputStream;
// import com.github.sarxos.webcam.*; // for geting webcam Videos 
// import java.sql.Connection;
// import java.sql.SQLException;
class AudioOutStreamThread extends Thread {
    private ObjectOutputStream oos;
    private AudioFormat format;
    private DataLine.Info info;
    private TargetDataLine microphone;
    private byte[] data;
    private int dsize;

    AudioOutStreamThread() {

    }

    public void run() {
        try {
            // Audio Stuff
            format = new AudioFormat(48000.0f, 16, 2, true, false);
            microphone = AudioSystem.getTargetDataLine(format);
            info = new DataLine.Info(TargetDataLine.class, format);
            data = new byte[1024];

            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();
            oos = new ObjectOutputStream(Client.audioSocket.getOutputStream());
            // read and send part
            while (Client.runCam) {
                dsize = microphone.read(data, 0, data.length);
                oos.write(data, 0, dsize);
                oos.reset();
            }
            System.out.println("[ Client ] : Attempting to stop ");
            oos.write(data, 0, 512);
            oos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
        microphone.stop();
        microphone.close();
    }

}