/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.colorbox;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Number with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author your_name_here
 */
public class Step13NumberTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * How many integer-type values in color-boxes are between 0 and 54? <br>
     * (カラーボックの中に入っているInteger型で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_IntegerOnly() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        long numberCount = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof Integer)
                .mapToInt(content -> (Integer) content)
                .filter(intContent -> {
                    log("====number======");
                    log(intContent);
                    log("================");
                    return intContent >= 0 && intContent <= 54;
                })
                .count();

        log("=====answer=====");
        log(numberCount);
        log("================");
    }

    /**
     * How many number values in color-boxes are between 0 and 54? <br>
     * (カラーボックの中に入っている数値で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_Number() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        long numberCount = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .mapToInt(content -> {
                    try {
                        int intContent = Integer.parseInt(String.valueOf(content));
                        log("====number======");
                        log(intContent);
                        log("================");
                        return intContent;
                    } catch (Exception e) {
//                        log("====number======");
//                        log(String.valueOf(content));
//                        log("================");
                        return -1;
                    }
                })
                .filter(intContent -> intContent >= 0 && intContent <= 54)
                .count();

        log("=====answer=====");
        log(numberCount);
        log("================");
    }

    /**
     * What color name is used by color-box that has integer-type content and the biggest width in them? <br>
     * (カラーボックスの中で、Integer型の Content を持っていてBoxSizeの幅が一番大きいカラーボックスの色は？)
     */
    public void test_findColorBigWidthHasInteger() { // TODO jotaro.yuza やり直し・できていない (2019-06-13)
//        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
////        for (ColorBox colorBox : colorBoxList) {
////            log(colorBox.getSize());
////        }
//        List<Integer> bigestBoxSizeColor = colorBoxList.stream()
//                .filter(colorBox -> colorBox.getSpaceList().stream()
//                                    .anyMatch(boxSpace -> boxSpace.getContent() instanceof Integer))
//                .mapToInt(colorBox -> {
////                    String boxColor = colorBox.getColor().getColorName();
//                    log(colorBox.getSize().getWidth());
//                    log(colorBox.getColor().getColorName());
//                    return colorBox.getSize().getWidth();
//                })
////                .max()
//                .collect(Collectors.toList());
////        for (Integer s : bigestBoxSizeColor) {
////            log(s);
////        }
//        log(bigestBoxSizeColor.get());
    }

    /**
     * What is total of BigDecimal values in List in color-boxes? <br>
     * (カラーボックスの中に入ってる List の中の BigDecimal を全て足し合わせると？)
     */
    public void test_sumBigDecimalInList() {
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What key is related to value that is max number in Map that has only number in color-boxes? <br>
     * (カラーボックスに入ってる、valueが数値のみの Map の中で一番大きいvalueのkeyは？)
     */
    public void test_findMaxMapNumberValue() {
    }

    /**
     * What is total of number or number-character values in Map in purple color-box? <br> 
     * (purpleのカラーボックスに入ってる Map の中のvalueの数値・数字の合計は？)
     */
    public void test_sumMapNumberValue() {
    }
}
