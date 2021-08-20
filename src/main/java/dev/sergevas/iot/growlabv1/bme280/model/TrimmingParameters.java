package dev.sergevas.iot.growlabv1.bme280.model;

public class TrimmingParameters {

    public static final int DIG_T1_ADDR = 0x88;
    public static final int DIG_H1_ADDR = 0xA1;
    public static final int DIG_H2_ADDR = 0xE1;

    public static int DIG_T1_OFFSET = 0;
    public static int DIG_T1_CHUNK_LENGTH = 24;
    public static int DIG_H1_OFFSET = DIG_T1_CHUNK_LENGTH;
    public static int DIG_H1_CHUNK_LENGTH = 1;

    public static int DIG_H2_OFFSET = DIG_H1_OFFSET + DIG_H1_CHUNK_LENGTH;
    public static int DIG_H2_CHUNK_LENGTH = 7;

    private byte[] digs = new byte[32];

    private int digT1;
    private int digT2;
    private int digT3;

    private int digP1;
    private int digP2;
    private int digP3;
    private int digP4;
    private int digP5;
    private int digP6;
    private int digP7;
    private int digP8;
    private int digP9;

    private int digH1;
    private int digH2;
    private int digH3;
    private int digH4;
    private int digH5;
    private int digH6;

    public byte[] getDigs() {
        return digs;
    }

    public int getDigT1() {
        return digT1;
    }

    public TrimmingParameters digT1(int digT1) {
        this.digT1 = digT1;
        return this;
    }

    public int getDigT2() {
        return digT2;
    }

    public TrimmingParameters digT2(int digT2) {
        this.digT2 = digT2;
        return this;
    }

    public int getDigT3() {
        return digT3;
    }

    public TrimmingParameters digT3(int digT3) {
        this.digT3 = digT3;
        return this;
    }

    public int getDigP1() {
        return digP1;
    }

    public TrimmingParameters digP1(int digP1) {
        this.digP1 = digP1;
        return this;
    }

    public int getDigP2() {
        return digP2;
    }

    public TrimmingParameters digP2(int digP2) {
        this.digP2 = digP2;
        return this;
    }

    public int getDigP3() {
        return digP3;
    }

    public TrimmingParameters digP3(int digP3) {
        this.digP3 = digP3;
        return this;
    }

    public int getDigP4() {
        return digP4;
    }

    public TrimmingParameters digP4(int digP4) {
        this.digP4 = digP4;
        return this;
    }

    public int getDigP5() {
        return digP5;
    }

    public TrimmingParameters digP5(int digP5) {
        this.digP5 = digP5;
        return this;
    }

    public int getDigP6() {
        return digP6;
    }

    public TrimmingParameters digP6(int digP6) {
        this.digP6 = digP6;
        return this;
    }

    public int getDigP7() {
        return digP7;
    }

    public TrimmingParameters digP7(int digP7) {
        this.digP7 = digP7;
        return this;
    }

    public int getDigP8() {
        return digP8;
    }

    public TrimmingParameters digP8(int digP8) {
        this.digP8 = digP8;
        return this;

    }

    public int getDigP9() {
        return digP9;
    }

    public TrimmingParameters digP9(int digP9) {
        this.digP9 = digP9;
        return this;
    }

    public int getDigH1() {
        return digH1;
    }

    public TrimmingParameters digH1(int digH1) {
        this.digH1 = digH1;
        return this;
    }

    public int getDigH2() {
        return digH2;
    }

    public TrimmingParameters digH2(int digH2) {
        this.digH2 = digH2;
        return this;
    }

    public int getDigH3() {
        return digH3;
    }

    public TrimmingParameters digH3(int digH3) {
        this.digH3 = digH3;
        return this;
    }

    public int getDigH4() {
        return digH4;
    }

    public TrimmingParameters digH4(int digH4) {
        this.digH4 = digH4;
        return this;
    }

    public int getDigH5() {
        return digH5;
    }

    public TrimmingParameters digH5(int digH5) {
        this.digH5 = digH5;
        return this;
    }

    public int getDigH6() {
        return digH6;
    }

    public TrimmingParameters digH6(int digH6) {
        this.digH6 = digH6;
        return this;
    }

    public void init() {
        digT1 = toUnsigned(digs[0], digs[1]);
        digT2 = toSigned(digs[2], digs[3]);
        digT3 = toSigned(digs[4], digs[5]);
        digP1 = toUnsigned(digs[6], digs[7]);
        digP2 = toSigned(digs[8], digs[9]);
        digP3 = toSigned(digs[10], digs[11]);
        digP4 = toSigned(digs[12], digs[13]);
        digP5 = toSigned(digs[14], digs[15]);
        digP6 = toSigned(digs[16], digs[17]);
        digP7 = toSigned(digs[18], digs[19]);
        digP8 = toSigned(digs[20], digs[21]);
        digP9 = toSigned(digs[22], digs[23]);
        digH1 = toUnsigned(digs[24]);
        digH2 = toSigned(digs[25], digs[26]);
        digH3 = toUnsigned(digs[27]);
        digH4 =
        digH5 =
        digH6 = toSigned(digs[31]);
    }

    private int toUnsigned(byte lsb, byte msb) {
        return Byte.toUnsignedInt(msb) << 8 | Byte.toUnsignedInt(lsb);
    }

    private int toSigned(byte lsb, byte msb) {
        return (int)msb << 8 | Byte.toUnsignedInt(lsb);
    }

    private int toUnsigned(byte b) {
        return Byte.toUnsignedInt(b);
    }

    private int toSigned(byte b) {
        return (int)b;
    }

    private int toDigH4(byte lsb, byte msb) {
        return
    }
}
