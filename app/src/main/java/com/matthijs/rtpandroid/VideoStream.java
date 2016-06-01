package com.matthijs.rtpandroid;

/**
 * Created by matthijs on 12-5-16.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.net.rtp.RtpStream;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;

import android.net.rtp.*;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class VideoStream {
    FileInputStream fis; //video file
    int frame_nb; //current frame nb
    MjpegInputStream mjpegInputStream;


    //-----------------------------------
    //constructor
    //-----------------------------------
    public VideoStream(String path) throws Exception{

        Uri video = Uri.parse(path);
        System.out.println("Video Path: " + video.getPath());

        //init variables

        mjpegInputStream = new MjpegInputStream(new FileInputStream(new File(video.getPath())));
        frame_nb = 0;
    }

    //-----------------------------------
    // getnextframe
    //returns the next frame as an array of byte and the size of the frame
    //-----------------------------------
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
//    public byte[] getnextframe(long frameTime) throws Exception {
//        Bitmap currentFrame = dataRetriever.getFrameAtTime(frameTime*1000, MediaMetadataRetriever.OPTION_CLOSEST);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        currentFrame.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//        byte[] frame = stream.toByteArray();
//        stream.close();
//        return frame;
//    }
}
