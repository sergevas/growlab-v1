package dev.sergevas.iot.growlabv1.bme280.domain;

import java.util.StringJoiner;

public class TrimmingParameters {

    public static final int DIG_T1_ADDR = 0x88;
    public static final int DIG_T2_ADDR = 0x8A;
    public static final int DIG_T3_ADDR = 0x8C;
    public static final int DIG_P1_ADDR = 0x8E;
    public static final int DIG_P2_ADDR = 0x90;
    public static final int DIG_P3_ADDR = 0x92;
    public static final int DIG_P4_ADDR = 0x94;
    public static final int DIG_P5_ADDR = 0x96;
    public static final int DIG_P6_ADDR = 0x98;
    public static final int DIG_P7_ADDR = 0x9A;
    public static final int DIG_P8_ADDR = 0x9C;
    public static final int DIG_P9_ADDR = 0x9E;
    public static final int DIG_H1_ADDR = 0xA1;
    public static final int DIG_H2_ADDR = 0xE1;
    public static final int DIG_H3_ADDR = 0xE3;
    public static final int DIG_H4_ADDR = 0xE4;
    public static final int DIG_H5_ADDR = 0xE6;
    public static final int DIG_H6_ADDR = 0xE7;

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

    public int getDigT1() {
        return digT1;
    }

    public void digT1(int digT1) {
        this.digT1 = digT1;
    }

    public int getDigT2() {
        return digT2;
    }

    public void digT2(int digT2) {
        this.digT2 = digT2;
    }

    public int getDigT3() {
        return digT3;
    }

    public void digT3(int digT3) {
        this.digT3 = digT3;
    }

    public int getDigP1() {
        return digP1;
    }

    public void digP1(int digP1) {
        this.digP1 = digP1;
    }

    public int getDigP2() {
        return digP2;
    }

    public void digP2(int digP2) {
        this.digP2 = digP2;
    }

    public int getDigP3() {
        return digP3;
    }

    public void digP3(int digP3) {
        this.digP3 = digP3;
    }

    public int getDigP4() {
        return digP4;
    }

    public void digP4(int digP4) {
        this.digP4 = digP4;
    }

    public int getDigP5() {
        return digP5;
    }

    public void digP5(int digP5) {
        this.digP5 = digP5;
    }

    public int getDigP6() {
        return digP6;
    }

    public void digP6(int digP6) {
        this.digP6 = digP6;
    }

    public int getDigP7() {
        return digP7;
    }

    public void digP7(int digP7) {
        this.digP7 = digP7;
    }

    public int getDigP8() {
        return digP8;
    }

    public void digP8(int digP8) {
        this.digP8 = digP8;
    }

    public int getDigP9() {
        return digP9;
    }

    public void digP9(int digP9) {
        this.digP9 = digP9;
    }

    public int getDigH1() {
        return digH1;
    }

    public void digH1(int digH1) {
        this.digH1 = digH1;
    }

    public int getDigH2() {
        return digH2;
    }

    public void digH2(int digH2) {
        this.digH2 = digH2;
    }

    public int getDigH3() {
        return digH3;
    }

    public void digH3(int digH3) {
        this.digH3 = digH3;
    }

    public int getDigH4() {
        return digH4;
    }

    public void digH4(int digH4) {
        this.digH4 = digH4;
    }

    public int getDigH5() {
        return digH5;
    }

    public void digH5(int digH5) {
        this.digH5 = digH5;
    }

    public int getDigH6() {
        return digH6;
    }

    public void digH6(int digH6) {
        this.digH6 = digH6;
    }

    public static int toUnsigned(int i) {
        return i & 0xFFFF;
    }

    public static int toDigH4(byte lsb, byte msb) {
        return msb << 4 | 0x0f & toUnsigned(lsb);
    }

    public static int toDigH5(byte lsb, byte msb) {
        return msb << 4 | 0x0f & toUnsigned(lsb) >> 4;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TrimmingParameters.class.getSimpleName() + "[", "]")
                .add("digT1=" + digT1)
                .add("digT2=" + digT2)
                .add("digT3=" + digT3)
                .add("digP1=" + digP1)
                .add("digP2=" + digP2)
                .add("digP3=" + digP3)
                .add("digP4=" + digP4)
                .add("digP5=" + digP5)
                .add("digP6=" + digP6)
                .add("digP7=" + digP7)
                .add("digP8=" + digP8)
                .add("digP9=" + digP9)
                .add("digH1=" + digH1)
                .add("digH2=" + digH2)
                .add("digH3=" + digH3)
                .add("digH4=" + digH4)
                .add("digH5=" + digH5)
                .add("digH6=" + digH6)
                .toString();
    }
}
