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

import java.awt.*;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.size.BoxSize;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, using Stream API you can. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author jotaro.yuza
 */
public class Step12StreamStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * What is color name length of first color-box? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = colorBoxList.stream()
                .findFirst()
                // consciously split as example
                .map(colorBox -> colorBox.getColor())
                .map(boxColor -> boxColor.getColorName())
                .map(colorName -> {
                    log("First color box name: " + colorName); // for visual check
                    return String.valueOf(colorName.length());
                })
                .orElse("not found"); // basically no way because of not-empty list and not-null returns
        log("length: " + answer);
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String maxStr = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .map(content -> ((String) content))
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
        log("{}: {}", maxStr, maxStr.length());
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        String maxStr = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .map(content -> ((String) content))
                .max(Comparator.comparingInt(String::length))
                .orElse(null);

        String minStr = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .map(content -> ((String) content))
                .min(Comparator.comparingInt(String::length))
                .orElse(null);

        assert maxStr != null;
        log("{}: {}", maxStr, maxStr.length());
        assert minStr != null;
        log("{}: {}", minStr, minStr.length());
        log("diff: {}", maxStr.length() - minStr.length());

        // IntSummaryStatisticsを使うともっと簡単
    }

    // has small #adjustmemts from ClassicStringTest
    //  o sort allowed in Stream
    /**
     * Which value (toString() if non-string) has second-max legnth in color-boxes? (sort allowed in Stream)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (Streamでのソートありで))
     */
    public void test_length_findSecondMax() { //TODO 見直す 2019/05/30
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        List<String> contentList = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content != null)
                .map(content -> (content.toString()))
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        log("Second max : {}", contentList.get(contentList.size() - 2));
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int sumLength = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .mapToInt(content -> (((String) content).length()))
                .sum();
        if (sumLength != 0) {
            log("Total lengths are {} characters", sumLength);
        } else {
            log("There is no string contents");
        }
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<String> maxLengthColor = colorBoxList.stream()
                .map(colorBox -> colorBox.getColor()
                        .getColorName())
                .max(Comparator.comparingInt(String::length));
        if (maxLengthColor.isPresent()) {
            log("'{}' has max length in color-boxes", maxLengthColor.get());
        } else {
            log("Not found name color");
        }
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() { // TODO: 2019-06-06 解き直す
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String searchWord = "Water";
        Optional<String> contentList = colorBoxList.stream()
                .filter(colorBox -> colorBox.getSpaceList().stream()
                                    .anyMatch(space -> space.getContent() instanceof String
                                                    && space.getContent().toString().startsWith(searchWord)))
                .map(colorBox -> colorBox.getColor().getColorName())
                .findFirst();
        contentList.ifPresentOrElse(result -> log("'{}' is color in the color-box that has string starting with 'Water'", result), () -> log("not found"));
    }

//    public void test_startsWith_findFirstWord() { // TODO: 2019-06-06 別解
//
//        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
//        Optional<String> answers = colorBoxList.stream().filter(box -> {
//            boolean startWithWater = false;
//            for (BoxSpace space : box.getSpaceList()) {
//                Object content = space.getContent();
//                if (content instanceof String && String.valueOf(content).startsWith("Water")) {
//                    startWithWater = true;
//                }
//            }
//            return startWithWater;
//        }).map(box -> box.getColor().getColorName()).findFirst();
//
//
//        answers.ifPresentOrElse(result-> log(result),()->log("not found"));
//
//
//    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String searchWord = "front";
        Optional<String> colorName = colorBoxList.stream()
                .filter(colorBox -> colorBox.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof String
                                && ((String) space.getContent()).endsWith(searchWord)))
                .map(colorBox -> colorBox.getColor().getColorName())
                .findFirst();
        colorName.ifPresentOrElse(result -> log("{} is color in the color-box that has string ending with 'front'", result), () -> log("not found"));
    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================
    /**
     * What number character is starting with first "front" of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列で、最初の "front" は何文字目から始まる？)
     */
    public void test_indexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String searchWord = "front";
        Optional<String> colorBoxContent  = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> (String)content)
                .filter(content -> content.endsWith(searchWord))
                .findFirst();
        colorBoxContent.ifPresentOrElse(result -> log("From '{}' characters", result.lastIndexOf(searchWord) + 1), () -> log("not found"));
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (カラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
//    public void test_lastIndexOf_findIndex() {
//        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
//        String targetChar = "ど";
//        Optional<String> strContent = colorBoxList.stream()
//                .flatMap(colorBox -> colorBox.getSpaceList().stream())
//                .map(boxSpace -> boxSpace.getContent())
//                .filter(content -> content instanceof String)
//                .map(content -> (String)content)
//                .map(content -> content.split(""))
//                .filter(contentChar -> contentChar.equals(targetChar))
//                .findFirst();
//    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * What character is first of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列の最初の一文字は？)
     */
    public void test_substring_findFirstChar() {
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
    public void test_showMap_flat() {
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    // has small #adjustmemts from ClassicStringTest
    //  o comment out because of too difficult to be stream?
    ///**
    // * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_flat() {
    //}
    //
    ///**
    // * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_nested() {
    //}
}
