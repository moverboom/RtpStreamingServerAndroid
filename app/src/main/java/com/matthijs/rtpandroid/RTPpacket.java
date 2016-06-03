package com.matthijs.rtpandroid;

/**
 * Created by Matthijs Overboom on 12-5-16.
 */
public class RTPpacket {
    // size of the RTP header:
    static int HEADER_SIZE = 12;

    // Fields that compose the RTP header
    public int Version;
    public int Padding;
    public int Extension;
    public int CC;
    public int Marker;
    public int PayloadType;
    public int SequenceNumber;
    public int TimeStamp;
    public int Ssrc;

    // Bitstream of the RTP header
    public byte[] header;

    // size of the RTP payload
    public int payload_size;
    // Bitstream of the RTP payload
    public byte[] payload;

    /**
     * Constructor
     *
     * @param PType Payload type. 26 in this project, jpeg
     * @param Framenb current frame
     * @param Time Time in ms in video file
     * @param data payload
     * @param data_length payload length
     */
    public RTPpacket(int PType, int Framenb, int Time, byte[] data,
                     int data_length) {
        //fill by default header fields:
        Version = 2;
        Padding = 0;
        Extension = 0;
        CC = 0;
        Marker = 0;
        Ssrc = 2147483647; //max signed int on 4 bytes

        //fill changing header fields:
        SequenceNumber = Framenb;
        TimeStamp = Time;
        PayloadType = PType;

        //build the header bistream:
        header = new byte[HEADER_SIZE];

        //First row
        header[0] = (byte)(Version << 6 | Padding << 5 | Extension << 4 | CC);
        header[1] = (byte)(Marker << 7 | PayloadType);
        header[2] = (byte)(Framenb >> 8);
        header[3] = (byte)Framenb;

        //Second row
        header[4] = (byte)(TimeStamp >> 24);
        header[5] = (byte)(TimeStamp >> 16);
        header[6] = (byte)(TimeStamp >> 8);
        header[7] = (byte)TimeStamp;

        //Third row
        header[8] = (byte)(Ssrc >> 24);
        header[9] = (byte)(Ssrc >> 16);
        header[10] = (byte)(Ssrc >> 8);
        header[11] = (byte)Ssrc;


        //fill the payload bitstream:
        payload_size = data_length;
        payload = new byte[data_length];

        //fill payload array of byte from data (given in parameter of the constructor)
        payload = data;
    }

    /**
     * Returns the payload's size
     * and sets the payload itself to the byte[] given as argument
     *
     * @param data byte[] to set the payload to
     * @return payload size
     */
    public int getpayload(byte[] data) {

        for (int i = 0; i < payload_size; i++)
            data[i] = payload[i];

        return (payload_size);
    }

    /**
     * Returns the payload length
     *
     * @return int payload length
     */
    public int getpayload_length() {
        return (payload_size);
    }

    /**
     * Return the full length of the RTPpacket
     * payload size + header size
     *
     * @return int full packet size
     */
    public int getlength() {
        return (payload_size + HEADER_SIZE);
    }

    /**
     * Returns the packet size
     * and sets the packet itself to the byte[] given as argument
     *
     * @param packet byte[] to set packet to
     * @return int packet size
     */
    public int getpacket(byte[] packet) {
        // construct the packet = header + payload
        for (int i = 0; i < HEADER_SIZE; i++)
            packet[i] = header[i];
        for (int i = 0; i < payload_size; i++)
            packet[i + HEADER_SIZE] = payload[i];

        // return total size of the packet
        return (payload_size + HEADER_SIZE);
    }

    /**
     * Returns the timestamp on which the packet was created
     * at the server
     *
     * @return int timestamp
     */
    public int gettimestamp() {
        return (TimeStamp);
    }

    /**
     * Returns the packet's sequence number
     *
     * @return int sequence number
     */
    public int getsequencenumber() {
        return (SequenceNumber);
    }

    /**
     * Returns the payload type
     * (in this project we use type 26, jpeg.
     *
     * @return int payload type
     */
    public int getpayloadtype() {
        return (PayloadType);
    }

    /**
     * Prints the header to the console without SSRC
     * SSRC is not actively used in this project
     */
    public void printheader() {

        for (int i = 0; i < (HEADER_SIZE - 4); i++) {
            for (int j = 7; j >= 0; j--)
                if (((1 << j) & header[i]) != 0)
                    System.out.print("1");
                else
                    System.out.print("0");
            System.out.print(" ");
        }

        System.out.println();
    }

    // return the unsigned value of 8-bit integer nb
    static int unsigned_int(int nb) {
        if (nb >= 0)
            return (nb);
        else
            return (256 + nb);
    }

}
