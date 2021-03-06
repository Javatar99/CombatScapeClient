// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

import java.io.*;
import java.net.Socket;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;

public final class OnDemandFetcher extends OnDemandFetcherParent
        implements Runnable {

    /**
     * Writes the checksum list for the specified archive type and length.
     * @param type The type of archive (0 = model, 1 = anim, 2 = midi, 3 = map).
     * @param length The number of files in the archive.
     */
    public void writeChecksumList(int type) {
        String name = null;
        switch (type) {
            case 0:
                name = "model";
                break;
            case 1:
                name = "anim";
                break;
            case 2:
                name = "midi";
                break;
            case 3:
                name = "map";
                break;
        }
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(Signlink.findcachedir() + name + "_crc.dat"));
            for (int index = 0; index < clientInstance.decompressors[type + 1].getFileCount(); index++) {
                out.writeInt(getChecksum(type, index));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileWriter outFile = new FileWriter(Signlink.findcachedir() + name + "_crc.txt");
            PrintWriter out = new PrintWriter(outFile);
            for (int index = 0; index < clientInstance.decompressors[type + 1].getFileCount(); index++) {
                out.println("Type: " + type + ", id: " + index + ", version: " + getChecksum(type, index));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the version list for the specified archive type and length.
     * @param type The type of archive (0 = model, 1 = anim, 2 = midi, 3 = map).
     * @param length The number of files in the archive.
     */
    public void writeVersionList(int type) {
        String name = null;
        switch (type) {
            case 0:
                name = "model";
                break;
            case 1:
                name = "anim";
                break;
            case 2:
                name = "midi";
                break;
            case 3:
                name = "map";
                break;
        }
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(Signlink.findcachedir() + name + "_version.dat"));
            for (int index = 0; index < clientInstance.decompressors[type + 1].getFileCount(); index++) {
                out.writeShort(getVersion(type, index));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileWriter outFile = new FileWriter(Signlink.findcachedir() + name + "_version.txt");
            PrintWriter out = new PrintWriter(outFile);
            for (int index = 0; index < clientInstance.decompressors[type + 1].getFileCount(); index++) {
                out.println("Type: " + type + ", id: " + index + ", version: " + getVersion(type, index));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Grabs the checksum of a file from the cache.
     * @param type The type of file (0 = model, 1 = anim, 2 = midi, 3 = map).
     * @param id The id of the file.
     * @return
     */
    public int getChecksum(int type, int id) {
        int crc = 0;
        byte[] data = clientInstance.decompressors[type + 1].decompress(id);
        if (data != null) {
            int length = data.length - 2;
            crc32.reset();
            crc32.update(data, 0, length);
            crc = (int) crc32.getValue();
        }
        return crc;
    }

    /**
     * Grabs the version of a file from the cache.
     * @param type The type of file (0 = model, 1 = anim, 2 = midi, 3 = map).
     * @param id The id of the file.
     * @return
     */
    public int getVersion(int type, int id) {
        int version = 1;
        byte[] data = clientInstance.decompressors[type + 1].decompress(id);
        if (data != null) {
            int length = data.length - 2;
            version = ((data[length] & 0xff) << 8) + (data[length + 1] & 0xff);
        }
        return version;
    }

    private boolean crcMatches(int j, byte abyte0[]) {
        if (abyte0 == null || abyte0.length < 2)
            return false;
        int k = abyte0.length - 2;
        crc32.reset();
        crc32.update(abyte0, 0, k);
        int i1 = (int) crc32.getValue();
        return i1 == j;
    }

    private void readData() {
        try {
            int j = inputStream.available();
            if (expectedSize == 0 && j >= 6) {
                waiting = true;
                for (int k = 0; k < 6; k += inputStream.read(ioBuffer, k, 6 - k)) ;
                int l = ioBuffer[0] & 0xff;
                int j1 = ((ioBuffer[1] & 0xff) << 8) + (ioBuffer[2] & 0xff);
                int l1 = ((ioBuffer[3] & 0xff) << 8) + (ioBuffer[4] & 0xff);
                int i2 = ioBuffer[5] & 0xff;
                current = null;
                for (OnDemandData onDemandData = (OnDemandData) requested.reverseGetFirst(); onDemandData != null; onDemandData = (OnDemandData) requested.reverseGetNext()) {
                    if (onDemandData.dataType == l && onDemandData.ID == j1)
                        current = onDemandData;
                    if (current != null)
                        onDemandData.loopCycle = 0;
                }

                if (current != null) {
                    loopCycle = 0;
                    if (l1 == 0) {
                        Signlink.reporterror("Rej: " + l + "," + j1);
                        current.buffer = null;
                        if (current.incomplete)
                            synchronized (aClass19_1358) {
                                aClass19_1358.insertHead(current);
                            }
                        else
                            current.unlink();
                        current = null;
                    } else {
                        if (current.buffer == null && i2 == 0)
                            current.buffer = new byte[l1];
                        if (current.buffer == null && i2 != 0)
                            throw new IOException("missing start of file");
                    }
                }
                completedSize = i2 * 500;
                expectedSize = 500;
                if (expectedSize > l1 - i2 * 500)
                    expectedSize = l1 - i2 * 500;
            }
            if (expectedSize > 0 && j >= expectedSize) {
                waiting = true;
                byte abyte0[] = ioBuffer;
                int i1 = 0;
                if (current != null) {
                    abyte0 = current.buffer;
                    i1 = completedSize;
                }
                for (int k1 = 0; k1 < expectedSize; k1 += inputStream.read(abyte0, k1 + i1, expectedSize - k1)) ;
                if (expectedSize + completedSize >= abyte0.length && current != null) {
                    if (clientInstance.decompressors[0] != null)
                        clientInstance.decompressors[current.dataType + 1].method234(abyte0.length, abyte0, current.ID);
                    if (!current.incomplete && current.dataType == 3) {
                        current.incomplete = true;
                        current.dataType = 93;
                    }
                    if (current.incomplete)
                        synchronized (aClass19_1358) {
                            aClass19_1358.insertHead(current);
                        }
                    else
                        current.unlink();
                }
                expectedSize = 0;
            }
        } catch (IOException ioexception) {
            try {
                socket.close();
            } catch (Exception _ex) {
            }
            socket = null;
            inputStream = null;
            outputStream = null;
            expectedSize = 0;
        }
    }


    public void start(StreamLoader streamLoader, client client1) {
        String as[] = {"model_version", "anim_version", "midi_version", "map_version"};
        for (int i = 0; i < 4; i++) {
            byte abyte0[] = streamLoader.getDataForName(as[i]);
            int j = abyte0.length / 2;
            Stream stream = new Stream(abyte0);
            checksums[i] = new int[j];
            versions[i] = new byte[j];
            for (int l = 0; l < j; l++)
                checksums[i][l] = stream.readUnsignedWord();

        }

        String as1[] = {
                "model_crc", "anim_crc", "midi_crc", "map_crc"
        };
        for (int k = 0; k < 4; k++) {
            byte abyte1[] = streamLoader.getDataForName(as1[k]);
            int i1 = abyte1.length / 4;
            Stream stream_1 = new Stream(abyte1);
            checksums[k] = new int[i1];
            for (int l1 = 0; l1 < i1; l1++)
                checksums[k][l1] = stream_1.readDWord();

        }

        byte abyte2[] = streamLoader.getDataForName("model_index");
        int j1 = checksums[0].length;
        modelIndices = new byte[j1];
        for (int k1 = 0; k1 < j1; k1++)
            if (k1 < abyte2.length)
                modelIndices[k1] = abyte2[k1];
            else
                modelIndices[k1] = 0;

        loadMapIndex(streamLoader);
        Stream stream2;

        abyte2 = streamLoader.getDataForName("anim_index");
        stream2 = new Stream(abyte2);
        j1 = abyte2.length / 2;
        anIntArray1360 = new int[j1];
        for (int j2 = 0; j2 < j1; j2++)
            anIntArray1360[j2] = stream2.readUnsignedWord();

        abyte2 = streamLoader.getDataForName("midi_index");
        stream2 = new Stream(abyte2);
        j1 = abyte2.length;
        anIntArray1348 = new int[j1];
        for (int k2 = 0; k2 < j1; k2++)
            anIntArray1348[k2] = stream2.readUnsignedByte();

        clientInstance = client1;
        running = true;
        clientInstance.startRunnable(this, 2);
        writeChecksumList(0);
        writeVersionList(0);
    }

    private void loadMapIndex(StreamLoader streamLoader) {
        byte[] abyte2;
        int j1;
        abyte2 = streamLoader.getDataForName("map_index");
        Stream stream2 = new Stream(abyte2);
        j1 = abyte2.length / 7;
        regionHash = new int[j1];
        floorIds = new int[j1];
        objectIds = new int[j1];
        membersArea = new int[j1];
        for (int i2 = 0; i2 < j1; i2++) {
            regionHash[i2] = stream2.readUnsignedWord();
            floorIds[i2] = stream2.readUnsignedWord();
            objectIds[i2] = stream2.readUnsignedWord();
            membersArea[i2] = stream2.readUnsignedByte();
        }
        System.out.println("Loaded " + regionHash.length + " maps.");
    }


    public int getNodeCount() {
        synchronized (nodeSubList) {
            return nodeSubList.getNodeCount();
        }
    }

    public void disable() {
        running = false;
    }

    public void method554(boolean flag) {
        int j = regionHash.length;
        for (int k = 0; k < j; k++)
            if (flag || membersArea[k] != 0) {
                method563((byte) 2, 3, objectIds[k]);
                method563((byte) 2, 3, floorIds[k]);
            }
    }

    public int getVersionCount(int j) {
        return checksums[j].length;
    }

    private void closeRequest(OnDemandData onDemandData) {
        try {
            if (socket == null) {
                long l = System.currentTimeMillis();
                if (l - openSocketTime < 4000L)
                    return;
                openSocketTime = l;
                socket = clientInstance.openSocket(43596 + client.portOff);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                outputStream.write(15);
                for (int j = 0; j < 8; j++)
                    inputStream.read();

                loopCycle = 0;
            }
            ioBuffer[0] = (byte) onDemandData.dataType;
            ioBuffer[1] = (byte) (onDemandData.ID >> 8);
            ioBuffer[2] = (byte) onDemandData.ID;
            if (onDemandData.incomplete)
                ioBuffer[3] = 2;
            else if (!clientInstance.loggedIn)
                ioBuffer[3] = 1;
            else
                ioBuffer[3] = 0;
            outputStream.write(ioBuffer, 0, 4);
            writeLoopCycle = 0;
            anInt1349 = -10000;
            return;
        } catch (IOException ioexception) {
        }
        try {
            socket.close();
        } catch (Exception _ex) {
        }
        socket = null;
        inputStream = null;
        outputStream = null;
        expectedSize = 0;
        anInt1349++;
    }

    public int getAnimCount() {
        return anIntArray1360.length;
    }

    public void method558(int i, int j) {
        synchronized (nodeSubList) {
            for (OnDemandData onDemandData = (OnDemandData) nodeSubList.reverseGetFirst(); onDemandData != null; onDemandData = (OnDemandData) nodeSubList.reverseGetNext())
                if (onDemandData.dataType == i && onDemandData.ID == j)
                    return;

            OnDemandData onDemandData_1 = new OnDemandData();
            onDemandData_1.dataType = i;
            onDemandData_1.ID = j;
            onDemandData_1.incomplete = true;
            synchronized (aClass19_1370) {
                aClass19_1370.insertHead(onDemandData_1);
            }
            nodeSubList.insertHead(onDemandData_1);
        }
    }

    public int getModelIndex(int i) {
        return modelIndices[i] & 0xff;
    }

    public void run() {
        try {
            while (running) {
                onDemandCycle++;
                int i = 20;
                if (anInt1332 == 0 && clientInstance.decompressors[0] != null)
                    i = 50;
                try {
                    Thread.sleep(i);
                } catch (Exception _ex) {
                }
                waiting = true;
                for (int j = 0; j < 100; j++) {
                    if (!waiting)
                        break;
                    waiting = false;
                    checkReceived();
                    handleFailed();
                    if (uncompletedCount == 0 && j >= 5)
                        break;
                    method568();
                    if (inputStream != null)
                        readData();
                }

                boolean flag = false;
                for (OnDemandData onDemandData = (OnDemandData) requested.reverseGetFirst(); onDemandData != null; onDemandData = (OnDemandData) requested.reverseGetNext())
                    if (onDemandData.incomplete) {
                        flag = true;
                        onDemandData.loopCycle++;
                        if (onDemandData.loopCycle > 50) {
                            onDemandData.loopCycle = 0;
                            closeRequest(onDemandData);
                        }
                    }

                if (!flag) {
                    for (OnDemandData onDemandData_1 = (OnDemandData) requested.reverseGetFirst(); onDemandData_1 != null; onDemandData_1 = (OnDemandData) requested.reverseGetNext()) {
                        flag = true;
                        onDemandData_1.loopCycle++;
                        if (onDemandData_1.loopCycle > 50) {
                            onDemandData_1.loopCycle = 0;
                            closeRequest(onDemandData_1);
                        }
                    }

                }
                if (flag) {
                    loopCycle++;
                    if (loopCycle > 750) {
                        try {
                            socket.close();
                        } catch (Exception _ex) {
                        }
                        socket = null;
                        inputStream = null;
                        outputStream = null;
                        expectedSize = 0;
                    }
                } else {
                    loopCycle = 0;
                    statusString = "";
                }
                if (clientInstance.loggedIn && socket != null && outputStream != null && (anInt1332 > 0 || clientInstance.decompressors[0] == null)) {
                    writeLoopCycle++;
                    if (writeLoopCycle > 500) {
                        writeLoopCycle = 0;
                        ioBuffer[0] = 0;
                        ioBuffer[1] = 0;
                        ioBuffer[2] = 0;
                        ioBuffer[3] = 10;
                        try {
                            outputStream.write(ioBuffer, 0, 4);
                        } catch (IOException _ex) {
                            loopCycle = 5000;
                        }
                    }
                }
            }
        } catch (Exception exception) {
            Signlink.reporterror("od_ex " + exception.getMessage());
        }
    }

    public void requestFile(int dataID, int dataType) {
        if (clientInstance.decompressors[0] == null)
            return;
        if (versions[dataType][dataID] == 0)
            return;
        if (anInt1332 == 0)
            return;
        OnDemandData onDemandData = new OnDemandData();
        onDemandData.dataType = dataType;
        onDemandData.ID = dataID;
        onDemandData.incomplete = false;
        synchronized (aClass19_1344) {
            aClass19_1344.insertHead(onDemandData);
        }
    }

    public OnDemandData getNextNode() {
        OnDemandData onDemandData;
        synchronized (aClass19_1358) {
            onDemandData = (OnDemandData) aClass19_1358.popHead();
        }
        if (onDemandData == null)
            return null;
        synchronized (nodeSubList) {
            onDemandData.unlinkSub();
        }
        if (onDemandData.buffer == null)
            return onDemandData;
        int i = 0;
        try {
            GZIPInputStream gzipinputstream = new GZIPInputStream(new ByteArrayInputStream(onDemandData.buffer));
            do {
                if (i == gzipInputBuffer.length)
                    throw new RuntimeException("buffer overflow!");
                int k = gzipinputstream.read(gzipInputBuffer, i, gzipInputBuffer.length - i);
                if (k == -1)
                    break;
                i += k;
            } while (true);
        } catch (IOException _ex) {
            throw new RuntimeException("error unzipping");
        }
        onDemandData.buffer = new byte[i];
        System.arraycopy(gzipInputBuffer, 0, onDemandData.buffer, 0, i);
        return onDemandData;
    }

    public int getMapID(int mapType, int regionY, int regionX) {
        int regionHash = (regionX << 8) + regionY;
        int mapNigga2;
        int mapNigga3;
        for (int j1 = 0; j1 < this.regionHash.length; j1++) {
            if (this.regionHash[j1] == regionHash) {
                if (mapType == 0) {
                    mapNigga2 = floorIds[j1] > 3535 ? -1 : floorIds[j1];
                    return mapNigga2;
                } else {
                    mapNigga3 = objectIds[j1] > 3535 ? -1 : objectIds[j1];
                    return mapNigga3;
                }
            }
        }
        return -1;
    }

    public void method548(int i) {
        method558(0, i);
    }

    public void method563(byte byte0, int i, int j) {
        try {
            if (clientInstance.decompressors[0] == null)
                return;
            byte abyte0[] = clientInstance.decompressors[i + 1].decompress(j);
            if (crcMatches(checksums[i][j], abyte0))
                return;
            versions[i][j] = byte0;
            if (byte0 > anInt1332)
                anInt1332 = byte0;
            totalFiles++;
        } catch (Exception e) {
        }
    }

    public boolean isObjectMap(int i) {
        for (int k = 0; k < regionHash.length; k++)
            if (objectIds[k] == i)
                return true;
        return false;
    }

    private void handleFailed() {
        uncompletedCount = 0;
        completedCount = 0;
        for (OnDemandData onDemandData = (OnDemandData) requested.reverseGetFirst(); onDemandData != null; onDemandData = (OnDemandData) requested.reverseGetNext())
            if (onDemandData.incomplete)
                uncompletedCount++;
            else
                completedCount++;

        while (uncompletedCount < 10) {
            OnDemandData onDemandData_1 = (OnDemandData) aClass19_1368.popHead();
            if (onDemandData_1 == null)
                break;
            if (versions[onDemandData_1.dataType][onDemandData_1.ID] != 0)
                filesLoaded++;
            versions[onDemandData_1.dataType][onDemandData_1.ID] = 0;
            requested.insertHead(onDemandData_1);
            uncompletedCount++;
            closeRequest(onDemandData_1);
            waiting = true;
        }
    }

    public void method566() {
        synchronized (aClass19_1344) {
            aClass19_1344.removeAll();
        }
    }

    private void checkReceived() {
        OnDemandData onDemandData;
        synchronized (aClass19_1370) {
            onDemandData = (OnDemandData) aClass19_1370.popHead();
        }
        while (onDemandData != null) {
            waiting = true;
            byte abyte0[] = null;
            if (clientInstance.decompressors[0] != null) {
                abyte0 = clientInstance.decompressors[onDemandData.dataType + 1].decompress(onDemandData.ID);
            }
            if (!crcMatches(checksums[onDemandData.dataType][onDemandData.ID], abyte0)) {
                abyte0 = null;
            }
            synchronized (aClass19_1370) {
                if (abyte0 == null) {
                    aClass19_1368.insertHead(onDemandData);
                } else {
                    onDemandData.buffer = abyte0;
                    synchronized (aClass19_1358) {
                        aClass19_1358.insertHead(onDemandData);
                    }
                }
                onDemandData = (OnDemandData) aClass19_1370.popHead();
            }
        }
    }

    private void method568() {
        while (uncompletedCount == 0 && completedCount < 10) {
            if (anInt1332 == 0)
                break;
            OnDemandData onDemandData;
            synchronized (aClass19_1344) {
                onDemandData = (OnDemandData) aClass19_1344.popHead();
            }
            while (onDemandData != null) {
                if (versions[onDemandData.dataType][onDemandData.ID] != 0) {
                    versions[onDemandData.dataType][onDemandData.ID] = 0;
                    requested.insertHead(onDemandData);
                    closeRequest(onDemandData);
                    waiting = true;
                    if (filesLoaded < totalFiles)
                        filesLoaded++;
                    statusString = "Loading extra files - " + (filesLoaded * 100) / totalFiles + "%";
                    completedCount++;
                    if (completedCount == 10)
                        return;
                }
                synchronized (aClass19_1344) {
                    onDemandData = (OnDemandData) aClass19_1344.popHead();
                }
            }
            for (int j = 0; j < 4; j++) {
                byte abyte0[] = versions[j];
                int k = abyte0.length;
                for (int l = 0; l < k; l++)
                    if (abyte0[l] == anInt1332) {
                        abyte0[l] = 0;
                        OnDemandData onDemandData_1 = new OnDemandData();
                        onDemandData_1.dataType = j;
                        onDemandData_1.ID = l;
                        onDemandData_1.incomplete = false;
                        requested.insertHead(onDemandData_1);
                        closeRequest(onDemandData_1);
                        waiting = true;
                        if (filesLoaded < totalFiles)
                            filesLoaded++;
                        //statusString = "Downloading files - " + (filesLoaded * 100) / totalFiles + "% Compelete";
                        completedCount++;
                        if (completedCount == 10)
                            return;
                    }

            }

            anInt1332--;
        }
    }

    public boolean method569(int i) {
        return anIntArray1348[i] == 1;
    }

    public OnDemandFetcher() {
        requested = new NodeList();
        statusString = "";
        crc32 = new CRC32();
        ioBuffer = new byte[500];
        versions = new byte[4][];
        aClass19_1344 = new NodeList();
        running = true;
        waiting = false;
        aClass19_1358 = new NodeList();
        gzipInputBuffer = new byte[0x71868];
        nodeSubList = new NodeSubList();
        checksums = new int[4][];
        aClass19_1368 = new NodeList();
        aClass19_1370 = new NodeList();
    }

    private int totalFiles;
    private final NodeList requested;
    private int anInt1332;
    public String statusString;
    private int writeLoopCycle;
    private long openSocketTime;
    private int[] objectIds;
    private final CRC32 crc32;
    private final byte[] ioBuffer;
    public int onDemandCycle;
    private final byte[][] versions;
    private client clientInstance;
    private final NodeList aClass19_1344;
    private int completedSize;
    private int expectedSize;
    private int[] anIntArray1348;
    public int anInt1349;
    private int[] floorIds;
    private int filesLoaded;
    private boolean running;
    private OutputStream outputStream;
    private int[] membersArea;
    private boolean waiting;
    private final NodeList aClass19_1358;
    private final byte[] gzipInputBuffer;
    private int[] anIntArray1360;
    private final NodeSubList nodeSubList;
    private InputStream inputStream;
    private Socket socket;
    private final int[][] checksums;
    private int uncompletedCount;
    private int completedCount;
    private final NodeList aClass19_1368;
    private OnDemandData current;
    private final NodeList aClass19_1370;
    private int[] regionHash;
    private byte[] modelIndices;
    private int loopCycle;
}
