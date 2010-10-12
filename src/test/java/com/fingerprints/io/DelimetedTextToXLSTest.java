/*
 * DelimetedTextToXLSTest.java
 *
 * Copyright (C) 2010 Fingerprints Software
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.fingerprints.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.fingerprintsoft.io.DelimetedTextToXLS;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author fuzail_s
 * 
 */
public class DelimetedTextToXLSTest {

    @Test
    public void testCSVtoXLS() {
        try {
            DelimetedTextToXLS.COMMA_SEPERATED_VALUE
                    .execute(
                            "/home/fuzail_s/workspaces/projects/fingerprints/io/src/test/resources/test.csv",
                            "/tmp/textcsv.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        File file = new File("/tmp/textcsv.xls");
        Assert.assertTrue(file.exists());

    }

    @Test
    public void testTSVtoXLS() {
        try {
            DelimetedTextToXLS.PIPE_SEPERATED_VALUE
                    .execute(
                            "/home/fuzail_s/workspaces/projects/fingerprints/io/src/test/resources/1002Q-00050778.dat",
                            "/tmp/1002Q-00050778.dat.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        File file = new File("/tmp/1002Q-00050778.dat.xls");
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testPSVtoXLS() {
        try {
            DelimetedTextToXLS.SPACE_SEPERATED_VALUE
                    .execute(
                            "/home/fuzail_s/workspaces/projects/fingerprints/io/src/test/resources/test.ssv",
                            "/tmp/textssv.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        File file = new File("/tmp/textssv.xls");
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testSSVtoXLS() {
        try {
            DelimetedTextToXLS.TAB_SEPERATED_VALUE
                    .execute(
                            "/home/fuzail_s/workspaces/projects/fingerprints/io/src/test/resources/test.tsv",
                            "/tmp/texttsv.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        File file = new File("/tmp/texttsv.xls");
        Assert.assertTrue(file.exists());
    }

}
