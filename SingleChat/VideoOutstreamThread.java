
import javax.swing.*;
// import java.sql.SQLException;
import java.awt.image.BufferedImage;
//import javax.swing.event.*;
// import java.io.IOException;
// import java.io.InputStream;
import java.io.ObjectOutputStream;

import com.github.sarxos.webcam.*; // for geting webcam Videos 
// import java.sql.Connection;
// import java.sql.SQLException;


class VideoOutstreamThread extends Thread {
    ImageIcon ic;
    BufferedImage br;
    ObjectOutputStream stream;
    Webcam cam;

    VideoOutstreamThread(ImageIcon ic, BufferedImage br, ObjectOutputStream stream, Webcam cam) {
        this.ic = ic;
        this.br = br;
        this.stream = stream;
        this.cam = cam;
    }

    public void run() {
        try {
            while (Client.runCam) {
                br = cam.getImage();
                ic = new ImageIcon(br);
                stream.writeObject(ic);
                stream.flush();
            }
            ic = new ImageIcon("images\\endVideo.png", "END_VIDEO");
            stream.writeObject(ic);
            stream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cam.close();
    }
}

