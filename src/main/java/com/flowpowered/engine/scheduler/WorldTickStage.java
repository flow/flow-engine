/*
 * This file is part of Flow Engine, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2013 Spout LLC <http://www.spout.org/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.flowpowered.engine.scheduler;

import com.flowpowered.commons.bit.ShortBitMask;
import com.flowpowered.commons.bit.ShortBitSet;

/**
 * Represents the various tick stages.<br> <br> The exact bit fields used are subject to change
 */
public enum WorldTickStage implements ShortBitMask {
    TICKSTART(1),
    /**
     * This is the first stage of the execution of the tick
     */
    STAGE1(2),
    /**
     * This is the second and subsequent stages of the tick
     */
    STAGE2P(3),
    /**
     * This is the final stage before entering the pre-snapshot stage.<br> <br> This is for minor changes prior to the snapshot process.
     */
    DYNAMIC_BLOCKS(4),
    /**
     * This is the final stage before entering the pre-snapshot stage.<br> <br> This is for minor changes prior to the snapshot process.
     */
    GLOBAL_DYNAMIC_BLOCKS(5),
    /**
     * This is the final stage before entering the pre-snapshot stage.<br> <br> This is for minor changes prior to the snapshot process.
     */
    PHYSICS(6),
    /**
     * This is the final stage before entering the pre-snapshot stage.<br> <br> This is for minor changes prior to the snapshot process.
     */
    GLOBAL_PHYSICS(7),
    /**
     * This is the final stage before entering the pre-snapshot stage.<br> <br> This is for minor changes prior to the snapshot process.
     */
    LIGHTING(8),
    /**
     * This is the final stage before entering the pre-snapshot stage.<br> <br> This is for minor changes prior to the snapshot process.
     */
    FINALIZE(9),
    /**
     * This stage occurs before the snapshot stage.<br> <br> This is a MONITOR ONLY stage, no changes should be made during the stage.
     */
    PRESNAPSHOT(10),
    /**
     * This is the snapshot copy stage.<br> <br> All snapshots are updated to the equal to the live value.
     */
    COPY_SNAPSHOT(11);

    public final static ShortBitMask ALL_PHYSICS = allOf(PHYSICS, GLOBAL_PHYSICS);
    public final static ShortBitMask ALL_DYNAMIC = allOf(DYNAMIC_BLOCKS, GLOBAL_DYNAMIC_BLOCKS);
    public final static ShortBitMask ALL_PHYSICS_AND_DYNAMIC = allOf(ALL_PHYSICS, ALL_DYNAMIC);

    private final short mask;
    private final int order;

    private WorldTickStage(int stage) {
        this.order = stage;
        this.mask = (short) (1 << (stage - 1));
    }

    @Override
    public short getMask() {
        return mask;
    }

    public int getOrder() {
        return order;
    }

    public static int getNumStages() {
        return values().length;
    }

    public static String getStage(int num) {
        switch (num) {
            case 1:
                return "TICKSTART";
            case 1 << 1:
                return "STAGE1";
            case 1 << 2:
                return "STAGE2P";
            case 1 << 3:
                return "PHYSICS";
            case 1 << 4:
                return "GLOBAL_PHYSICS";
            case 1 << 5:
                return "DYNAMIC_BLOCKS";
            case 1 << 6:
                return "GLOBAL_DYNAMIC_BLOCKS";
            case 1 << 7:
                return "LIGHTING";
            case 1 << 8:
                return "FINALIZE";
            case 1 << 9:
                return "PRESNAPSHOT";
            case 1 << 10:
                return "SNAPSHOT";
            default:
                return null;
        }
    }

    public static String getAllStages(int num) {
        int scan = 1;
        boolean first = true;
        StringBuilder sb = new StringBuilder();

        while (scan != 0) {
            int checkNum = num & scan;
            if (checkNum != 0) {
                String stage = getStage(checkNum);
                if (stage != null) {   
                    if (first) {
                        first = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(stage);
                }
            }
            scan <<= 1;
        }
        return sb.toString();
    }

    public static ShortBitMask allOf(ShortBitMask... stages) {
        short mask = 0;
        for (ShortBitMask stage : stages) {
            mask |= stage.getMask();
        }
        return new ShortBitSet(mask);
    }

    public static ShortBitMask noneOf(ShortBitMask... stages) {
        short mask = 0;
        for (ShortBitMask stage : stages) {
            mask |= stage.getMask();
        }
        return new ShortBitSet(~mask);
    }
}
