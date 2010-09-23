/*
 * DelimetedTextToExcel.java
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
package org.fingerprintsoft.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.Validate;

/**
 * @author Fuzail Sarang
 * 
 */
public class DelimetedTextToXLS {

    public static final DelimetedTextToXLS COMMA_SEPERATED_VALUE = new DelimetedTextToXLS(
            String.valueOf(","));
    public static final DelimetedTextToXLS TAB_SEPERATED_VALUE = new DelimetedTextToXLS(
            String.valueOf("\t"));
    public static final DelimetedTextToXLS PIPE_SEPERATED_VALUE = new DelimetedTextToXLS(
            String.valueOf("\\|"));
    public static final DelimetedTextToXLS SPACE_SEPERATED_VALUE = new DelimetedTextToXLS(
            String.valueOf(" "));

    private String delimeter;

    private DelimetedTextToXLS(String delimeter) {
        this.delimeter = delimeter;
    }

    public void execute(String inputFilePath, String outputFilePath)
            throws FileNotFoundException, IOException {
        Validate.notNull(inputFilePath);
        Validate.notNull(outputFilePath);
        List<List<String>> rows = read(inputFilePath);
        write(outputFilePath, rows);

    }

    /**
     * 
     * @param path
     * @return
     */
    protected List<List<String>> read(String path)
            throws FileNotFoundException, IOException {
        List<List<String>> rows = null;
        List<String> cells = null;
        String rowText = null;
        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(path);

            dataInputStream = new DataInputStream(fileInputStream);

            inputStreamReader = new InputStreamReader(dataInputStream);

            bufferedReader = new BufferedReader(inputStreamReader);

            int i = 0;
            rows = new ArrayList<List<String>>();
            while ((rowText = bufferedReader.readLine()) != null) {
                String[] cell = rowText.split(getDelimeter());
                cells = Arrays.asList(cell);
                rows.add(cells);
                i++;
            }
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();

                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return rows;
    }

    /**
     * 
     * @param path
     * @param rows
     */
    protected void write(String path, List<List<String>> rows) {
        WritableWorkbook workbook = null;
        try {
            File file = new File(path);
            String sheetName = path.substring(
                    path.lastIndexOf(File.separatorChar) + 1,
                    path.lastIndexOf("."));

            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet(sheetName, 0);

            int rowNumber = 0;
            for (List<String> cells : rows) {
                int cellNumber = 0;
                for (String cell : cells) {
                    Label label = new Label(cellNumber, rowNumber, cell);
                    sheet.addCell(label);
                    cellNumber++;
                }
                rowNumber++;
            }

            workbook.write();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RowsExceededException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public String getDelimeter() {
        return delimeter;
    }

    public void setDelimeter(String delimeter) {
        this.delimeter = delimeter;
    }

}
