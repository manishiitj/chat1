import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

// client interface
public class Client extends Activity implements OnClickListener, OnTouchListener  {
    private static final String TAG = "Client";
    private Button connectButton;
    private EditText hostEditText;
    private TextView touchTextView;
    private Socket socket;
    InputStream inStream;
    OutputStream outStream;
    Scanner in;
    PrintWriter out;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        connectButton = (Button)findViewById(R.id.connect);
        connectButton.setOnClickListener(this);
        hostEditText = (EditText)findViewById(R.id.host);
        touchTextView= (TextView)findViewById(R.id.touch);
        touchTextView.setOnTouchListener(this);
    }    

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.connect:
                Log.d(TAG, socket == null ? "null" : "not null");
                if(socket == null)
                    connect();      
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        if(e.getAction() != MotionEvent.ACTION_DOWN || socket == null)
            return false;

        float x, y;
        switch(v.getId()) {

            case R.id.touch:
                x = e.getX();
                y = e.getY();

                sendXY((int)x, (int)y);
                break;
        }
        return true;
    }

    //===== OR THE PROBLEM HERE? ======//
    // send x, y to server
    private void sendXY(int x, int y)
    {
        out.print(x);
        out.print(y);
    }


    private void connect()
    {
        try {
            String hostName = hostEditText.getText().toString();
            Log.d(TAG, hostName);
            socket = new Socket(hostName, 4444);

            inStream = socket.getInputStream();
            outStream = socket.getOutputStream();
            in = new Scanner(inStream);
            out = new PrintWriter(outStream);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
