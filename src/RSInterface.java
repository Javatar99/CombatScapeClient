// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class RSInterface {

    public static StreamLoader getaClass44() {
        return aClass44;
    }

    public static void setaClass44(StreamLoader aClass44) {
        RSInterface.aClass44 = aClass44;
    }

    public static RSInterface[] getInterfaceCache() {
        return interfaceCache;
    }

    public static void setInterfaceCache(RSInterface[] interfaceCache) {
        RSInterface.interfaceCache = interfaceCache;
    }

    public static MRUNodes getaMRUNodes_238() {
        return aMRUNodes_238;
    }

    public static void setaMRUNodes_238(MRUNodes aMRUNodes_238) {
        RSInterface.aMRUNodes_238 = aMRUNodes_238;
    }

    public static MRUNodes getaMRUNodes_264() {
        return aMRUNodes_264;
    }

    public void swapInventoryItems(int i, int j) {
        int k = getInv()[i];
        getInv()[i] = getInv()[j];
        getInv()[j] = k;
        k = getInvStackSizes()[i];
        getInvStackSizes()[i] = getInvStackSizes()[j];
        getInvStackSizes()[j] = k;
    }

    public static void unpack(StreamLoader streamLoader, TextDrawingArea textDrawingAreas[], StreamLoader streamLoader_1) {
        setaMRUNodes_238(new MRUNodes(50000));
        Stream stream = new Stream(streamLoader.getDataForName("data"));
        int i = -1;
        int j = stream.readUnsignedWord();
        setInterfaceCache(new RSInterface[31000]);
        while (stream.currentOffset < stream.buffer.length) {
            int k = stream.readUnsignedWord();
            if (k == 65535) {
                i = stream.readUnsignedWord();
                k = stream.readUnsignedWord();
            }
            RSInterface rsInterface = getInterfaceCache()[k] = new RSInterface();
            rsInterface.setId(k);
            rsInterface.setParentID(i);
            rsInterface.setType(stream.readUnsignedByte());
            rsInterface.setAtActionType(stream.readUnsignedByte());
            rsInterface.setContentType(stream.readUnsignedWord());
            rsInterface.setWidth(stream.readUnsignedWord());
            rsInterface.setHeight(stream.readUnsignedWord());
            rsInterface.setaByte254((byte) stream.readUnsignedByte());
            rsInterface.setmOverInterToTrigger(stream.readUnsignedByte());
            if (rsInterface.getmOverInterToTrigger() != 0)
                rsInterface.setmOverInterToTrigger((rsInterface.getmOverInterToTrigger() - 1 << 8) + stream.readUnsignedByte());
            else
                rsInterface.setmOverInterToTrigger(-1);
            int i1 = stream.readUnsignedByte();
            if (i1 > 0) {
                rsInterface.setAnIntArray245(new int[i1]);
                rsInterface.setAnIntArray212(new int[i1]);
                for (int j1 = 0; j1 < i1; j1++) {
                    rsInterface.getAnIntArray245()[j1] = stream.readUnsignedByte();
                    rsInterface.getAnIntArray212()[j1] = stream.readUnsignedWord();
                }

            }
            int k1 = stream.readUnsignedByte();
            if (k1 > 0) {
                rsInterface.setValueIndexArray(new int[k1][]);
                for (int l1 = 0; l1 < k1; l1++) {
                    int i3 = stream.readUnsignedWord();
                    rsInterface.getValueIndexArray()[l1] = new int[i3];
                    for (int l4 = 0; l4 < i3; l4++)
                        rsInterface.getValueIndexArray()[l1][l4] = stream.readUnsignedWord();

                }

            }
            if (rsInterface.getType() == 0) {
                rsInterface.setDrawsTransparent(false);
                rsInterface.setScrollMax(stream.readUnsignedWord());
                rsInterface.setMouseoverTriggered(stream.readUnsignedByte() == 1);
                int i2 = stream.readUnsignedWord();
                rsInterface.setChildren(new int[i2]);
                rsInterface.setChildX(new int[i2]);
                rsInterface.setChildY(new int[i2]);
                for (int j3 = 0; j3 < i2; j3++) {
                    rsInterface.getChildren()[j3] = stream.readUnsignedWord();
                    rsInterface.getChildX()[j3] = stream.readSignedWord();
                    rsInterface.getChildY()[j3] = stream.readSignedWord();
                }
            }
            if (rsInterface.getType() == 1) {
                stream.readUnsignedWord();
                stream.readUnsignedByte();
            }
            if (rsInterface.getType() == 2) {
                rsInterface.setInv(new int[rsInterface.getWidth() * rsInterface.getHeight()]);
                rsInterface.setInvStackSizes(new int[rsInterface.getWidth() * rsInterface.getHeight()]);
                rsInterface.setaBoolean259(stream.readUnsignedByte() == 1);
                rsInterface.setInventoryInterface(stream.readUnsignedByte() == 1);
                rsInterface.setUsableItemInterface(stream.readUnsignedByte() == 1);
                rsInterface.setaBoolean235(stream.readUnsignedByte() == 1);
                rsInterface.setInvSpritePadX(stream.readUnsignedByte());
                rsInterface.setInvSpritePadY(stream.readUnsignedByte());
                rsInterface.setSpritesX(new int[20]);
                rsInterface.setSpritesY(new int[20]);
                rsInterface.setSprites(new Sprite[20]);
                for (int j2 = 0; j2 < 20; j2++) {
                    int k3 = stream.readUnsignedByte();
                    if (k3 == 1) {
                        rsInterface.getSpritesX()[j2] = stream.readSignedWord();
                        rsInterface.getSpritesY()[j2] = stream.readSignedWord();
                        String s1 = stream.readString();
                        if (streamLoader_1 != null && s1.length() > 0) {
                            int i5 = s1.lastIndexOf(",");
                            rsInterface.getSprites()[j2] = requestSprite(Integer.parseInt(s1.substring(i5 + 1)), streamLoader_1, s1.substring(0, i5));
                        }
                    }
                }
                rsInterface.setActions(new String[5]);
                for (int l3 = 0; l3 < 5; l3++) {
                    rsInterface.getActions()[l3] = stream.readString();
                    if (rsInterface.getActions()[l3].length() == 0)
                        rsInterface.getActions()[l3] = null;
                    if (rsInterface.getParentID() == 1644)
                        rsInterface.getActions()[2] = "Operate";
                }
            }
            if (rsInterface.getType() == 3)
                rsInterface.setaBoolean227(stream.readUnsignedByte() == 1);
            if (rsInterface.getType() == 4 || rsInterface.getType() == 1) {
                rsInterface.setCenterText(stream.readUnsignedByte() == 1);
                int k2 = stream.readUnsignedByte();
                if (textDrawingAreas != null)
                    rsInterface.setTextDrawingAreas(textDrawingAreas[k2]);
                rsInterface.setTextShadow(stream.readUnsignedByte() == 1);
            }
            if (rsInterface.getType() == 4) {
                rsInterface.setMessage(stream.readString());
                rsInterface.setaString228(stream.readString());
            }
            if (rsInterface.getType() == 1 || rsInterface.getType() == 3 || rsInterface.getType() == 4)
                rsInterface.setTextColor(stream.readDWord());
            if (rsInterface.getType() == 3 || rsInterface.getType() == 4) {
                rsInterface.setAnInt219(stream.readDWord());
                rsInterface.setAnInt216(stream.readDWord());
                rsInterface.setAnInt239(stream.readDWord());
            }
            if (rsInterface.getType() == 5) {
                rsInterface.setDrawsTransparent(false);
                String s = stream.readString();
                if (streamLoader_1 != null && s.length() > 0) {
                    int i4 = s.lastIndexOf(",");
                    rsInterface.setSprite1(requestSprite(Integer.parseInt(s.substring(i4 + 1)), streamLoader_1, s.substring(0, i4)));
                }
                s = stream.readString();
                if (streamLoader_1 != null && s.length() > 0) {
                    int j4 = s.lastIndexOf(",");
                    rsInterface.setSprite2(requestSprite(Integer.parseInt(s.substring(j4 + 1)), streamLoader_1, s.substring(0, j4)));
                }
            }
            if (rsInterface.getType() == 6) {
                int l = stream.readUnsignedByte();
                if (l != 0) {
                    rsInterface.setAnInt233(1);
                    rsInterface.setMediaID((l - 1 << 8) + stream.readUnsignedByte());
                }
                l = stream.readUnsignedByte();
                if (l != 0) {
                    rsInterface.setAnInt255(1);
                    rsInterface.setAnInt256((l - 1 << 8) + stream.readUnsignedByte());
                }
                l = stream.readUnsignedByte();
                if (l != 0)
                    rsInterface.setAnInt257((l - 1 << 8) + stream.readUnsignedByte());
                else
                    rsInterface.setAnInt257(-1);
                l = stream.readUnsignedByte();
                if (l != 0)
                    rsInterface.setAnInt258((l - 1 << 8) + stream.readUnsignedByte());
                else
                    rsInterface.setAnInt258(-1);
                rsInterface.setModelZoom(stream.readUnsignedWord());
                rsInterface.setModelRotation1(stream.readUnsignedWord());
                rsInterface.setModelRotation2(stream.readUnsignedWord());
            }
            if (rsInterface.getType() == 7) {
                rsInterface.setInv(new int[rsInterface.getWidth() * rsInterface.getHeight()]);
                rsInterface.setInvStackSizes(new int[rsInterface.getWidth() * rsInterface.getHeight()]);
                rsInterface.setCenterText(stream.readUnsignedByte() == 1);
                int l2 = stream.readUnsignedByte();
                if (textDrawingAreas != null)
                    rsInterface.setTextDrawingAreas(textDrawingAreas[l2]);
                rsInterface.setTextShadow(stream.readUnsignedByte() == 1);
                rsInterface.setTextColor(stream.readDWord());
                rsInterface.setInvSpritePadX(stream.readSignedWord());
                rsInterface.setInvSpritePadY(stream.readSignedWord());
                rsInterface.setInventoryInterface(stream.readUnsignedByte() == 1);
                rsInterface.setActions(new String[5]);
                for (int k4 = 0; k4 < 5; k4++) {
                    rsInterface.getActions()[k4] = stream.readString();
                    if (rsInterface.getActions()[k4].length() == 0)
                        rsInterface.getActions()[k4] = null;
                }

            }
            if (rsInterface.getAtActionType() == 2 || rsInterface.getType() == 2) {
                rsInterface.setSelectedActionName(stream.readString());
                rsInterface.setSpellName(stream.readString());
                rsInterface.setSpellUsableOn(stream.readUnsignedWord());
            }

            if (rsInterface.getType() == 8)
                rsInterface.setMessage(stream.readString());

            if (rsInterface.getAtActionType() == 1 || rsInterface.getAtActionType() == 4 || rsInterface.getAtActionType() == 5 || rsInterface.getAtActionType() == 6) {
                rsInterface.setTooltip(stream.readString());
                if (rsInterface.getTooltip().length() == 0) {
                    if (rsInterface.getAtActionType() == 1)
                        rsInterface.setTooltip("Ok");
                    if (rsInterface.getAtActionType() == 4)
                        rsInterface.setTooltip("Select");
                    if (rsInterface.getAtActionType() == 5)
                        rsInterface.setTooltip("Select");
                    if (rsInterface.getAtActionType() == 6)
                        rsInterface.setTooltip("Continue");
                }
            }
        }
        setaClass44(streamLoader);
        setaMRUNodes_238(null);
    }

    public static void addButton(int id, int sid, String spriteName, String tooltip) {
        RSInterface tab = getInterfaceCache()[id] = new RSInterface();
        tab.setId(id);
        tab.setParentID(id);
        tab.setType(5);
        tab.setAtActionType(1);
        tab.setContentType(0);
        tab.setaByte254((byte) 0);
        tab.setmOverInterToTrigger(52);
        tab.setSprite1(imageLoader(sid, spriteName));
        tab.setSprite2(imageLoader(sid, spriteName));
        tab.setWidth(tab.getSprite1().myWidth);
        tab.setHeight(tab.getSprite2().myHeight);
        tab.setTooltip(tooltip);
    }

    private String popupString;

    public static void addTooltipBox(int id, String text) {
        RSInterface rsi = addInterface(id);
        rsi.setId(id);
        rsi.setParentID(id);
        rsi.setType(8);
        rsi.setPopupString(text);
    }

    public static void addTooltip(int id, String text) {
        RSInterface rsi = addInterface(id);
        rsi.setId(id);
        rsi.setType(0);
        rsi.setMouseoverTriggered(true);
        rsi.setmOverInterToTrigger(-1);
        addTooltipBox(id + 1, text);
        rsi.totalChildren(1);
        rsi.child(0, id + 1, 0, 0);
    }

    public static void addPrayer(int i, int configId, int configFrame, int anIntArray212, int spriteID, String prayerName) {
        RSInterface tab = addTabInterface(i);
        tab.setId(i);
        tab.setParentID(5608);
        tab.setType(5);
        tab.setAtActionType(4);
        tab.setContentType(0);
        tab.setaByte254((byte) 0);
        tab.setmOverInterToTrigger(-1);
        tab.setSprite1(imageLoader(0, "PRAYERGLOW"));
        tab.setSprite2(imageLoader(1, "PRAYERGLOW"));
        tab.setWidth(34);
        tab.setHeight(34);
        tab.setAnIntArray245(new int[1]);
        tab.setAnIntArray212(new int[1]);
        tab.getAnIntArray245()[0] = 1;
        tab.getAnIntArray212()[0] = configId;
        tab.setValueIndexArray(new int[1][3]);
        tab.getValueIndexArray()[0][0] = 5;
        tab.getValueIndexArray()[0][1] = configFrame;
        tab.getValueIndexArray()[0][2] = 0;
        tab.setTooltip("Activate@or2@ " + prayerName);
        //tab.tooltip = "Select";
        RSInterface tab2 = addTabInterface(i + 1);
        tab2.setId(i + 1);
        tab2.setParentID(5608);
        tab2.setType(5);
        tab2.setAtActionType(0);
        tab2.setContentType(0);
        tab2.setaByte254((byte) 0);
        tab2.setmOverInterToTrigger(-1);
        tab2.setSprite1(imageLoader(spriteID, "/PRAYER/PRAYON"));
        tab2.setSprite2(imageLoader(spriteID, "/PRAYER/PRAYOFF"));
        tab2.setWidth(34);
        tab2.setHeight(34);
        tab2.setAnIntArray245(new int[1]);
        tab2.setAnIntArray212(new int[1]);
        tab2.getAnIntArray245()[0] = 2;
        tab2.getAnIntArray212()[0] = anIntArray212 + 1;
        tab2.setValueIndexArray(new int[1][3]);
        tab2.getValueIndexArray()[0][0] = 2;
        tab2.getValueIndexArray()[0][1] = 5;
        tab2.getValueIndexArray()[0][2] = 0;
        //RSInterface tab3 = addTabInterface(i + 50);
    }

    private static Sprite CustomSpriteLoader(int id, String s) {
        long l = (TextClass.method585(s) << 8) + (long) id;
        Sprite sprite = (Sprite) getaMRUNodes_238().insertFromCache(l);
        if (sprite != null) {
            return sprite;
        }
        try {
            sprite = new Sprite("/Attack/" + id + s);
            getaMRUNodes_238().removeFromCache(sprite, l);
        } catch (Exception exception) {
            return null;
        }
        return sprite;
    }

    public static RSInterface addInterface(int id) {
        RSInterface rsi = getInterfaceCache()[id] = new RSInterface();
        rsi.setId(id);
        rsi.setParentID(id);
        rsi.setWidth(512);
        rsi.setHeight(334);
        return rsi;
    }

    public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean centered) {
        RSInterface rsi = getInterfaceCache()[id] = new RSInterface();
        if (centered)
            rsi.setCenterText(true);
        rsi.setTextShadow(true);
        rsi.setTextDrawingAreas(tda[idx]);
        rsi.setMessage(text);
        rsi.setTextColor(color);
        rsi.setId(id);
        rsi.setType(4);
    }

    public static void textColor(int id, int color) {
        RSInterface rsi = getInterfaceCache()[id];
        rsi.setTextColor(color);
    }

    public static void textSize(int id, TextDrawingArea tda[], int idx) {
        RSInterface rsi = getInterfaceCache()[id];
        rsi.setTextDrawingAreas(tda[idx]);
    }

    public static void addCacheSprite(int id, int sprite1, int sprite2, String sprites) {
        RSInterface rsi = getInterfaceCache()[id] = new RSInterface();
        rsi.setSprite1(requestSprite(sprite1, getaClass44(), sprites));
        rsi.setSprite2(requestSprite(sprite2, getaClass44(), sprites));
        rsi.setParentID(id);
        rsi.setId(id);
        rsi.setType(5);
    }

    public static void sprite1(int id, int sprite) {
        RSInterface class9 = getInterfaceCache()[id];
        class9.setSprite1(CustomSpriteLoader(sprite, ""));
    }

    public static void addActionButton(int id, int sprite, int sprite2, int width, int height, String s) {
        RSInterface rsi = getInterfaceCache()[id] = new RSInterface();
        rsi.setSprite1(CustomSpriteLoader(sprite, ""));
        if (sprite2 == sprite)
            rsi.setSprite2(CustomSpriteLoader(sprite, "a"));
        else
            rsi.setSprite2(CustomSpriteLoader(sprite2, ""));
        rsi.setTooltip(s);
        rsi.setContentType(0);
        rsi.setAtActionType(1);
        rsi.setWidth(width);
        rsi.setmOverInterToTrigger(52);
        rsi.setParentID(id);
        rsi.setId(id);
        rsi.setType(5);
        rsi.setHeight(height);
    }

    public static void addToggleButton(int id, int sprite, int setconfig, int width, int height, String s) {
        RSInterface rsi = addInterface(id);
        rsi.setSprite1(CustomSpriteLoader(sprite, ""));
        rsi.setSprite2(CustomSpriteLoader(sprite, "a"));
        rsi.setAnIntArray212(new int[1]);
        rsi.getAnIntArray212()[0] = 1;
        rsi.setAnIntArray245(new int[1]);
        rsi.getAnIntArray245()[0] = 1;
        rsi.setValueIndexArray(new int[1][3]);
        rsi.getValueIndexArray()[0][0] = 5;
        rsi.getValueIndexArray()[0][1] = setconfig;
        rsi.getValueIndexArray()[0][2] = 0;
        rsi.setAtActionType(4);
        rsi.setWidth(width);
        rsi.setmOverInterToTrigger(-1);
        rsi.setParentID(id);
        rsi.setId(id);
        rsi.setType(5);
        rsi.setHeight(height);
        rsi.setTooltip(s);
    }

    public void totalChildren(int id, int x, int y) {
        setChildren(new int[id]);
        setChildX(new int[x]);
        setChildY(new int[y]);
    }

    public static void removeSomething(int id) {
        RSInterface rsi = getInterfaceCache()[id] = new RSInterface();
    }

    public void specialBar(int id) //7599
    {
        /*addActionButton(ID, SpriteOFF, SpriteON, Width, Height, "SpriteText");*/
        addActionButton(id - 12, 7587, -1, 150, 26, "Use @gre@Special Attack");
        /*removeSomething(ID);*/
        for (int i = id - 11; i < id; i++)
            removeSomething(i);

        RSInterface rsi = getInterfaceCache()[id - 12];
        rsi.setWidth(150);
        rsi.setHeight(26);

        rsi = getInterfaceCache()[id];
        rsi.setWidth(150);
        rsi.setHeight(26);

        rsi.child(0, id - 12, 0, 0);

        rsi.child(12, id + 1, 3, 7);

        rsi.child(23, id + 12, 16, 8);

        for (int i = 13; i < 23; i++) {
            rsi.getChildY()[i] -= 1;
        }

        rsi = getInterfaceCache()[id + 1];
        rsi.setType(5);
        rsi.setSprite1(CustomSpriteLoader(7600, ""));

        for (int i = id + 2; i < id + 12; i++) {
            rsi = getInterfaceCache()[i];
            rsi.setType(5);
        }

        sprite1(id + 2, 7601);
        sprite1(id + 3, 7602);
        sprite1(id + 4, 7603);
        sprite1(id + 5, 7604);
        sprite1(id + 6, 7605);
        sprite1(id + 7, 7606);
        sprite1(id + 8, 7607);
        sprite1(id + 9, 7608);
        sprite1(id + 10, 7609);
        sprite1(id + 11, 7610);
    }


    public static void addText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA, int j) {
        RSInterface RSInterface = addInterface(i);
        RSInterface.setParentID(i);
        RSInterface.setId(i);
        RSInterface.setType(4);
        RSInterface.setAtActionType(0);
        RSInterface.setWidth(0);
        RSInterface.setHeight(0);
        RSInterface.setContentType(0);
        RSInterface.setaByte254((byte) 0);
        RSInterface.setmOverInterToTrigger(a);
        RSInterface.setCenterText(l);
        RSInterface.setTextShadow(m);
        RSInterface.setTextDrawingAreas(TDA[j]);
        RSInterface.setMessage(s);
        RSInterface.setaString228("");
        RSInterface.setTextColor(k);
    }

    private String hoverText;

    public static void addHoverBox(int id, String text) {
        RSInterface rsi = getInterfaceCache()[id];//addTabInterface(id);
        rsi.setId(id);
        rsi.setParentID(id);
        rsi.setMouseoverTriggered(true);
        rsi.setType(8);
        rsi.setHoverText(text);
    }

    public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center, boolean shadow) {
        RSInterface tab = addTabInterface(id);
        tab.setParentID(id);
        tab.setId(id);
        tab.setType(4);
        tab.setAtActionType(0);
        tab.setWidth(0);
        tab.setHeight(11);
        tab.setContentType(0);
        tab.setaByte254((byte) 0);
        tab.setmOverInterToTrigger(-1);
        tab.setCenterText(center);
        tab.setTextShadow(shadow);
        tab.setTextDrawingAreas(tda[idx]);
        tab.setMessage(text);
        tab.setaString228("");
        tab.setTextColor(color);
        tab.setAnInt219(0);
        tab.setAnInt216(0);
        tab.setAnInt239(0);
    }

    public static void addButton(int id, int sid, String spriteName, String tooltip, int w, int h) {
        RSInterface tab = getInterfaceCache()[id] = new RSInterface();
        tab.setId(id);
        tab.setParentID(id);
        tab.setType(5);
        tab.setAtActionType(1);
        tab.setContentType(0);
        tab.setaByte254((byte) 0);
        tab.setmOverInterToTrigger(52);
        tab.setSprite1(imageLoader(sid, spriteName));
        tab.setSprite2(imageLoader(sid, spriteName));
        tab.setWidth(w);
        tab.setHeight(h);
        tab.setTooltip(tooltip);
    }

    public static void addConfigButton(int ID, int pID, int bID, int bID2, String bName, int width, int height, String tT, int configID, int aT, int configFrame) {
        RSInterface Tab = addTabInterface(ID);
        Tab.setParentID(pID);
        Tab.setId(ID);
        Tab.setType(5);
        Tab.setAtActionType(aT);
        Tab.setContentType(0);
        Tab.setWidth(width);
        Tab.setHeight(height);
        Tab.setaByte254((byte) 0);
        Tab.setmOverInterToTrigger(-1);
        Tab.setAnIntArray245(new int[1]);
        Tab.setAnIntArray212(new int[1]);
        Tab.getAnIntArray245()[0] = 1;
        Tab.getAnIntArray212()[0] = configID;
        Tab.setValueIndexArray(new int[1][3]);
        Tab.getValueIndexArray()[0][0] = 5;
        Tab.getValueIndexArray()[0][1] = configFrame;
        Tab.getValueIndexArray()[0][2] = 0;
        Tab.setSprite1(imageLoader(bID, bName));
        Tab.setSprite2(imageLoader(bID2, bName));
        Tab.setTooltip(tT);
    }

    public static void addSprite(int id, int spriteId, String spriteName) {
        RSInterface tab = getInterfaceCache()[id] = new RSInterface();
        tab.setId(id);
        tab.setParentID(id);
        tab.setType(5);
        tab.setAtActionType(0);
        tab.setContentType(0);
        tab.setaByte254((byte) 0);
        tab.setmOverInterToTrigger(52);
        tab.setSprite1(imageLoader(spriteId, spriteName));
        tab.setSprite2(imageLoader(spriteId, spriteName));
        tab.setWidth(512);
        tab.setHeight(334);
    }

    public static void addHoverButton(int i, String imageName, int j, int width, int height, String text, int contentType, int hoverOver, int aT) {//hoverable button
        RSInterface tab = addTabInterface(i);
        tab.setId(i);
        tab.setParentID(i);
        tab.setType(5);
        tab.setAtActionType(aT);
        tab.setContentType(contentType);
        tab.setaByte254((byte) 0);
        tab.setmOverInterToTrigger(hoverOver);
        tab.setSprite1(imageLoader(j, imageName));
        tab.setSprite2(imageLoader(j, imageName));
        tab.setWidth(width);
        tab.setHeight(height);
        tab.setTooltip(text);
    }

    public static void addHoveredButton(int i, String imageName, int j, int w, int h, int IMAGEID) {//hoverable button
        RSInterface tab = addTabInterface(i);
        tab.setParentID(i);
        tab.setId(i);
        tab.setType(0);
        tab.setAtActionType(0);
        tab.setWidth(w);
        tab.setHeight(h);
        tab.setMouseoverTriggered(true);
        tab.setaByte254((byte) 0);
        tab.setmOverInterToTrigger(-1);
        tab.setScrollMax(0);
        addHoverImage(IMAGEID, j, j, imageName);
        tab.totalChildren(1);
        tab.child(0, IMAGEID, 0, 0);
    }

    public static void addHoverImage(int i, int j, int k, String name) {
        RSInterface tab = addTabInterface(i);
        tab.setId(i);
        tab.setParentID(i);
        tab.setType(5);
        tab.setAtActionType(0);
        tab.setContentType(0);
        tab.setWidth(512);
        tab.setHeight(334);
        tab.setaByte254((byte) 0);
        tab.setmOverInterToTrigger(52);
        tab.setSprite1(imageLoader(j, name));
        tab.setSprite2(imageLoader(k, name));
    }

    public static void addTransparentSprite(int id, int spriteId, String spriteName) {
        RSInterface tab = getInterfaceCache()[id] = new RSInterface();
        tab.setId(id);
        tab.setParentID(id);
        tab.setType(5);
        tab.setAtActionType(0);
        tab.setContentType(0);
        tab.setaByte254((byte) 0);
        tab.setmOverInterToTrigger(52);
        tab.setSprite1(imageLoader(spriteId, spriteName));
        tab.setSprite2(imageLoader(spriteId, spriteName));
        tab.setWidth(512);
        tab.setHeight(334);
        tab.setDrawsTransparent(true);
    }

    public static RSInterface addScreenInterface(int id) {
        RSInterface tab = getInterfaceCache()[id] = new RSInterface();
        tab.setId(id);
        tab.setParentID(id);
        tab.setType(0);
        tab.setAtActionType(0);
        tab.setContentType(0);
        tab.setWidth(512);
        tab.setHeight(334);
        tab.setaByte254((byte) 0);
        tab.setmOverInterToTrigger(0);
        return tab;
    }

    public static RSInterface addTabInterface(int id) {
        RSInterface tab = getInterfaceCache()[id] = new RSInterface();
        tab.setId(id);//250
        tab.setParentID(id);//236
        tab.setType(0);//262
        tab.setAtActionType(0);//217
        tab.setContentType(0);
        tab.setWidth(512);//220
        tab.setHeight(700);//267
        tab.setaByte254((byte) 0);
        tab.setmOverInterToTrigger(-1);//Int 230
        return tab;
    }

    private static Sprite imageLoader(int i, String s) {
        long l = (TextClass.method585(s) << 8) + (long) i;
        Sprite sprite = (Sprite) getaMRUNodes_238().insertFromCache(l);
        if (sprite != null)
            return sprite;
        try {
            sprite = new Sprite(s + " " + i);
            getaMRUNodes_238().removeFromCache(sprite, l);
        } catch (Exception exception) {
            return null;
        }
        return sprite;
    }

    public void child(int id, int interID, int x, int y) {
        getChildren()[id] = interID;
        getChildX()[id] = x;
        getChildY()[id] = y;
    }

    public void totalChildren(int t) {
        setChildren(new int[t]);
        setChildX(new int[t]);
        setChildY(new int[t]);
    }

    private Model method206(int i, int j) {
        Model model = (Model) getaMRUNodes_264().insertFromCache((i << 16) + j);
        if (model != null)
            return model;
        if (i == 1)
            model = Model.method462(j);
        if (i == 2)
            model = EntityDef.forID(j).method160();
        if (i == 3)
            model = client.myPlayer.method453();
        if (i == 4)
            model = ItemDef.forID(j).method202(50);
        if (i == 5)
            model = null;
        if (model != null)
            getaMRUNodes_264().removeFromCache(model, (i << 16) + j);
        return model;
    }

    private static Sprite requestSprite(int i, StreamLoader streamLoader, String s) {
        long l = (TextClass.method585(s) << 8) + (long) i;
        Sprite sprite = (Sprite) getaMRUNodes_238().insertFromCache(l);
        if (sprite != null)
            return sprite;
        try {
            sprite = new Sprite(streamLoader, s, i);
            getaMRUNodes_238().removeFromCache(sprite, l);
        } catch (Exception _ex) {
            return null;
        }
        return sprite;
    }

    public static void method208(boolean flag, Model model) {
        int i = 0;//was parameter
        int j = 5;//was parameter
        if (flag)
            return;
        getaMRUNodes_264().unlinkAll();
        if (model != null)
            getaMRUNodes_264().removeFromCache(model, (j << 16) + i);
    }

    public Model method209(int j, int k, boolean flag) {
        Model model;
        if (flag)
            model = method206(getAnInt255(), getAnInt256());
        else
            model = method206(getAnInt233(), getMediaID());
        if (model == null)
            return null;
        if (k == -1 && j == -1 && model.anIntArray1640 == null)
            return model;
        Model model_1 = new Model(true, Class36.method532(k) & Class36.method532(j), false, model);
        if (k != -1 || j != -1)
            model_1.method469();
        if (k != -1)
            model_1.method470(k);
        if (j != -1)
            model_1.method470(j);
        model_1.method479(64, 768, -50, -10, -50, true);
        return model_1;
    }

    RSInterface() {
    }

    private static StreamLoader aClass44;
    private boolean drawsTransparent;
    private Sprite sprite1;
    private int anInt208;
    private Sprite[] sprites;
    private static RSInterface[] interfaceCache;
    private int[] anIntArray212;
    private int contentType;//anInt214
    private int[] spritesX;
    private int anInt216;
    private int atActionType;
    private String spellName;
    private int anInt219;
    private int width;
    private String tooltip;
    private String selectedActionName;
    private boolean centerText;
    private int scrollPosition;
    private String[] actions;
    private int[][] valueIndexArray;
    private boolean aBoolean227;
    private String aString228;
    private int mOverInterToTrigger;
    private int invSpritePadX;
    private int textColor;
    private int anInt233;
    private int mediaID;
    private boolean aBoolean235;
    private int parentID;
    private int spellUsableOn;
    private static MRUNodes aMRUNodes_238;
    private int anInt239;
    private int[] children;
    private int[] childX;
    private boolean usableItemInterface;
    private TextDrawingArea textDrawingAreas;
    private int invSpritePadY;
    private int[] anIntArray245;
    private int anInt246;
    private int[] spritesY;
    private String message;
    private boolean isInventoryInterface;
    private int id;
    private int[] invStackSizes;
    private int[] inv;
    private byte aByte254;
    private int anInt255;
    private int anInt256;
    private int anInt257;
    private int anInt258;
    private boolean aBoolean259;
    private Sprite sprite2;
    private int scrollMax;
    private int type;
    private int anInt263;
    private static final MRUNodes aMRUNodes_264 = new MRUNodes(30);
    private int anInt265;
    private boolean isMouseoverTriggered;
    private int height;
    private boolean textShadow;
    private int modelZoom;
    private int modelRotation1;
    private int modelRotation2;
    private int[] childY;


    public static void equipmentScreen(TextDrawingArea[] wid) {
        RSInterface Interface = RSInterface.getInterfaceCache()[1644];
        addButton(19144, 6, "Equipment/CUSTOM", 150, 40, "Show Equipment Stats", 1);
        removeSomething(19145);
        removeSomething(19146);
        removeSomething(19147);
        setBounds(19144, 21, 210, 23, Interface);
        setBounds(19145, 40, 210, 24, Interface);
        setBounds(19146, 40, 210, 25, Interface);
        setBounds(19147, 40, 210, 26, Interface);

        RSInterface tab = addTabInterface(15106);
        addSprite(15107, 7, "Equipment/CUSTOM");
        addHoverButton(15210, "Equipment/CUSTOM", 8, 21, 21, "Close", 250, 15211, 3);
        addHoveredButton(15211, "Equipment/CUSTOM", 9, 21, 21, 15212);
        addText(15111, "Equip Your Character...", wid, 2, 0xe4a146, false, true);
        addText(15112, "Attack bonus", wid, 2, 0xe4a146, false, true);
        addText(15113, "Defence bonus", wid, 2, 0xe4a146, false, true);
        addText(15114, "Other bonuses", wid, 2, 0xe4a146, false, true);
        for (int i = 1675; i <= 1684; i++) {
            textSize(i, wid, 1);
        }
        textSize(1686, wid, 1);
        textSize(1687, wid, 1);
        addChar(15125);
        tab.totalChildren(44);
        tab.child(0, 15107, 4, 20);
        tab.child(1, 15210, 476, 29);
        tab.child(2, 15211, 476, 29);
        tab.child(3, 15111, 14, 30);
        int Child = 4;
        int Y = 69;
        for (int i = 1675; i <= 1679; i++) {
            tab.child(Child, i, 20, Y);
            Child++;
            Y += 14;
        }
        tab.child(9, 1680, 20, 161);
        tab.child(10, 1681, 20, 177);
        tab.child(11, 1682, 20, 192);
        tab.child(12, 1683, 20, 207);
        tab.child(13, 1684, 20, 221);
        tab.child(14, 1686, 20, 262);
        tab.child(15, 15125, 170, 200);
        tab.child(16, 15112, 16, 55);
        tab.child(17, 1687, 20, 276);
        tab.child(18, 15113, 16, 147);
        tab.child(19, 15114, 16, 248);
        tab.child(20, 1645, 104 + 295, 149 - 52);
        tab.child(21, 1646, 399, 163);
        tab.child(22, 1647, 399, 163);
        tab.child(23, 1648, 399, 58 + 146);
        tab.child(24, 1649, 26 + 22 + 297 - 2, 110 - 44 + 118 - 13 + 5);
        tab.child(25, 1650, 321 + 22, 58 + 154);
        tab.child(26, 1651, 321 + 134, 58 + 118);
        tab.child(27, 1652, 321 + 134, 58 + 154);
        tab.child(28, 1653, 321 + 48, 58 + 81);
        tab.child(29, 1654, 321 + 107, 58 + 81);
        tab.child(30, 1655, 321 + 58, 58 + 42);
        tab.child(31, 1656, 321 + 112, 58 + 41);
        tab.child(32, 1657, 321 + 78, 58 + 4);
        tab.child(33, 1658, 321 + 37, 58 + 43);
        tab.child(34, 1659, 321 + 78, 58 + 43);
        tab.child(35, 1660, 321 + 119, 58 + 43);
        tab.child(36, 1661, 321 + 22, 58 + 82);
        tab.child(37, 1662, 321 + 78, 58 + 82);
        tab.child(38, 1663, 321 + 134, 58 + 82);
        tab.child(39, 1664, 321 + 78, 58 + 122);
        tab.child(40, 1665, 321 + 78, 58 + 162);
        tab.child(41, 1666, 321 + 22, 58 + 162);
        tab.child(42, 1667, 321 + 134, 58 + 162);
        tab.child(43, 1688, 50 + 297 - 2, 110 - 13 + 5);
        for (int i = 1675; i <= 1684; i++) {
            RSInterface rsi = getInterfaceCache()[i];
            rsi.setTextColor(0xe4a146);
            rsi.setCenterText(false);
        }
        for (int i = 1686; i <= 1687; i++) {
            RSInterface rsi = getInterfaceCache()[i];
            rsi.setTextColor(0xe4a146);
            rsi.setCenterText(false);
        }
    }

    public static void addChar(int ID) {
        RSInterface t = getInterfaceCache()[ID] = new RSInterface();
        t.setId(ID);
        t.setParentID(ID);
        t.setType(6);
        t.setAtActionType(0);
        t.setContentType(328);
        t.setWidth(136);
        t.setHeight(168);
        t.setaByte254((byte) 0);
        t.setmOverInterToTrigger(0);
        t.setModelZoom(560);
        t.setModelRotation1(150);
        t.setModelRotation2(0);
        t.setAnInt257(-1);
        t.setAnInt258(-1);
    }

    private static Sprite LoadLunarSprite(int i, String s) {
        Sprite sprite = imageLoader(i, "/Lunar/" + s);
        return sprite;
    }

    public static void addLunarSprite(int i, int j, String name) {
        RSInterface RSInterface = addInterface(i);
        RSInterface.setId(i);
        RSInterface.setParentID(i);
        RSInterface.setType(5);
        RSInterface.setAtActionType(5);
        RSInterface.setContentType(0);
        RSInterface.setaByte254((byte) 0);
        RSInterface.setmOverInterToTrigger(52);
        RSInterface.setSprite1(LoadLunarSprite(j, name));
        RSInterface.setWidth(500);
        RSInterface.setHeight(500);
        RSInterface.setTooltip("");
    }

    public static void drawRune(int i, int id, String runeName) {
        RSInterface RSInterface = addInterface(i);
        RSInterface.setType(5);
        RSInterface.setAtActionType(0);
        RSInterface.setContentType(0);
        RSInterface.setaByte254((byte) 0);
        RSInterface.setmOverInterToTrigger(52);
        RSInterface.setSprite1(LoadLunarSprite(id, "RUNE"));
        RSInterface.setWidth(500);
        RSInterface.setHeight(500);
    }

    public static void addRuneText(int ID, int runeAmount, int RuneID, TextDrawingArea[] font) {
        RSInterface rsInterface = addInterface(ID);
        rsInterface.setId(ID);
        rsInterface.setParentID(1151);
        rsInterface.setType(4);
        rsInterface.setAtActionType(0);
        rsInterface.setContentType(0);
        rsInterface.setWidth(0);
        rsInterface.setHeight(14);
        rsInterface.setaByte254((byte) 0);
        rsInterface.setmOverInterToTrigger(-1);
        rsInterface.setAnIntArray245(new int[1]);
        rsInterface.setAnIntArray212(new int[1]);
        rsInterface.getAnIntArray245()[0] = 3;
        rsInterface.getAnIntArray212()[0] = runeAmount;
        rsInterface.setValueIndexArray(new int[1][4]);
        rsInterface.getValueIndexArray()[0][0] = 4;
        rsInterface.getValueIndexArray()[0][1] = 3214;
        rsInterface.getValueIndexArray()[0][2] = RuneID;
        rsInterface.getValueIndexArray()[0][3] = 0;
        rsInterface.setCenterText(true);
        rsInterface.setTextDrawingAreas(font[0]);
        //rsInterface.textShadowed = true;
        rsInterface.setMessage("%1/" + runeAmount + "");
        rsInterface.setPopupString("");
        //rsInterface.disabledColour = 12582912;
        //rsInterface.enabledColour = 49152;
    }

    public static void homeTeleport() {
        RSInterface RSInterface = addInterface(30000);
        RSInterface.setTooltip("Cast @gre@Lunar Home Teleport");
        RSInterface.setId(30000);
        RSInterface.setParentID(30000);
        RSInterface.setType(5);
        RSInterface.setAtActionType(5);
        RSInterface.setContentType(0);
        RSInterface.setaByte254((byte) 0);
        RSInterface.setmOverInterToTrigger(30001);
        RSInterface.setSprite1(LoadLunarSprite(1, "SPRITE"));
        RSInterface.setWidth(20);
        RSInterface.setHeight(20);
        RSInterface Int = addInterface(30001);
        Int.setMouseoverTriggered(true);
        Int.totalChildren(1);
        addLunarSprite(30002, 0, "SPRITE");
        setBounds(30002, 0, 0, 0, Int);
    }

    public static void addLunar2RunesSmallBox(int ID, int r1, int r2, int ra1, int ra2, int rune1, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
        RSInterface rsInterface = addInterface(ID);
        rsInterface.setId(ID);
        rsInterface.setParentID(1151);
        rsInterface.setType(5);
        rsInterface.setAtActionType(type);
        rsInterface.setContentType(0);
        rsInterface.setmOverInterToTrigger(ID + 1);
        //rsInterface.spellUsableOn = suo;
        rsInterface.setSelectedActionName("Cast On");
        rsInterface.setWidth(20);
        rsInterface.setHeight(20);
        rsInterface.setTooltip("Cast @gre@" + name);
        rsInterface.setSpellName(name);
        rsInterface.setAnIntArray245(new int[3]);
        rsInterface.setAnIntArray212(new int[3]);
        rsInterface.getAnIntArray245()[0] = 3;
        rsInterface.getAnIntArray212()[0] = ra1;
        rsInterface.getAnIntArray245()[1] = 3;
        rsInterface.getAnIntArray212()[1] = ra2;
        rsInterface.getAnIntArray245()[2] = 3;
        rsInterface.getAnIntArray212()[2] = lvl;
        rsInterface.setValueIndexArray(new int[3][]);
        rsInterface.getValueIndexArray()[0] = new int[4];
        rsInterface.getValueIndexArray()[0][0] = 4;
        rsInterface.getValueIndexArray()[0][1] = 3214;
        rsInterface.getValueIndexArray()[0][2] = r1;
        rsInterface.getValueIndexArray()[0][3] = 0;
        rsInterface.getValueIndexArray()[1] = new int[4];
        rsInterface.getValueIndexArray()[1][0] = 4;
        rsInterface.getValueIndexArray()[1][1] = 3214;
        rsInterface.getValueIndexArray()[1][2] = r2;
        rsInterface.getValueIndexArray()[1][3] = 0;
        rsInterface.getValueIndexArray()[2] = new int[3];
        rsInterface.getValueIndexArray()[2][0] = 1;
        rsInterface.getValueIndexArray()[2][1] = 6;
        rsInterface.getValueIndexArray()[2][2] = 0;
        rsInterface.setSprite2(LoadLunarSprite(sid, "LUNARON"));
        rsInterface.setSprite1(LoadLunarSprite(sid, "LUNAROFF"));
        RSInterface INT = addInterface(ID + 1);
        INT.setMouseoverTriggered(true);
        INT.totalChildren(7);
        addLunarSprite(ID + 2, 0, "BOX");
        setBounds(ID + 2, 0, 0, 0, INT);
        addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
        setBounds(ID + 3, 90, 4, 1, INT);
        addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
        setBounds(ID + 4, 90, 19, 2, INT);
        setBounds(30016, 37, 35, 3, INT);//Rune
        setBounds(rune1, 112, 35, 4, INT);//Rune
        addRuneText(ID + 5, ra1 + 1, r1, TDA);
        setBounds(ID + 5, 50, 66, 5, INT);
        addRuneText(ID + 6, ra2 + 1, r2, TDA);
        setBounds(ID + 6, 123, 66, 6, INT);

    }

    public static void addLunar3RunesSmallBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
        RSInterface rsInterface = addInterface(ID);
        rsInterface.setId(ID);
        rsInterface.setParentID(1151);
        rsInterface.setType(5);
        rsInterface.setAtActionType(type);
        rsInterface.setContentType(0);
        rsInterface.setmOverInterToTrigger(ID + 1);
        //rsInterface.spellUsableOn = suo;
        rsInterface.setSelectedActionName("Cast on");
        rsInterface.setWidth(20);
        rsInterface.setHeight(20);
        rsInterface.setTooltip("Cast @gre@" + name);
        rsInterface.setSpellName(name);
        rsInterface.setAnIntArray245(new int[4]);
        rsInterface.setAnIntArray212(new int[4]);
        rsInterface.getAnIntArray245()[0] = 3;
        rsInterface.getAnIntArray212()[0] = ra1;
        rsInterface.getAnIntArray245()[1] = 3;
        rsInterface.getAnIntArray212()[1] = ra2;
        rsInterface.getAnIntArray245()[2] = 3;
        rsInterface.getAnIntArray212()[2] = ra3;
        rsInterface.getAnIntArray245()[3] = 3;
        rsInterface.getAnIntArray212()[3] = lvl;
        rsInterface.setValueIndexArray(new int[4][]);
        rsInterface.getValueIndexArray()[0] = new int[4];
        rsInterface.getValueIndexArray()[0][0] = 4;
        rsInterface.getValueIndexArray()[0][1] = 3214;
        rsInterface.getValueIndexArray()[0][2] = r1;
        rsInterface.getValueIndexArray()[0][3] = 0;
        rsInterface.getValueIndexArray()[1] = new int[4];
        rsInterface.getValueIndexArray()[1][0] = 4;
        rsInterface.getValueIndexArray()[1][1] = 3214;
        rsInterface.getValueIndexArray()[1][2] = r2;
        rsInterface.getValueIndexArray()[1][3] = 0;
        rsInterface.getValueIndexArray()[2] = new int[4];
        rsInterface.getValueIndexArray()[2][0] = 4;
        rsInterface.getValueIndexArray()[2][1] = 3214;
        rsInterface.getValueIndexArray()[2][2] = r3;
        rsInterface.getValueIndexArray()[2][3] = 0;
        rsInterface.getValueIndexArray()[3] = new int[3];
        rsInterface.getValueIndexArray()[3][0] = 1;
        rsInterface.getValueIndexArray()[3][1] = 6;
        rsInterface.getValueIndexArray()[3][2] = 0;
        rsInterface.setSprite2(LoadLunarSprite(sid, "LUNARON"));
        rsInterface.setSprite1(LoadLunarSprite(sid, "LUNAROFF"));
        RSInterface INT = addInterface(ID + 1);
        INT.setMouseoverTriggered(true);
        INT.totalChildren(9);
        addLunarSprite(ID + 2, 0, "BOX");
        setBounds(ID + 2, 0, 0, 0, INT);
        addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
        setBounds(ID + 3, 90, 4, 1, INT);
        addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
        setBounds(ID + 4, 90, 19, 2, INT);
        setBounds(30016, 14, 35, 3, INT);
        setBounds(rune1, 74, 35, 4, INT);
        setBounds(rune2, 130, 35, 5, INT);
        addRuneText(ID + 5, ra1 + 1, r1, TDA);
        setBounds(ID + 5, 26, 66, 6, INT);
        addRuneText(ID + 6, ra2 + 1, r2, TDA);
        setBounds(ID + 6, 87, 66, 7, INT);
        addRuneText(ID + 7, ra3 + 1, r3, TDA);
        setBounds(ID + 7, 142, 66, 8, INT);
    }

    public static void addLunar3RunesBigBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
        RSInterface rsInterface = addInterface(ID);
        rsInterface.setId(ID);
        rsInterface.setParentID(1151);
        rsInterface.setType(5);
        rsInterface.setAtActionType(type);
        rsInterface.setContentType(0);
        rsInterface.setmOverInterToTrigger(ID + 1);
        //rsInterface.spellUsableOn = suo;
        rsInterface.setSelectedActionName("Cast on");
        rsInterface.setWidth(20);
        rsInterface.setHeight(20);
        rsInterface.setTooltip("Cast @gre@" + name);
        rsInterface.setSpellName(name);
        rsInterface.setAnIntArray245(new int[4]);
        rsInterface.setAnIntArray212(new int[4]);
        rsInterface.getAnIntArray245()[0] = 3;
        rsInterface.getAnIntArray212()[0] = ra1;
        rsInterface.getAnIntArray245()[1] = 3;
        rsInterface.getAnIntArray212()[1] = ra2;
        rsInterface.getAnIntArray245()[2] = 3;
        rsInterface.getAnIntArray212()[2] = ra3;
        rsInterface.getAnIntArray245()[3] = 3;
        rsInterface.getAnIntArray212()[3] = lvl;
        rsInterface.setValueIndexArray(new int[4][]);
        rsInterface.getValueIndexArray()[0] = new int[4];
        rsInterface.getValueIndexArray()[0][0] = 4;
        rsInterface.getValueIndexArray()[0][1] = 3214;
        rsInterface.getValueIndexArray()[0][2] = r1;
        rsInterface.getValueIndexArray()[0][3] = 0;
        rsInterface.getValueIndexArray()[1] = new int[4];
        rsInterface.getValueIndexArray()[1][0] = 4;
        rsInterface.getValueIndexArray()[1][1] = 3214;
        rsInterface.getValueIndexArray()[1][2] = r2;
        rsInterface.getValueIndexArray()[1][3] = 0;
        rsInterface.getValueIndexArray()[2] = new int[4];
        rsInterface.getValueIndexArray()[2][0] = 4;
        rsInterface.getValueIndexArray()[2][1] = 3214;
        rsInterface.getValueIndexArray()[2][2] = r3;
        rsInterface.getValueIndexArray()[2][3] = 0;
        rsInterface.getValueIndexArray()[3] = new int[3];
        rsInterface.getValueIndexArray()[3][0] = 1;
        rsInterface.getValueIndexArray()[3][1] = 6;
        rsInterface.getValueIndexArray()[3][2] = 0;
        rsInterface.setSprite2(LoadLunarSprite(sid, "LUNARON"));
        rsInterface.setSprite1(LoadLunarSprite(sid, "LUNAROFF"));
        RSInterface INT = addInterface(ID + 1);
        INT.setMouseoverTriggered(true);
        INT.totalChildren(9);
        addLunarSprite(ID + 2, 1, "BOX");
        setBounds(ID + 2, 0, 0, 0, INT);
        addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
        setBounds(ID + 3, 90, 4, 1, INT);
        addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
        setBounds(ID + 4, 90, 21, 2, INT);
        setBounds(30016, 14, 48, 3, INT);
        setBounds(rune1, 74, 48, 4, INT);
        setBounds(rune2, 130, 48, 5, INT);
        addRuneText(ID + 5, ra1 + 1, r1, TDA);
        setBounds(ID + 5, 26, 79, 6, INT);
        addRuneText(ID + 6, ra2 + 1, r2, TDA);
        setBounds(ID + 6, 87, 79, 7, INT);
        addRuneText(ID + 7, ra3 + 1, r3, TDA);
        setBounds(ID + 7, 142, 79, 8, INT);
    }

    public static void addLunar3RunesLargeBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
        RSInterface rsInterface = addInterface(ID);
        rsInterface.setId(ID);
        rsInterface.setParentID(1151);
        rsInterface.setType(5);
        rsInterface.setAtActionType(type);
        rsInterface.setContentType(0);
        rsInterface.setmOverInterToTrigger(ID + 1);
        //rsInterface.spellUsableOn = suo;
        rsInterface.setSelectedActionName("Cast on");
        rsInterface.setWidth(20);
        rsInterface.setHeight(20);
        rsInterface.setTooltip("Cast @gre@" + name);
        rsInterface.setSpellName(name);
        rsInterface.setAnIntArray245(new int[4]);
        rsInterface.setAnIntArray212(new int[4]);
        rsInterface.getAnIntArray245()[0] = 3;
        rsInterface.getAnIntArray212()[0] = ra1;
        rsInterface.getAnIntArray245()[1] = 3;
        rsInterface.getAnIntArray212()[1] = ra2;
        rsInterface.getAnIntArray245()[2] = 3;
        rsInterface.getAnIntArray212()[2] = ra3;
        rsInterface.getAnIntArray245()[3] = 3;
        rsInterface.getAnIntArray212()[3] = lvl;
        rsInterface.setValueIndexArray(new int[4][]);
        rsInterface.getValueIndexArray()[0] = new int[4];
        rsInterface.getValueIndexArray()[0][0] = 4;
        rsInterface.getValueIndexArray()[0][1] = 3214;
        rsInterface.getValueIndexArray()[0][2] = r1;
        rsInterface.getValueIndexArray()[0][3] = 0;
        rsInterface.getValueIndexArray()[1] = new int[4];
        rsInterface.getValueIndexArray()[1][0] = 4;
        rsInterface.getValueIndexArray()[1][1] = 3214;
        rsInterface.getValueIndexArray()[1][2] = r2;
        rsInterface.getValueIndexArray()[1][3] = 0;
        rsInterface.getValueIndexArray()[2] = new int[4];
        rsInterface.getValueIndexArray()[2][0] = 4;
        rsInterface.getValueIndexArray()[2][1] = 3214;
        rsInterface.getValueIndexArray()[2][2] = r3;
        rsInterface.getValueIndexArray()[2][3] = 0;
        rsInterface.getValueIndexArray()[3] = new int[3];
        rsInterface.getValueIndexArray()[3][0] = 1;
        rsInterface.getValueIndexArray()[3][1] = 6;
        rsInterface.getValueIndexArray()[3][2] = 0;
        rsInterface.setSprite2(LoadLunarSprite(sid, "LUNARON"));
        rsInterface.setSprite1(LoadLunarSprite(sid, "LUNAROFF"));
        RSInterface INT = addInterface(ID + 1);
        INT.setMouseoverTriggered(true);
        INT.totalChildren(9);
        addLunarSprite(ID + 2, 2, "BOX");
        setBounds(ID + 2, 0, 0, 0, INT);
        addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
        setBounds(ID + 3, 90, 4, 1, INT);
        addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
        setBounds(ID + 4, 90, 34, 2, INT);
        setBounds(30016, 14, 61, 3, INT);
        setBounds(rune1, 74, 61, 4, INT);
        setBounds(rune2, 130, 61, 5, INT);
        addRuneText(ID + 5, ra1 + 1, r1, TDA);
        setBounds(ID + 5, 26, 92, 6, INT);
        addRuneText(ID + 6, ra2 + 1, r2, TDA);
        setBounds(ID + 6, 87, 92, 7, INT);
        addRuneText(ID + 7, ra3 + 1, r3, TDA);
        setBounds(ID + 7, 142, 92, 8, INT);
    }

    public static void configureLunar(TextDrawingArea[] TDA) {
        homeTeleport();
        drawRune(30003, 1, "Fire");
        drawRune(30004, 2, "Water");
        drawRune(30005, 3, "Air");
        drawRune(30006, 4, "Earth");
        drawRune(30007, 5, "Mind");
        drawRune(30008, 6, "Body");
        drawRune(30009, 7, "Death");
        drawRune(30010, 8, "Nature");
        drawRune(30011, 9, "Chaos");
        drawRune(30012, 10, "Law");
        drawRune(30013, 11, "Cosmic");
        drawRune(30014, 12, "Blood");
        drawRune(30015, 13, "Soul");
        drawRune(30016, 14, "Astral");
        addLunar3RunesSmallBox(30017, 9075, 554, 555, 0, 4, 3, 30003, 30004, 64, "Bake Pie", "Bake pies without a stove", TDA, 0, 16, 2);
        addLunar2RunesSmallBox(30025, 9075, 557, 0, 7, 30006, 65, "Cure Plant", "Cure disease on farming patch", TDA, 1, 4, 2);
        addLunar3RunesBigBox(30032, 9075, 564, 558, 0, 0, 0, 30013, 30007, 65, "Monster Examine", "Detect the combat statistics of a\\nmonster", TDA, 2, 2, 2);
        addLunar3RunesSmallBox(30040, 9075, 564, 556, 0, 0, 1, 30013, 30005, 66, "NPC Contact", "Speak with varied NPCs", TDA, 3, 0, 2);
        addLunar3RunesSmallBox(30048, 9075, 563, 557, 0, 0, 9, 30012, 30006, 67, "Cure Other", "Cure poisoned players", TDA, 4, 8, 2);
        addLunar3RunesSmallBox(30056, 9075, 555, 554, 0, 2, 0, 30004, 30003, 67, "Humidify", "fills certain vessels with water", TDA, 5, 0, 5);
        addLunar3RunesSmallBox(30064, 9075, 563, 557, 1, 0, 1, 30012, 30006, 68, "Moonclan Teleport", "Teleports you to moonclan island", TDA, 6, 0, 5);
        addLunar3RunesBigBox(30075, 9075, 563, 557, 1, 0, 3, 30012, 30006, 69, "Tele Group Moonclan", "Teleports players to Moonclan\\nisland", TDA, 7, 0, 5);
        addLunar3RunesSmallBox(30083, 9075, 563, 557, 1, 0, 5, 30012, 30006, 70, "Ourania Teleport", "Teleports you to ourania rune altar", TDA, 8, 0, 5);
        addLunar3RunesSmallBox(30091, 9075, 564, 563, 1, 1, 0, 30013, 30012, 70, "Cure Me", "Cures Poison", TDA, 9, 0, 5);
        addLunar2RunesSmallBox(30099, 9075, 557, 1, 1, 30006, 70, "Hunter Kit", "Get a kit of hunting gear", TDA, 10, 0, 5);
        addLunar3RunesSmallBox(30106, 9075, 563, 555, 1, 0, 0, 30012, 30004, 71, "Waterbirth Teleport", "Teleports you to Waterbirth island", TDA, 11, 0, 5);
        addLunar3RunesBigBox(30114, 9075, 563, 555, 1, 0, 4, 30012, 30004, 72, "Tele Group Waterbirth", "Teleports players to Waterbirth\\nisland", TDA, 12, 0, 5);
        addLunar3RunesSmallBox(30122, 9075, 564, 563, 1, 1, 1, 30013, 30012, 73, "Cure Group", "Cures Poison on players", TDA, 13, 0, 5);
        addLunar3RunesBigBox(30130, 9075, 564, 559, 1, 1, 4, 30013, 30008, 74, "Stat Spy", "Cast on another player to see their\\nskill levels", TDA, 14, 8, 2);
        addLunar3RunesBigBox(30138, 9075, 563, 554, 1, 1, 2, 30012, 30003, 74, "Barbarian Teleport", "Teleports you to the Barbarian\\noutpost", TDA, 15, 0, 5);
        addLunar3RunesBigBox(30146, 9075, 563, 554, 1, 1, 5, 30012, 30003, 75, "Tele Group Barbarian", "Teleports players to the Barbarian\\noutpost", TDA, 16, 0, 5);
        addLunar3RunesSmallBox(30154, 9075, 554, 556, 1, 5, 9, 30003, 30005, 76, "Superglass Make", "Make glass without a furnace", TDA, 17, 16, 2);
        addLunar3RunesSmallBox(30162, 9075, 563, 555, 1, 1, 3, 30012, 30004, 77, "Khazard Teleport", "Teleports you to Port khazard", TDA, 18, 0, 5);
        addLunar3RunesSmallBox(30170, 9075, 563, 555, 1, 1, 7, 30012, 30004, 78, "Tele Group Khazard", "Teleports players to Port khazard", TDA, 19, 0, 5);
        addLunar3RunesBigBox(30178, 9075, 564, 559, 1, 0, 4, 30013, 30008, 78, "Dream", "Take a rest and restore hitpoints 3\\n times faster", TDA, 20, 0, 5);
        addLunar3RunesSmallBox(30186, 9075, 557, 555, 1, 9, 4, 30006, 30004, 79, "String Jewellery", "String amulets without wool", TDA, 21, 0, 5);
        addLunar3RunesLargeBox(30194, 9075, 557, 555, 1, 9, 9, 30006, 30004, 80, "Stat Restore Pot\\nShare", "Share a potion with up to 4 nearby\\nplayers", TDA, 22, 0, 5);
        addLunar3RunesSmallBox(30202, 9075, 554, 555, 1, 6, 6, 30003, 30004, 81, "Magic Imbue", "Combine runes without a talisman", TDA, 23, 0, 5);
        addLunar3RunesBigBox(30210, 9075, 561, 557, 2, 1, 14, 30010, 30006, 82, "Fertile Soil", "Fertilise a farming patch with super\\ncompost", TDA, 24, 4, 2);
        addLunar3RunesBigBox(30218, 9075, 557, 555, 2, 11, 9, 30006, 30004, 83, "Boost Potion Share", "Shares a potion with up to 4 nearby\\nplayers", TDA, 25, 0, 5);
        addLunar3RunesSmallBox(30226, 9075, 563, 555, 2, 2, 9, 30012, 30004, 84, "Fishing Guild Teleport", "Teleports you to the fishing guild", TDA, 26, 0, 5);
        addLunar3RunesLargeBox(30234, 9075, 563, 555, 1, 2, 13, 30012, 30004, 85, "Tele Group Fishing\\nGuild", "Teleports players to the Fishing\\nGuild", TDA, 27, 0, 5);
        addLunar3RunesSmallBox(30242, 9075, 557, 561, 2, 14, 0, 30006, 30010, 85, "Plank Make", "Turn Logs into planks", TDA, 28, 16, 5);
        /********Cut Off Limit**********/
        addLunar3RunesSmallBox(30250, 9075, 563, 555, 2, 2, 9, 30012, 30004, 86, "Catherby Teleport", "Teleports you to Catherby", TDA, 29, 0, 5);
        addLunar3RunesSmallBox(30258, 9075, 563, 555, 2, 2, 14, 30012, 30004, 87, "Tele Group Catherby", "Teleports players to Catherby", TDA, 30, 0, 5);
        addLunar3RunesSmallBox(30266, 9075, 563, 555, 2, 2, 7, 30012, 30004, 88, "Ice Plateau Teleport", "Teleports you to Ice Plateau", TDA, 31, 0, 5);
        addLunar3RunesBigBox(30274, 9075, 563, 555, 2, 2, 15, 30012, 30004, 89, "Tele Group Ice\\n Plateau", "Teleports players to Ice Plateau", TDA, 32, 0, 5);
        addLunar3RunesBigBox(30282, 9075, 563, 561, 2, 1, 0, 30012, 30010, 90, "Energy Transfer", "Spend hitpoints and SA Energy to\\n give another player hitpoints and run energy", TDA, 33, 8, 2);
        addLunar3RunesBigBox(30290, 9075, 563, 565, 2, 2, 0, 30012, 30014, 91, "Heal Other", "Transfer up to 75% of hitpoints\\n to another player", TDA, 34, 8, 2);
        addLunar3RunesBigBox(30298, 9075, 560, 557, 2, 1, 9, 30009, 30006, 92, "Vengeance Other", "Allows another player to rebound\\ndamage to an opponent", TDA, 35, 8, 2);
        addLunar3RunesSmallBox(30306, 9075, 560, 557, 3, 1, 9, 30009, 30006, 93, "Vengeance", "Rebound damage to an opponent", TDA, 36, 0, 5);
        addLunar3RunesBigBox(30314, 9075, 565, 563, 3, 2, 5, 30014, 30012, 94, "Heal Group", "Transfer up to 75% of hitpoints to a group", TDA, 37, 0, 5);
        addLunar3RunesBigBox(30322, 9075, 564, 563, 2, 1, 0, 30013, 30012, 95, "Spellbook Swap", "Change to another spellbook for 1\\nspell cast", TDA, 38, 0, 5);
    }

    public static void constructLunar() {
        RSInterface Interface = addInterface(29999);
        Interface.totalChildren(70);
        setBounds(30000, 11, 10, 0, Interface);
        setBounds(30017, 40, 9, 1, Interface);
        setBounds(30025, 71, 12, 2, Interface);
        setBounds(30032, 103, 10, 3, Interface);
        setBounds(30040, 135, 12, 4, Interface);
        setBounds(30048, 165, 10, 5, Interface);
        setBounds(30056, 8, 38, 6, Interface);
        setBounds(30064, 39, 39, 7, Interface);
        setBounds(30075, 71, 39, 8, Interface);
        setBounds(30083, 103, 39, 9, Interface);
        setBounds(30091, 135, 39, 10, Interface);
        setBounds(30099, 165, 37, 11, Interface);
        setBounds(30106, 12, 68, 12, Interface);
        setBounds(30114, 42, 68, 13, Interface);
        setBounds(30122, 71, 68, 14, Interface);
        setBounds(30130, 103, 68, 15, Interface);
        setBounds(30138, 135, 68, 16, Interface);
        setBounds(30146, 165, 68, 17, Interface);
        setBounds(30154, 14, 97, 18, Interface);
        setBounds(30162, 42, 97, 19, Interface);
        setBounds(30170, 71, 97, 20, Interface);
        setBounds(30178, 101, 97, 21, Interface);
        setBounds(30186, 135, 98, 22, Interface);
        setBounds(30194, 168, 98, 23, Interface);
        setBounds(30202, 11, 125, 24, Interface);
        setBounds(30210, 42, 124, 25, Interface);
        setBounds(30218, 74, 125, 26, Interface);
        setBounds(30226, 103, 125, 27, Interface);
        setBounds(30234, 135, 125, 28, Interface);
        setBounds(30242, 164, 126, 29, Interface);
        setBounds(30250, 10, 155, 30, Interface);
        setBounds(30258, 42, 155, 31, Interface);
        setBounds(30266, 71, 155, 32, Interface);
        setBounds(30274, 103, 155, 33, Interface);
        setBounds(30282, 136, 155, 34, Interface);
        setBounds(30290, 165, 155, 35, Interface);
        setBounds(30298, 13, 185, 36, Interface);
        setBounds(30306, 42, 185, 37, Interface);
        setBounds(30314, 71, 184, 38, Interface);
        setBounds(30322, 104, 184, 39, Interface);
        setBounds(30001, 6, 184, 40, Interface);//hover
        setBounds(30018, 5, 176, 41, Interface);//hover
        setBounds(30026, 5, 176, 42, Interface);//hover
        setBounds(30033, 5, 163, 43, Interface);//hover
        setBounds(30041, 5, 176, 44, Interface);//hover
        setBounds(30049, 5, 176, 45, Interface);//hover
        setBounds(30057, 5, 176, 46, Interface);//hover
        setBounds(30065, 5, 176, 47, Interface);//hover
        setBounds(30076, 5, 163, 48, Interface);//hover
        setBounds(30084, 5, 176, 49, Interface);//hover
        setBounds(30092, 5, 176, 50, Interface);//hover
        setBounds(30100, 5, 176, 51, Interface);//hover
        setBounds(30107, 5, 176, 52, Interface);//hover
        setBounds(30115, 5, 163, 53, Interface);//hover
        setBounds(30123, 5, 176, 54, Interface);//hover
        setBounds(30131, 5, 163, 55, Interface);//hover
        setBounds(30139, 5, 163, 56, Interface);//hover
        setBounds(30147, 5, 163, 57, Interface);//hover
        setBounds(30155, 5, 176, 58, Interface);//hover
        setBounds(30163, 5, 176, 59, Interface);//hover
        setBounds(30171, 5, 176, 60, Interface);//hover
        setBounds(30179, 5, 163, 61, Interface);//hover
        setBounds(30187, 5, 176, 62, Interface);//hover
        setBounds(30195, 5, 149, 63, Interface);//hover
        setBounds(30203, 5, 176, 64, Interface);//hover
        setBounds(30211, 5, 163, 65, Interface);//hover
        setBounds(30219, 5, 163, 66, Interface);//hover
        setBounds(30227, 5, 176, 67, Interface);//hover
        setBounds(30235, 5, 149, 68, Interface);//hover
        setBounds(30243, 5, 176, 69, Interface);//hover
        /*
		setBounds(30251, 5, 5, 70, Interface);//hover
		setBounds(30259, 5, 5, 71, Interface);//hover
		setBounds(30267, 5, 5, 72, Interface);//hover
		setBounds(30275, 5, 5, 73, Interface);//hover	
		setBounds(30283, 5, 5, 74, Interface);//hover
		setBounds(30291, 5, 5, 75, Interface);//hover
		setBounds(30299, 5, 5, 76, Interface);//hover
		setBounds(30307, 5, 5, 77, Interface);//hover
		setBounds(30323, 5, 5, 78, Interface);//hover
		setBounds(30315, 5, 5, 79, Interface);//hover*/
    }

    public static void setBounds(int ID, int X, int Y, int frame, RSInterface RSinterface) {
        RSinterface.getChildren()[frame] = ID;
        RSinterface.getChildX()[frame] = X;
        RSinterface.getChildY()[frame] = Y;
    }

    public static void addButton(int i, int j, String name, int W, int H, String S, int AT) {
        RSInterface RSInterface = addInterface(i);
        RSInterface.setId(i);
        RSInterface.setParentID(i);
        RSInterface.setType(5);
        RSInterface.setAtActionType(AT);
        RSInterface.setContentType(0);
        RSInterface.setaByte254((byte) 0);
        RSInterface.setmOverInterToTrigger(52);
        RSInterface.setSprite1(imageLoader(j, name));
        RSInterface.setSprite2(imageLoader(j, name));
        RSInterface.setWidth(W);
        RSInterface.setHeight(H);
        RSInterface.setTooltip(S);
    }


    public String getPopupString() {
        return popupString;
    }

    public void setPopupString(String popupString) {
        this.popupString = popupString;
    }

    public String getHoverText() {
        return hoverText;
    }

    public void setHoverText(String hoverText) {
        this.hoverText = hoverText;
    }

    public boolean isDrawsTransparent() {
        return drawsTransparent;
    }

    public void setDrawsTransparent(boolean drawsTransparent) {
        this.drawsTransparent = drawsTransparent;
    }

    public Sprite getSprite1() {
        return sprite1;
    }

    public void setSprite1(Sprite sprite1) {
        this.sprite1 = sprite1;
    }

    public int getAnInt208() {
        return anInt208;
    }

    public void setAnInt208(int anInt208) {
        this.anInt208 = anInt208;
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    public void setSprites(Sprite[] sprites) {
        this.sprites = sprites;
    }

    public int[] getAnIntArray212() {
        return anIntArray212;
    }

    public void setAnIntArray212(int[] anIntArray212) {
        this.anIntArray212 = anIntArray212;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int[] getSpritesX() {
        return spritesX;
    }

    public void setSpritesX(int[] spritesX) {
        this.spritesX = spritesX;
    }

    public int getAnInt216() {
        return anInt216;
    }

    public void setAnInt216(int anInt216) {
        this.anInt216 = anInt216;
    }

    public int getAtActionType() {
        return atActionType;
    }

    public void setAtActionType(int atActionType) {
        this.atActionType = atActionType;
    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    public int getAnInt219() {
        return anInt219;
    }

    public void setAnInt219(int anInt219) {
        this.anInt219 = anInt219;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getSelectedActionName() {
        return selectedActionName;
    }

    public void setSelectedActionName(String selectedActionName) {
        this.selectedActionName = selectedActionName;
    }

    public boolean isCenterText() {
        return centerText;
    }

    public void setCenterText(boolean centerText) {
        this.centerText = centerText;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    public String[] getActions() {
        return actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    public int[][] getValueIndexArray() {
        return valueIndexArray;
    }

    public void setValueIndexArray(int[][] valueIndexArray) {
        this.valueIndexArray = valueIndexArray;
    }

    public boolean isaBoolean227() {
        return aBoolean227;
    }

    public void setaBoolean227(boolean aBoolean227) {
        this.aBoolean227 = aBoolean227;
    }

    public String getaString228() {
        return aString228;
    }

    public void setaString228(String aString228) {
        this.aString228 = aString228;
    }

    public int getmOverInterToTrigger() {
        return mOverInterToTrigger;
    }

    public void setmOverInterToTrigger(int mOverInterToTrigger) {
        this.mOverInterToTrigger = mOverInterToTrigger;
    }

    public int getInvSpritePadX() {
        return invSpritePadX;
    }

    public void setInvSpritePadX(int invSpritePadX) {
        this.invSpritePadX = invSpritePadX;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getAnInt233() {
        return anInt233;
    }

    public void setAnInt233(int anInt233) {
        this.anInt233 = anInt233;
    }

    public int getMediaID() {
        return mediaID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    public boolean isaBoolean235() {
        return aBoolean235;
    }

    public void setaBoolean235(boolean aBoolean235) {
        this.aBoolean235 = aBoolean235;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getSpellUsableOn() {
        return spellUsableOn;
    }

    public void setSpellUsableOn(int spellUsableOn) {
        this.spellUsableOn = spellUsableOn;
    }

    public int getAnInt239() {
        return anInt239;
    }

    public void setAnInt239(int anInt239) {
        this.anInt239 = anInt239;
    }

    public int[] getChildren() {
        return children;
    }

    public void setChildren(int[] children) {
        this.children = children;
    }

    public int[] getChildX() {
        return childX;
    }

    public void setChildX(int[] childX) {
        this.childX = childX;
    }

    public boolean isUsableItemInterface() {
        return usableItemInterface;
    }

    public void setUsableItemInterface(boolean usableItemInterface) {
        this.usableItemInterface = usableItemInterface;
    }

    public TextDrawingArea getTextDrawingAreas() {
        return textDrawingAreas;
    }

    public void setTextDrawingAreas(TextDrawingArea textDrawingAreas) {
        this.textDrawingAreas = textDrawingAreas;
    }

    public int getInvSpritePadY() {
        return invSpritePadY;
    }

    public void setInvSpritePadY(int invSpritePadY) {
        this.invSpritePadY = invSpritePadY;
    }

    public int[] getAnIntArray245() {
        return anIntArray245;
    }

    public void setAnIntArray245(int[] anIntArray245) {
        this.anIntArray245 = anIntArray245;
    }

    public int getAnInt246() {
        return anInt246;
    }

    public void setAnInt246(int anInt246) {
        this.anInt246 = anInt246;
    }

    public int[] getSpritesY() {
        return spritesY;
    }

    public void setSpritesY(int[] spritesY) {
        this.spritesY = spritesY;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isInventoryInterface() {
        return isInventoryInterface;
    }

    public void setInventoryInterface(boolean inventoryInterface) {
        isInventoryInterface = inventoryInterface;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getInvStackSizes() {
        return invStackSizes;
    }

    public void setInvStackSizes(int[] invStackSizes) {
        this.invStackSizes = invStackSizes;
    }

    public int[] getInv() {
        return inv;
    }

    public void setInv(int[] inv) {
        this.inv = inv;
    }

    public byte getaByte254() {
        return aByte254;
    }

    public void setaByte254(byte aByte254) {
        this.aByte254 = aByte254;
    }

    public int getAnInt255() {
        return anInt255;
    }

    public void setAnInt255(int anInt255) {
        this.anInt255 = anInt255;
    }

    public int getAnInt256() {
        return anInt256;
    }

    public void setAnInt256(int anInt256) {
        this.anInt256 = anInt256;
    }

    public int getAnInt257() {
        return anInt257;
    }

    public void setAnInt257(int anInt257) {
        this.anInt257 = anInt257;
    }

    public int getAnInt258() {
        return anInt258;
    }

    public void setAnInt258(int anInt258) {
        this.anInt258 = anInt258;
    }

    public boolean isaBoolean259() {
        return aBoolean259;
    }

    public void setaBoolean259(boolean aBoolean259) {
        this.aBoolean259 = aBoolean259;
    }

    public Sprite getSprite2() {
        return sprite2;
    }

    public void setSprite2(Sprite sprite2) {
        this.sprite2 = sprite2;
    }

    public int getScrollMax() {
        return scrollMax;
    }

    public void setScrollMax(int scrollMax) {
        this.scrollMax = scrollMax;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAnInt263() {
        return anInt263;
    }

    public void setAnInt263(int anInt263) {
        this.anInt263 = anInt263;
    }

    public int getAnInt265() {
        return anInt265;
    }

    public void setAnInt265(int anInt265) {
        this.anInt265 = anInt265;
    }

    public boolean isMouseoverTriggered() {
        return isMouseoverTriggered;
    }

    public void setMouseoverTriggered(boolean mouseoverTriggered) {
        isMouseoverTriggered = mouseoverTriggered;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isTextShadow() {
        return textShadow;
    }

    public void setTextShadow(boolean textShadow) {
        this.textShadow = textShadow;
    }

    public int getModelZoom() {
        return modelZoom;
    }

    public void setModelZoom(int modelZoom) {
        this.modelZoom = modelZoom;
    }

    public int getModelRotation1() {
        return modelRotation1;
    }

    public void setModelRotation1(int modelRotation1) {
        this.modelRotation1 = modelRotation1;
    }

    public int getModelRotation2() {
        return modelRotation2;
    }

    public void setModelRotation2(int modelRotation2) {
        this.modelRotation2 = modelRotation2;
    }

    public int[] getChildY() {
        return childY;
    }

    public void setChildY(int[] childY) {
        this.childY = childY;
    }
}
