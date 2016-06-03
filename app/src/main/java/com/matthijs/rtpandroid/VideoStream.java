package com.matthijs.rtpandroid;


import android.graphics.Bitmap;
import android.net.Uri;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * VideoStream. Fetches frames from .mjpeg file
 *
 * Created by Matthijs Overboom on 12-5-16.
 */
public class VideoStream {
    int frame_nb; //current frame nb
    MjpegInputStream mjpegInputStream;


    public VideoStream(String path) throws Exception{
        Uri video = Uri.parse(path);
        System.out.println("Video Path: " + video.getPath());
        mjpegInputStream = new MjpegInputStream(new FileInputStream(new File(video.getPath())));
        frame_nb = 0;
    }

    /**
     * Fetches the next frame
     *
     * @return byte[] containing frame
     * @throws Exception
     */
    public byte[] getnextframe() throws Exception
    {
        Bitmap frameBitmap = mjpegInputStream.readMjpegFrame();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(frameBitmap != null) {
            frameBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
        } else {
            throw new OutOfFramesException("No more frames");
        }
        return stream.toByteArray();
    }
}
