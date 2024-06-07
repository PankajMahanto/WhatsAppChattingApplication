
import javax.swing.*;
// import javax.swing.border.EmptyBorder;
import java.net.*;
// import java.io.IOException;
// import java.io.InputStream;
import java.io.ObjectInputStream;
// import java.io.ObjectOutputStream;
import java.awt.event.*;
// import java.awt.*;
// import com.github.sarxos.webcam.*; // for geting webcam Videos 
// import java.sql.Connection;
// import java.sql.SQLException;



class ClientVideoStreamThread extends Thread {
    Socket videoSocket;

    public void run() {
        try {
            videoSocket = new Socket(Client.IP_ADDRESS_STRING, Client.PORT + 1);
            Client.videoSocket = videoSocket;

            JFrame videoFrame = Client.videoFrame;
            ImageIcon ic;
            JLabel videoFeed = new JLabel();
            // videoFrame.setLayout(null);
            videoFrame.setTitle("Client :" + Client.CURRENT_USER);
            videoFrame.add(videoFeed);
            videoFrame.setVisible(false);
            videoFrame.setSize(Client.VIDEO_HEIGHT, Client.VIDEO_WIDTH);
            videoFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            videoFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    videoFrame.setVisible(false);
                }
            });
            while (true) {
                ObjectInputStream oin = new ObjectInputStream((videoSocket.getInputStream()));
                ic = (ImageIcon) oin.readObject();
                videoFeed.setIcon(ic);
                if (!videoFrame.isVisible())
                    videoFrame.setVisible(true);
                if (ic != null && ic.getDescription() != null && ic.getDescription().equals("END_VIDEO")) {
                    videoFrame.setVisible(false);
                }
            }

        } catch (java.io.EOFException e) {
            System.out.println("Ended");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

