package dev.sergevas.iot.growlabv1.bme280.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrimmingParametersTest {

    /* Register readings of a real physical device:
    digs[0]=4E
    digs[1]=6D
    digs[2]=2D
    digs[3]=67
    digs[4]=32
    digs[5]=00
    digs[6]=80
    digs[7]=94
    digs[8]=CF
    digs[9]=D5
    digs[10]=D0
    digs[11]=0B
    digs[12]=11
    digs[13]=1A
    digs[14]=D4
    digs[15]=FF
    digs[16]=F9
    digs[17]=FF
    digs[18]=AC
    digs[19]=26
    digs[20]=0A
    digs[21]=D8
    digs[22]=BD
    digs[23]=10
    digs[24]=4B
    digs[25]=6F
    digs[26]=01
    digs[27]=00
    digs[28]=13
    digs[29]=02
    digs[30]=00
    digs[31]=1E
    digT1 = toUnsigned(digs[0], digs[1]) 6D4E
    digT2 = toSigned(digs[2], digs[3]) 672D
    digT3 = toSigned(digs[4], digs[5]); 0032
    digP1 = toUnsigned(digs[6], digs[7]); 9480
    digP2 = toSigned(digs[8], digs[9]); D5CF
    digP3 = toSigned(digs[10], digs[11]); 0BD0
    digP4 = toSigned(digs[12], digs[13]); 1A11
    digP5 = toSigned(digs[14], digs[15]); FFD4
    digP6 = toSigned(digs[16], digs[17]); FFF9
    digP7 = toSigned(digs[18], digs[19]); 26AC
    digP8 = toSigned(digs[20], digs[21]); D80A
    digP9 = toSigned(digs[22], digs[23]); 10BD
    digH1 = toUnsigned(digs[24]); 4B
    digH2 = toSigned(digs[25], digs[26]); 016F
    digH3 = toUnsigned(digs[27]); 00
    digH4 = toDigH4(digs[29], digs[28]); 0132
    digH5 = toDigH5(digs[29], digs[30]); 0000
    digH6 = toSigned(digs[31]); 1E
    * */
    private static TrimmingParameters trimmingParameters;
    private static byte digsReadings[];

    @BeforeAll
    static void setup() {
        trimmingParameters = new TrimmingParameters();
        byte[] digs = trimmingParameters.getDigs();
        digs[0]=(byte)0x4E;
        digs[1]=(byte)0x6D;
        digs[2]=(byte)0x2D;
        digs[3]=(byte)0x67;
        digs[4]=(byte)0x32;
        digs[5]=(byte)0x00;
        digs[6]=(byte)0x80;
        digs[7]=(byte)0x94;
        digs[8]=(byte)0xCF;
        digs[9]=(byte)0xD5;
        digs[10]=(byte)0xD0;
        digs[11]=(byte)0x0B;
        digs[12]=(byte)0x11;
        digs[13]=(byte)0x1A;
        digs[14]=(byte)0xD4;
        digs[15]=(byte)0xFF;
        digs[16]=(byte)0xF9;
        digs[17]=(byte)0xFF;
        digs[18]=(byte)0xAC;
        digs[19]=(byte)0x26;
        digs[20]=(byte)0x0A;
        digs[21]=(byte)0xD8;
        digs[22]=(byte)0xBD;
        digs[23]=(byte)0x10;
        digs[24]=(byte)0x4B;
        digs[25]=(byte)0x6F;
        digs[26]=(byte)0x01;
        digs[27]=(byte)0x00;
        digs[28]=(byte)0x13;
        digs[29]=(byte)0x02;
        digs[30]=(byte)0x00;
        digs[31]=(byte)0x1E;
        trimmingParameters.init();
    }

    @Test
    void toDigH4() {
        TrimmingParameters tp = new TrimmingParameters();
        byte[] digs = tp.getDigs();
        // -78 FFB2 1111 1111 1011 0010
        digs[28] = (byte)0b1111_1011;
        digs[29] = (byte)0b1111_0010;
        tp.init();
        assertEquals(-78, tp.getDigH4());
    }

    @Test
    void toDigH5() {
        TrimmingParameters tp = new TrimmingParameters();
        byte[] digs = tp.getDigs();
        // -78 FFB2 1111 1111 1011 0010
        digs[29] = (byte)0b0010_1111;
        digs[30] = (byte)0b1111_1011;
        tp.init();
        assertEquals(-78, tp.getDigH5());
    }

    @Test
    void toSignedOneParam() {
        TrimmingParameters tp = new TrimmingParameters();
        assertEquals(127, tp.toSigned((byte)0x7F));
        assertEquals(-1, tp.toSigned((byte)0xFF));
    }

    @Test
    void toUnsignedSingleOneParam() {
        TrimmingParameters tp = new TrimmingParameters();
        assertEquals(255, tp.toUnsigned((byte)0xFF));
    }

    @Test
    void toSignedTwoParams() {
        TrimmingParameters tp = new TrimmingParameters();
        assertEquals(32766, tp.toSigned((byte)0xFE, (byte)0x7F));
        assertEquals(-2, tp.toSigned((byte)0xFE, (byte)0xFF));
    }

    @Test
    void toUnignedTwoParams() {
        TrimmingParameters tp = new TrimmingParameters();
        assertEquals(65534, tp.toUnsigned((byte)0xFE, (byte)0xFF));
    }

    @Test
    void init() {
        assertEquals(27982, trimmingParameters.getDigT1());
        assertEquals(26413, trimmingParameters.getDigT2());
        assertEquals(50, trimmingParameters.getDigT3());
        assertEquals(38016, trimmingParameters.getDigP1());
        assertEquals(-10801, trimmingParameters.getDigP2());
        assertEquals(3024, trimmingParameters.getDigP3());
        assertEquals(6673, trimmingParameters.getDigP4());
        assertEquals(-44, trimmingParameters.getDigP5());
        assertEquals(-7, trimmingParameters.getDigP6());
        assertEquals(9900, trimmingParameters.getDigP7());
        assertEquals(-10230, trimmingParameters.getDigP8());
        assertEquals(4285, trimmingParameters.getDigP9());
        assertEquals(75, trimmingParameters.getDigH1());
        assertEquals(367, trimmingParameters.getDigH2());
        assertEquals(0, trimmingParameters.getDigH3());
        assertEquals(306, trimmingParameters.getDigH4());
        assertEquals(0, trimmingParameters.getDigH5());
        assertEquals(30, trimmingParameters.getDigH6());
    }
}
